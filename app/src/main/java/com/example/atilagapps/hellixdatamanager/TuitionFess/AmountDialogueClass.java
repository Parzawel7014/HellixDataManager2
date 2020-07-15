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
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
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

    private TextInputEditText amountEdit, RegEditText,paidWith,receivedBy;
    private AmountDialogueListener listener;
    private String regStatus, tableName, StudentID, monthlyPayment;
    private int paymentVal;
    ProgressDialog progressDialog;
    String remainingAmt, Reg_amt;
    String selectedText,admin1,admin2;
    String srNo;
    String StudentName;
    String TuitionName;
    String contact;

    MaterialCheckBox checkBox,smsCheckBox;

    ImageView altImg;
    int position;


    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        final DataBaseHelper db = new DataBaseHelper(getActivity());

        MaterialAlertDialogBuilder builder=new MaterialAlertDialogBuilder(requireActivity());
      //  AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.payment_dialogue, null);


        if (getArguments() != null) {
            StudentClass studentClass = getArguments().getParcelable("RegAmountPaid");
            assert studentClass != null;
            StudentName= studentClass.getStudentName();
            StudentID = studentClass.getStudentId();
            tableName = getArguments().getString("TableName");

            remainingAmt = studentClass.getRemainingPayment();
            Reg_amt = db.getRegFee(tableName);
            admin1=getArguments().getString("Admin1");
            admin2=getArguments().getString("Admin2");
            TuitionName=getArguments().getString("tuitionName");
            srNo= studentClass.getSrNo();
            position=getArguments().getInt("Position");
            regStatus =studentClass.getRegFeePaymentStatus();
            contact = db.getContact(StudentID);


                    //db.getRegFeePaymentStatus(tableName, StudentID);

        }

       // Log.d("ADebugTag", "Value: " + StudentID);

        checkBox=v.findViewById(R.id.closeTransactCheckBoxId);
        smsCheckBox=v.findViewById(R.id.sendMessageCheckBoxId);
        altImg=v.findViewById(R.id.instructId);

        amountEdit = v.findViewById(R.id.amountPaidEditText);
        RegEditText = v.findViewById(R.id.regAmountPaidEditId);
        paidWith=v.findViewById(R.id.PaidWithId);
        receivedBy=v.findViewById(R.id.ReceivedById);
        amountEdit.setText(remainingAmt);



        altImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder mBuilder=new MaterialAlertDialogBuilder(requireActivity());
                mBuilder.setTitle("Instruction");
                mBuilder.setIcon(R.drawable.alert);
                mBuilder.setMessage("By clicking the checkbox the transaction for this month will be closed.Meaning their will be no pending" +
                        "amount remaining.Can be used if you want to collect less amount than the monthly amount for that month");
                mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                     dialog.dismiss();
                    }
                });
                mBuilder.show();
            }
        });


        final String[] payMethod = new String[]{
                "CASH",
                "G-PAY/PhonePay",
                "OTHERS",
        };



        final String[] admins=db.getAdminNames();



        paidWith.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AlertDialog.Builder mBuilder=new AlertDialog.Builder(getActivity());



                MaterialAlertDialogBuilder mBuilder=new MaterialAlertDialogBuilder(requireActivity());
                mBuilder.setTitle("Paid With");

                mBuilder.setItems(payMethod, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedText = Arrays.asList(payMethod).get(which);
                        paidWith.setText(selectedText);
                    }
                }).show();
             //   AlertDialog mDialogue = mBuilder.create();
              //  mDialogue.show();

            }
        });

        receivedBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // AlertDialog.Builder mBuilder=new AlertDialog.Builder(getActivity());

                MaterialAlertDialogBuilder mBuilder=new MaterialAlertDialogBuilder(requireActivity());

                mBuilder.setTitle("Received By");

                mBuilder.setItems(admins, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String s=Arrays.asList(admins).get(which);
                        receivedBy.setText(s);
                    }
                }).show();

              //  AlertDialog mDialogue = mBuilder.create();
                //mDialogue.show();
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
                    @SuppressLint({"SimpleDateFormat", "UnlocalizedSms"})
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String amount = amountEdit.getText().toString();
                        listener.getAmount(amount,position);
                        paymentVal = Integer.parseInt(amount);
                        String paymentBy=paidWith.getText().toString().trim();
                        String payedTo=receivedBy.getText().toString().trim();

                        if (amountEdit != null) {

                            if (checkBox.isChecked()){

                                boolean res=db.UpdateFeeTableIsChecked(tableName, StudentID, amount,paymentBy,payedTo,srNo);
                                if (res){

                                    if (smsCheckBox.isChecked()) {
                                    ArrayList<PaymentRecievedSMSClass> paymentRecievedSMSClasses = new ArrayList<>();
                                    paymentRecievedSMSClasses = db.getpaymentRecievedSMS(tableName, srNo);

                                        SmsManager smsManager = SmsManager.getDefault();
                                        String finalSMS = "Dear " + StudentName + "," + "\n" + "We have received your tuition fee payment for the month-" + "\n" +
                                                paymentRecievedSMSClasses.get(0).getDate() + "/" + paymentRecievedSMSClasses.get(0).getMonth() + "/" + paymentRecievedSMSClasses.get(0).getYear() + "\n" +
                                                " Have a nice day!" + "\n" +
                                                "-" + TuitionName;
                                        contact = db.getContact(StudentID);
                                        smsManager.sendTextMessage(contact, null, finalSMS, null, null);
                                    }
                                }
                            }

                            else {
                                boolean result2 = db.UpdateFeeTable(tableName, StudentID, amount, paymentBy, payedTo, srNo);
                                if (result2) {

                                    if (smsCheckBox.isChecked()) {

                                        ArrayList<PaymentRecievedSMSClass> paymentRecievedSMSClasses = new ArrayList<>();
                                        paymentRecievedSMSClasses = db.getpaymentRecievedSMS(tableName, srNo);

                                        try {

                                            SmsManager smsManager = SmsManager.getDefault();
                                            String finalSMS = "Dear " + StudentName + "," + "\n" + "We have received your tuition fee payment for the month-" + "\n" +
                                                    paymentRecievedSMSClasses.get(0).getDate() + "/" + paymentRecievedSMSClasses.get(0).getMonth() + "/" + paymentRecievedSMSClasses.get(0).getYear() + "\n" +
                                                    " Have a nice day!" + "\n" +
                                                    "-" + TuitionName;
                                            smsManager.sendTextMessage(contact, null, finalSMS, null, null);

                                        } catch (Exception e) {
                                            Toast.makeText(requireActivity(), "Your SMS sent has failed!", Toast.LENGTH_LONG).show();
                                            e.printStackTrace();
                                        }
                                        //  Toast.makeText(getActivity(), "Successful", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }

                        if (regStatus.equals("Pending")) {
                            //  String PaidVal=db.getRegFeePaid(tableName,StudentID);
                            //RegEditText.setText(PaidVal);
                            String regAmount = Objects.requireNonNull(RegEditText.getText()).toString().trim();
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
        } //else {*/

        //}


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
        void getAmount(String amount,int position);

    }
}







