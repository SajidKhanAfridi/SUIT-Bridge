package com.mrrobot.suitbridge;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers register via email/password.
 */
public class RegisterActivity extends AppCompatActivity
{
    // Constants
    public static final String CHAT_PREFS = "ChatPrefs";
    public static final String STATE_PREFS  = "StatePrefs";
    public static final String STATE_PREF_KEY = "userState";
    public static final String DISPLAY_NAME_KEY = "username";

    //Variables
    private int checkedRadio = 0;

    //SharedPreferences Variables
    private SharedPreferences userStatePreferences;
    private SharedPreferences.Editor userStatePreferencesEditor;


    // UI references.
    private EditText mUsernameView;
    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText mConfirmPasswordView;
    private RadioGroup studentTeacherRadioGroup;

    //Firebase Instance variables
    private FirebaseAuth mauth;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Set up the login form.

        mUsernameView = (EditText) findViewById(R.id.register_username);
        mEmailView = (EditText) findViewById(R.id.register_email);
        mPasswordView = (EditText) findViewById(R.id.register_password);
        mConfirmPasswordView = (EditText) findViewById(R.id.register_confirm_password);
        studentTeacherRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        checkedRadio = studentTeacherRadioGroup.getCheckedRadioButtonId();
        Log.d("Suitbridge", "checked Radio Button ID is:" + checkedRadio);



        // Keyboard sign in action
        mConfirmPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent)
            {
                if (id == R.integer.register_form_finished || id == EditorInfo.IME_NULL)
                {
                    attemptRegistration();
                    return true;
                }
                return false;
            }
        });

        // TODO: Get hold of an instance of FirebaseAuth
        mauth = FirebaseAuth.getInstance();
    }

    // Executed when Next Step button is pressed.
    public void NextStep(View v)
    {
        Log.d("Suitbridge","calling attempt regestration");
        attemptRegistration();
    }

    private void attemptRegistration()
    {

        // Reset errors displayed in the form.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password))
        {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email))
        {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email))
        {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel)
        {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }
        else
        {
            // TODO: Call create FirebaseUser() here
            createFireBaseUser();
        }
    }

    private boolean isEmailValid(String email)
    {
        // You can add more checking logic here.
        return email.contains("@") && email.contains(".com");
    }

    private boolean isPasswordValid(String password)
    {
        //TODO: Add own logic to check for a valid password (minimum 6 characters)
        String confirmPassword = mConfirmPasswordView.getText().toString();
        if(confirmPassword.equals(password) && password.length() > 4)
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    // TODO: Create a Firebase user
    public void createFireBaseUser()
    {
        Toast.makeText(getApplicationContext(), "Inside create firebase user", Toast.LENGTH_SHORT).show();

        String email, password;
        email = mEmailView.getText().toString();
        password = mPasswordView.getText().toString();

        mauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                Log.d("suitbridge", "OnComplete Returned: " + task.isSuccessful());
                Toast.makeText(getApplicationContext(), "Inside listenere, ", Toast.LENGTH_SHORT).show();

                if (!task.isSuccessful())
                {
                    Log.d("suitbridge", "UserCreation Failed");
                    Toast.makeText(getApplicationContext(), "User Registration Failed!, Check Your Internet Connection and Try again", Toast.LENGTH_SHORT).show();
                    showErrorDialog("Registration Failed!");
                }
                else
                {
                    saveDisplayName();
                    Log.d("suitbridge", "User Created");
                    Toast.makeText(getApplicationContext(),"Registration Successful!", Toast.LENGTH_SHORT).show();
                    String username;
                    username = mUsernameView.getText().toString();

                    if(checkedRadio == 1)
                    {
                        saveUserState(1);
                        Bundle dataBundle = new Bundle();
                        dataBundle.putString("name", username );

                        Intent teacherIntent = new Intent(RegisterActivity.this, TeacherRegistration.class);
                        teacherIntent.putExtras(dataBundle);
                        finish();
                        startActivity(teacherIntent);
                    }
                    else if (checkedRadio == 2)
                    {
                        saveUserState(2);
                        Intent studentIntent = new Intent(RegisterActivity.this, StudentRegisteration.class);
                        studentIntent.putExtra("username", mUsernameView.getText().toString());
                        finish();
                        startActivity(studentIntent);
                    }
                    else if (checkedRadio == 0)
                    {
                        AlertDialog.Builder radioButtonNotSelectetedDialog = new AlertDialog.Builder(getApplicationContext());
                        radioButtonNotSelectetedDialog.setTitle("Oops!")
                                .setMessage("Please Select One of the Radio Button to Proceed, Select Student if you are a Student and Teacher if you are a Teacher")
                                .setPositiveButton("Ok", null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                }
            }
        });
    }
    // TODO: Save the State of the user i-e if its a teacher = 1 or a student = 2
    private void saveUserState(int state)
    {
        userStatePreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userStatePreferencesEditor = userStatePreferences.edit();

        userStatePreferencesEditor.putInt(STATE_PREF_KEY, state);
        userStatePreferencesEditor.commit();
    }
    // TODO: Save the display name to Shared Preferences
    private void saveDisplayName()
    {
        String displayName = mUsernameView.getText().toString();
        SharedPreferences prefs = getSharedPreferences(CHAT_PREFS, 0);
        prefs.edit().putString(DISPLAY_NAME_KEY, displayName).apply();
    }
    // TODO: Create an alert dialog to show in case registration failed
    private void showErrorDialog(String message)
    {
        AlertDialog.Builder errorDialog = new AlertDialog.Builder(RegisterActivity.this);
        errorDialog.setTitle("Error!")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }
    // TODO: Check that which radio button was clicked.
    //if checked radio is 1 then teacher if 2 the student ids are radio_teacher and radio_student
    public int onRadioButtonClicked(View view)
    {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_teacher:
                if (checked)
                {
                    Log.d("suitbridge", "Teacher Radio Button selected");
                    checkedRadio = 1;
                    Log.d("suitbridge", "checked radio button has a value: " + checkedRadio);
                }
                break;
            case R.id.radio_student:
                if (checked)
                {
                    Log.d("suitbridge", "Student Radio Button selected");
                    checkedRadio = 2;
                    Log.d("suitbridge", "checked radio has value: " + checkedRadio);
                }
                break;
        }
        return 0;
    }

}

