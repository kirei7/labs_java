package ua.vntu.lab10.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MessageSender extends Thread {

    private PrintWriter clientOut;
    private BufferedReader clientIn;
    private Socket clientSocket;

    public MessageSender(Socket clientSocket) {
        try {
            this.clientSocket = clientSocket;
            clientOut = new PrintWriter(clientSocket.getOutputStream(), true);
            clientIn = new BufferedReader(new InputStreamReader( clientSocket.getInputStream()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("Enter the string:");
        Scanner in = new Scanner(System.in);
        System.out.println(clientSocket.getPort());
    }
}
