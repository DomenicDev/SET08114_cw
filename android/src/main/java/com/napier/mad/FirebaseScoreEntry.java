package com.napier.mad;

public class FirebaseScoreEntry {

    private String name;
    private Long score;

    public FirebaseScoreEntry() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "FirebaseScoreEntry{" +
                "name='" + name + '\'' +
                ", score='" + score + '\'' +
                '}';
    }
}
