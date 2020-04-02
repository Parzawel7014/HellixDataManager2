package com.example.atilagapps.hellixdatamanager.Batches;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atilagapps.hellixdatamanager.R;
import com.example.atilagapps.hellixdatamanager.Students.FindStudentRecyclerAdapter;

import java.text.BreakIterator;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {


    private List<SubjectAdapter> mList;

    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.batches_card_layout,parent,false);
        ViewHolder vh=new ViewHolder(v,mListener);
        
        return vh;
    }


    public RecyclerAdapter(List<SubjectAdapter> list){
        mList=list;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SubjectAdapter currentItem=mList.get(position);
        holder.subText.setText(currentItem.getMsubject());
        holder.batchTime.setText(currentItem.getmTime());
        holder.teacherText.setText(currentItem.getmTeacher());



    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView subText,batchTime,teacherText;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            subText=itemView.findViewById(R.id.SubjectCardID);
            batchTime=itemView.findViewById(R.id.TimeIdBatches);
            teacherText=itemView.findViewById(R.id.TeacherCardId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        int position =getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
