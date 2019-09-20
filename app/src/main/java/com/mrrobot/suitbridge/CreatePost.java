package com.mrrobot.suitbridge;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreatePost extends AppCompatActivity
{

    EditText posttextEdittext;
    Button sendpostButton;
    FloatingActionButton goBack;

    String postText;

    //Database Reference.
    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        posttextEdittext = findViewById(R.id.post_text_lostorfound);
        sendpostButton = findViewById(R.id.send_post);
        goBack = findViewById(R.id.cancel_fl_button);


        sendpostButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                postText = posttextEdittext.getText().toString();

                //Getting the Class ID to put the post text into that class
                SharedPreferences classidPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor classIdPrefsEditor = classidPrefs.edit();
                String classID = classidPrefs.getString("classID", "NO Class Id Saved");

                //Getting the Name of the User
                SharedPreferences nameprefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor nameprefsEditor = nameprefs.edit();
                String name = nameprefs.getString("name", "Couldnt Fetch the Name");

                //Getting the Registration of the User
                SharedPreferences regprefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor regprefsEditor = regprefs.edit();
                String regNo = regprefs.getString("registration","Couldnt fetch the registration");

                Log.d("saqoo", "Inside CreatePost: Class ID, Name and Registratio is: " + classID + name + regNo);

                //Getting the date and time on which the post is created
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd ");
                String date = sdf.format(new Date());

                Log.d("saqoo", "Current Date and Time is: " + date);

                //First creating the timeline object item to send it to the firebase
                TimelineItem postItem = new TimelineItem(name, postText, date, regNo, "student");

                mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Classes").child(classID);
                mDatabaseReference.child("posts").push().setValue(postItem);

                Intent completeIntent = new Intent(getApplicationContext(), StudentClass_Activity.class);
                startActivity(completeIntent);

            }
        });

        goBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //This is the floating back button which will take back to the timeline screen.
                Intent timelineIntent = new Intent(getApplicationContext(), StudentClass_Activity.class);
                startActivity(timelineIntent);
            }
        });

    }
}
