package com.mrrobot.suitbridge;

public class TimelineItem
{
    String name;
    String content;
    String date;
    String regNo;
    String type;

    public TimelineItem()
    {
    }

    public TimelineItem(String name, String content, String date, String regNo, String type)
    {
        this.name = name;
        this.content = content;
        this.date = date;
        this.regNo = regNo;
        this.type = type;
    }

    public String getRegNo()
    {
        return regNo;
    }

    public void setRegNo(String regNo)
    {
        this.regNo = regNo;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }
}
