package com.mrrobot.suitbridge;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class LostOrFoundTimelineRecyclerAdapter extends RecyclerView.Adapter<LostOrFoundTimelineRecyclerAdapter.LostorFoundTimelineViewHolder>
{
    Context mContext;
    List<TimelineItem> mData = new ArrayList<>();

    private DatabaseReference mDatabaseReference;

    public class LostorFoundTimelineViewHolder extends RecyclerView.ViewHolder
    {
        TextView nameData;
        TextView contentData;
        TextView regnoData;
        TextView dateData;
        ImageView user_image;

        //Reference to the card relative layout to add animation to it.
        RelativeLayout card_layout;

        public LostorFoundTimelineViewHolder(View itemView)
        {
            super(itemView);
            nameData = itemView.findViewById(R.id.tv_title_lostorfound);
            contentData = itemView.findViewById(R.id.post_text_lostorfound);
            regnoData = itemView.findViewById(R.id.tv_regno_lostorfound);
            dateData = itemView.findViewById(R.id.tv_date_lostorfound);
            user_image = itemView.findViewById(R.id.img_user);

            card_layout = itemView.findViewById(R.id.card_relativelayout_id);
        }
    }

    public LostOrFoundTimelineRecyclerAdapter(Context context, String classID)
    {
        mContext = context;
        Log.d("saqoo", "Inside Lost or found Timeline Recycler Adapter got the class ID: " + classID);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("lostorfound");
        mDatabaseReference.addValueEventListener(mValueEventListener);
        Log.d("saqoo", "Completing the database reference");
    }

    @NonNull
    @Override
    public LostorFoundTimelineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Log.d("saqoo", " Inside onCreate View HOlder");
        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.lost_or_found_timeline_item, parent ,false);
        Log.d("saqoo", "completing the oncreate view holder method");

        //mData.add(new TimelineItem("First One", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.","23 March 1997", "su1501002015"));

        return new LostorFoundTimelineViewHolder(layout);
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
                String reg;
                String date;

                name = (String) ds.child("name").getValue();
                content = (String) ds.child("content").getValue();
                date = (String) ds.child("date").getValue();
                reg = (String) ds.child("regNo").getValue();

                TimelineItem timelineItem = new TimelineItem(name, content, date, reg, "none");
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
    public void onBindViewHolder(@NonNull LostorFoundTimelineViewHolder holder, int position)
    {
        holder.nameData.setText(mData.get(position).name);
        holder.contentData.setText(mData.get(position).content);
        holder.dateData.setText(mData.get(position).date);
        //holder.regnoData.setText(mData.get(position).regNo);
    }

    @Override
    public int getItemCount()
    {
        return mData.size();
    }

}
