package com.example.atilagapps.hellixdatamanager.StaffManager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.example.atilagapps.hellixdatamanager.Batches.BatchesActivity;
import com.example.atilagapps.hellixdatamanager.DataBaseHelper;
import com.example.atilagapps.hellixdatamanager.R;
import com.example.atilagapps.hellixdatamanager.TuitionFess.PaymentRecievedSMSClass;
import com.example.atilagapps.hellixdatamanager.TuitionFess.StudentClass;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class StaffAmountDialogueClass extends DialogFragment {


    private AmountDialogueListener listener;
    StaffPaymentClass staffPaymentClass;
    String id;
    String month,year;
    String amountToPay;

    String TableName,selectedText;



    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        final DataBaseHelper db = new DataBaseHelper(getActivity());


        MaterialAlertDialogBuilder mBuilder=new MaterialAlertDialogBuilder(getActivity());

       // AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.staff_payment_dialogue, null);


        final TextInputEditText toPayEditText=v.findViewById(R.id.salaryAmountPaidEditId);
        final TextInputEditText paidWithEditText=v.findViewById(R.id.salaryAmountPayWithEditId);



        if (getArguments() != null) {
            staffPaymentClass=getArguments().getParcelable("StaffPaymentAct");
            assert staffPaymentClass != null;
            month=staffPaymentClass.getForMonth();
            id=getArguments().getString("StaffId");
            TableName=staffPaymentClass.getTableName();
        }


        amountToPay=db.getStaffAmountToPay(id,month,TableName);

        toPayEditText.setText(amountToPay);



        final String[] payMethod = new String[]{
                "CASH",
                "G-PAY"
        };


        paidWithEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MaterialAlertDialogBuilder mBuilder=new MaterialAlertDialogBuilder(getActivity());

                //AlertDialog.Builder mBuilder=new AlertDialog.Builder(getActivity());

                mBuilder.setItems(payMethod, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedText = Arrays.asList(payMethod).get(which);
                        paidWithEditText.setText(selectedText);
                    }
                });
                //AlertDialog mDialogue = mBuilder.create();
                mBuilder.show();
            }
        });







        mBuilder.setView(v)
                .setTitle("Salary Payment")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Pay", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @SuppressLint("SimpleDateFormat")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        String finalAmount= Objects.requireNonNull(toPayEditText.getText()).toString().trim();
                        listener.getAmount(finalAmount);
                        int finalAmt=Integer.parseInt(finalAmount);
                        boolean res=db.UpdateStaffFeeTable(id,TableName,finalAmt,month,selectedText);
                        if (res){
                            Toast.makeText(getActivity(), "Fees Paid", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getActivity(), "Fees Not Paid", Toast.LENGTH_SHORT).show();
                        }


                    }
                });



        return mBuilder.create();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (AmountDialogueListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement Listener");
        }
    }

    public interface AmountDialogueListener {
        void getAmount(String amount);

    }
}







