package com.mrrobot.suitbridge;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment_Teacher_Users extends Fragment
{
    View v;
    private final String classIdKey = "classID";
    private RecyclerView mTeacherRecyclerView;

    public Fragment_Teacher_Users()
    {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.fragment_teacher_users,container,false);

        String classID;
        SharedPreferences classprefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        classID = classprefs.getString(classIdKey, "Couldnt get teh class id");


        mTeacherRecyclerView = v.findViewById(R.id.teacher_users_recylerview_id);
        TeacherUsersRecyclerViewAdapter mTeacherUsersRecyclerViewAdapter = new TeacherUsersRecyclerViewAdapter(getContext(), classID);
        mTeacherRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTeacherRecyclerView.setAdapter(mTeacherUsersRecyclerViewAdapter);
        return v;
    }
}
