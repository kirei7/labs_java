package ua.vntu.lab10.network;

import ua.vntu.lab10.util.DataProvider;
import ua.vntu.lab10.util.FileDataProvider;
import ua.vntu.lab10.util.MessageEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketEngine {

    private final static int SERVER_PORT = 4444;


    private MessageEvent event;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private Socket fromSocket;
    private DataProvider dataProvider = new FileDataProvider();

    private PrintWriter serverOut;
    private BufferedReader serverIn;

    private String myName;

    private MessageSender messageSender;
    private MessageReceiver messageReceiver;

    public SocketEngine(MessageEvent event){
        try {
            this.event = event;
            serverSocket = new ServerSocket(SERVER_PORT);
            messageReceiver = new MessageReceiver(serverSocket);
            clientSocket = new Socket(
                    dataProvider.getAddress(),
                    SERVER_PORT
            );
            messageSender = new MessageSender(clientSocket);
            myName = dataProvider.getName();
            System.out.println(myName);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void start() {
        messageSender.start();
        messageReceiver.start();
    }

}
