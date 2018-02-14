package com.prokop.mesosframe;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import lombok.extern.java.Log;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import java.io.FileReader;
import java.io.IOException;

import static java.lang.String.format;

@Log
public class Messages {

    static String PATH_TO_MESSAGES = "./messages/";

    public static RequestBody getBodyforSubscribe(Scheduler scheduler) {
        String requestString = format(parseJsonFromFile("subscribe.json"), scheduler.name);
        return RequestBody.create(MediaType.parse("application/json"), requestString.getBytes());
    }

    private static String parseJsonFromFile(String filename) {
        String jsonString = "";
        String pathToFile = PATH_TO_MESSAGES + filename;
        try {
            JsonReader reader = new JsonReader(new FileReader(pathToFile));
            jsonString = new Gson().fromJson(reader, JsonObject.class).toString();

        } catch (IOException e) {
            log.info("Can't get json file from path: " + pathToFile);
        }
        return jsonString;
    }

    public static RequestBody getBodyforTask(Scheduler scheduler, Task taskToLaunch) {
        String requestString = format(parseJsonFromFile("task.json"), scheduler.name);
        return RequestBody.create(MediaType.parse("application/json"), requestString.getBytes());
    }
}
