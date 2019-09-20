package com.mrrobot.suitbridge;

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

public class TeacherUsersRecyclerViewAdapter extends RecyclerView.Adapter<TeacherUsersRecyclerViewAdapter.MyViewHolder>
{
    private DatabaseReference mDatabaseReference;
    List<TeacherRegistrationDataModel> mList = new ArrayList<>();

    Context mContext;
    String mClassID;
    Dialog mDialog;

    String mReference;
    String mAuthor;

    private final String NAMEIDKEY = "name";

    private static final String TAG = "TeacherUsersRecyclerVie";

    private final String REGIDKEY = "registration";

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView mTextView_name;
        private TextView mTextView_section;
        LinearLayout mLinearLayout;

        public MyViewHolder(View itemView)
        {
            super(itemView);

            mTextView_name = itemView.findViewById(R.id.user_name_list_item_textview);
            mTextView_section = itemView.findViewById(R.id.section_list_item_textview);
            mLinearLayout = itemView.findViewById(R.id.student_teacher_list_item_id);
        }
    }

    public TeacherUsersRecyclerViewAdapter(Context context, String classID)
    {
        mContext = context;
        mClassID = classID;
        Log.d(TAG, "TeacherUsersRecyclerViewAdapter: Got the Class ID: " + mClassID);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Classes").child(mClassID).child("ConnectedTeachers");
        mDatabaseReference.addValueEventListener(mValueEventListener);
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.student_teacher_list_item,parent,false);
        final TeacherUsersRecyclerViewAdapter.MyViewHolder viewHolder = new TeacherUsersRecyclerViewAdapter.MyViewHolder(v);

        viewHolder.mLinearLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(mContext, "Item clicked: " + viewHolder.getAdapterPosition(), Toast.LENGTH_SHORT).show();

                String name = mList.get(viewHolder.getAdapterPosition()).getName();
                String id = mList.get(viewHolder.getAdapterPosition()).getID();

                SharedPreferences regPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
                String registration = regPrefs.getString(REGIDKEY, "couldnt get the reg");

                //adding stuff
                mReference = Integer.toString(Integer.parseInt(registration) + Integer.parseInt(id));

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
                mAuthor = prefs.getString(NAMEIDKEY, "Couldnt get the name");

                //Dialog Initialization
                mDialog = new Dialog(mContext);
                mDialog.setContentView(R.layout.dialog_contact);
                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                //showing the dialog to the user
                TextView dialog_name_tv = (TextView) mDialog.findViewById(R.id.dialog_username_id);
                TextView dialog_reg_tv = (TextView) mDialog.findViewById(R.id.dialog_reg_id);
                Button dialog_button = (Button) mDialog.findViewById(R.id.dialog_massege_button_id);

                dialog_name_tv.setText(name);
                dialog_reg_tv.setText("ID: "+ id);
                dialog_button.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        Bundle bundle = new Bundle();
                        bundle.putString("class", mClassID);
                        bundle.putString("ref", mReference);
                        bundle.putString("author", mAuthor);

                        Toast.makeText(mContext, "You Can Now Text to the Person: with ref: "  + mReference, Toast.LENGTH_SHORT).show();
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        holder.mTextView_name.setText(mList.get(position).getName());
        holder.mTextView_section.setText("ID: " + mList.get(position).getID());

    }


    @Override
    public int getItemCount()
    {
        return mList.size();
    }

    private ValueEventListener mValueEventListener = new ValueEventListener()
    {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
        {
            for(DataSnapshot ds :dataSnapshot.getChildren())
            {
                String t_name = (String) ds.child("name").getValue();
                String t_id = (String)ds.child("id").getValue();

                TeacherRegistrationDataModel model = new TeacherRegistrationDataModel(t_name, t_id, "department");
                mList.add(model);

                Log.d(TAG, "onDataChange: Size of mList is: " + mList.size());
            }
            notifyDataSetChanged();

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError)
        {

        }
    };

}
