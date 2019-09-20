package com.mrrobot.suitbridge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StudentOptions extends AppCompatActivity
{
    Button gotoClassButton;
    Button lostOrFoundButton;
    Button calculateGPAButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_options);

        gotoClassButton = findViewById(R.id.gotoClassButton);
        lostOrFoundButton = findViewById(R.id.LostorFoundbutton);
        calculateGPAButton = findViewById(R.id.calculateGpaButton);
    }

    public void GoToClass(View view)
    {
        Intent gotoClassIntent = new Intent(this, StudentClass_Activity.class);
        finish();
        startActivity(gotoClassIntent);
    }

    public void LostorFound(View view)
    {
        Intent lostorfoundIntent = new Intent(this, LostorFoundMain.class);
        startActivity(lostorfoundIntent);
    }

    public void CalculateGPA(View view)
    {
        Intent intent = new Intent(this, GPACalculator.class);
        startActivity(intent);
    }
}
