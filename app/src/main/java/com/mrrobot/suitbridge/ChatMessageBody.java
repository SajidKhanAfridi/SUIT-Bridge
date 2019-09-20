package com.mrrobot.suitbridge;

public class ChatMessageBody
{
    private String message;
    private String author;
    private String datetime;
    private String type;

    public ChatMessageBody(String message, String author, String datetime, String type)
    {
        this.message = message;
        this.author = author;
        this.datetime = datetime;
        this.type = type;
    }

    public ChatMessageBody()
    {
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getDatetime()
    {
        return datetime;
    }

    public void setDatetime(String datetime)
    {
        this.datetime = datetime;
    }
}
