package com.example.atilagapps.hellixdatamanager.Students;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atilagapps.hellixdatamanager.R;

import org.w3c.dom.Text;

import java.util.List;

public class RegBatchesAdapter extends RecyclerView.Adapter<RegBatchesAdapter.RegBatchViewHolder> {


    private List<RegSubClass> mList;


    public RegBatchesAdapter(List<RegSubClass> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public RegBatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.registered_batch_card, parent, false);
        RegBatchViewHolder vh=new RegBatchViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RegBatchViewHolder holder, int position) {

        RegSubClass currentItem=mList.get(position);
        holder.subject.setText(currentItem.getmBatchName());
        holder.time.setText(currentItem.getmBatchTime());
        holder.remAmt.setText(currentItem.getmRemainingAmt());
        holder.paidAmt.setText(currentItem.getmTotalPaidAmt());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class RegBatchViewHolder extends RecyclerView.ViewHolder {

        TextView subject,time,remAmt,paidAmt;

        public RegBatchViewHolder(@NonNull View itemView) {
            super(itemView);

            subject=itemView.findViewById(R.id.RegBatchSubjectId);
            time=itemView.findViewById(R.id.RegBatchTimeId);
            remAmt=itemView.findViewById(R.id.RegBatchPendingId);
            paidAmt=itemView.findViewById(R.id.RegBatchTotalPaidId);

        }
    }
}
