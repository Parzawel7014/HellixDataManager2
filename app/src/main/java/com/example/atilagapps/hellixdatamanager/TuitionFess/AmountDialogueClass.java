package com.example.atilagapps.hellixdatamanager.TuitionFess;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.util.LocaleData;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.example.atilagapps.hellixdatamanager.BuildConfig;
import com.example.atilagapps.hellixdatamanager.DataBaseHelper;
import com.example.atilagapps.hellixdatamanager.R;
import com.example.atilagapps.hellixdatamanager.Reciept.Common;
import com.example.atilagapps.hellixdatamanager.Reciept.CreatePDF;
import com.example.atilagapps.hellixdatamanager.Reciept.PdfDocumentAdapter;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class AmountDialogueClass extends DialogFragment {

    private EditText amountEdit, RegEditText,paidWith,receivedBy;
    private AmountDialogueListener listener;
    private StudentClass studentClass;
    private String regStatus, tableName, StudentID, monthlyPayment;
    private int paymentVal;
    ProgressDialog progressDialog;
    String remainingAmt, Reg_amt;
    String selectedText,admin1,admin2;



    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        final DataBaseHelper db = new DataBaseHelper(getActivity());


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.payment_dialogue, null);


        if (getArguments() != null) {
            studentClass = getArguments().getParcelable("RegAmountPaid");
            assert studentClass != null;

            StudentID = studentClass.getStudentId();
            tableName = getArguments().getString("TableName");
            regStatus = db.getRegFeePaymentStatus(tableName, StudentID);
            remainingAmt = studentClass.getRemainingPayment();
            Reg_amt = db.getRegFee(tableName);
            admin1=getArguments().getString("Admin1");
            admin2=getArguments().getString("Admin2");

        }

        amountEdit = v.findViewById(R.id.amountPaidEditText);
        RegEditText = v.findViewById(R.id.regAmountPaidEditId);
        paidWith=v.findViewById(R.id.PaidWithId);
        receivedBy=v.findViewById(R.id.ReceivedById);
        amountEdit.setText(remainingAmt);



        final String[] payMethod = new String[]{
                "CASH",
                "G-PAY/PhonePay",
                "OTHERS",
        };



        final String[] admins=new String[]{
        admin1,admin2
        };



        paidWith.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder=new AlertDialog.Builder(getActivity());

                mBuilder.setItems(payMethod, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedText = Arrays.asList(payMethod).get(which);
                        paidWith.setText(selectedText);
                    }
                });
                AlertDialog mDialogue = mBuilder.create();
                mDialogue.show();

            }
        });

        receivedBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder=new AlertDialog.Builder(getActivity());
                mBuilder.setItems(admins, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String s=Arrays.asList(admins).get(which);
                        receivedBy.setText(s);
                    }
                });

                AlertDialog mDialogue = mBuilder.create();
                mDialogue.show();
            }
        });




        builder.setView(v)
                .setTitle("Amount")
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
                        String amount = amountEdit.getText().toString();
                        listener.getAmount(amount);
                        paymentVal = Integer.parseInt(amount);
                        String paymentBy=paidWith.getText().toString().trim();
                        String payedTo=receivedBy.getText().toString().trim();

                        if (amountEdit != null) {
                            boolean result2 = db.UpdateFeeTable(tableName, StudentID, amount,paymentBy,payedTo);
                            if (result2) {
                                Toast.makeText(getActivity(), "Successful", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "UnSuccessful", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Please Enter Amount", Toast.LENGTH_SHORT).show();
                        }

                        if (regStatus.equals("Pending")) {
                            //  String PaidVal=db.getRegFeePaid(tableName,StudentID);
                            //RegEditText.setText(PaidVal);
                            String regAmount = RegEditText.getText().toString().trim();
                            int newReg_amt = Integer.parseInt(Reg_amt);
                            int newRegAmount = Integer.parseInt(regAmount);

                            if (newRegAmount <= newReg_amt) {
                                boolean result = db.updateRegAmtStatus(tableName, StudentID, regAmount, Reg_amt);

                                if (result) {
                                    Toast.makeText(getActivity(), "Ok", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "Not Ok", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "Registration Amount Exceeds LIMIT", Toast.LENGTH_SHORT).show();
                            }
                        }


                    }
                });


        if (regStatus.equals("Paid")) {

            RegEditText.setFocusable(false);
            RegEditText.setEnabled(false);
            RegEditText.setCursorVisible(false);
            RegEditText.setKeyListener(null);
            RegEditText.setBackgroundColor(Color.TRANSPARENT);
            RegEditText.setText(regStatus);
        } else {
            String PaidVal = db.getRegFeePaid(tableName, StudentID);
            RegEditText.setText(PaidVal);
        }


        return builder.create();
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







