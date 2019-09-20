package com.mrrobot.suitbridge;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatTeacherToStudentChatListAdapter extends BaseAdapter
{
    private Context mActivity;
    private DatabaseReference mDatabaseReference;
    private ArrayList<DataSnapshot> mSnapshotList;

    private String mClassname;
    private String mReference;
    String mSender;

    private static final String TAG = "ChatTeacherToStudentCha";



    private ChildEventListener mChildEventListener = new ChildEventListener()
    {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
        {
            mSnapshotList.add(dataSnapshot);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
        {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot)
        {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
        {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError)
        {

        }
    };

    public ChatTeacherToStudentChatListAdapter(Context activity, String classname, String reference, String sender)
    {
        mActivity = activity;
        mClassname =classname;
        mSnapshotList = new ArrayList<>();
        mSender = sender;

        Log.d(TAG, "ChatTeacherToStudentChatListAdapter: reference is: " + reference);
        Log.d(TAG, "ChatTeacherToStudentChatListAdapter: Class name is: " + classname);

        mReference = reference;
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Classes").child(mClassname).child("messages").child(reference);
        mDatabaseReference.addChildEventListener(mChildEventListener);
    }

    static class ViewHolder
    {
        TextView author, message, time;
        LinearLayout.LayoutParams params;
    }

    @Override
    public int getCount()
    {
        Log.d(TAG, "getCount: Size of list is: " + mSnapshotList.size());
        return mSnapshotList.size();

    }

    @Override
    public ChatMessageBody getItem(int i)
    {
        DataSnapshot snapshot = mSnapshotList.get(i);
        return snapshot.getValue(ChatMessageBody.class);
    }

    @Override
    public long getItemId(int i)
    {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        if (view == null)
        {
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.message_view,viewGroup,false);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.author = view.findViewById(R.id.author);
            viewHolder.message = view.findViewById(R.id.message_text);
            viewHolder.time = view.findViewById(R.id.dateTime_message);
            viewHolder.params = (LinearLayout.LayoutParams) viewHolder.author.getLayoutParams();
            view.setTag(viewHolder);
        }

        final ChatMessageBody message = getItem(i);
        final ChatTeacherToStudentChatListAdapter.ViewHolder holder = (ChatTeacherToStudentChatListAdapter.ViewHolder) view.getTag();

        String currentAuthor = message.getAuthor();
        holder.author.setText(currentAuthor);

        String currentMessage = message.getMessage();
        holder.message.setText(currentMessage);

        String currentDatetime = message.getDatetime();
        holder.time.setText(currentDatetime);

        boolean isMe = message.getAuthor().equals(mSender);

        setChatRowAppearence(isMe, holder);

        return view;
    }

    public void setChatRowAppearence(boolean isItMe, ChatTeacherToStudentChatListAdapter.ViewHolder holder)
    {
        if (isItMe)
        {
            holder.params.gravity = Gravity.END;
            holder.author.setTextColor(Color.GREEN);
            holder.time.setGravity(Gravity.END);
            holder.message.setBackgroundResource(R.drawable.bubble2);
        }
        else
        {
            holder.params.gravity = Gravity.START;
            holder.author.setTextColor(Color.BLUE);
            holder.time.setGravity(Gravity.START);
            holder.message.setBackgroundResource(R.drawable.bubble1);
        }

        holder.author.setLayoutParams(holder.params);
        holder.message.setLayoutParams(holder.params);
    }
    

    public void cleanup()
    {
        mDatabaseReference.removeEventListener(mChildEventListener);
    }
}
