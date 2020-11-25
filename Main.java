package ru.geekbrains.java2.Lesson1;

public class Main {
    public static void main(String[] args) {
        Team[] team = new Team[3];
        team[0] = new Cat("Стив",300,5);
        team[1] = new Human("Вася", 200,3);
        team[2] = new Robot("Terminator", 1800,12);

        Treadmill treadmill = new Treadmill(300);
        Wall wall = new Wall(2);

        playMarathon(team, treadmill, wall);
    }
    public static void playMarathon(Team[] team, Treadmill treadmill, Wall wall){
        int stage = 0;
        do {
            for (stage = 1;stage <= 3 ; stage++) {
                treadmill.setRange(treadmill.getRange()*stage);
                wall.setHeight(wall.getHeight()*stage);
                System.out.println(stage + " Этап" + "\n" + "Длина трассы - "
                        + treadmill.getRange() + " метров. " + " Высота стены - "
                        + wall.getHeight() + " метра.");
                System.out.println("------------------------------------------");
                for (int i = 0; i <= 2; i++) {
                    team[i].runTreadmill(treadmill);
                    team[i].jumpWall(wall);
                }
                System.out.println("------------------------------------------"+"\n");
            }

        } while (stage==3);
    }
}
