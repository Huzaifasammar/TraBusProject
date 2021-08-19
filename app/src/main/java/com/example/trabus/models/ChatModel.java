package com.example.trabus.models;

public class ChatModel {
    String id,sendermessage;
    public ChatModel()
    {

    }
    public ChatModel (String message,String id)
    {
        this.id=id;
        this.sendermessage=message;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSendermessage() {
        return sendermessage;
    }

    public void setSendermessage(String sendermessage) {
        this.sendermessage = sendermessage;
    }

}

