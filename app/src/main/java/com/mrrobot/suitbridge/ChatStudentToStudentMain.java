package com.mrrobot.suitbridge;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatStudentToStudentMain extends AppCompatActivity implements DialogClass.dialogListner
{

    DatabaseReference mDatabaseReference;
    private ChatListViewAdapter mAdapter;

    EditText messageText;
    Button sendmessage;
    ListView mListView;

    String name;
    String receiversName;
    String reference;
    StorageReference mStorageReference;
    String classID;
    Integer select_file = 0;
    Integer fn;
    String imageDate;

    //Shared Prefreence to store the entered registration number
    SharedPreferences mreferncePrefs;
    SharedPreferences.Editor mreferencePrefsEditor;

    private static final String TAG = "ChatStudent";

    //key of senders name shared prfs
    private final String sendersNameKey = "name";

    //Key of class id
    private final String classIdKey = "classID";

    //Key of message reference
    private final String messagereferenceKey = "message";

    //key of receivers shared preferences
    private final String receiversPrefKey = "receiver";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_student_to_student_main);

        messageText = findViewById(R.id.message_chatbox);
        sendmessage = findViewById(R.id.message_send);
        mListView = findViewById(R.id.messages_list);

        messageText.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent)
            {
                sendmessage();
                return false;
            }
        });

        sendmessage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                sendmessage();
            }
        });

        //Getting the senders name
        SharedPreferences sendersPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor senderPrefEditor = sendersPrefs.edit();
        name = sendersPrefs.getString(sendersNameKey, "Cannot Get the senders name");

        //Getting the Class ID for the reference
        SharedPreferences classidPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor classIdPrefsEditor = classidPrefs.edit();
        classID = classidPrefs.getString(classIdKey, "NO Class Id Saved");

        //Getting the added registration numbers..AKA the message reference
        SharedPreferences messageReference = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor messageReferenceEditor = messageReference.edit();
        Long aLong = messageReference.getLong(messagereferenceKey, 000000);
        reference = Long.toString(aLong);

        Toast.makeText(this, "maessage ref is : " + reference, Toast.LENGTH_SHORT).show();
        //Gettting the receivers name
        SharedPreferences receiversPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor reveiversPreferencesEditor = receiversPreferences.edit();
        receiversName = receiversPreferences.getString(receiversPrefKey, "Couldnt get receivers name");

        if (reference == "Couldnt get the message reference")
        {
            showDialog();
        }
    }

    public void showDialog()
    {
        DialogClass dialogClass = new DialogClass();
        dialogClass.show(getSupportFragmentManager(), "OOPS!");
    }

    public void sendmessage()
    {
        //Getting the date and time on which the message is created
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        String date = sdf.format(new Date());
        String message = messageText.getText().toString();
        String type = "text";

        ChatMessageBody messageBody = new ChatMessageBody(message, name, date, type);

        //Putting the messageBody object on firebase
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Classes").child(classID);
        mDatabaseReference.child("messages").child(reference).push().setValue(messageBody);

        messageText.setText("");
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        mAdapter = new ChatListViewAdapter(receiversName, name,this);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void applyText(String registrtion)
    {
        reference = registrtion;
        mreferncePrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mreferencePrefsEditor = mreferncePrefs.edit();
        mreferencePrefsEditor.putString(messagereferenceKey, reference);
        mreferencePrefsEditor.apply();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        mAdapter.cleanUp();
    }

    public void attachFiles(View view)
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent, "Select Image from"),select_file);

    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( requestCode==select_file)

        {    //Bundle b = data.getExtras();
            //Bitmap bmp = (Bitmap) b.get("data");
            Uri selectImageUri = data.getData();
            sendpic(selectImageUri);
            *//*imageholder ih = new imageholder(selectImageUri);
            ih.show(getFragmentManager(),ih.getTag());
*//*
        }

    }*/
    /*private void sendpic(Uri uri)
    {
        imageDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            fn = sharedPreferences.getInt("imagefilename",1);

        Log.d(TAG, "sendpic: value of fn is: " + fn);

            StorageReference riversRef = mStorageReference.child("messages").child(reference).child("image").child(String.valueOf(fn));

            riversRef.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "error...."+exception, Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress =(100.0*taskSnapshot.getBytesTransferred())/ taskSnapshot.getTotalByteCount();
                    progressDialog.setMessage(((int)progress )+"% Uploaded");
                    senfiledata(fn,imageDate);

                }
            });

            int n =fn;
            n=n+1;
            Log.d("log.d","count......."+n);
            editor.putInt("imagefilename",n);
            editor.commit();

        }
    private void senfiledata(Integer fn, String date) {
        Integer filename=fn;
        String stringFileName = Integer.toString(filename);
        String dat=date;
        String type = "image";

        ChatMessageBody imageMessage = new ChatMessageBody(stringFileName, name, date, type);

*//*
        c=false;
        imageModelClass imc = new imageModelClass(filename,dat);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //audioModel am=new audioModel(filenam,uri,dat);
        mDatabase.child("Teacher").child(tid).child(sub).child("Homework").child("image").child(String.valueOf(fn)).setValue(imc);
*//*

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Classes").child(classID);
        mDatabaseReference.child("messages").child(reference).push().setValue(imageMessage);

    }*/
}

