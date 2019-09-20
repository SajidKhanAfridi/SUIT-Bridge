package com.mrrobot.suitbridge;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TeacherCreatePost extends AppCompatActivity
{
    TextView mTextView;
    String classiName;

    //Keys for shared Prefs
    String t_name_prefs_key = "teacher_name";
    String t_id_prefs_key = "teacher_id";
    String t_department_prefs_key = "teacher_department";

    String teacherName;
    String department;
    String id;
    String content;

    DatabaseReference mDatabaseReference;

    private static final String TAG = "post";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_create_post);

        Bundle className = getIntent().getExtras();
        classiName = className.getString("class");

        Log.d(TAG, "onCreate: Got the class: " + classiName);

        SharedPreferences name = PreferenceManager.getDefaultSharedPreferences(this);
        teacherName = "Sir " + name.getString(t_name_prefs_key, "couldn't get the teachers name");

        SharedPreferences dep = PreferenceManager.getDefaultSharedPreferences(this);
        department = dep.getString(t_department_prefs_key, "couldnt get the teachers department");

        SharedPreferences ID = PreferenceManager.getDefaultSharedPreferences(this);
        id = ID.getString(t_id_prefs_key, "couldnt get the teachers id");

        Log.d(TAG, "onCreate: Got the teacher name:" + teacherName);
        Log.d(TAG, "onCreate: Got the department: " + department);
        Log.d(TAG, "onCreate: Got the id: " + id);

        mTextView = findViewById(R.id.teacherCreatePost_text);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Classes").child(classiName);
    }

    public void SendPost(View view)
    {
        content = mTextView.getText().toString();
        //Getting the date and time on which the post is created
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd ");
        String date = sdf.format(new Date());

        if (content.equals(""))
        {
            Toast.makeText(this, "Please Enter Content to post!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            TimelineItem item = new TimelineItem(teacherName, content, date, id, "teacher");
            mDatabaseReference.child("posts").push().setValue(item);
            mTextView.setText("");
            Toast.makeText(this, "Posted!", Toast.LENGTH_SHORT).show();
        }
    }

    public void GoBack(View view)
    {
        Intent intent = new Intent(this, FragmentTeacherTimeline.class);
        startActivity(intent);
    }
}
