package com.mrrobot.suitbridge;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentRegisteration extends AppCompatActivity
{
    EditText userName;

    Spinner sectionSpinner;
    Spinner semesterSpinner;
    Spinner departmentSpinner;
    Spinner programSpinner;
    //These are the different spinners for registrations no's
    //Spinner batchSpinner;
    //Spinner programNoSpinner;
    //Spinner sectionPartSpinner;
    Spinner rollnoPartSpinner;

    //String to hold all the parts of the
    String batchpart;
    String programNoPart;
    String sectionNoPart;
    String rollNoPart;

    //Strings to hold all the values entered by the user
    String section;
    String semester;
    String program;
    String department;
    String classID;
    String name;
    String reg;

    //Class ID Shared Preferences
    private SharedPreferences classIdSharedPrefs;
    private SharedPreferences.Editor classIdSharedPrefsEditor;

    //Name Shared Prefs
    private SharedPreferences nameSharedPrefs;
    private SharedPreferences.Editor nameSharedPrefsEditor;

    //Registration no prefs
    private SharedPreferences regnoPrefs;
    private SharedPreferences.Editor regnoPrefsEditor;

    //shared Prefs keys
    private final String classIdKey = "classID";
    private final String NAMEIDKEY = "name";
    private final String REGIDKEY = "registration";

    String t_name_prefs_key = "teacher_name";

    DatabaseReference mFirebaseDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registeration);

        userName = findViewById(R.id.register_username);

        sectionSpinner = findViewById(R.id.section_spinner);
        semesterSpinner = findViewById(R.id.semesterSpinner);
        departmentSpinner = findViewById(R.id.departmentSpinner);
        programSpinner = findViewById(R.id.programSpinner);

        //Initializing the all of the spinners of registration no:
        //batchSpinner = findViewById(R.id.bath_regno_spinner);
        //programNoSpinner = findViewById(R.id.program_regno_spinner);
        //sectionPartSpinner = findViewById(R.id.section_regno_spinner);
        rollnoPartSpinner = findViewById(R.id.rollno_regno_spinner);


        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Classes");

        //Setting up adapter for the for all the regno spinners
        /*//Array adapter for batch part in regno
        ArrayAdapter<String> batchSpinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.batchArray));
        batchSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        batchSpinner.setAdapter(batchSpinnerAdapter);
        batchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                batchpart = (String) adapterView.getItemAtPosition(i);
                Log.d("saqoo", "Batch selected is: " + batchpart);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
        //Setting up adapter for program part in regno
        ArrayAdapter<String> RegnoprogramSpinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.programArray));
        RegnoprogramSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        programNoSpinner.setAdapter(RegnoprogramSpinnerAdapter);
        programNoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                programNoPart = (String) adapterView.getItemAtPosition(i);
                Log.d("saqoo", "Program no in regno selected is: " + programNoPart);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
        //Setting up adapter for sections part in regno
        ArrayAdapter<String> RegnoSectionSpinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,getResources().getStringArray(R.array.sectionArray));
        RegnoSectionSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sectionPartSpinner.setAdapter(RegnoSectionSpinnerAdapter);
        sectionPartSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                sectionNoPart = (String) adapterView.getItemAtPosition(i);
                Log.d("saqoo", "Section no in regno is: " + sectionNoPart);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });*/
        //Setting up the rollno part in regno
        ArrayAdapter<String> RegnoRollnoSpinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,getResources().getStringArray(R.array.rollnoArray));
        RegnoRollnoSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        rollnoPartSpinner.setAdapter(RegnoRollnoSpinnerAdapter);
        rollnoPartSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                rollNoPart = (String) adapterView.getItemAtPosition(i);
                Log.d("saqoo", "Rollno in regno selected is: " + rollNoPart);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

        //Setting Adapter for Sections Spinner//
        ArrayAdapter<String> sectionSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.sectionSpinnerData));
        sectionSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sectionSpinner.setAdapter(sectionSpinnerAdapter);
        sectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                section = (String) adapterView.getItemAtPosition(i);
                Log.d("saqoo", "Sectio is: " + section);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                Log.d("saqoo", "Nothing in the sections list was selected");
            }
        });

        //Setting Adapter for Semester Spinner//
        ArrayAdapter<String> semesterSpinnerAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.semesterSpinnerData));
        semesterSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        semesterSpinner.setAdapter(semesterSpinnerAdapter);

        semesterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                semester = (String) adapterView.getItemAtPosition(i);
                Log.d("saqoo", "Semester is: " + semester);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                Log.d("saqoo", "No Semester was selected");
            }
        });
        //Setting Adapter for Program Spinner//
        ArrayAdapter<String> programSpinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.programSpinner));
        programSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        programSpinner.setAdapter(programSpinnerAdapter);

        programSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                program = (String) adapterView.getItemAtPosition(i);
                Log.d("saqoo", "Program Selected was: " + program);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

        //SettingAdapter for Department Spinner//
        ArrayAdapter<String > departmentSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.departmentSpinnerData));
        departmentSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        departmentSpinner.setAdapter(departmentSpinnerAdapter);

        departmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                department = (String) adapterView.getItemAtPosition(i);
                Log.d("saqoo", "Department is: " + department);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                Log.d("saqoo", "No department was selected");
            }
        });
    }

    public void confirmButton(View view)
    {

        name = userName.getText().toString();
        reg = rollNoPart;

        Log.d("registration", "The Registration no(Last Rollno part) is: " + reg);

        //Making Class ID//
        classID = section + semester + program + department;
        Log.d("saqoo", "hello, this is student registration class and Class ID is: " + classID);

        //Saving the Class ID
        classIdSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        classIdSharedPrefsEditor = classIdSharedPrefs.edit();
        classIdSharedPrefsEditor.putString(classIdKey, classID);
        classIdSharedPrefsEditor.commit();

        //Saving the Name
        nameSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        nameSharedPrefsEditor = nameSharedPrefs.edit();
        nameSharedPrefsEditor.putString(NAMEIDKEY, name);
        nameSharedPrefsEditor.commit();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(t_name_prefs_key, name);
        prefsEditor.apply();

        //Saving the Registration No
        regnoPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        regnoPrefsEditor = regnoPrefs.edit();
        regnoPrefsEditor.putString(REGIDKEY,reg);
        regnoPrefsEditor.commit();


        Toast.makeText(this, "ClassID is: " + classID,Toast.LENGTH_SHORT).show();

        StudentRegisrationDataModel srdm = new StudentRegisrationDataModel(name,section,semester,department,reg);

        mFirebaseDatabaseReference.child(classID).child("Students").child(reg).setValue(srdm);

        //Showing the Toast to the Student User.
        Toast.makeText(this, "Student Created Successfully!", Toast.LENGTH_SHORT).show();

        //After the Registration is successfull we will now go to the login activity where the user can login.
        Intent loginIntent = new Intent(this, LoginActivity.class);
        finish();
        startActivity(loginIntent);

    }
}
