package ua.vntu.lab10.network;

import ua.vntu.lab10.util.MessageEvent;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketEngine {

    private final static int THIS_SERVER_PORT = 4444;

    private MessageEvent event;
    private ServerSocket serverSocket;
    private Socket clientSocket;

    public SocketEngine(MessageEvent event) throws IOException{
        this.event = event;

        serverSocket = new ServerSocket(THIS_SERVER_PORT);
        clientSocket = new Socket();
    }

    public void start() {

    }
}
