package com.example.atilagapps.hellixdatamanager.Batches;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atilagapps.hellixdatamanager.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {


    private List<SubjectAdapter> mList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.batches_card_layout,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }


    public RecyclerAdapter(List<SubjectAdapter> list){
        mList=list;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SubjectAdapter currentItem=mList.get(position);
        holder.subText.setText(currentItem.getMsubject());


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView subText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subText=itemView.findViewById(R.id.SubjectCardID);
        }
    }
}
