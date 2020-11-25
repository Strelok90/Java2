package ru.geekbrains.java2.Lesson1;

public class Robot extends Team {
    public Robot(String name,int maxRange,int maxJump){
        super(name, maxRange, maxJump);
    }

    public void runTreadmill(Treadmill treadmill) {
        if (treadmill.getRange() <= getMaxRange() )
            System.out.println("Робот - " + getName() + " пробежал " + treadmill.getRange() + " метров" );
       // else System.out.println("Робот - " + getName() + " не смог пробежать " + treadmill.getRange() + " метров");
    }


    public void jumpWall(Wall wall) {
        if (wall.getHeight() <= getMaxJump())
            System.out.println("Робот - " + getName() + " смог перепрыгнуть препятствие высотой " + wall.getHeight() + " метра");
      //  else System.out.println("Робот - " + getName() + " не смог перепрыгнуть препятствие высотой  " + wall.getHeight() + " метра");
    }
}
