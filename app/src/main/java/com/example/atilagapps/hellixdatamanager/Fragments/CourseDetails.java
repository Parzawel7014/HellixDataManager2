package com.example.atilagapps.hellixdatamanager.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atilagapps.hellixdatamanager.Batches.BatchesActivity;
import com.example.atilagapps.hellixdatamanager.Batches.NewBatchAddActivity;
import com.example.atilagapps.hellixdatamanager.DataBaseHelper;
import com.example.atilagapps.hellixdatamanager.R;
import com.example.atilagapps.hellixdatamanager.Reciept.CreatePDF;
import com.example.atilagapps.hellixdatamanager.SharedViewModel;
import com.example.atilagapps.hellixdatamanager.Students.StudentAddActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;


public class CourseDetails extends Fragment implements AdapterView.OnItemSelectedListener {

    Button subjectButton;
    MaterialButton confirmButt;
    TextView amountTextV;
    int amount = 0;
    String[] listItems, timeItems, finalArray, finalTimeArray, regFeeArray;
    boolean[] checkedItem;
    boolean[] checkedItemFinal;
    ArrayList<Integer> mUserItems = new ArrayList<>();

    ArrayList<String> amtArray;
    byte[] image;
    ArrayList<Integer> mUserItemsFinal = new ArrayList<>();

    SharedViewModel viewModel;
    RelativeLayout relativeLayout;
    ArrayList<String> regIds;
    TextInputEditText dateEditText;

