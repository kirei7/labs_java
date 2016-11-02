package ua.vntu.lab10;

import ua.vntu.lab10.network.SocketEngine;
import ua.vntu.lab10.ui.MainFrame;
import ua.vntu.lab10.util.MessageEvent;
import ua.vntu.lab10.util.Message;
import ua.vntu.lab10.util.PutMessageEvent;

public class Application {

    private MainFrame mainFrame;
    private MessageEvent messageEvent;
    private SocketEngine socketEngine;

    public Application() {
        mainFrame = new MainFrame();
        messageEvent = new PutMessageEvent(mainFrame);
        socketEngine = new SocketEngine(messageEvent);
    }

    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }

    public void run() {
        /*mainFrame.setVisible(true);
        mainFrame.putMessage(new Message().withName("Sam").withText("Hello world!"));
        mainFrame.putMessage(new Message().withName("Dean").withText("Howdy, bro"));*/
        socketEngine.start();
    }

}
