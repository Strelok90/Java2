package ru.geekbrains.java2.Lesson5;

import java.util.Arrays;

public class Main {
    private static final int size = 10000000;
    private static final int n = 2;
    private static final int h = size / n;
    private static final float[] arr = new float[size];

    public static void main(String[] args) {
        Main main = new Main();
        main.methodOne();
        main.methodTwo();
    }

    private void methodOne(){
        System.out.println("Метод 1");
        Arrays.fill(arr, 1);
        long a = System.currentTimeMillis();
        for(int i = 0; i < arr.length; i++){
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        long b = System.currentTimeMillis();
        System.out.println("Время работы метода " + (b - a) + " мс");
        System.out.println();
    }

    private void methodTwo() {
        System.out.println("Метод 2");
        Thread[] threads = new Thread[n];

        Arrays.fill(arr, 1.0f);
        long a = System.currentTimeMillis();

        float[][] newArr = new float[n][h];
        for (int i = 0; i < n; i++) {
            System.arraycopy(arr, i*h, newArr[i], 0, h);
        }

        long separation = System.currentTimeMillis();
        System.out.println("Время разделения массива "+ (separation - a) + " мс");

        for(int i=0; i<n; i++) {
            int finalI = i;
            threads[i] = new Thread(() -> calcTwoArr(newArr, finalI));
            threads[i].start();
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long gluing = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            System.arraycopy(newArr[i], 0, arr, i*h, h);
        }
        long end = System.currentTimeMillis();
        System.out.println("Время склейки массива "+ (end - gluing) + " мс");
        System.out.println("Время работы метода "+ (end - a) + " мс");
    }

    private void calcTwoArr(float[][] arr, int n) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < h; i++) {
            arr[n][i] = (float) (arr[n][i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        long end = System.currentTimeMillis();
        System.out.println("Время выполнения " + (n + 1) + "-го потока "+ (end - start) + " мс");
    }
}
