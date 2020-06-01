package com.example.atilagapps.hellixdatamanager.StaffManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atilagapps.hellixdatamanager.R;

import java.util.List;

public class StaffPaymentRecyclerAdapter extends RecyclerView.Adapter<StaffPaymentRecyclerAdapter.StaffPayViewHolder> {


    private List<StaffPaymentClass> mList;
    private StaffPaymentRecyclerAdapter.OnItemClickListener mListener;

    public StaffPaymentRecyclerAdapter(List<StaffPaymentClass> mList) {
        this.mList = mList;
    }

    public void setOnItemClickListener(StaffPaymentRecyclerAdapter.OnItemClickListener listener){
        mListener=listener;
    }



    public interface OnItemClickListener{
        void onItemClick(int position);

    }


    @NonNull
    @Override
    public StaffPayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.staff_payment_card_layout, parent, false);
        StaffPayViewHolder vh=new StaffPayViewHolder(v,mListener);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull StaffPayViewHolder holder, int position) {
            StaffPaymentClass currentItem=mList.get(position);
            holder.teacherName.setText(currentItem.getStaffName());
            holder.date.setText(currentItem.getForMonth());
            holder.amountPendingStaff.setText(currentItem.getPendingAmount());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class StaffPayViewHolder extends RecyclerView.ViewHolder {

        TextView teacherName,date,amountPendingStaff;

        public StaffPayViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);

            teacherName=itemView.findViewById(R.id.T_name_Pay_Id);
            date=itemView.findViewById(R.id.StaffPayDateId);
            amountPendingStaff=itemView.findViewById(R.id.T_pending_Pay_id);



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
