package com.example.atilagapps.hellixdatamanager.Charts;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.example.atilagapps.hellixdatamanager.Batches.BatchesActivity;
import com.example.atilagapps.hellixdatamanager.DataBaseHelper;
import com.example.atilagapps.hellixdatamanager.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class AddIncomeExenseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner Spinner;
    String paymentType;
    TextInputLayout l1;
    TextInputLayout l2;
    MaterialButton submitExpButton;
    TextInputEditText amountEdit, ReceivedEdit, ToEdit, paymentDateEdit, paymentDescEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income_exense);

        Spinner = findViewById(R.id.inc_exp_spinner_id);

        final DataBaseHelper db = new DataBaseHelper(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.PaymentType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner.setAdapter(adapter);
        Spinner.setOnItemSelectedListener(this);

        l1 = findViewById(R.id.ReceivedFromHiddenId);
        l2 = findViewById(R.id.PaidToHiddenId);

        paymentDateEdit = findViewById(R.id.PaymentDateEditTextId);
        paymentDescEdit = findViewById(R.id.PaymentDescriptionEditTextId);
        amountEdit = findViewById(R.id.PaymentAmtEditTextId);
        ReceivedEdit = findViewById(R.id.ReceivedFromEditTextId);
        ToEdit = findViewById(R.id.PaidToEditTextId);
        submitExpButton = findViewById(R.id.SubmitExpButtonId);

        Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int finalMonth = month + 1;
        final int day = c.get(Calendar.DAY_OF_MONTH);


        Toolbar toolbar=findViewById(R.id.AddInExToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Transaction");
        //getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        paymentDateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        paymentDateEdit.setText(dayOfMonth + "/" + finalMonth + "/" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();


            }
        });


        submitExpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (paymentType.equals("Income")) {
                    if (!validatePerson() | !validatePAmount() | !validatePDate()) {
                        return;
                    } else {
                        String paidTo = ToEdit.getText().toString().trim();
                        String receivedFrom = ReceivedEdit.getText().toString().trim();
                        String amount = amountEdit.getText().toString().trim();
                        String date = paymentDateEdit.getText().toString().trim();
                        String desc = paymentDescEdit.getText().toString().trim();


                        db.insertIntoExtraInExTable(paymentType, paidTo, receivedFrom, amount, date, desc);
                        startActivity(new Intent(getApplicationContext(),ExtraIncomeExpense.class));
                        finish();


                    }

                } else if (paymentType.equals("Expense")) {
                    if (!validatePersonTo() | !validatePAmount() | !validatePDate()) {
                        return;
                    } else {
                        String paidTo = ToEdit.getText().toString().trim();
                        String receivedFrom = ReceivedEdit.getText().toString().trim();
                        String amount = amountEdit.getText().toString().trim();
                        String date = paymentDateEdit.getText().toString().trim();
                        String desc = paymentDescEdit.getText().toString().trim();


                        db.insertIntoExtraInExTable(paymentType, paidTo, receivedFrom, amount, date, desc);

                        startActivity(new Intent(getApplicationContext(),ExtraIncomeExpense.class));
                        finish();


                    }
                }else if (paymentType.equals("Select Payment Type")){

                    MaterialAlertDialogBuilder reconfirmBuilder=new MaterialAlertDialogBuilder(AddIncomeExenseActivity.this);
                    //AlertDialog.Builder reconfirmBuilder = new AlertDialog.Builder(BatchesActivity.this);
                    reconfirmBuilder.setTitle("Alert");
                    reconfirmBuilder.setCancelable(false);
                    reconfirmBuilder.setMessage("Please select the payment type");
                    reconfirmBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();

                }




            }

        });


    }

    private boolean validatePerson() {


        String eduInput = ReceivedEdit.getText().toString().trim();
        if (eduInput.isEmpty()) {
            ReceivedEdit.setError("Field can't be empty");
            return false;
        } else {
            ReceivedEdit.setError(null);
            // editTextEmail.setErrorEnabled(false);
            return true;
        }

    }


    private boolean validatePersonTo() {


        String eduInput = ToEdit.getText().toString().trim();
        if (eduInput.isEmpty()) {
            ToEdit.setError("Field can't be empty");
            return false;
        } else {
            ToEdit.setError(null);
            // editTextEmail.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validatePAmount() {


        String amountInput = amountEdit.getText().toString().trim();
        if (amountInput.isEmpty()) {
            amountEdit.setError("Field can't be empty");
            return false;
        } else {
            amountEdit.setError(null);
            // editTextEmail.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validatePDate() {


        String amountInput = paymentDateEdit.getText().toString().trim();
        if (amountInput.isEmpty()) {
            paymentDateEdit.setError("Field can't be empty");
            return false;
        } else {
            paymentDateEdit.setError(null);
            // editTextEmail.setErrorEnabled(false);
            return true;
        }

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        paymentType = parent.getItemAtPosition(position).toString();
        if (paymentType.equals("Income")) {
            l1.setVisibility(View.VISIBLE);
            l2.setVisibility(View.GONE);
        }
        if (paymentType.equals("Expense")) {
            l1.setVisibility(View.GONE);
            l2.setVisibility(View.VISIBLE);
        }

        if (paymentType.equals("Select Payment Type")){
            l1.setVisibility(View.GONE);
            l2.setVisibility(View.GONE);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}