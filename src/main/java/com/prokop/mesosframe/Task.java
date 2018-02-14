package com.prokop.mesosframe;

import com.google.gson.JsonObject;

import static java.util.UUID.randomUUID;

public class Task {

    String id = randomUUID().toString();

    String agentID;

    String dockerImage;
    int cpus;
    int memMb;

    Task(JsonObject body) {
        agentID = body.get("agentID").getAsString();
        dockerImage = body.get("dockerImage").getAsString();
        cpus = body.get("cpus").getAsInt();
        memMb = body.get("memMb").getAsInt();
    }
}
