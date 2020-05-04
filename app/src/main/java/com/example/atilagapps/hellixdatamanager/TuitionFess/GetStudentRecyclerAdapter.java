package com.example.atilagapps.hellixdatamanager.TuitionFess;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atilagapps.hellixdatamanager.DataBaseHelper;
import com.example.atilagapps.hellixdatamanager.R;
import com.example.atilagapps.hellixdatamanager.Students.FindStudent;
import com.example.atilagapps.hellixdatamanager.Students.FindStudentRecyclerAdapter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

public class GetStudentRecyclerAdapter extends RecyclerView.Adapter<GetStudentRecyclerAdapter.GetStudentViewHolder> implements Filterable {

    private List<StudentClass> mList;
    private List<StudentClass> mListFull;

   DataBaseHelper db=new DataBaseHelper(null);
    public GetStudentRecyclerAdapter(List<StudentClass> mList) {
        this.mList = mList;
        mListFull=new ArrayList<>(mList);
    }





    private OnItemClickListener mListener;

    @Override
    public Filter getFilter() {
        return StudentFilter;
    }

    private Filter StudentFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<StudentClass> filteredList=new ArrayList<>();
            if (constraint==null||constraint.length()==0){
                filteredList.addAll(mListFull);
            }else {
                String filterPattern=constraint.toString().toLowerCase().trim();

                for (StudentClass item:mListFull){
                    if (item.getStudentName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=filteredList;

            return results;
        }


        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            mList.clear();
            mList.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };



        public interface OnItemClickListener{
        void onItemClick(int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }

    @NonNull
    @Override
    public GetStudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.get_students_card,parent,false);

        GetStudentViewHolder vh=new GetStudentViewHolder(v,mListener);
        //DataBaseHelper db=new DataBaseHelper(v.getContext());

        return vh;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull GetStudentViewHolder holder, int position) {


        StudentClass currentItem=mList.get(position);
        holder.studentName.setText(currentItem.getStudentName());
        holder.regAmountPaid.setText(currentItem.getRegFeePaymentStatus());
        holder.monthlyRemainingAmount.setText(currentItem.getRemainingPayment());
        holder.IDText.setText(currentItem.getStudentId());

    }



    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class GetStudentViewHolder extends RecyclerView.ViewHolder {

        TextView studentName,regAmountPaid,monthlyRemainingAmount,IDText;

        public GetStudentViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);

            studentName=itemView.findViewById(R.id.StudentNameTextIdCard);
            regAmountPaid=itemView.findViewById(R.id.RegAmountPaidStatusId);
            monthlyRemainingAmount=itemView.findViewById(R.id.MonthlyAmountStatusId);
            IDText=itemView.findViewById(R.id.dateId);
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
