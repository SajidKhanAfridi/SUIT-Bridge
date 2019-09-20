package com.mrrobot.suitbridge;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LostOrFoundCreateNewPostFragment extends Fragment
{
    View v;
    EditText posttext;
    Button sendbutton;

    String content;

    DatabaseReference mDatabaseReference;

    public LostOrFoundCreateNewPostFragment()
    {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.lost_or_found_create_new_post, container, false);

        posttext = v.findViewById(R.id.post_text_lostorfound);
        sendbutton = v.findViewById(R.id.send_post);

        sendbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                content = posttext.getText().toString();

                //Getting the Class ID to put the post text into that class
                SharedPreferences classidPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor classIdPrefsEditor = classidPrefs.edit();
                String classID = classidPrefs.getString("classID", "NO Class Id Saved");

                //Getting the Name of the User
                SharedPreferences nameprefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor nameprefsEditor = nameprefs.edit();
                String name = nameprefs.getString("name", "Couldnt Fetch the Name");

                //Getting the Registration of the User
                SharedPreferences regprefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor regprefsEditor = regprefs.edit();
                String regNo = regprefs.getString("registration", "Could Fetch the Registration no");

                Log.d("saqoo", "Inside CreatePost: Class ID, Name and Registratio is: " + classID + name + regNo);

                //Getting the date and time on which the post is created
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
                String date = sdf.format(new Date());

                Log.d("saqoo", "Current Date and Time is: " + date);

                //First creating the timeline object item to send it to the firebase
                TimelineItem postItem = new TimelineItem(name, content, date, regNo, "none");

                mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("lostorfound");
                mDatabaseReference.push().setValue(postItem);

                Toast.makeText(getContext(), "Your Post was uploaded", Toast.LENGTH_SHORT).show();

                Intent backintent = new Intent(getContext(),LostorFoundMain.class);
                startActivity(backintent);
            }
        });

        return v;
    }
}
