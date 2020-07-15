package com.example.atilagapps.hellixdatamanager.Charts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.atilagapps.hellixdatamanager.DataBaseHelper;
import com.example.atilagapps.hellixdatamanager.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

public class IncomeExpenseDetailsActivity extends AppCompatActivity {


    TextView date,paymentType,paymentPerson,paymentAmount,paymentDesc;

    ImageView detailsImage;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_expense_details);


        DataBaseHelper db=new DataBaseHelper(this);
        Toolbar toolbar=findViewById(R.id.DetailsExtraIncomeExpenseToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Transaction Details");
        //getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent=getIntent();
        ExtraInExClass extraInExClass=intent.getParcelableExtra("PaymentDetails");

        assert extraInExClass != null;
        String id=extraInExClass.getId();


        ArrayList<ExtraDetailsClass> extraDetailsClasses=new ArrayList<>();
        extraDetailsClasses=db.getExtraPaymentInfo(id);


        date=findViewById(R.id.DetailsExtraDateId);
        paymentType=findViewById(R.id.Details_Received_Name_Id);
        paymentPerson=findViewById(R.id.Details_Received_From_Id);
        paymentAmount=findViewById(R.id.DetailsExtraAmountId);
        paymentDesc=findViewById(R.id.descriptionId);
        detailsImage=findViewById(R.id.DetailsExtraImgId);

        String E_date=extraDetailsClasses.get(0).getDate();
        String E_P_Type=extraDetailsClasses.get(0).getPaymentType();
        String E_P_P=extraDetailsClasses.get(0).getPaymentPerson();
        String E_P_Amount=extraDetailsClasses.get(0).getPaymentAmount();
        String E_P_Description=extraDetailsClasses.get(0).getPaymentDesc();
        String ExpenseCode=extraDetailsClasses.get(0).getPaymentTypeCode();

        if (ExpenseCode.equals("I")) {
            detailsImage.setImageResource(R.drawable.ic_received);
        }
        if (ExpenseCode.equals("E")){
            detailsImage.setImageResource(R.drawable.ic_expense);
        }


        date.setText(E_date);
        paymentType.setText(E_P_Type);
        paymentPerson.setText(E_P_P);
        paymentAmount.setText("Rs."+E_P_Amount);
        paymentDesc.setText(E_P_Description);


        if (E_P_Type.equals("I")){
            detailsImage.setImageResource(R.drawable.add_fees);
        }else if (E_P_Type.equals("E")){
            detailsImage.setImageResource(R.drawable.expense);
        }



    }
}