    HashMap<String, String> data = new HashMap<String, String>();
    int n = 0;
    private static final String TAG = "MyActivity";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String regId;
    String sub, time, amt, StudentNameString, AddressString, PhoneString, GenderString, CastString,EmailString,EduString;
    ArrayList<CoursesClass> coursesClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_course_details, container, false);

        subjectButton = v.findViewById(R.id.SubjectButt);
        amountTextV = v.findViewById(R.id.amountText);
        confirmButt = v.findViewById(R.id.ConfirmButton);
        dateEditText = v.findViewById(R.id.dateEditText);
        relativeLayout=v.findViewById(R.id.SelectedSubLayoutId);

        DataBaseHelper db = new DataBaseHelper(getContext());
        listItems = db.getDistinctDialogueLabels().toArray(new String[0]);
        checkedItem = new boolean[listItems.length];
        mRecyclerView = v.findViewById(R.id.CoursesRecyclerViewId);
        mRecyclerView.setHasFixedSize(true);
        subjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount = 0;
                OpenDialogue();
            }
        });

        regIds=new ArrayList<>();

        regIds=db.getAllRegistrationNumbers();


        Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int finalMonth=month+1;
        final int day = c.get(Calendar.DAY_OF_MONTH);

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateEditText.setText(dayOfMonth + "/" + finalMonth + "/" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });


        confirmButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!validateDate()) {
                    return;
                } else {

                    openRegistrationFeesDialogue();}


            }
        });


        return v;
    }



    private boolean validateDate(){
        String eduInput=dateEditText.getText().toString().trim();
        if (eduInput.isEmpty()){
            dateEditText.setError("Field can't be empty");
            return false;
        }else {
            dateEditText.setError(null);
            // editTextEmail.setErrorEnabled(false);
            return true;
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        viewModel.getName().observe(getViewLifecycleOwner(), new Observer<CharSequence>() {
            @Override
            public void onChanged(CharSequence name) {
                StudentNameString = name.toString();
            }
        });
        viewModel.getAddress().observe(getViewLifecycleOwner(), new Observer<CharSequence>() {
            @Override
            public void onChanged(CharSequence address) {
                AddressString = address.toString();
            }
        });
        viewModel.getPhone().observe(getViewLifecycleOwner(), new Observer<CharSequence>() {
            @Override
            public void onChanged(CharSequence phone) {
                PhoneString = phone.toString();
            }
        });

        viewModel.getCastValue().observe(getViewLifecycleOwner(), new Observer<CharSequence>() {
            @Override
            public void onChanged(CharSequence gender) {
                CastString = gender.toString();
            }
        });

        viewModel.getGenderValue().observe(getViewLifecycleOwner(), new Observer<CharSequence>() {
            @Override
            public void onChanged(CharSequence cast) {
                GenderString = cast.toString();
            }
        });

        viewModel.getEmailValue().observe(getViewLifecycleOwner(), new Observer<CharSequence>() {
            @Override
            public void onChanged(CharSequence email) {
                EmailString = email.toString();
            }
        });

        viewModel.getEduValue().observe(getViewLifecycleOwner(), new Observer<CharSequence>() {
            @Override
            public void onChanged(CharSequence edu) {
                EduString = edu.toString();
            }
        });

        viewModel.getProfilePic().observe(getViewLifecycleOwner(), new Observer<byte[]>() {
            @Override
            public void onChanged(byte[] img) {
                image = img;
            }
        });


    }

    private void OpenDialogue() {

        MaterialAlertDialogBuilder mBuilder=new MaterialAlertDialogBuilder(requireContext(),R.style.AlertDialogTheme);

       // AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        mBuilder.setTitle("Select Subjects");
        mBuilder.setMultiChoiceItems(listItems, checkedItem, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position, boolean isChecked) {

                if (isChecked) {

                    if (!mUserItems.contains(position)) {
                        mUserItems.add(position);
                        n = position;
                        openTimeDialogue(listItems[position]);

                    }
                } else if (mUserItems.contains(position)) {
                    mUserItems.remove((Integer) position);

                    data.remove(listItems[position]);

                }
            }
        });
        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String item = "";
                DataBaseHelper db = new DataBaseHelper(getContext());
                amtArray=new ArrayList<>();
                coursesClass = new ArrayList<>();
                mLayoutManager = new LinearLayoutManager(getContext());
                mAdapter = new CourseRecyclerAdapter(coursesClass);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);
                finalArray = new String[mUserItems.size()];
                //finalTimeArray = new String[mUserTimeItems.size()];
                finalTimeArray = new String[finalArray.length];

                if (finalArray.length!=0){
                    confirmButt.setVisibility(View.VISIBLE);

                }

                for (int i = 0; i < mUserItems.size(); i++) {
                    item = item + listItems[mUserItems.get(i)];
                    sub = listItems[mUserItems.get(i)];

                    finalArray[i] = listItems[mUserItems.get(i)];
                    finalTimeArray[i] = data.get(sub);

                    String BatchName = finalArray[i];
                    String BatchTime = finalTimeArray[i];
                    String newBatchName = BatchName.replace(" ", "_");
                    String newBatchTime = BatchTime.replace(":", "_");
                    newBatchTime = newBatchTime.replace(" ", "_");

                    String TableName = newBatchName + newBatchTime;

                    amt = db.getAmount(TableName);
                    amtArray.add(amt);


                    coursesClass.add(new CoursesClass(sub, data.get(sub), amt));

                    if (i != mUserItems.size() - 1) {
                        item = item + ",";
                    }
                }

                checkedItemFinal = new boolean[finalArray.length];
                Log.i(TAG, Arrays.toString(finalArray));

                for (int i = 0; i < mUserItems.size(); i++) {

                    amount+=Integer.parseInt(amtArray.get(i));
                    //amount += 500;
                }
                Toast.makeText(getContext(), "Val " + item, Toast.LENGTH_SHORT).show();
                amountTextV.setText(Integer.toString(amount));

                relativeLayout.setVisibility(View.VISIBLE);

            }
        });

        mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        mBuilder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < checkedItem.length; i++) {
                    checkedItem[i] = false;
                    mUserItems.clear();
                    amtArray=new ArrayList<>();
                    coursesClass = new ArrayList<>();
                    mLayoutManager = new LinearLayoutManager(getContext());
                    mAdapter = new CourseRecyclerAdapter(coursesClass);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);
                    confirmButt.setVisibility(View.GONE);
                }
            }
        });

       // AlertDialog mDialogue = mBuilder.create();
        mBuilder.show();

    }

    private void openTimeDialogue(final String listItem) {

        DataBaseHelper db = new DataBaseHelper(getContext());
        timeItems = db.getBatchTime(listItem).toArray(new String[0]);
        MaterialAlertDialogBuilder mBuilder=new MaterialAlertDialogBuilder(requireActivity(),R.style.AlertDialogTheme);

      //  AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        mBuilder.setTitle("Select Slot");
        mBuilder.setSingleChoiceItems(timeItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                time = timeItems[which];
                data.put(listItem, time);
                Toast.makeText(getContext(), "Selected Value" + timeItems[which], Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        mBuilder.setCancelable(false);
       // AlertDialog mDialogue = mBuilder.create();
        mBuilder.show();

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String label = parent.getItemAtPosition(position).toString();

        if (position != 0) {
            Toast.makeText(parent.getContext(), "You selected: " + label,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void insertData() {

        DataBaseHelper db = new DataBaseHelper(getContext());
        String date = dateEditText.getText().toString().trim();
        String r;
        do {
            Random rnd = new Random();
            int n = 10000 + rnd.nextInt(90000);
            r = Integer.toString(n);
        }
        while (regIds.contains(r));
        regId=r;

        boolean isInserted = db.insert_PersonalData_Stu_Table(StudentNameString, AddressString, PhoneString, GenderString, CastString,EmailString,EduString,regId,image);

        if (isInserted) {
            Toast.makeText(getContext(), "Student Personal Details Registered!", Toast.LENGTH_SHORT).show();
            int cnt=db.getcnt();
            for (int i = 0; i < finalArray.length; i++) {
                String BatchName = finalArray[i];
                String BatchTime = finalTimeArray[i];
                String newBatchName = BatchName.replace(" ", "_");
                String newBatchTime = BatchTime.replace(":", "_");
                newBatchTime = newBatchTime.replace(" ", "_");

                String TableName = newBatchName + newBatchTime;
                String RegFeeStatus = "";

                boolean isInsertedTable = true;
                if (!Arrays.asList(regFeeArray).contains(finalArray[i])) {
                    RegFeeStatus = "Pending";
                    isInsertedTable = db.insertIntoTables(TableName, StudentNameString, date, RegFeeStatus,amtArray.get(i));
                    db.updateEnrolledBatches(TableName,cnt);
                }
                if (Arrays.asList(regFeeArray).contains(finalArray[i])) {
                    RegFeeStatus = "Paid";
                    isInsertedTable = db.insertIntoTables(TableName, StudentNameString, date, RegFeeStatus,amtArray.get(i));
                    db.updateEnrolledBatches(TableName,cnt);
                }


                if (isInsertedTable) {
                    Toast.makeText(getContext(), "Student Academic Details Registered", Toast.LENGTH_SHORT).show();

                   // SharedPreferences sharedPreferences= this.requireActivity().getSharedPreferences("TuitionInfo",MODE_PRIVATE);


                } else {
                    Toast.makeText(getContext(), "Table insertion Unsuccessful", Toast.LENGTH_SHORT).show();
                }

            }

        } else {
            Toast.makeText(getContext(), "Data insertion Unsuccessful", Toast.LENGTH_SHORT).show();
        }
    }

    private void openRegistrationFeesDialogue() {
        MaterialAlertDialogBuilder mBuilder1=new MaterialAlertDialogBuilder(requireActivity(),R.style.AlertDialogTheme);

      //  AlertDialog.Builder mBuilder1 = new AlertDialog.Builder(getContext());
        mBuilder1.setTitle("Registration Fees Paid?");
        mBuilder1.setMultiChoiceItems(finalArray, checkedItemFinal, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position, boolean isChecked) {

                if (isChecked) {

                    if (!mUserItemsFinal.contains(position)) {
                        mUserItemsFinal.add(position);

                    }
                } else if (mUserItemsFinal.contains(position)) {
                    mUserItemsFinal.remove((Integer) position);

                }
            }
        });
        mBuilder1.setCancelable(false);
        mBuilder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(DialogInterface dialog, int which) {

                final DataBaseHelper db = new DataBaseHelper(getContext());
                regFeeArray = new String[mUserItemsFinal.size()];
                for (int i = 0; i < mUserItemsFinal.size(); i++) {
                    //sub = finalArray[mUserItemsFinal.get(i)];
                    regFeeArray[i] = finalArray[mUserItemsFinal.get(i)];

                }
                Log.i(TAG, Arrays.toString(regFeeArray));
                MaterialAlertDialogBuilder reconfirmBuilder=new MaterialAlertDialogBuilder(requireActivity(),R.style.AlertDialogTheme);

             //   AlertDialog.Builder reconfirmBuilder = new AlertDialog.Builder(getContext());
                reconfirmBuilder.setTitle("Confirm");
                reconfirmBuilder.setMessage("Are you Sure all the detail are Accurate as per your Knowledge");
                reconfirmBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        MaterialAlertDialogBuilder reconfirmBuilder=new MaterialAlertDialogBuilder(requireActivity(),R.style.AlertDialogTheme);
                        //AlertDialog.Builder reconfirmBuilder = new AlertDialog.Builder(BatchesActivity.this);
                        reconfirmBuilder.setMessage("Do you want to send welcome message with students registration number to students?\n\nCaution:This may cause you charges as messages will be sent by your network carrier!");
                        reconfirmBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                insertData();
                                String tuitionName=db.getTuitionName();

                                SmsManager smsManager = SmsManager.getDefault();
                                String finalSMS = "Welcome "+ StudentNameString+","+"\n" +"Please find the registration number attached to the message-" +"\n"+
                                        "Registration Number-"+regId+"\n"+
                                        "-" + tuitionName;

                                smsManager.sendTextMessage(PhoneString, null, finalSMS, null, null);

                                requireActivity().finish();

                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                insertData();
                                requireActivity().finish();
                            }
                        }).setCancelable(false).show();

                    }
                });
                reconfirmBuilder.setNegativeButton("Re-Evaluate", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

               // AlertDialog cnfDialogue = reconfirmBuilder.create();
                reconfirmBuilder.show();

            }
        });

        mBuilder1.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        mBuilder1.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < checkedItemFinal.length; i++) {
                    checkedItemFinal[i] = false;
                    mUserItemsFinal.clear();
                }
            }
        });
       // AlertDialog mDialogue1 = mBuilder1.create();
        mBuilder1.show();

    }


}