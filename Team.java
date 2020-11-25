package ru.geekbrains.java2.Lesson1;

public class Team {
    private String name;
    private int maxRange;
    private int maxJump;

    public Team(String name, int maxRange, int maxJump){
        this.name = name;
        this.maxRange = maxRange;
        this.maxJump = maxJump;
    }

    public String getName() {
        return name;
    }

    public int getMaxRange() {
        return maxRange;
    }

    public int getMaxJump() {
        return maxJump;
    }

    public void runTreadmill(Treadmill treadmill) {
    }

    public void jumpWall(Wall wall) {
    }
}
