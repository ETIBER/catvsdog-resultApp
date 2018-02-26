package Logger;

import java.io.Serializable;
import java.util.concurrent.locks.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.AppenderLoggingException;
import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.layout.PatternLayout;

import redis.clients.jedis.Jedis;

// note: class name need not match the @Plugin name.
@Plugin(name="RedisAppender", category="Core", elementType="appender", printObject=true)
final class RedisAppenderImpl extends AbstractAppender {

    private static final String REDIS_HOST = System.getenv("REDIS_HOST") != null
            ? System.getenv("REDIS_HOST") : "localhost";

    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private Jedis jedis;

    protected RedisAppenderImpl(String name, Filter filter,
                                   Layout<? extends Serializable> layout, final boolean ignoreExceptions) {
        super(name, filter, layout, ignoreExceptions);
        System.out.println("Connect to server log: "+REDIS_HOST);
        jedis = new Jedis(REDIS_HOST);
    }

    // The append method is where the appender does the work.
    // Given a log event, you are free to do with it what you want.
    // This example demonstrates:
    // 1. Concurrency: this method may be called by multiple threads concurrently
    // 2. How to use layouts
    // 3. Error handling
    @Override
    public void append(LogEvent event) {
        readLock.lock();
        try {
            jedis.rpush("log",new String(getLayout().toByteArray(event)));
        } catch (Exception ex) {
            if (!ignoreExceptions()) {
                throw new AppenderLoggingException(ex);
            }
        } finally {
            readLock.unlock();
        }
    }

    // Your custom appender needs to declare a factory method
    // annotated with `@PluginFactory`. Log4j will parse the configuration
    // and call this factory method to construct an appender instance with
    // the configured attributes.
    @PluginFactory
    public static RedisAppenderImpl createAppender(
            @PluginAttribute("name") String name,
            @PluginElement("Layout") Layout<? extends Serializable> layout,
            @PluginElement("Filter") final Filter filter,
            @PluginAttribute("otherAttribute") String otherAttribute) {
        if (name == null) {
            LOGGER.error("No name provided for MyCustomAppenderImpl");
            return null;
        }
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
        return new RedisAppenderImpl(name, filter, layout, true);
    }
}