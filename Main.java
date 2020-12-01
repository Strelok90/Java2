package ru.geekbrains.java2.Lesson3;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

        String[] words = {"Май", "ЭтоВсе!", "Воскресенье", "Июль", "Среда", "Январь", "Май", "Август", "Ноябрь", "Февраль", "Октябрь",
                "Декабрь", "ЭтоВсе!", "Воскресенье", "Пятница", "Суббота", "ЭтоВсе!", "Сентябрь", "Понедельник", "Воскресенье"};
        HashMap<String, Integer> list = new HashMap<>();
        for (String x : words) {
            list.put(x, list.getOrDefault(x,0)+1);
        }
        
        System.out.println(list);

        Phonebook book = new Phonebook();
        book.addContact("Иванов", "8(911)222-33-44");
        book.addContact("Петрова", "8(911)333-44-55");
        book.addContact("Васечкин", "8(911)444-55-66");
        book.addContact("Иванов", "8(911)555-66-77");
        book.addContact("Шварц", "8(911)666-77-88");
        book.addContact("Шварц", "8(911)777-88-99");


        book.findAndPrint("Иванов");
        book.findAndPrint("Шварц");
        book.findAndPrint("Васечкин");

    }
}

