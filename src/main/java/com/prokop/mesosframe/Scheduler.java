package com.prokop.mesosframe;

import com.google.gson.JsonObject;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;

@Log
public class Scheduler {

    String name;
    Mesos mesos;
    String frameworkId;
    JsonObject offers;
    List<Task> tasks = new ArrayList<>();

    Scheduler(String name, Mesos mesos) {
        this.name = name;
        this.mesos = mesos;
    }

    public void start() {
        mesos.subscribe(this);
    }

    public void handle(JsonObject event) {
        if (event.has("subscribed")) {
            this.frameworkId = event.get("subscribed").getAsJsonObject()
                    .get("framework_id").getAsJsonObject()
                    .get("value").getAsString();
            log.info("My id is: " + this.frameworkId);
        } else if (event.has("offers")) {
            offers = event.get("offers").getAsJsonObject();

        }
    }

    public void launchTask(Task taskToLaunch) {
        mesos.launch(this, taskToLaunch);
        tasks.add(taskToLaunch);
    }
}
