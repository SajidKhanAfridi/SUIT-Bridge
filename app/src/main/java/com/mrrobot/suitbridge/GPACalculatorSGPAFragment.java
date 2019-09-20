package com.mrrobot.suitbridge;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GPACalculatorSGPAFragment extends Fragment
{
    View v;

    EditText marks_editText;
    Button ok;
    TextView result;
    Button calculateCGPA;

    public GPACalculatorSGPAFragment()
    {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.gpa_calculator_sgpa_fragment, container, false);

        marks_editText = v.findViewById(R.id.marks);
        ok = v.findViewById(R.id.ok_button);
        result = v.findViewById(R.id.result11);
        calculateCGPA = v.findViewById(R.id.calculateGpaButton);


        return v;
    }
}
