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

public class Fragment_Student_Users extends Fragment
{
    private View v;

    private RecyclerView mRecyclerView;

    public Fragment_Student_Users() { }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.fragment_student_users,container,false);
        SharedPreferences classidPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor classIdPrefsEditor = classidPrefs.edit();
        String classID = classidPrefs.getString("classID", "NO Class Id Saved");

        Log.d("saqoo", "Inside Student Fragment: Found Class ID " + classID);

        mRecyclerView = v.findViewById(R.id.student_users_recylerview_id);
        StudentUsersRecyclerViewAdapter studentUsersRecyclerViewAdapter = new StudentUsersRecyclerViewAdapter(getContext(), classID);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(studentUsersRecyclerViewAdapter);
        return v;
    }

}