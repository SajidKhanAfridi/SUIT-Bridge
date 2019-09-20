package com.mrrobot.suitbridge;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.TagLostException;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.util.CrashUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class TeacherOptionsRecyclerViewAdapter extends RecyclerView.Adapter<TeacherOptionsRecyclerViewAdapter.MyViewHolder>
{
    private static final String TAG = "saqoo";
    Context mContext;
    android.app.FragmentManager mFragmentManager;
    Activity mActivity;
    TextView mclassID1 , mclassID2, mclassID3;

    String ID = "Click to Join Class";
    int adapterPosition;

    String firebaseClass1, firebaseClass2, firebaseClass3;
    String class1,class2, class3;
    String mteacherName, mteacherID, mteacherDepartment;


    private ArrayList<String> mData = new ArrayList<>();
    DatabaseReference mDatabaseReference;
    DatabaseReference mReference;
    TeacherRegistrationDataModel mModel;

    public TeacherOptionsRecyclerViewAdapter(Context context , FragmentManager manager , Activity activity, String name, String id, String dep)
    {
        mContext = context;
        mFragmentManager = manager;
        mActivity = activity;
        mteacherName = name;
        mteacherID = id;
        mteacherDepartment = dep;

        Log.d(TAG, "TeacherOptionsRecyclerViewAdapter: got the teacher name: " + mteacherName);
        Log.d(TAG, "TeacherOptionsRecyclerViewAdapter: got the teacher id: " + mteacherID);
        Log.d(TAG, "TeacherOptionsRecyclerViewAdapter: got the teacher department: " + mteacherDepartment);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Teachers").child(mteacherName).child("MyClasses");

        mReference = FirebaseDatabase.getInstance().getReference().child("Classes");

        mDatabaseReference.addValueEventListener(mValueEventListener);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        final View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.teacher_options_recycler_view_item,parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(v);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position)
    {
        Log.d(TAG, "onBindViewHolder: first class is: " + mData.get(position));
        holder.mTextView_teacherOptions.setText(mData.get(position));
        holder.mLinearLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(holder.mTextView_teacherOptions.getText().toString().equals("click to join class"))
                {

                    showDialog();
                }
                else
                {
                    adapterPosition = holder.getAdapterPosition();
                    String className = mData.get(adapterPosition);
                    //go to the next class here with the class name
                    Toast.makeText(mActivity, "you will be taken to new class here with data: " + className ,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mActivity, TeacherClass.class);
                    intent.putExtra("class",className);
                    mActivity.startActivity(intent);
                }
            }
        });
    }

    public void showDialog()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);

        LayoutInflater inflater = mActivity.getLayoutInflater();
        final View view = inflater.inflate(R.layout.teacher_options_dialog,null);

        mclassID1 = view.findViewById(R.id.input);
        mclassID2 = view.findViewById(R.id.input2);
        mclassID3 = view.findViewById(R.id.input3);

        if (mData.get(0).equals( "click to join class") )
            mclassID1.setHint(mData.get(0));
        else
        {
            mclassID1.setText(mData.get(0));
            mclassID1.setEnabled(false);
        }


        if (mData.get(1).equals( "click to join class"))
            mclassID2.setHint(mData.get(1));
        else
        {
            mclassID2.setText(mData.get(1));
            mclassID2.setEnabled(false);
        }

        if (mData.get(2).equals( "click to join class"))
            mclassID3.setHint(mData.get(2));
        else
        {
            mclassID3.setText(mData.get(2));
            mclassID3.setEnabled(false);
        }

        builder.setView(view)
                .setTitle("Enter Information!")
                .setMessage("Please Enter Class-ID here, you can get the class Id from students of corresponding class")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        Log.d(TAG, "onClickPositiveButton: triggered!");

                        class1 = mclassID1.getText().toString();

                        if (class1.equals(""))
                            class1 = "click to join class";
                        else
                        {
                            TeacherRegistrationDataModel model = new TeacherRegistrationDataModel(mteacherName, mteacherID, mteacherDepartment);
                            mReference.child(class1).child("ConnectedTeachers").child(mteacherName).setValue(model);
                        }

                        class2 = mclassID2.getText().toString();
                        if (class2.equals(""))
                            class2 = "click to join class";


                        class3 = mclassID3.getText().toString();
                        if (class3.equals(""))
                            class3 = "click to join class";


                        TeacherOptionsClasses classes = new TeacherOptionsClasses(class1, class2, class3);
                        mDatabaseReference.setValue(classes);

                        notifyDataSetChanged();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        dialogInterface.dismiss();
                    }
                })
                .show();

    }

    @Override
    public int getItemCount()
    {
        Log.d(TAG, "getItemCount: size of mdata is : " + mData.size());
        return mData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout mLinearLayout;
        private TextView mTextView_teacherOptions;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            mLinearLayout =  itemView.findViewById(R.id.teacher_options_linear_layout_id);
            mTextView_teacherOptions = itemView.findViewById(R.id.teacher_options_textview);

        }
    }

    private ValueEventListener mValueEventListener = new ValueEventListener()
    {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
        {
            firebaseClass1 = (String) dataSnapshot.child("class1").getValue();
            Log.d(TAG, "onDataChange: Firebaseclass 1 is: " + firebaseClass1);
            mData.add(firebaseClass1);
            firebaseClass2 = (String) dataSnapshot.child("class2").getValue();
            Log.d(TAG, "onDataChange: Firebaseclass2 is: " + firebaseClass2);
            mData.add(firebaseClass2);
            firebaseClass3 = (String) dataSnapshot.child("class3").getValue();
            Log.d(TAG, "onDataChange: Firebase Class3 is: " + firebaseClass3);
            mData.add(firebaseClass3);

            Log.d(TAG, "onDataChange: Size of mdata array is: " + mData.size());
            getItemCount();
            notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError)
        {

        }
    };
}
