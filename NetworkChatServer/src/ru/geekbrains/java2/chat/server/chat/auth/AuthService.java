package ru.geekbrains.java2.chat.server.chat.auth;

public interface AuthService {

    void start();
    void stop();

    String getNickByLoginPass(String login, String password);

}
