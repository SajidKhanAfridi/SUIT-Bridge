package com.mrrobot.suitbridge;

public class StudentRegisrationDataModel

{
    String name;
    String section;
    String semester;
    String department;
    String reg;


    public StudentRegisrationDataModel(String name, String section, String semester, String department, String reg)
    {
        this.name = name;
        this.section = section;
        this.semester = semester;
        this.department = department;
        this.reg = reg;
    }

    public StudentRegisrationDataModel()
    {
    }

    public String getName()
    {
        return name;
    }

    public String getSection()
    {
        return section;
    }

    public String getSemester()
    {
        return semester;
    }

    public String getDepartment()
    {
        return department;
    }

    public String getReg()
    {
        return reg;
    }
}

