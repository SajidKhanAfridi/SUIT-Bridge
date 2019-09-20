package com.mrrobot.suitbridge;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class GPACalculatorViewPagerAdapter extends FragmentPagerAdapter
{
    private final List<Fragment> lstFragment = new ArrayList<>();
    private final List<String> lstTitles = new ArrayList<>();

    public GPACalculatorViewPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        return lstFragment.get(position);
    }

    @Override
    public int getCount()
    {
        return lstFragment.size();
    }
    public void addFragment (Fragment fragment, String title)
    {
        lstFragment.add(fragment);
        lstTitles.add(title);
    }

}
