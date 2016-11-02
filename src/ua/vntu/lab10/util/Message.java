package ua.vntu.lab10.util;

public class Message {
    private String name;
    private String text;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Message withName(String name){
        setName(name);
        return this;
    }
    public Message withText(String text){
        setText(text);
        return this;
    }
}
