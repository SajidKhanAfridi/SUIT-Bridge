package com.mrrobot.suitbridge;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatTeacherToStudentMain extends AppCompatActivity
{
    ListView mListView;
    ChatTeacherToStudentChatListAdapter mListAdapter;
    TextView messageText;

    String author;
    String datetime;
    String message;
    String className;

    String reference;

    DatabaseReference mDatabaseReference;

    //Keys for shared Prefs
    String t_name_prefs_key = "teacher_name";

    private static final String TAG = "ChatTeacherToStudentMai";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_teacher_to_student_main);

        mListView = findViewById(R.id.chatTeachertoStudent_messages_list);


        Intent getIntent = getIntent();
        Bundle myBundle = getIntent.getBundleExtra("Bundle");

        className = myBundle.getString("class");
        reference = myBundle.getString("ref");
        author = myBundle.getString("author");

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Classes").child(className);

        messageText = findViewById(R.id.chatTeachertoStudent_message_chatbox);

        Log.d(TAG, "onCreate: Got the className" + className);
        Log.d(TAG, "onCreate: Got the ref: " + reference);
        Log.d(TAG, "onCreate: Got the author name: " + author);

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        mListAdapter = new ChatTeacherToStudentChatListAdapter(getApplicationContext(), className, reference, author );
        mListView.setAdapter(mListAdapter);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        mListAdapter.cleanup();
    }

    public void attachFiles(View view)
    {
    }

    public void sendMessage(View view)
    {
        //Getting the date and time on which the message is created
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        datetime = sdf.format(new Date());
        message = messageText.getText().toString();
        ChatMessageBody messageBody = new ChatMessageBody(message, author, datetime, "teacher");

        mDatabaseReference.child("messages").child(reference).push().setValue(messageBody);
        Toast.makeText(this, "Sent!", Toast.LENGTH_SHORT).show();

        messageText.setText("");
    }
}
