package com.mrrobot.suitbridge;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TeacherTimelineRecyclerViewAdapter extends RecyclerView.Adapter<TeacherTimelineRecyclerViewAdapter.TeacherTimelineViewHolder>
{
    Activity mActivity;
    Context mContext;
    String mclassName;

    DatabaseReference mReference;
    List<TimelineItem> mList;

    public TeacherTimelineRecyclerViewAdapter(Activity activity, Context context, String className)
    {
        mActivity = activity;
        mContext = context;
        mclassName = className;

        mList = new ArrayList<>();

        mReference = FirebaseDatabase.getInstance().getReference().child("Classes").child(mclassName).child("posts");
        mReference.addValueEventListener(mValueEventListener);
    }



    @NonNull
    @Override
    public TeacherTimelineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.teacher_timeline_item,parent, false);

        return new TeacherTimelineViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherTimelineViewHolder holder, int position)
    {
        holder.nameTextview.setText(mList.get(position).name);
        holder.regTextview.setText(mList.get(position).regNo);
        holder.dateTextview.setText(mList.get(position).date);
        holder.contentTextview.setText(mList.get(position).content);
    }

    @Override
    public int getItemCount()
    {
        return mList.size();
    }

    public class TeacherTimelineViewHolder extends RecyclerView.ViewHolder
    {
        TextView nameTextview;
        TextView dateTextview;
        TextView regTextview;
        TextView contentTextview;

        RelativeLayout card_layout;

        public TeacherTimelineViewHolder(View itemView)
        {
            super(itemView);

            nameTextview = itemView.findViewById(R.id.teacherTimeline_tv_title_lostorfound);
            dateTextview = itemView.findViewById(R.id.teacherTimeline_tv_date);
            regTextview = itemView.findViewById(R.id.teacherTimeline_tv_regno);
            contentTextview = itemView.findViewById(R.id.teacherTimeline_post_text);

            card_layout = itemView.findViewById(R.id.teacherTimeline_card_relativelayout_id);
        }
    }

    private ValueEventListener mValueEventListener = new ValueEventListener()
    {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
        {
            for(DataSnapshot ds :dataSnapshot.getChildren())
            {
                String name;
                String content;
                String reg = "";
                String date;

                name = (String) ds.child("name").getValue();
                content = (String) ds.child("content").getValue();
                date = (String) ds.child("date").getValue();
                try
                {
                    reg = (String) ds.child("regNo").getValue();
                }catch (ClassCastException e)
                {
                    Log.d("saqoo", e.toString());
                }
                TimelineItem timelineItem = new TimelineItem(name, content, date, reg, "student");
                mList.add(timelineItem);
            }
            notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError)
        {

        }
    };
}
