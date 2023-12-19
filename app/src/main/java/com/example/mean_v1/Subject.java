package com.example.mean_v1;

public class Subject {
    private String name;
    private float mean_score;

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    private float max;

    public Subject(String name, float mean, float max) {
        this.name = name;
        this.mean_score = mean;
        this.max = max;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getMean_score() {
        return mean_score;
    }

    public void setMean_score(float mean_score) {
        this.mean_score = mean_score;
    }
}
