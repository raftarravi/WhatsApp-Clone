package com.example.whatsappclone.Models;

public class MessageModel {

    String message,messageId,uId;
    Long timeStamp;

    public MessageModel(){

    }

    public MessageModel(String message, String uId, Long timeStamp) {
        this.message = message;
        this.uId = uId;
        this.timeStamp = timeStamp;
    }

    public MessageModel(String message, String uId) {
        this.message = message;
        this.uId = uId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
