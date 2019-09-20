package com.mrrobot.suitbridge;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TeacherOptionsRecyclerViewAdapter2 extends RecyclerView.Adapter<TeacherOptionsRecyclerViewAdapter2.MyViewHolder>
{
    Context mContext;

    public TeacherOptionsRecyclerViewAdapter2(Context context)
    {
        mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        final View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.teacher_options_recycler_view_item,parent, false);
        final TeacherOptionsRecyclerViewAdapter2.MyViewHolder viewHolder = new TeacherOptionsRecyclerViewAdapter2.MyViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position)
    {
        holder.mTextView_teacherOptions.setText("Lost or Found");
        holder.mTextView_teacherOptions.setText("Calculate GPA");

        holder.mLinearLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (holder.getAdapterPosition() == 1)
                {
                    Intent intent = new Intent(mContext, LostorFoundMain.class);
                    mContext.startActivity(intent);
                }
                else if (holder.getAdapterPosition() == 2)
                {
                    Intent intent = new Intent(mContext, GPACalculator.class);
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return 2;
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
}
