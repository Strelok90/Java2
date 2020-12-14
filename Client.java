package lesson6.homework;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        Socket socket = null;

        try {
            socket = new Socket("localhost", 8189);
            Scanner in =  new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner sc = new Scanner(System.in);
            System.out.println("Подключение к серверу.....успешно");

            new Thread(() -> {
                while (true) {
                    String msg = in.nextLine();
                    System.out.println("Сервер: " + msg);
                }
            }).start();

            while (true) {
                String msg = sc.nextLine();
                out.println(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

