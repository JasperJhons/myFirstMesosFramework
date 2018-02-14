package com.prokop.mesosframe;

import com.google.gson.JsonParser;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.BufferedReader;
import java.util.concurrent.TimeUnit;

@Log
public class Mesos {

    String apiUrl;

    OkHttpClient httpClient = new OkHttpClient.Builder()
            .readTimeout(1, TimeUnit.MINUTES)
            .build();

    String streamId;

    Mesos(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    @SneakyThrows
    public void subscribe(Scheduler scheduler) {
        RequestBody requestBody = Messages.getBodyforSubscribe(scheduler);

        Request request = new Request.Builder()
                .url(apiUrl)
                .post(requestBody)
                .build();

        Response response = httpClient.newCall(request).execute();
        streamId = response.header("Mesos-Stream-Id");

        BufferedReader stream = new BufferedReader(response.body().charStream());
        String line;
        while ((line = stream.readLine()) != null) {
            log.info("Get from Mesos Master: " + line);
            if (line.contains("{")) {
                line = line.substring(0, line.lastIndexOf("}") + 1);
                scheduler.handle(new JsonParser().parse(line).getAsJsonObject());
            }

        }
    }

    public void launch(Scheduler scheduler, Task taskToLaunch) {
    }
}
