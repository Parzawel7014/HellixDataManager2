package com.example.atilagapps.hellixdatamanager.Students;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atilagapps.hellixdatamanager.R;

import java.util.ArrayList;
import java.util.List;

public class FindStudentRecyclerAdapter extends RecyclerView.Adapter<FindStudentRecyclerAdapter.FIndStudentViewHolder> implements Filterable {


    private List<FindStudent> mList;
    private List<FindStudent> mListFull;

    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }

    public FindStudentRecyclerAdapter(List<FindStudent> list) {
        this.mList = list;
        mListFull=new ArrayList<>(mList);
    }



    @NonNull
    @Override
    public FIndStudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.find_student_card, parent, false);
        FIndStudentViewHolder vh=new FIndStudentViewHolder(v,mListener);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull FIndStudentViewHolder holder, int position) {

        FindStudent currentItem=mList.get(position);
        holder.StudentNameText.setText(currentItem.getmStudentName());


    }

    @Override
    public int getItemCount() {

            return mList.size();
    }

    @Override
    public Filter getFilter() {
        return StudentFilter;
    }

    private Filter StudentFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<FindStudent> filteredList=new ArrayList<>();
            if (constraint==null||constraint.length()==0){
                filteredList.addAll(mListFull);
            }else {
                String filterPattern=constraint.toString().toLowerCase().trim();

                for (FindStudent item:mListFull){
                    if (item.getmStudentName().toLowerCase().contains(filterPattern)){
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

    public static class FIndStudentViewHolder extends RecyclerView.ViewHolder{

        public TextView StudentNameText;


        public FIndStudentViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            StudentNameText=itemView.findViewById(R.id.FindStudentNameCardId);
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
