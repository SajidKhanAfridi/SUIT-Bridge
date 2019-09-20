package com.mrrobot.suitbridge;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TeacherOptions extends AppCompatActivity
{
    //Views
    RecyclerView mRecyclerView;
    RecyclerView mRecyclerView2;

    //Keys for shared Prefs
    String t_name_prefs_key = "teacher_name";
    String t_id_prefs_key = "teacher_id";
    String t_department_prefs_key = "teacher_department";

    String teacherName;
    String department;
    String ied;

    //variable to track the number of times plus button is clicked
    int i = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_options);

        SharedPreferences name = PreferenceManager.getDefaultSharedPreferences(this);
        teacherName = name.getString(t_name_prefs_key, "couldn't get the teachers name");

        SharedPreferences dep = PreferenceManager.getDefaultSharedPreferences(this);
        department = dep.getString(t_department_prefs_key, "couldnt get the teachers department");

        SharedPreferences ID = PreferenceManager.getDefaultSharedPreferences(this);
        ied = ID.getString(t_id_prefs_key, "couldnt get the teachers id");


        mRecyclerView = findViewById(R.id.teacher_options_recyclerview);
        TeacherOptionsRecyclerViewAdapter mAdapter = new TeacherOptionsRecyclerViewAdapter(this, getFragmentManager(), this, teacherName, ied, department);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView2 = findViewById(R.id.teacher_options_recyclerview2);
        TeacherOptionsRecyclerViewAdapter2 mAdapter2 = new TeacherOptionsRecyclerViewAdapter2(this);
        mRecyclerView2.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView2.setAdapter(mAdapter2);

    }


}
