package uk.com.v3softech.online.store;

public class _chat {
    private String messageFrom,MessageTo,message,messageTime,messageDate;

    public _chat(){

    }

    public _chat(String messageFrom, String messageTo, String message, String messageTime, String messageDate) {
        this.messageFrom = messageFrom;
        MessageTo = messageTo;
        this.message = message;
        this.messageTime = messageTime;
        this.messageDate = messageDate;
    }

    public String getMessageFrom() {
        return messageFrom;
    }

    public void setMessageFrom(String messageFrom) {
        this.messageFrom = messageFrom;
    }

    public String getMessageTo() {
        return MessageTo;
    }

    public void setMessageTo(String messageTo) {
        MessageTo = messageTo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }
}
