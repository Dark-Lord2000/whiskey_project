package com.example.whiskey_project;
public class Whiskey_timer {
    private long startTime = 0;
    private long stopTime = 0;
    private boolean running = false;

    // get start time
    public void start() {
        this.startTime = System.currentTimeMillis();
        this.running = true;
    }

    // get stop time
    public void stop() {
        this.stopTime = System.currentTimeMillis();
        this.running = false;
    }

    // get elapsed time between start and end of seekbar interaction
    public long elapsedTime(){
        long elapsed;

        if(running){
            elapsed = (System.currentTimeMillis() - startTime) / 1000;
        }
        else {
            elapsed = (stopTime - startTime) / 1000;
        }

        return elapsed;
    }

}
