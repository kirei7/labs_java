package ua.vntu.lab10.util;

import ua.vntu.lab10.ui.MainFrame;

public class PutMessageEvent implements MessageEvent {
    private MainFrame mainFrame;

    public PutMessageEvent(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void onMessageSent(Message msg) {
        mainFrame.putMessage(msg);
    }
}
