package com.mrrobot.suitbridge;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentTeachersStudents extends Fragment
{
    RecyclerView mRecyclerView;
    TeacherStudentsRecyclerViewAdapter mTeacherStudentsRecyclerViewAdapter;

    View mView;
    String className;
    public FragmentTeachersStudents()
    {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mView = inflater.inflate(R.layout.fragment_teachers_students,container,false);

        TeacherClass teacherClass = (TeacherClass) getActivity();
        className = teacherClass.getdata();

        mRecyclerView = mView.findViewById(R.id.teachersStudentsRecyclerView);
        mTeacherStudentsRecyclerViewAdapter = new TeacherStudentsRecyclerViewAdapter(getActivity(),getContext(), className);
        mRecyclerView.setAdapter(mTeacherStudentsRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return mView;
    }
}

