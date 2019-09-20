package com.mrrobot.suitbridge;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class TeacherClass extends AppCompatActivity
{
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;

    String classi;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_class);

        Bundle className = getIntent().getExtras();
        classi = className.getString("class");
        Toast.makeText(this,"Got the the class: it is: " + classi, Toast.LENGTH_SHORT).show();

        mTabLayout = findViewById(R.id.teacherclass_tablayout);
        mViewPager = findViewById(R.id.teacherClass_viewpager);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        mViewPagerAdapter.AddFragment(new FragmentTeacherTimeline(),"");
        mViewPagerAdapter.AddFragment(new FragmentTeachersStudents(),"");

        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.getTabAt(0).setIcon(R.drawable.ic_format_align_center_black_24dp);
        mTabLayout.getTabAt(1).setIcon(R.drawable.ic_people_students);

    }

    public String getdata()
    {
        return classi;
    }
}
