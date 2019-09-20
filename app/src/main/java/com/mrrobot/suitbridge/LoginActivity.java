package com.mrrobot.suitbridge;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.*;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity
{
    //SharedPreferences Keys
    private final String EMAIL_KEY = "EMAIL_KEY";
    private final String PASSWORD_PREF_KEY = "PASSWORD";
    private final String CHECKBOX_PREF_KEY = "CHECKBOX";
    //Variables
    private FirebaseAuth mauth;
    private int userState;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private CheckBox saveUserInfoCheckBox;

    //Shared Preferences
    SharedPreferences userInfoPreferences;
    SharedPreferences.Editor userInfoPreferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        // Views
        mEmailView = (EditText) findViewById(R.id.email_autoCompleteTextview);
        mPasswordView = (EditText) findViewById(R.id.password_editText);
        saveUserInfoCheckBox = findViewById(R.id.checkBox);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent)
            {
                if (id == R.integer.login || id == EditorInfo.IME_NULL)
                {
                    attemptLogin();
                    return true;
                }

                return false;
            }
        });

        // TODO: Grab an instance of FirebaseAuth
        mauth = FirebaseAuth.getInstance();

        userInfoPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userInfoPreferencesEditor = userInfoPreferences.edit();

        PreviouslyStoredPreferences();

    }

    // Executed when Sign in button pressed
    public void signInExistingUser(View v)
    {

        if(saveUserInfoCheckBox.isChecked())
        {
            //Save the Email
            String Email = mEmailView.getText().toString();
            userInfoPreferencesEditor.putString(EMAIL_KEY, Email);
            userInfoPreferencesEditor.commit();

            //Saving the Password
            String password = mPasswordView.getText().toString();
            userInfoPreferencesEditor.putString(PASSWORD_PREF_KEY, password);
            userInfoPreferencesEditor.commit();

            //Saving the CheckBox State
            userInfoPreferencesEditor.putString(CHECKBOX_PREF_KEY, "True");
            userInfoPreferencesEditor.commit();
        }
        else
        {
            //Save the Email
            userInfoPreferencesEditor.putString(EMAIL_KEY, "");
            userInfoPreferencesEditor.commit();

            //Saving the Password
            userInfoPreferencesEditor.putString(PASSWORD_PREF_KEY, "");
            userInfoPreferencesEditor.commit();

            //Saving the CheckBox State
            userInfoPreferencesEditor.putString(CHECKBOX_PREF_KEY, "False");
            userInfoPreferencesEditor.commit();
        }
        // TODO: Call attemptLogin() here
        attemptLogin();
    }

    // Executed when Register button pressed
    public void registerNewUser(View v)
    {
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

    // TODO: Complete the attemptLogin() method
    private void attemptLogin()
    {
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        if(email.isEmpty() || password.isEmpty())
        {
            return;
        }
        else
        {
            Toast.makeText(this, "Login in Progress...", Toast.LENGTH_SHORT);
        }

        // TODO: Use FirebaseAuth to sign in with email & password
        mauth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                Log.d("suitbridge", " OnComplete Triggered!" + task.isSuccessful());

                if(!task.isSuccessful())
                {
                    Log.d("suitbridge", "Login failed!" + task.getException());
                    //showErroDialog("Login Failed! Check Your Email or Password or Check your Internet Connection");
                }
                else
                {
                    Log.d("saqoo", "Sign in from the firebase is successfull, now calling the checktheSateofUser() method");
                    Toast.makeText(getApplicationContext(), "Login Successfull!", Toast.LENGTH_SHORT).show();
                    checkTheStateOfUser();
                }

                task.addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(getApplicationContext(), "Login Failed! Check Your Email and Password or Check Your Internet Connection", Toast.LENGTH_SHORT).show();

                    }
                });
            }

        });
    }

    //TODO: Going to the next screen according to the state of the user 2 for student 1 for teacher:
    private void checkTheStateOfUser()
    {
        Log.d("saqoo", "ChecktheStateofUser Method called: checking the state of the user");
//        SharedPreferences userStatePrefs = getSharedPreferences(RegisterActivity.STATE_PREFS, MODE_PRIVATE);
//        userState = Integer.parseInt(userStatePrefs.getString(RegisterActivity.STATE_PREF_KEY, null));
        SharedPreferences userStatePreferences;
        SharedPreferences.Editor userStatePreferencesEditor;

        userStatePreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userStatePreferencesEditor = userStatePreferences.edit();

        userState = userStatePreferences.getInt("userState",0);

        Log.d("saqoo", "Got the User State it is: " + userState);

        if (userState == 1)
        {
            Intent teacherActivityIntent = new Intent(this, TeacherOptions.class);
            finish();
            startActivity(teacherActivityIntent);
        }
        else if (userState == 2 )
        {
            Intent studentActivityIntent = new Intent(this, StudentOptions.class);
            finish();
            startActivity(studentActivityIntent);
        }
        else if (userState == 0)
        {
            Toast.makeText(this, "Oops! There is a Problem",Toast.LENGTH_SHORT).show();
        }
    }

    // TODO: Show error on screen with an alert dialog
    private void showErroDialog(String message)
    {
        AlertDialog.Builder errorDialog = new AlertDialog.Builder(getApplicationContext());
        errorDialog
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton("Ok",null)
                //.setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    //TODO: Add implementation for Checkbox

    //TODO: Implementation for checking the previously stored shared preference
    public void PreviouslyStoredPreferences()
    {
         String email = userInfoPreferences.getString(EMAIL_KEY, "");
         String password = userInfoPreferences.getString(PASSWORD_PREF_KEY, "");
         String checkbox = userInfoPreferences.getString(CHECKBOX_PREF_KEY, "false");

         mEmailView.setText(email);
         mPasswordView.setText(password);

         if (checkbox.equals("true"))
         {
             this.saveUserInfoCheckBox.setChecked(true);
         }
         else
         {
             this.saveUserInfoCheckBox.setChecked(false);
         }
    }

}
