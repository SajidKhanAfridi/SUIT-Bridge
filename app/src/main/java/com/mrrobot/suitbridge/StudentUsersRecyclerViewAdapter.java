package com.mrrobot.suitbridge;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

public class StudentUsersRecyclerViewAdapter extends RecyclerView.Adapter<StudentUsersRecyclerViewAdapter.MyViewHolder>
{
    private DatabaseReference mDatabaseReference;
    private ArrayList<StudentUserModelClass> student = new ArrayList<>();

    //Receivers Shared Preferences
    private SharedPreferences receiversSharedPrefs;
    private SharedPreferences.Editor receiversSharedPrefsEditor;

    //Sendersandreceivers both combined shared prefs
    private SharedPreferences messagereference;
    private SharedPreferences.Editor messagereferenceEditor;

    //Keys for shared prefs
    private final String receiversPrefKey = "receiver";
    private final String messagereferenceKey = "message";
    private final String REGIDKEY = "registration";

    Dialog mDialog;

    Context mContext;

    String classIDforMessageDatabaseReference;
    String senderAndReciever;

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        private LinearLayout item_student_teacher_list;
        private TextView mTextView_name;
        private TextView mTextView_section;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            item_student_teacher_list = (LinearLayout)itemView.findViewById(R.id.student_teacher_list_item_id);
            mTextView_name = itemView.findViewById(R.id.user_name_list_item_textview);
            mTextView_section = itemView.findViewById(R.id.section_list_item_textview);
        }
    }

    public StudentUsersRecyclerViewAdapter(Context context, String classID)
    {
        Log.d("saqoo", "inside recycler view adapter Classid is" + classID);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Classes").child(classID).child("Students");
        Log.d("saqoo", "adding the value eventlistner to the mdatabasereference");
        mDatabaseReference.addValueEventListener(mChildEventListener);

        classIDforMessageDatabaseReference = classID;

        mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.student_teacher_list_item,parent,false);
        final MyViewHolder viewHolder = new MyViewHolder(v);

        //Dialog Initialization
        mDialog = new Dialog(mContext);
        mDialog.setContentView(R.layout.dialog_contact);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        viewHolder.item_student_teacher_list.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Getting the Name of the Sender(Who is using the app right now) and Receiver(The receiver of the message, it is the person, which is clicked from the list by the sender).

                //Getting the Registraion of the Sender(who is using the app right now)
                SharedPreferences senderRegprefs = PreferenceManager.getDefaultSharedPreferences(mContext);
                SharedPreferences.Editor senderRegPrefsEditor = senderRegprefs.edit();
                int regOfSender = Integer.parseInt(senderRegprefs.getString(REGIDKEY, "Couldnt get the registration no of sender"));

                //Getting the Registraion of of the reciever
                int regOfReciver = Integer.parseInt(student.get(viewHolder.getAdapterPosition()).getRegistration());

                //Combining the name of sender and reciever to put them is the reference of firebase:
                int senderAndReciever = regOfSender + regOfReciver;
                Log.d("saqoo", "registration of sender: " + regOfSender);
                Log.d("saqoo", "registration of Reciever:" + regOfReciver);
                Log.d("Saqoo", "Here is the registration of sender and Reciever...added: " + senderAndReciever);

                Toast.makeText(mContext, "The name of sender, receiver and then combined them in form of  message reference is : " + regOfSender + " " + regOfReciver + " " + senderAndReciever, Toast.LENGTH_SHORT).show();

                //Saving the added registartion of sender and receiver
                messagereference = PreferenceManager.getDefaultSharedPreferences(mContext);
                messagereferenceEditor = messagereference.edit();
                messagereferenceEditor.putLong(messagereferenceKey, senderAndReciever);
                messagereferenceEditor.apply();

                //showing the dialog to the user
                TextView dialog_name_tv = (TextView) mDialog.findViewById(R.id.dialog_username_id);
                TextView dialog_reg_tv = (TextView) mDialog.findViewById(R.id.dialog_reg_id);
                Button dialog_button = (Button) mDialog.findViewById(R.id.dialog_massege_button_id);

                String name = student.get(viewHolder.getAdapterPosition()).getName();
                String reg = student.get(viewHolder.getAdapterPosition()).getRegistration();


                //Saving name of receiver to later display it in the chat message.
                receiversSharedPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
                receiversSharedPrefsEditor = receiversSharedPrefs.edit();
                receiversSharedPrefsEditor.putString(receiversPrefKey, name);
                receiversSharedPrefsEditor.commit();

                dialog_name_tv.setText(name);
                dialog_reg_tv.setText("Reg: " + reg);
                dialog_button.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        Toast.makeText(mContext, "You Can Now Text to the Person", Toast.LENGTH_SHORT).show();
                        Intent chatIntent = new Intent(mContext, ChatStudentToStudentMain.class);
                        mContext.startActivity(chatIntent);
                    }
                });

                Toast.makeText(mContext, "Test Click " + String.valueOf(viewHolder.getAdapterPosition()),Toast.LENGTH_SHORT).show();
                mDialog.show();
            }
        });

        return viewHolder;
    }

    private ValueEventListener mChildEventListener = new ValueEventListener()
    {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
        {
            int i = 1;

            for(DataSnapshot ds :dataSnapshot.getChildren())
            {
                String name = (String) ds.child("name").getValue();
                Log.d("log.d", "Name of current recieved user is: " + name);

                String  reg = "";
                try{
                    reg = (String) ds.child("reg").getValue();
                }catch (ClassCastException e)
                {
                    Log.d("saqoo", e.toString());
                }
                //Log.d("log.d", "Registration  of current recieved User is: " + reg);

                Log.d("log.d", "adding the received data to studentmodelclass object");
                StudentUserModelClass modelClass = new StudentUserModelClass("DummyDepartment",reg,"DummySection","DummySemester",name);
                student.add(modelClass);

                Log.d("saqoo", "Loop Execution Number: " + i );
                Log.d("saqoo", "Size of studentlistarray: " + student.size());
                i++;

            }
            notifyDataSetChanged();
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError)
        {
            Toast.makeText(mContext, "OOPS! There was a problem getting the data.",Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public int getItemCount()
    {
        Log.d("saqoo","Im inside getItemCountMethod...returning the size of student list array");
        Log.d("saqoo", "The Size of Student List array is: " + student.size());
        return student.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        Log.d("saqoo", "Inside onBindViewHolderMethod: The value of the position variable is: " + position);

        holder.mTextView_name.setText(student.get(position).getName());
        holder.mTextView_section.setText("Reg: " + student.get(position).getRegistration());
    }


}