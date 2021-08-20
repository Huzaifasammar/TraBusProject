package com.example.trabus.models;

public class ChatModel {
    String id,sendermessage,senderid;
    public ChatModel()
    {

    }
    public ChatModel (String message,String id,String senderid)
    {
        this.id=id;
        this.sendermessage=message;
        this.senderid=senderid;
    }

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
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

