package com.mrrobot.suitbridge;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GPACalculatorCGPAFragment extends Fragment
{
    View v;
    public GPACalculatorCGPAFragment()
    {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.gpa_calculator_cgpa_fragment, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
