package ru.geekbrains.java2.chat.server.chat.handler;


import ru.geekbrains.java2.chat.server.chat.MyServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {

    private static final String END_CMD = "/end";
    private static final String PRIVATE = "/w";
    private static final String AUTH_CMD = "/auth"; // "/auth login password"
    private static final String AUTH_OK_CMD = "/authok";

    private final MyServer myServer;
    private final Socket clientSocket;

    private DataInputStream in;
    private DataOutputStream out;

    private String nickname;



    public ClientHandler(MyServer myServer, Socket clientSocket) {
        this.myServer = myServer;
        this.clientSocket = clientSocket;
    }

    public void handle() throws IOException {
        in  = new DataInputStream(clientSocket.getInputStream());
        out = new DataOutputStream(clientSocket.getOutputStream());

        new Thread(() -> {
            try {
                authentication();
                readMessages();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    closeConnection();
                } catch (IOException e) {
                    System.err.println("Failed to close connection!");
                }
            }
        }).start();
    }

    private void authentication() throws IOException {
        while (true) {
            String message = in.readUTF();
            if (message.startsWith(AUTH_CMD)) {
                String[] parts = message.split(" ", 3);
                String login = parts[1];
                String password = parts[2];
                String nickname = myServer.getAuthService().getNickByLoginPass(login, password);
                if (nickname == null) {
                    out.writeUTF("Некорректные логин или пароль!");
                    continue;
                }

                if (myServer.isNickBusy(nickname)) {
                    out.writeUTF("Такой пользователь уже существует!");
                    continue;
                }

                out.writeUTF(String.format("%s %s", AUTH_OK_CMD, nickname));
                setNickname(nickname);
                myServer.broadcastMessage(String.format("Пользователь '%s' зашел в чат!", nickname), null);
                myServer.subscribe(this);
                return;
            }
        }
    }

    private void readMessages() throws IOException {
        while (true) {
            String message = in.readUTF();
            System.out.println("message: " + message);
            if (message.startsWith(END_CMD)) {
                return;
            } if(message.startsWith(PRIVATE)){
                String to = message.split(" ")[1];
                String msg = message.split(" ")[2];
                myServer.whisperMsg(this, to, msg);
            }
            else {
                myServer.broadcastMessage(nickname + ": " + message, this);
            }
        }
    }

    private void closeConnection() throws IOException {
        myServer.unsubscribe(this);
        clientSocket.close();
    }


    public void sendMessage(String message) throws IOException {
        out.writeUTF(message);
    }

    public String getNickname() {
        return nickname;
    }

    private void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);//отослать сообщение клиенту
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
