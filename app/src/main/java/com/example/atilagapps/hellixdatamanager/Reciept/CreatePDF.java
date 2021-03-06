package com.example.atilagapps.hellixdatamanager.Reciept;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.example.atilagapps.hellixdatamanager.Batches.BatchesActivity;
import com.example.atilagapps.hellixdatamanager.Batches.NewBatchAddActivity;
import com.example.atilagapps.hellixdatamanager.DataBaseHelper;
import com.example.atilagapps.hellixdatamanager.MainActivity;
import com.example.atilagapps.hellixdatamanager.R;
import com.example.atilagapps.hellixdatamanager.Students.FindStudent;
import com.example.atilagapps.hellixdatamanager.Students.RegSubClass;
import com.example.atilagapps.hellixdatamanager.TuitionFess.ReceiptMonthClass;
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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

public class CreatePDF extends AppCompatActivity {

    Button button;
    TextInputEditText batchName, feeMonth, feeYear;
    String[] listItems;
    String tableName, batchname, batchTime;
    String monthVal, yearVal;
    String stuRegId, stuName, id;

    String TuitionName,TuitionAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_p_d_f);
        button = findViewById(R.id.pdfbutton);
        batchName = findViewById(R.id.BatchNameEditId);
        feeMonth = findViewById(R.id.FeeMonthEditId);
        feeYear = findViewById(R.id.FeeYearEditId);
        final DataBaseHelper db = new DataBaseHelper(this);
       // SharedPreferences sharedPreferences=getSharedPreferences("TuitionInfo",MODE_PRIVATE);
        TuitionName=db.getTuitionName();
        TuitionAddress=db.getTuitionAddress();

        final LinearLayout ll=findViewById(R.id.hidLLId);

        Toolbar toolbar=findViewById(R.id.PDFToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Generate Receipt");
        //getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        FindStudent findStudent = intent.getParcelableExtra("STUDENTIDPDF");

        assert findStudent != null;
        id = findStudent.getmStudentID();
        stuName = findStudent.getmStudentName();
        stuRegId = findStudent.getmStudentRegId();




        ArrayList<RegSubClass> regSubClasses = new ArrayList<>();
        regSubClasses = db.getAllRegisteredBathes(id);
        listItems = new String[regSubClasses.size()];

        for (int i = 0; i < regSubClasses.size(); i++) {
            listItems[i] = regSubClasses.get(i).getmBatchName();

        }


        final ArrayList<RegSubClass> finalRegSubClasses = regSubClasses;
        batchName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder mBuilder=new MaterialAlertDialogBuilder(CreatePDF.this,R.style.AlertDialogTheme);

               // AlertDialog.Builder mBuilder = new AlertDialog.Builder(CreatePDF.this);
                mBuilder.setTitle("Select Subjects");

                mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        batchName.setText(finalRegSubClasses.get(which).getmBatchName() + "->" + finalRegSubClasses.get(which).getmBatchTime());
                        String newBatchName = finalRegSubClasses.get(which).getmBatchName().replace(" ", "_");
                        String newBatchTime = finalRegSubClasses.get(which).getmBatchTime().replace(":", "_");
                        newBatchTime = newBatchTime.replace(" ", "_");
                        ll.setVisibility(View.VISIBLE);
                        tableName = newBatchName + newBatchTime;

                        batchname = finalRegSubClasses.get(which).getmBatchName();
                        batchTime = finalRegSubClasses.get(which).getmBatchTime();
                        dialog.dismiss();
                    }
                });
                //AlertDialog mDialogue = mBuilder.create();
                mBuilder.show();

            }

        });


        // final ArrayList<ReceiptMonthClass> finalReceiptMonths = ReceiptMonths;
        feeMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ReceiptMonthClass> ReceiptMonths = new ArrayList<>();
                ReceiptMonths = db.getReceiptMonths(tableName, id);
                String[] monthItem = new String[ReceiptMonths.size()];

                for (int i = 0; i < ReceiptMonths.size(); i++) {
                    monthItem[i] = ReceiptMonths.get(i).getMonth();
                }


                final ArrayList<ReceiptMonthClass> finalReceiptMonths = ReceiptMonths;

                if (finalReceiptMonths.isEmpty()){

                    MaterialAlertDialogBuilder mBuilder=new MaterialAlertDialogBuilder(CreatePDF.this,R.style.AlertDialogTheme);
                    //  AlertDialog.Builder mBuilder = new AlertDialog.Builder(CreatePDF.this);
                    mBuilder.setTitle("Alert");
                    mBuilder.setMessage("No Months To Fetch");
                    mBuilder.setIcon(R.drawable.alert);
                    mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                        }
                    });

                    //AlertDialog mDialogue = mBuilder.create();
                    mBuilder.show();

                }else {

                MaterialAlertDialogBuilder mBuilder=new MaterialAlertDialogBuilder(CreatePDF.this,R.style.AlertDialogTheme);
              //  AlertDialog.Builder mBuilder = new AlertDialog.Builder(CreatePDF.this);
                mBuilder.setTitle("Select Months");

                mBuilder.setSingleChoiceItems(monthItem, -1, new DialogInterface.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        feeMonth.setText(finalReceiptMonths.get(which).getMonth());
                        monthVal = finalReceiptMonths.get(which).getMonth();
                        dialog.dismiss();
                    }
                });
                //AlertDialog mDialogue = mBuilder.create();
                mBuilder.show();
            }
            }
        });

        feeYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<ReceiptMonthClass> ReceiptMonths = new ArrayList<>();
                ReceiptMonths = db.getReceiptMonths(tableName, id);
                String[] yearItem = new String[ReceiptMonths.size()];

                for (int i = 0; i < ReceiptMonths.size(); i++) {
                    yearItem[i] = ReceiptMonths.get(i).getYear();
                }
                final ArrayList<ReceiptMonthClass> finalReceiptMonths = ReceiptMonths;

                if (finalReceiptMonths.isEmpty()){
                    MaterialAlertDialogBuilder mBuilder=new MaterialAlertDialogBuilder(CreatePDF.this,R.style.AlertDialogTheme);
                    //  AlertDialog.Builder mBuilder = new AlertDialog.Builder(CreatePDF.this);
                    mBuilder.setTitle("Alert");
                    mBuilder.setMessage("No Years To Fetch");
                    mBuilder.setIcon(R.drawable.alert);
                    mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    //AlertDialog mDialogue = mBuilder.create();
                    mBuilder.show();

                }else {
                MaterialAlertDialogBuilder mBuilder=new MaterialAlertDialogBuilder(CreatePDF.this,R.style.AlertDialogTheme);
                //AlertDialog.Builder mBuilder = new AlertDialog.Builder(CreatePDF.this);
                mBuilder.setTitle("Select Year");

                mBuilder.setSingleChoiceItems(yearItem, -1, new DialogInterface.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        feeYear.setText(finalReceiptMonths.get(which).getYear());
                        yearVal = finalReceiptMonths.get(which).getYear();
                        dialog.dismiss();
                    }
                });
               // AlertDialog mDialogue = mBuilder.create();
                mBuilder.show();

            }}
        });


        Dexter.withActivity(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        button.setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onClick(View v) {

                                if (!validateBatch() | !validateMonth() | !validateYear()){
                                    return;
                                }else {
                                createPDFFile();}
                            }
                        });


                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                })
                .check();


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createPDFFile() {

        DataBaseHelper db = new DataBaseHelper(this);

        String path = Common.getAppPath(CreatePDF.this) + "test_pdf.pdf";
        if (new File(path).exists()) {
            new File(path).delete();
        }
        try {

            Document document = new Document();
            //Save
            PdfWriter.getInstance(document, new FileOutputStream(path));


            //Open to Write
            document.open();

            //Setting
            document.setPageSize(PageSize.A4);
            document.addCreationDate();
            document.addAuthor("Atilag");
            document.addCreator("Sam");


            //Font Setting
            BaseColor colorAccent = new BaseColor(0, 153, 204, 255);
            float fontSize = 12.0f;
            float valueFontSize = 26.0f;

            //Custom font
            BaseFont fontName = BaseFont.createFont("assets/fonts/brandon_medium.otf", "UTF-8", BaseFont.EMBEDDED);


            //Create Title
            Font CoachingTitle = new Font(fontName, 20.0f, Font.NORMAL, BaseColor.BLACK);
            addNewItem(document, TuitionName, Element.ALIGN_CENTER, CoachingTitle);
            Font address = new Font(fontName, 9.0f, Font.NORMAL, BaseColor.BLACK);
            addNewItem(document, TuitionAddress, Element.ALIGN_CENTER, address);
           // addNewItem(document, "Amravati, Maharashta", Element.ALIGN_CENTER, address);
            addLineSpace(document);
            addLineSeparator(document);
            addLineSpace(document);

            Font titleFont = new Font(fontName, 15.0f, Font.NORMAL, BaseColor.BLACK);
            addNewItem(document, "Fee Receipt ", Element.ALIGN_CENTER, titleFont);

            addLineSpace(document);
            addLineSpace(document);
            addLineSpace(document);


            //Add Date

            LocalDate today = LocalDate.now();
            String formattedDate = today.format(DateTimeFormatter.ofPattern("d/M/yyyy"));
            Font DateFont = new Font(fontName, fontSize, Font.NORMAL, colorAccent);
            //addNewItem(document,"Date: "+formattedDate,Element.ALIGN_LEFT,DateFont);

            //Add Receipt Number
            String r, receiptNumber;
            String ForIdReceiptNum=db.getReceiptNumberForId(id,monthVal,yearVal);
            if (ForIdReceiptNum==null) {
                ArrayList<String> ReceiptNum = new ArrayList<>();
                ReceiptNum = db.getReceiptNumber();

                do {
                    Random rnd = new Random();
                    int n = 1000000 + rnd.nextInt(9000000);
                    r = Integer.toString(n);
                }
                while (ReceiptNum.contains(r));
                receiptNumber = r;
                db.addReceiptNo(receiptNumber, monthVal, yearVal, id);
            }
            else {
                receiptNumber=ForIdReceiptNum;
            }


            Font ReceiptNumberFont = new Font(fontName, fontSize, Font.NORMAL, BaseColor.BLACK);
            addNewItemWithLeftAndRight(document, "Date: " + formattedDate, "Receipt number: " + receiptNumber, DateFont, ReceiptNumberFont);

            ArrayList<PaymentInfoClass> paymentInfoClasses = new ArrayList<>();
            paymentInfoClasses = db.getPaymentInfo(tableName, id, monthVal, yearVal);

            String paymentDate = paymentInfoClasses.get(0).getPaymentDate();


            Font forMonthFont = new Font(fontName, fontSize, Font.NORMAL, BaseColor.BLACK);
            addNewItem(document, "For Month: " + monthVal + "/" + yearVal, Element.ALIGN_LEFT, forMonthFont);

            Font paymentDateFont = new Font(fontName, fontSize, Font.NORMAL, BaseColor.BLACK);
            addNewItem(document, "Paid-On: " + paymentDate, Element.ALIGN_LEFT, paymentDateFont);

            Font batchNameFont = new Font(fontName, fontSize, Font.NORMAL, BaseColor.BLACK);
            addNewItem(document, "Batch Name: " + batchname, Element.ALIGN_LEFT, batchNameFont);

            Font batchTimeFont = new Font(fontName, fontSize, Font.NORMAL, BaseColor.BLACK);
            addNewItem(document, "Batch Time: " + batchTime, Element.ALIGN_LEFT, batchTimeFont);


            addNewItemWithLeftAndRight(document, "Name: " + stuName, "Registration No.: " + stuRegId, batchTimeFont, batchTimeFont);
            //addNewItemWithLeftAndRight(document,"12.0*1000","12000.0",titleFont,orderNumberValueFont);
            addLineSeparator(document);

            addLineSpace(document);


            String amountToPay = paymentInfoClasses.get(0).getAmountToPay();
            String paidAmount = paymentInfoClasses.get(0).getPaidAmount();
            String remainingAmount = paymentInfoClasses.get(0).getRemainingAmount();
            String paidWith = paymentInfoClasses.get(0).getPaidWith();
            String receivedBy = paymentInfoClasses.get(0).getReceivedBy();


            Font AmountToPayFont = new Font(fontName, fontSize, Font.NORMAL, BaseColor.BLACK);
            addNewItem(document, "Amount to be Paid:" + "  " + "Rs." + amountToPay, Element.ALIGN_RIGHT, AmountToPayFont);
            Font PaidAmountFont = new Font(fontName, fontSize, Font.NORMAL, BaseColor.BLACK);
            addNewItem(document, "Amount Paid:" + "  " + "Rs." + paidAmount, Element.ALIGN_RIGHT, PaidAmountFont);
            Font RemainingAmountFont = new Font(fontName, fontSize, Font.NORMAL, BaseColor.BLACK);
            addNewItem(document, "Amount Remaining:" + "  " + "Rs." + remainingAmount, Element.ALIGN_RIGHT, RemainingAmountFont);
            Font PaidByFont = new Font(fontName, fontSize, Font.NORMAL, BaseColor.BLACK);
            addNewItem(document, "Paid With:" + "  " + paidWith, Element.ALIGN_RIGHT, PaidByFont);

            addLineSeparator(document);

            addLineSpace(document);
            addLineSpace(document);

            Font ReceivedBy = new Font(fontName, fontSize, Font.NORMAL, BaseColor.BLACK);
            addNewItem(document, "Received By: " + receivedBy, Element.ALIGN_RIGHT, ReceivedBy);


            document.close();

            Toast.makeText(this, "Successfully PDF created", Toast.LENGTH_SHORT).show();


            printPDF();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void printPDF() {

        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
        try {
            PrintDocumentAdapter printDocumentAdapter = new PdfDocumentAdapter(CreatePDF.this, Common.getAppPath(getApplicationContext()) + "test_pdf.pdf");
            printManager.print("Document", printDocumentAdapter, new PrintAttributes.Builder().build());
        } catch (Exception ex) {
            Log.e("Atilag", "" + ex.getMessage());
        }

    }

    private void addNewItemWithLeftAndRight(Document document, String textLeft, String textRight, Font textLeftFont, Font textRightFont) throws DocumentException {

        Chunk chunkTextLeft = new Chunk(textLeft, textLeftFont);
        Chunk chunkTextRight = new Chunk(textRight, textRightFont);

        Paragraph p = new Paragraph(chunkTextLeft);
        p.add(new Chunk(new VerticalPositionMark()));
        p.add(chunkTextRight);
        document.add(p);

    }

    private void addLineSeparator(Document document) throws DocumentException {

        LineSeparator lineSeparator = new LineSeparator();
        lineSeparator.setLineColor(new BaseColor(0, 0, 0, 60));
        document.add(new Chunk(lineSeparator));
        addLineSpace(document);

    }

    private void addLineSpace(Document document) throws DocumentException {

        document.add(new Paragraph(""));

    }

    private void addNewItem(Document document, String text, int align, Font font) throws DocumentException {

        Chunk chunk = new Chunk(text, font);
        Paragraph paragraph = new Paragraph(chunk);
        paragraph.setAlignment(align);
        document.add(paragraph);


    }


    private boolean validateMonth(){
        String phoneInput=feeMonth.getText().toString().trim();
        if (phoneInput.isEmpty()){
            feeMonth.setError("Field can't be empty");
            return false;
        }else {
            feeMonth.setError(null);
          //  feeMonth.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateYear(){
        String phoneInput=feeYear.getText().toString().trim();
        if (phoneInput.isEmpty()){
            feeYear.setError("Field can't be empty");
            return false;
        }else {
            feeYear.setError(null);
            //  feeMonth.setErrorEnabled(false);
            return true;
        }
    }


    private boolean validateBatch(){
        String phoneInput=batchName.getText().toString().trim();
        if (phoneInput.isEmpty()){
            batchName.setError("Field can't be empty");
            return false;
        }else {
            batchName.setError(null);
            //  feeMonth.setErrorEnabled(false);
            return true;
        }
    }



}