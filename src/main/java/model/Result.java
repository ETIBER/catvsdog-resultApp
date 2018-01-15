package model;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParsingException;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.Objects;

public class Result {


    private final LocalDateTime createTime;
    private final Double cat;
    private final Double dog;

    public Result(double cat, double dog) {
        this.cat = cat;
        this.dog = dog;
        this.createTime = LocalDateTime.now();
    }

    public Result(String jsonString) throws JsonParsingException {
        JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
        JsonObject jsonObject = jsonReader.readObject();
        this.cat = jsonObject.getJsonNumber("cat").doubleValue();
        this.dog = jsonObject.getJsonNumber("dog").doubleValue();

        this.createTime = LocalDateTime.now();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return Objects.equals(cat, result.cat) &&
                Objects.equals(dog, result.dog);
    }

    @Override
    public int hashCode() {

        return Objects.hash(cat, dog);
    }

    @Override
    public String toString() {
        return "Result{" +
                "createTime=" + createTime +
                ", cat=" + cat +
                ", dog=" + dog +
                '}';
    }

    // GETTER
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public Double getCat() {
        return cat;
    }

    public Double getDog() {
        return dog;
    }
}
