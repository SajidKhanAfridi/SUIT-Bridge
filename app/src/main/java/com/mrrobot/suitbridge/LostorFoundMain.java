package com.mrrobot.suitbridge;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LostorFoundMain extends AppCompatActivity
{
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lost_or_found_main);

        mTabLayout = findViewById(R.id.lostorfoundTablayout_id);
        mViewPager = findViewById(R.id.lostorfoundViewpager_id);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        //Adding the Fragments.
        mViewPagerAdapter.AddFragment(new LostOrFoundTimelineFragment(),"All Posts");
        mViewPagerAdapter.AddFragment(new LostOrFoundCreateNewPostFragment(), "Create New Post");

        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
