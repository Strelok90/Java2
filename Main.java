package ru.geekbrains.java2.Lesson2;

public class Main {

    public static void main(String[] args) throws MyArraySizeException {

        //Object[][] arr = {{7, 2, 1, 12}, {8, 1, 2, 2, 3}}; // Массив с нерпавильным размером
        //Object[][] arr = {{7, 2, 1, 12}, {8, 1, '2', 3}}; //Массив с тип отличный от int
        Object[][] arr = { {7,2,1,12}, {8,1,2,3}}; // Правильный массив

        int sum = 0;

        for (int i = 0; i < arr.length; i++)
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i].length != 4 && arr[j].length !=4) {
                    throw new MyArraySizeException("Массив может быть размером 4х4, иначе никак.");
                }
                try {
                    sum += (int) arr[i][j];
                } catch (RuntimeException e) {
                    throw new MyArrayDataException("в ячейке [" + i + "],[" + j + "] находится тип отличный от int.");
                }
            }
        System.out.println("Сумма массива = " + sum);
    }
}
