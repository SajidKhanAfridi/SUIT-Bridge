package com.mrrobot.suitbridge;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StudentClass_Activity extends AppCompatActivity
{
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_class_);

        //Linking views
        mTabLayout = findViewById(R.id.tablayout_id);
        mViewPager = findViewById(R.id.viewpager_id);
        //Set up the viewPager
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        //Add Fragments
        mViewPagerAdapter.AddFragment(new Fragment_Timeline(),"Timeline");
        mViewPagerAdapter.AddFragment(new Fragment_Student_Users(),"Students");
        mViewPagerAdapter.AddFragment(new Fragment_Teacher_Users(),"Teachers");

        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.getTabAt(0).setIcon(R.drawable.ic_format_align_center_black_24dp);
        mTabLayout.getTabAt(1).setIcon(R.drawable.ic_people_students);
        mTabLayout.getTabAt(2).setIcon(R.drawable.ic_person_teacher);

        //Removing Shadow from the action bar
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setElevation(0);
    }
}
