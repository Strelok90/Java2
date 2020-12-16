package ru.geekbrains.java2.chat.server;


import ru.geekbrains.java2.chat.server.chat.MyServer;

import java.io.IOException;

public class ServerApp {

    private static final int DEFAULT_PORT = 8189;

    public static void main(String[] args) {
        int port = DEFAULT_PORT;
        if (args.length != 0) {
            port = Integer.parseInt(args[0]);
        }
        try {
            new MyServer().start(port);
        } catch (IOException e) {
            System.err.println("Failed to create MyServer");
            e.printStackTrace();
        }
    }

}
