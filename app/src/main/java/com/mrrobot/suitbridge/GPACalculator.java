package com.mrrobot.suitbridge;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GPACalculator extends AppCompatActivity
{
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpacalculator);

        mTabLayout = findViewById(R.id.gpa_tablayout);
        mViewPager = findViewById(R.id.gpa_viewpager);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        mViewPagerAdapter.AddFragment(new GPACalculatorSGPAFragment(),"SGPA" );
        mViewPagerAdapter.AddFragment(new GPACalculatorCGPAFragment(), "CGPA");

        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
