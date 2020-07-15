package com.example.atilagapps.hellixdatamanager.Charts;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atilagapps.hellixdatamanager.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class ExtraInExRecyclerAdapter extends RecyclerView.Adapter<ExtraInExRecyclerAdapter.ExtraViewHolder> {


    private List<ExtraInExClass> mList;

    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public ExtraInExRecyclerAdapter(List<ExtraInExClass> mList) {
        this.mList = mList;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }


    @NonNull
    @Override
    public ExtraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.extra_in_ex_card_layout,parent,false);
        ExtraViewHolder vh=new ExtraViewHolder(v,mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExtraViewHolder holder, int position) {

        ExtraInExClass currentItem=mList.get(position);
        holder.paymentType.setText(currentItem.getPayment_type());
        holder.paymentTo.setText(currentItem.getPayment_person());
        holder.paymentAmount.setText("Rs."+currentItem.getPayment_amount());
        holder.paymentDate.setText(currentItem.getPayment_date());
        //holder.paymentImg.setImageBitmap(currentItem.getPayment_image());
        //String paymentType= (String) holder.paymentType.getText();

        String type=currentItem.getPayment_type_code();

        if (type.equals("I")) {
            holder.paymentImg.setImageResource(R.drawable.ic_received);
        }
        if (type.equals("E")){
            holder.paymentImg.setImageResource(R.drawable.ic_expense);
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ExtraViewHolder extends RecyclerView.ViewHolder {


        TextView paymentType,paymentTo,paymentDate,paymentAmount;
        CircularImageView paymentImg;
        public ExtraViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            paymentType=itemView.findViewById(R.id.Received_From_Id);
            paymentTo=itemView.findViewById(R.id.Received_Name_Id);
            paymentDate=itemView.findViewById(R.id.Extra_Date_Text_Id);
            paymentAmount=itemView.findViewById(R.id.ExtraAmountId);
            paymentImg=itemView.findViewById(R.id.ExtraImgId);


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
