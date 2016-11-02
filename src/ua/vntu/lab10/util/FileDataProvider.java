package ua.vntu.lab10.util;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class FileDataProvider implements DataProvider {

    private final static String ADDRESS_PATH = "/home/userok/workspace/idea/labs_java/resources/lab10/address.txt";
    private final static String NAME_PATH = "/home/userok/workspace/idea/labs_java/resources/lab10/name.txt";


    @Override
    public InetAddress getAddress() {
        InetAddress address = null;
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(ADDRESS_PATH)));
            address = InetAddress.getByName((
                    input.readLine()
                    ));
            input.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return address;
    }

    @Override
    public String getName() {
        String name = null;
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(NAME_PATH)));
            name = input.readLine();
            input.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return name;
    }
}
