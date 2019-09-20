package com.mrrobot.suitbridge;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TimelineRecyclerAdapter extends RecyclerView.Adapter<TimelineRecyclerAdapter.TimelineViewHolder>
{
    Context mContext;
    List<TimelineItem> mData = new ArrayList<>();

    private DatabaseReference mDatabaseReference;

    public class TimelineViewHolder extends RecyclerView.ViewHolder
    {
        TextView title_tv;
        TextView content_tv;
        TextView date_tv;
        TextView reg_tv;
        ImageView user_image;

        //Reference to the card relative layout to add animation to it.
        RelativeLayout card_layout;

        public TimelineViewHolder(View itemView)
        {
            super(itemView);

            title_tv = itemView.findViewById(R.id.tv_title_lostorfound);
            content_tv = itemView.findViewById(R.id.post_text_lostorfound);
            date_tv = itemView.findViewById(R.id.tv_date_lostorfound);
            reg_tv = itemView.findViewById(R.id.tv_regno_lostorfound);
            user_image = itemView.findViewById(R.id.img_user);

            card_layout = itemView.findViewById(R.id.card_relativelayout_id);
        }
    }

    public TimelineRecyclerAdapter(Context context, String classID)
    {
        mContext = context;
        Log.d("saqoo", "Inside Timeline Recycler Adapter got the class ID: " + classID);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Classes").child(classID).child("posts");
        mDatabaseReference.addValueEventListener(mValueEventListener);


    }

    @NonNull
    @Override
    public TimelineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.item_timeline,parent, false);
        return new TimelineViewHolder(layout);
    }

    //Setting up the value event listner for the firebase

    private ValueEventListener mValueEventListener = new ValueEventListener()
    {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
        {
            int i = 1;

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
                mData.add(timelineItem);

                Log.d("saqoo", "Loop Execution Number: " + i );
                Log.d("saqlain", "Size of TimelineItemslistarray: " + mData.size());
                i++;
            }
            notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError)
        {
            Toast.makeText(mContext, "There was a problem in getting data from the server, check your Internet connection", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onBindViewHolder(@NonNull TimelineViewHolder holder, int position)
    {
        //Adding Animations
        holder.user_image.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));
        holder.card_layout.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));

        Log.d("saqlain", "Inside onBindViewHolder method: Binding the Data: The current value of position is: " +  position );
        Log.d("saqlain", "The name at current position is: " + mData.get(position).name);
        Log.d("saqlain", "The content at current position is: " + mData.get(position).content);
        Log.d("saqlain", "The Date at current position is: " + mData.get(position).date);
        Log.d("saqlain", "The regno at current position is: " + mData.get(position).regNo);

        //Attaching or Binding the data here
        holder.title_tv.setText(mData.get(position).name);
        holder.content_tv.setText(mData.get(position).content);
        holder.date_tv.setText(mData.get(position).date);
        holder.reg_tv.setText("Reg no: " + mData.get(position).regNo);
        holder.user_image.setImageResource(R.drawable.ic_person);

        Log.d("saqlain", "Done with first Position, moving on to the next one");

    }

    @Override
    public int getItemCount()
    {
        return mData.size();
    }


}
