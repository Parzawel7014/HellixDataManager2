package com.example.atilagapps.hellixdatamanager.Fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atilagapps.hellixdatamanager.R;

import java.util.ArrayList;
import java.util.List;

public class CourseRecyclerAdapter extends RecyclerView.Adapter<CourseRecyclerAdapter.CourseViewHolder> {
    private List<CoursesClass> mList;

    public CourseRecyclerAdapter(List<CoursesClass> list) {
        mList = list;

    }


    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.courses_card_layout, parent, false);
        CourseViewHolder vh=new CourseViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        CoursesClass currentItem=mList.get(position);
        holder.subText.setText(currentItem.getmSubject());
        holder.batchTime.setText(currentItem.getmTime());
        holder.amountText.setText(currentItem.getmAmount());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {

        public TextView subText,batchTime,amountText;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            subText=itemView.findViewById(R.id.CourseSubjectCardID);
            batchTime=itemView.findViewById(R.id.CourseTimeIdBatches);
            amountText=itemView.findViewById(R.id.AmountCardId);
        }
    }
}
