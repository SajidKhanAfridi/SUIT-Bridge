package com.mrrobot.suitbridge;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentTeacherTimeline extends Fragment
{
    View mView;
    String className;

    RecyclerView mRecyclerView;
    TeacherTimelineRecyclerViewAdapter mTeacherTimelineRecyclerViewAdapter;

    FloatingActionButton mActionButton;

    public FragmentTeacherTimeline()
    {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mView = inflater.inflate(R.layout.fragment_teacher_timeline,container,false);

        mRecyclerView = mView.findViewById(R.id.teachersTimelineRecyclerView);

        TeacherClass teacherClass = (TeacherClass) getActivity();
        className = teacherClass.getdata();

        mActionButton = mView.findViewById(R.id.teacherTimeline_floatButton);
        mActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getActivity(), TeacherCreatePost.class);
                intent.putExtra("class", className);
                startActivity(intent);
            }
        });

        mTeacherTimelineRecyclerViewAdapter = new TeacherTimelineRecyclerViewAdapter(getActivity(), getContext(), className);
        mRecyclerView.setAdapter(mTeacherTimelineRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return mView;
    }
}
