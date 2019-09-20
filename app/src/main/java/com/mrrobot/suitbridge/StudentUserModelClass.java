package com.mrrobot.suitbridge;

public class StudentUserModelClass
{
    private String name;
    private String registration;
    private String section;
    private String semester;
    private String department;
    private int photo;

    public StudentUserModelClass()
    {
    }

    public StudentUserModelClass(String department,String registration,String section,String semester, String name)
    {
        this.name = name;
        this.registration = registration;
        this.section = section;
        this.semester = semester;
        this.department = department;
    }

    //Getters and Setters

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getRegistration()
    {
        return registration;
    }

    public void setRegistration(String registration)
    {
        this.registration = registration;
    }

    public String getSection()
    {
        return section;
    }

    public void setSection(String section)
    {
        this.section = section;
    }

    public String getSemester()
    {
        return semester;
    }

    public void setSemester(String semester)
    {
        this.semester = semester;
    }

    public String getDepartment()
    {
        return department;
    }

    public void setDepartment(String department)
    {
        this.department = department;
    }
}
