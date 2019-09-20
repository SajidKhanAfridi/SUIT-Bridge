package com.mrrobot.suitbridge;

public class TeacherRegistrationDataModel
{
    String name;
    String ID;
    String department;

    public TeacherRegistrationDataModel(String name, String ID, String department)
    {
        this.name = name;
        this.ID = ID;
        this.department = department;
    }

    public TeacherRegistrationDataModel()
    {
    }

    public String getName()
    {
        return name;
    }

    public String getID()
    {
        return ID;
    }

    public String getDepartment()
    {
        return department;
    }
}
