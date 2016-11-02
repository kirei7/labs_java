package ua.vntu.lab10.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MessageReceiver extends Thread {

    private ServerSocket serverSocket;

    public MessageReceiver(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        try {
            Socket fromSocket = serverSocket.accept();
            PrintWriter serverOut = new PrintWriter(fromSocket.getOutputStream(), true);
            BufferedReader serverIn = new BufferedReader(new InputStreamReader( fromSocket.getInputStream()));
            while (true) {
                String received = serverIn.readLine();
                System.out.println(received);
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
