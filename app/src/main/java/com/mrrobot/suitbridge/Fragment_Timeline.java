package com.mrrobot.suitbridge;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Timeline extends Fragment
{
    View v;
    RecyclerView timelineRecyclerView;
    TimelineRecyclerAdapter mTimelineRecyclerAdapter;

    //Create a new Post Button
    FloatingActionButton mFloatingActionButton;

    //Database Reference
    private DatabaseReference mDatabaseReference;
    //Array list to hold the data
    private ArrayList<TimelineItem> mTimelineItems = new ArrayList<>();

    public Fragment_Timeline()
    {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.fragment_timeline,container,false);

        timelineRecyclerView = v.findViewById(R.id.timeline_recycler_view);

        mFloatingActionButton = v.findViewById(R.id.fab_createnewpost);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(getContext(), "You can now create a new Post", Toast.LENGTH_SHORT).show();
                Intent createPostIntent = new Intent(getContext(), CreatePost.class);
                startActivity(createPostIntent);
            }
        });

        //filling the list timeline with data
        //Getting the Class ID to get all the posts in it
        SharedPreferences classidPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor classIdPrefsEditor = classidPrefs.edit();
        String classID = classidPrefs.getString("classID", "NO Class Id Saved");
        Log.d("saqoo", "Inside FragmentTimeline: Class ID is: " + classID);

               //initializing the adapter and setting it up
        Log.d("saqoo", "Calling the adapter");
        mTimelineRecyclerAdapter  = new TimelineRecyclerAdapter(getContext(), classID);
        timelineRecyclerView .setAdapter(mTimelineRecyclerAdapter);
        timelineRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
}
