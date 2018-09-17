package ru.dgis.doublegisfloors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class GsonSerializer {
    private Gson gson;

    public GsonSerializer() {
        gson = new GsonBuilder()
                .registerTypeAdapter(Double.class, (com.google.gson.JsonSerializer<Double>) (src, typeOfSrc, context) -> {
                    if (src == src.longValue()) // preventing conversion integer values to double
                        return new JsonPrimitive(src.longValue());
                    return new JsonPrimitive(src);
                })
                .create();

    }

    public <T> T fromJson(String json, Class<T> classOfT) {
        if(json == null) {
            return null;
        }
        return gson.fromJson(json, classOfT);
    }

    public <T> T fromJson(JsonArray payload, Type typeOfT) {
        return gson.fromJson(payload, typeOfT);
    }

    public <T> T fromJson(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }

    public String toJson(Object value) {
        if(value == null) {
            return null;
        }
        return gson.toJson(value);
    }

    public List<String> parseList(String jsonArray) {
        return fromJson(jsonArray, new TypeToken<List<String>>() {}.getType());
    }
}