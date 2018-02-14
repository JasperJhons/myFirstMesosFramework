package com.prokop.mesosframe;

public class Framework {


    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler("My very first framework",
                new Mesos("http://localhost:5050/api/v1/scheduler"));


        Api api = new Api(scheduler);

        api.start();
        scheduler.start();
    }
}
