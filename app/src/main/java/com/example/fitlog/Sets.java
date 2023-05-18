package com.example.fitlog;

public class Sets {

    private String weight;
    private String reps;

    public Sets() {
    }

    public Sets(String weight, String reps) {
        this.weight = weight;
        this.reps = reps;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getReps() {
        return reps;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }
}
