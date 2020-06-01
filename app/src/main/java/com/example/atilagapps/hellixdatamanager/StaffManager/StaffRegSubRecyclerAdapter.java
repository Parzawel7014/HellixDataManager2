package com.example.atilagapps.hellixdatamanager.StaffManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atilagapps.hellixdatamanager.Fragments.CourseRecyclerAdapter;
import com.example.atilagapps.hellixdatamanager.Fragments.CoursesClass;
import com.example.atilagapps.hellixdatamanager.R;

import java.util.List;

public class StaffRegSubRecyclerAdapter extends RecyclerView.Adapter<StaffRegSubRecyclerAdapter.StaffRegSubViewHolder> {


    private List<StaffRegisteredSubjectClass> mList;

    public StaffRegSubRecyclerAdapter(List<StaffRegisteredSubjectClass> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public StaffRegSubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_card_layout, parent, false);
        StaffRegSubViewHolder vh=new StaffRegSubViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull StaffRegSubViewHolder holder, int position) {

        StaffRegisteredSubjectClass currentItem=mList.get(position);
        holder.batchName.setText(currentItem.getmBatchName());
        holder.batchTime.setText(currentItem.getmBatchTime());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class StaffRegSubViewHolder extends RecyclerView.ViewHolder {

        TextView batchName,batchTime;

    public StaffRegSubViewHolder(@NonNull View itemView) {
        super(itemView);
        batchName=itemView.findViewById(R.id.StaffSubjectCardID);
        batchTime=itemView.findViewById(R.id.StaffTimeIdBatches);
    }
}

}
