package com.mrrobot.suitbridge;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ChatListViewAdapter extends BaseAdapter
{
    //All the keys to get the data from shared preferences.
    //Key of receivers name shared prefs
    private final String receiversPrefKey = "receiver";

    //key of senders name shared prfs
    private final String sendersNameKey = "name";

    //key of added registration number; AKA the database reference
    private final String messagereferenceKey = "message";

    //Key of class id
    private final String classIdKey = "classID";

    // private final String receiversPrefKey = "receiver";
    // All the needed variables:
    private String receiversName;
    private String sendersName;
    private String messageText;
    private String reference;


    private Activity mActivity;
    private DatabaseReference mDatabaseReference;
    private ArrayList<DataSnapshot> mSnapshotList;
    private ChildEventListener mListener = new ChildEventListener()
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

    //Constructor
    public ChatListViewAdapter(String receiver, String sender, Activity activity)
    {
        //First i will get all the values from the sharedprefs
        //Getting the Class ID for the reference
        SharedPreferences classidPrefs = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor classIdPrefsEditor = classidPrefs.edit();
        String classID = classidPrefs.getString(classIdKey, "NO Class Id Saved");

        //Getting the senders name
        SharedPreferences sendersPrefs = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor senderPrefEditor = sendersPrefs.edit();
        sendersName = sendersPrefs.getString(sendersNameKey, "Cannot Get the senders name");

        //Getting the receivers name
        SharedPreferences receiverPregs = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor receiverPrefsEditor = receiverPregs.edit();
        receiversName = receiverPregs.getString(receiversPrefKey, "Cannot Get the receivers name");

        //Getting the added registration numbers..AKA the message reference
        SharedPreferences messageReference = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor messageReferenceEditor = messageReference.edit();
        Long aLong = messageReference.getLong(messagereferenceKey, 000000);
        reference = Long.toString(aLong);

        this.receiversName = receiver;
        this.sendersName = sender;
        Log.d("messages", "The reference is: " + reference);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Classes").child(classID).child("messages").child(reference);
        mDatabaseReference.addChildEventListener(mListener);
        mActivity = activity;
        mSnapshotList = new ArrayList<>();
    }

    //Static Class to Hold all the views
    static class ViewHolder
    {
        TextView author;
        TextView message;
        TextView dateTime;
        LinearLayout.LayoutParams mParams;
    }

    //All List view Methods
    @Override
    public int getCount()
    {
        Log.d("messages", "size of msnapshotlist array is:" + mSnapshotList.size());

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
            view = inflater.inflate(R.layout.message_view, viewGroup, false);

            final ViewHolder holder = new ViewHolder();
            holder.author = view.findViewById(R.id.author);
            holder.message = view.findViewById(R.id.message_text);
            holder.dateTime = view.findViewById(R.id.dateTime_message);
            holder.mParams = (LinearLayout.LayoutParams) holder.author.getLayoutParams();
            view.setTag(holder);
        }

        final ChatMessageBody message = getItem(i);
        final ViewHolder holder = (ViewHolder) view.getTag();

        String currentAuthor = message.getAuthor();
        holder.author.setText(currentAuthor);

        String currentMessage = message.getMessage();
        holder.message.setText(currentMessage);

        String currentDatetime = message.getDatetime();
        holder.dateTime.setText(currentDatetime);

        boolean isMe = message.getAuthor().equals(sendersName);

        Log.d("messages", "Inside ChatlistAdapter: checking for isME: Name of sender is: " + sendersName + " and the boolean is: " + isMe);
        setChatRowAppearence(isMe, holder);
        return view;
    }

    public void setChatRowAppearence(boolean isItMe, ViewHolder holder)
    {
        if (isItMe)
        {
            holder.mParams.gravity = Gravity.END;
            holder.author.setTextColor(Color.GREEN);
            holder.dateTime.setGravity(Gravity.END);
            holder.message.setBackgroundResource(R.drawable.bubble2);
        }
        else
        {
            holder.mParams.gravity = Gravity.START;
            holder.author.setTextColor(Color.BLUE);
            holder.dateTime.setGravity(Gravity.START);
            holder.message.setBackgroundResource(R.drawable.bubble1);
        }

        holder.author.setLayoutParams(holder.mParams);
        holder.message.setLayoutParams(holder.mParams);
    }

    public void cleanUp()
    {
        mDatabaseReference.removeEventListener(mListener);
    }
}
