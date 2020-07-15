package com.example.atilagapps.hellixdatamanager.StaffManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atilagapps.hellixdatamanager.R;
import com.example.atilagapps.hellixdatamanager.Students.FindStudent;
import com.example.atilagapps.hellixdatamanager.Students.FindStudentRecyclerAdapter;
import com.google.android.material.button.MaterialButton;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

public class Staff_Recycler_Adapter extends RecyclerView.Adapter<Staff_Recycler_Adapter.Staff_ViewHolder> implements Filterable {



    private List<Staff_Class> mList;
    private List<Staff_Class> mListFull;

    private Staff_Recycler_Adapter.OnItemClickListener mListener;



    public interface OnItemClickListener{
        void onItemClick(int position);
        void onPaymentButtonClick(int position);
    }

    public void setOnItemClickListener(Staff_Recycler_Adapter.OnItemClickListener listener){
        mListener=listener;
    }

    public Staff_Recycler_Adapter(List<Staff_Class> mList) {
        this.mList = mList;
        mListFull=new ArrayList<>(mList);
    }



    @NonNull
    @Override
    public Staff_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.staff_card_layout, parent, false);
        Staff_ViewHolder vh=new Staff_ViewHolder(v,mListener);
        //notifyDataSetChanged();
        return vh;


    }

    @Override
    public void onBindViewHolder(@NonNull Staff_ViewHolder holder, int position) {
        Staff_Class currentItem=mList.get(position);
        holder.staff_name.setText(currentItem.getStaff_name());
        holder.staff_subject.setText(currentItem.getStaff_subject_name());
        if (currentItem.getStaff_image()!=null) {
            holder.image.setImageBitmap(currentItem.getStaff_image());
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    @Override
    public Filter getFilter() {
        return Staff_Filter;
    }

    private Filter Staff_Filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Staff_Class> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Staff_Class item : mListFull) {
                    if (item.getStaff_name().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mList.clear();
            mList.addAll((List) results.values);
            notifyDataSetChanged();
        }


    };
        public static class Staff_ViewHolder extends RecyclerView.ViewHolder {

            TextView staff_name,staff_subject;
            CircularImageView image;
            MaterialButton paymentBut;


            public Staff_ViewHolder(@NonNull View itemView, final OnItemClickListener  listener) {
            super(itemView);

            staff_name=itemView.findViewById(R.id.T_name_Id);
            staff_subject=itemView.findViewById(R.id.T_subject);
            image=itemView.findViewById(R.id.staff_Image_id);
            paymentBut=itemView.findViewById(R.id.paymentButtonId);

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

            paymentBut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        int position =getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION){
                            listener.onPaymentButtonClick(position);
                        }
                    }
                }
            });


        }
    }
}
