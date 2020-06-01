package com.example.atilagapps.hellixdatamanager.TuitionFess;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atilagapps.hellixdatamanager.Batches.SubjectAdapter;
import com.example.atilagapps.hellixdatamanager.DataBaseHelper;
import com.example.atilagapps.hellixdatamanager.R;
import com.example.atilagapps.hellixdatamanager.Reciept.Common;
import com.example.atilagapps.hellixdatamanager.Reciept.CreatePDF;
import com.example.atilagapps.hellixdatamanager.Reciept.PdfDocumentAdapter;
import com.example.atilagapps.hellixdatamanager.Students.FindStudentRecyclerAdapter;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Students_In_Batch extends AppCompatActivity implements AmountDialogueClass.AmountDialogueListener {

    private RecyclerView mRecyclerView;
    private GetStudentRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    TextView monthlyText;
    String MonthlyPayment;
    StudentClass studentClass;
    ArrayList<StudentClass> studentClasses;
    String TableName;

    HashMap<String, String> data = new HashMap<String, String>();


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mAdapter.notifyDataSetChanged();
        DataBaseHelper db = new DataBaseHelper(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students__in__batch);

        final DataBaseHelper db = new DataBaseHelper(this);
        //studentClasses.clear();


        studentClasses = new ArrayList<>();


        Toolbar toolbar=findViewById(R.id.StuInBatchActivityToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("STUDENTS");
        //getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        Intent intent = getIntent();
        SubjectAdapter subjectAdapter = intent.getParcelableExtra("Name");
        assert subjectAdapter != null;
        String name = subjectAdapter.getMsubject();
        String time = subjectAdapter.getmTime();


        String newBatchName = name.replace(" ", "_");
        String newBatchTime = time.replace(":", "_");
        newBatchTime = newBatchTime.replace(" ", "_");

       TableName = newBatchName + newBatchTime;


        LocalDate date = LocalDate.now();
        studentClasses = db.getUnpaidStudent(TableName);


        /*    int month = db.getLastMonth(TableName);
            int year = db.getLastYear(TableName);

            int curr_mont = date.getMonthValue();
            int curr_year = date.getYear();
            int curr_day = date.getDayOfMonth();


            int occ = db.getOccurrence(TableName, curr_mont);
            if (occ != 1) {
                if (curr_year == year) {
                    if (curr_mont == (month + 1)) {
                        boolean res = db.insertIntoFeesTable(TableName, curr_mont, curr_year);
                        if (res) {
                            Toast.makeText(this, "Ok", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "No Student", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                if (curr_year > (year)) {
                    boolean res = db.insertIntoFeesTable(TableName, curr_mont, curr_year);
                    if (res) {
                        Toast.makeText(this, "Ok", Toast.LENGTH_SHORT).show();

                    }
                }
            }
*/


        mRecyclerView = findViewById(R.id.GetStudentRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new GetStudentRecyclerAdapter(studentClasses);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


        final ArrayList<StudentClass> finalStudentClass = studentClasses;


        mAdapter.notifyDataSetChanged();


        mAdapter.setOnItemClickListener(new GetStudentRecyclerAdapter.OnItemClickListener() {

            // StudentClass studentClass = null;
            @Override
            public void onItemClick(int position) {
                String Id = finalStudentClass.get(position).getStudentId();

                SharedPreferences sharedPreferences=getSharedPreferences("TuitionInfo",MODE_PRIVATE);
                String admin1=sharedPreferences.getString("Tuition Admin1","");
                String admin2=sharedPreferences.getString("Tuition Admin2","");
                String tuitionName=sharedPreferences.getString("Tuition Name","");

                Bundle bundle = new Bundle();
                bundle.putParcelable("RegAmountPaid", finalStudentClass.get(position));
                bundle.putString("TableName", TableName);
                bundle.putString("MonthlyPayment", MonthlyPayment);
                bundle.putString("Admin1",admin1);
                bundle.putString("Admin2",admin2);
                bundle.putString("tuitionName",tuitionName);
                AmountDialogueClass amountDialogueClass = new AmountDialogueClass();
                amountDialogueClass.setArguments(bundle);
                amountDialogueClass.show(getSupportFragmentManager(), null);

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.find_student_menu,menu);
        MenuItem searchItem=menu.findItem(R.id.action_search);
        androidx.appcompat.widget.SearchView searchView=(androidx.appcompat.widget.SearchView)searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ((GetStudentRecyclerAdapter) mAdapter).getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getAmount(String amount) {
        MonthlyPayment = amount;

        Intent intent = getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        startActivity(intent);

    }



}