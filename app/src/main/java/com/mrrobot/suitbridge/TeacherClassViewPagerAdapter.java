package com.mrrobot.suitbridge;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TeacherClassViewPagerAdapter extends FragmentPagerAdapter
{
    private final List<Fragment> IstFragment = new ArrayList<>();
    private final List<String> IstTitles = new ArrayList<>();

    public TeacherClassViewPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        return IstFragment.get(position);
    }

    @Override
    public int getCount()
    {
        return IstTitles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        return IstTitles.get(position);
    }

    public void AddFragment (Fragment fragment, String title)
    {
        IstFragment.add(fragment);
        IstTitles.add(title);
    }
}
