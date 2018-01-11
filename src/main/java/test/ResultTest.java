package test;

import model.Result;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import javax.json.stream.JsonParsingException;
import java.time.LocalDateTime;

public class ResultTest {


    @Test
    public void returnAResultWithTimeSet() {
        // GIVEN
        // WHEN
        Result result= new Result("{\"cat\":1 ,\"dog\":0}");
        // THEN
        Assert.assertTrue(result.getCreateTime() instanceof LocalDateTime);
    }
    @Test
    public void returnAResultWithCatAndDogNotNull() {
        // GIVEN
        // WHEN
        Result result= new Result("{\"cat\":1 ,\"dog\":0}");
        // THEN
        Assert.assertNotNull(result.getCat());
        Assert.assertNotNull(result.getDog());
    }
    @Test
    public void returnAResultWithCatAndDogValueFromJson() {
        // GIVEN
        String jsonString = "{\"cat\":1 ,\"dog\":0}";
        // WHEN

        Result result= new Result(jsonString);
        // THEN
        Assert.assertEquals(Double.valueOf(1),result.getCat());
        Assert.assertEquals(Double.valueOf(0),result.getDog());
    }
    @Test(expected = JsonParsingException.class)
    public void returnAnExceptionWhenResultStringIsIncorrect() {
        // GIVEN
        String jsonString = "{\"cat";
        // WHEN
        Result result= new Result(jsonString);
        // THEN

    }


}