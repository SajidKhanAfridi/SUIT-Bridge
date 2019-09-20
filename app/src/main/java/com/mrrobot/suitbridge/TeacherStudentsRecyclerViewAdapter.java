package com.mrrobot.suitbridge;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TeacherStudentsRecyclerViewAdapter extends RecyclerView.Adapter<TeacherStudentsRecyclerViewAdapter.TeacherStudentsViewHolder>
{
    Activity mActivity;
    Context mContext;
    String mClassName;

    String teacherName;
    String department;
    String id;

    Dialog mDialog;

    private static final String TAG = "TeacherStudentsRecycler";

    //Keys for shared Prefs
    String t_name_prefs_key = "teacher_name";
    String t_id_prefs_key = "teacher_id";
    String t_department_prefs_key = "teacher_department";

    DatabaseReference mReference;
    List<StudentUserModelClass> mList = new ArrayList<>();

    public TeacherStudentsRecyclerViewAdapter(Activity activity, Context context, String className)
    {
        mActivity = activity;
        mContext = context;
        mClassName = className;

        mReference = FirebaseDatabase.getInstance().getReference().child("Classes").child(mClassName).child("Students");
        mReference.addValueEventListener(mValueEventListener);

        SharedPreferences name = PreferenceManager.getDefaultSharedPreferences(mActivity);
        teacherName = "Sir " + name.getString(t_name_prefs_key, "couldn't get the teachers name");

        SharedPreferences dep = PreferenceManager.getDefaultSharedPreferences(mActivity);
        department = dep.getString(t_department_prefs_key, "couldnt get the teachers department");

        SharedPreferences ID = PreferenceManager.getDefaultSharedPreferences(mActivity);
        id = ID.getString(t_id_prefs_key, "couldnt get the teachers id");

        Log.d(TAG, "onCreate: Got the teacher name:" + teacherName);
        Log.d(TAG, "onCreate: Got the department: " + department);
        Log.d(TAG, "onCreate: Got the id: " + id);
    }

    @NonNull
    @Override
    public TeacherStudentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        final View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.teachers_students_fragment_item ,parent,false);
        final TeacherStudentsRecyclerViewAdapter.TeacherStudentsViewHolder viewHolder = new TeacherStudentsRecyclerViewAdapter.TeacherStudentsViewHolder(v);



        viewHolder.mLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Dialog Initialization
                mDialog = new Dialog(mContext);
                mDialog.setContentView(R.layout.dialog_contact);
                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                //Toast.makeText(mActivity, "Clicked: view no: " + viewHolder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                //showing the dialog to the user
                TextView dialog_name_tv = (TextView) mDialog.findViewById(R.id.dialog_username_id);
                TextView dialog_reg_tv = (TextView) mDialog.findViewById(R.id.dialog_reg_id);
                Button dialog_button = (Button) mDialog.findViewById(R.id.dialog_massege_button_id);

                String name = mList.get(viewHolder.getAdapterPosition()).getName();
                final String reg = mList.get(viewHolder.getAdapterPosition()).getRegistration();

                dialog_name_tv.setText(name);
                dialog_reg_tv.setText(reg);
                dialog_button.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        Toast.makeText(mContext, "You Can Now Text to the Person", Toast.LENGTH_SHORT).show();

                        String ref = Integer.toString(Integer.parseInt(id) + Integer.parseInt(reg));
                        Log.d(TAG, "onClick: got the references: " + ref);

                        SharedPreferences name = PreferenceManager.getDefaultSharedPreferences(mContext);
                        String author = name.getString(t_name_prefs_key, "couldn't get the name");

                        Bundle bundle = new Bundle();
                        bundle.putString("class", mClassName);
                        bundle.putString("ref", ref);
                        bundle.putString("author", author);

                        Intent chatIntent = new Intent(mContext, ChatTeacherToStudentMain.class);
                        chatIntent.putExtra("Bundle", bundle);
                        mContext.startActivity(chatIntent);
                    }
                });
                mDialog.show();
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherStudentsViewHolder holder, int position)
    {
        holder.username_textview.setText(mList.get(position).getName());
        holder.reg_textview.setText(mList.get(position).getRegistration());
    }

    @Override
    public int getItemCount()
    {
        return mList.size();
    }

    public static class TeacherStudentsViewHolder extends RecyclerView.ViewHolder
    {
        TextView username_textview;
        TextView reg_textview;
        LinearLayout mLayout;
        public TeacherStudentsViewHolder (View itemView)
        {
            super(itemView);
            username_textview = itemView.findViewById(R.id.teachersStudents_username_list_item_textview);
            reg_textview = itemView.findViewById(R.id.teachersStudents_registration_list_item_textview);
            mLayout = itemView.findViewById(R.id.teachersStudents_list_item_id);
        }
    }

    private ValueEventListener mValueEventListener = new ValueEventListener()
    {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
        {
            for(DataSnapshot ds :dataSnapshot.getChildren())
            {
                String name = (String) ds.child("name").getValue();
                Log.d("log.d", "Name of current recieved user is: " + name);

                String reg = "";
                try
                {
                    reg = (String) ds.child("reg").getValue();
                } catch (ClassCastException e)
                {
                    Log.d("saqoo", e.toString());
                }
                //Log.d("log.d", "Registration  of current recieved User is: " + reg);

                Log.d("log.d", "adding the received data to studentmodelclass object");
                StudentUserModelClass modelClass = new StudentUserModelClass("DummyDepartment", reg, "DummySection", "DummySemester", name);
                mList.add(modelClass);
            }
            notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError)
        {

        }
    };
}
