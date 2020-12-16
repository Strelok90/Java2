package ru.geekbrains.java2.chat.client.models;

import javafx.application.Platform;
import ru.geekbrains.java2.chat.client.ClientChat;
import ru.geekbrains.java2.chat.client.controllers.ViewController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Network {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8189;

    private static final String AUTH_OK_CMD = "/authok";

    private String host;
    private int port;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private Socket socket;
    private ClientChat clientChat;
    private String nickname;

    public Network() {
        this(SERVER_ADDRESS, SERVER_PORT);
    }

    public Network(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Network(ClientChat clientChat) {
        this();
        this.clientChat = clientChat;
    }

    public boolean connect() {
        try {
            socket = new Socket(host, port);
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
            return true;
        } catch (IOException e) {
            System.err.println("Соединение не было установлено!");
            e.printStackTrace();
            return false;
        }
    }

    public DataInputStream getInputStream() {
        return inputStream;
    }

    public DataOutputStream getOutputStream() {
        return outputStream;
    }

    public void sendMessage(String message) throws IOException {
        getOutputStream().writeUTF(message);
    }

    public void waitMessages(ViewController viewController) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        String message = inputStream.readUTF();
                        if (clientChat.getState() == ClientChatState.AUTHENTICATION) {
                            if (message.startsWith(AUTH_OK_CMD)) {
                                String[] parts = message.split(" ", 2);
                                nickname = parts[1];
                                Platform.runLater(() -> {
                                    clientChat.activeChatDialog(nickname);
                                });
                            }
                            else {
                                Platform.runLater(() -> {
                                    ClientChat.showNetworkError(message, "Auth error", null);
                                });
                            }
                        }
                         else {
                            Platform.runLater(() -> {
                                viewController.appendMessage(message);
                            });
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Соединение было потеряно!");
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public void close() {
        try {
            if (socket != null && socket.isConnected()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
