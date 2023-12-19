package com.example.mean_v1;

public class Score {

    private float score;
    private float max_score;
    private float weight;

    public Score( float score, float max_score, float weight) {
        this.score = score;
        this.max_score = max_score;
        this.weight = weight;
    }


    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public float getMax_score() {
        return max_score;
    }

    public void setMax_score(float max_score) {
        this.max_score = max_score;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }


}
