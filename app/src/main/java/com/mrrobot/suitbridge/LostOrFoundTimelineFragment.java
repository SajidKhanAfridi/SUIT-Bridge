package com.mrrobot.suitbridge;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class LostOrFoundTimelineFragment extends Fragment
{
    View v;
    RecyclerView lostorFoundTimelineRecyclerView;
    LostOrFoundTimelineRecyclerAdapter mlostorFoundTimelineRecyclerAdapter;

    public LostOrFoundTimelineFragment()
    {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.lost_or_found_timeline_fragment,container, false);

        lostorFoundTimelineRecyclerView = v.findViewById(R.id.lostorfound_recyclerview);

        //filling the list timeline with data
        //Getting the Class ID to get all the posts in it
        SharedPreferences classidPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor classIdPrefsEditor = classidPrefs.edit();
        String classID = classidPrefs.getString("classID", "NO Class Id Saved");
        Log.d("saqoo", "Inside FragmentTimeline: Class ID is: " + classID);

        //initializing the adapter and setting it up
        Log.d("saqoo", "Calling the adapter");
        mlostorFoundTimelineRecyclerAdapter = new LostOrFoundTimelineRecyclerAdapter(getContext(), classID);
        lostorFoundTimelineRecyclerView.setAdapter(mlostorFoundTimelineRecyclerAdapter);
        lostorFoundTimelineRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return v;
    }
}
