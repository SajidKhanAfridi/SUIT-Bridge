package com.mrrobot.suitbridge;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TeacherJoinClass extends AppCompatActivity
{
    EditText classID;

    DatabaseReference mReference;

    //classid
    String id;

    //Variables for storing the name, id and department of the teacher
    String t_name;
    String t_id;
    String t_department;

    //Keys for shared Prefs
    String t_name_prefs_key = "teacher_name";
    String t_id_prefs_key = "teacher_id";
    String t_department_prefs_key = "teacher_department";

    //Key to store classID entered by the teacher to display it in its corresponding button;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_join_class);

        classID = findViewById(R.id.classID_edittext);

        id = classID.getText().toString();

        //Shared prefs to get the name id and department of the teacher
        SharedPreferences name_prefs = PreferenceManager.getDefaultSharedPreferences(this);
        t_name = name_prefs.getString(t_name_prefs_key, "Couldn't get the name of teacher");
        Log.d("teacherJoinClass",t_name);

        SharedPreferences id_prefs = PreferenceManager.getDefaultSharedPreferences(this);
        t_id = id_prefs.getString(t_id_prefs_key, "couldn't get the id of the teacher");
        Log.d("teacherJoinClass", t_id);

        SharedPreferences department_prefs = PreferenceManager.getDefaultSharedPreferences(this);
        t_department = department_prefs.getString(t_department_prefs_key, "couldn't get the department of the teacehr");
        Log.d("teacherJoinClass", t_department);
    }

    public void confirm(View view)
    {
        //Creating the teacher registration data model object to put it under connected teacher node, same as done in registration;

        TeacherRegistrationDataModel teacherRegistrationDataModelObject = new TeacherRegistrationDataModel(t_name,t_id,t_department);

        mReference = FirebaseDatabase.getInstance().getReference().child(id);
        mReference.child("connectedTeachers").push().setValue(teacherRegistrationDataModelObject);

        Toast.makeText(this, "Congratulation you have been added to the class", Toast.LENGTH_SHORT).show();
    }
}
