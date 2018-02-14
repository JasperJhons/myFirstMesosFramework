package com.prokop.mesosframe;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import static spark.Spark.get;
import static spark.Spark.post;

public class Api {


    Scheduler scheduler;

    public Api(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void start() {

        get("/tasks", (request, response) -> new Gson().toJson(scheduler.tasks));

        post("/tasks", (request, response) -> {
                    JsonObject body = new JsonParser().parse(request.body()).getAsJsonObject();
                    Task taskToLaunch = new Task(body);
                    scheduler.launchTask(taskToLaunch);
                    return new Gson().toJson(taskToLaunch);
                }

        );


    }

}


