package com.example.atilagapps.hellixdatamanager.StaffManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.atilagapps.hellixdatamanager.DataBaseHelper;
import com.example.atilagapps.hellixdatamanager.R;
import com.example.atilagapps.hellixdatamanager.TuitionFess.GetStudentRecyclerAdapter;

import java.util.ArrayList;

public class StaffPaymentActivity extends AppCompatActivity implements StaffAmountDialogueClass.AmountDialogueListener {

    StaffPaymentRecyclerAdapter mAdapter;
    String StaffName,StaffId;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    String TableName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_payment);

      //  RecyclerView recyclerView=findViewById(R.id.StaffPayRecyclerViewId);
        DataBaseHelper db=new DataBaseHelper(this);

        Intent intent=getIntent();
       Staff_Class staff_class=intent.getParcelableExtra("StaffNameId");
        assert staff_class != null;
        StaffName=staff_class.getStaff_name();
        StaffId=staff_class.getId();



        ArrayList<StaffPaymentClass> staffPaymentClasses=new ArrayList<>();
        staffPaymentClasses=db.getStaffPaymentInfo(StaffId);


        mRecyclerView = findViewById(R.id.StaffPayRecyclerViewId);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new StaffPaymentRecyclerAdapter(staffPaymentClasses);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        final ArrayList<StaffPaymentClass> finalStaffPaymentClasses = staffPaymentClasses;
        mAdapter.setOnItemClickListener(new StaffPaymentRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Bundle bundle=new Bundle();
                bundle.putString("StaffId", StaffId);
                bundle.putParcelable("StaffPaymentAct", finalStaffPaymentClasses.get(position));
                StaffAmountDialogueClass staffAmountDialogueClass=new StaffAmountDialogueClass();
                staffAmountDialogueClass.setArguments(bundle);
                staffAmountDialogueClass.show(getSupportFragmentManager(),null);


            }
        });



    }

    @Override
    public void getAmount(String amount) {
        Intent intent = getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        startActivity(intent);
    }
}