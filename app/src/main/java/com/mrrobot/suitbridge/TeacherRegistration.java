package com.mrrobot.suitbridge;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.Random;

public class TeacherRegistration extends AppCompatActivity
{
    String teacherName;
    String teacherID;
    String teacherDepartment;

    EditText name;
    Spinner department;

    //Keys for shared Prefs
    String t_name_prefs_key = "teacher_name";
    String t_id_prefs_key = "teacher_id";
    String t_department_prefs_key = "teacher_department";

    DatabaseReference mFirebaseDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_registration);

        name = findViewById(R.id.teacherNameID);
        department = findViewById(R.id.teacherDepartmentID);

        //setting up adapter for teacher department
        ArrayAdapter<String> departmentSpinner = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.departmentSpinnerData));
        departmentSpinner.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        department.setAdapter(departmentSpinner);
        department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                teacherDepartment = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

    }
    //Executed when the confirm button is pressed.
    public void confirmTeacher(View view)
    {
        teacherName = name.getText().toString();

        //Using random function to generate a unique teacher id for the teacher.
        Random random = new Random();

        try
        {
            teacherID = Integer.toString(random.nextInt(200));
        } catch (ClassCastException e)
        {
            e.printStackTrace();
        }
        //Shared prefs to store name
        SharedPreferences namePrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor namePrefsEditor = namePrefs.edit();
        namePrefsEditor.putString(t_name_prefs_key, teacherName);
        namePrefsEditor.apply();

        //Shared prefs to store id
        SharedPreferences idPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor idPrefsEditor = idPrefs.edit();
        idPrefsEditor.putString(t_id_prefs_key, teacherID);
        idPrefsEditor.apply();

        //Shared prefs to store department
        SharedPreferences departmentPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor departmentPrefsEditor = departmentPrefs.edit();
        departmentPrefsEditor.putString(t_department_prefs_key, teacherDepartment);
        departmentPrefsEditor.apply();

        TeacherRegistrationDataModel teacherRegistrationDataModelObject = new TeacherRegistrationDataModel(teacherName,teacherID,teacherDepartment);


        TeacherOptionsClasses teacherOptionsClasses = new TeacherOptionsClasses("click to join class","click to join class","click to join class");

        mFirebaseDatabaseReference.child("Teachers").child(teacherName).child("info").setValue(teacherRegistrationDataModelObject);

        mFirebaseDatabaseReference.child("Teachers").child(teacherName).child("MyClasses").setValue(teacherOptionsClasses);

        showDialog();

        //Shared prefs to store name
        Log.d("saqoo", "Teacher Registration : got the teacher name (Saving it): " + teacherName);
        SharedPreferences namesPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor namesPrefsEditor = namePrefs.edit();
        namePrefsEditor.putString(t_name_prefs_key, teacherName);
        namePrefsEditor.apply();
        //Toast.makeText(this, "Teacher Created Successfully: your ID is: " + teacherID,Toast.LENGTH_SHORT);

        Intent loginIntent = new Intent(this, LoginActivity.class);
        finish();
        startActivity(loginIntent);
    }

    public void showDialog()
    {
        AlertDialog.Builder info = new AlertDialog.Builder(getApplicationContext());
        info.setTitle("Information")
                .setMessage("You have been assigned a unique user ID:" + teacherID + ", Please remember this")
                .setPositiveButton("OK",null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .create()
                .show();
    }
}

