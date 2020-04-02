package com.example.atilagapps.hellixdatamanager.TuitionFess;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atilagapps.hellixdatamanager.R;

import java.util.List;

public class GetStudentRecyclerAdapter extends RecyclerView.Adapter<GetStudentRecyclerAdapter.GetStudentViewHolder> {


    private List<StudentClass> mList;

    public GetStudentRecyclerAdapter(List<StudentClass> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public GetStudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.get_students_card,parent,false);

        GetStudentViewHolder vh=new GetStudentViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull GetStudentViewHolder holder, int position) {

        StudentClass currentItem=mList.get(position);
        holder.studentName.setText(currentItem.getStudentName());


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class GetStudentViewHolder extends RecyclerView.ViewHolder {

        TextView studentName;

        public GetStudentViewHolder(@NonNull View itemView) {
            super(itemView);

            studentName=itemView.findViewById(R.id.StudentNameTextIdCard);

        }
    }


}
