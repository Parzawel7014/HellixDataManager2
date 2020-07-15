package com.example.atilagapps.hellixdatamanager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.biometric.BiometricManager;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.atilagapps.hellixdatamanager.Batches.NewBatchAddActivity;
import com.example.atilagapps.hellixdatamanager.Batches.SubjectAdapter;
import com.example.atilagapps.hellixdatamanager.Charts.AddIncomeExenseActivity;
import com.example.atilagapps.hellixdatamanager.Charts.IncomeExpenseActivity;
import com.example.atilagapps.hellixdatamanager.Fragments.HomeFragment;
import com.example.atilagapps.hellixdatamanager.SendSMS.SMSInfoClass;
import com.example.atilagapps.hellixdatamanager.Students.FindStudentActivity;
import com.example.atilagapps.hellixdatamanager.Students.StudentAddActivity;
import com.example.atilagapps.hellixdatamanager.TuitionFess.AddFeesActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

//import android.widget.Toolbar;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    SwitchMaterial switcher;
    boolean F_Flag;
    public static final int PICKFILE_RESULT_CODE = 1;
    DataBaseHelper dbRef;
    //Button nextButton;
    NavigationView navigationView;
    protected DrawerLayout drawerLayout;
    String TuitionName;

    String databaseName;


    private Uri fileUri;
    private String filePath;
    String path;
    ProgressDialog LoadingBar;
    int ImpExpFlag = 0;
    SwipeRefreshLayout swipeRefresh;
    SharedPreferences sharedPreferences;
    String TuitionNameFirebaseId;


    StorageReference storageReference;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbRef = new DataBaseHelper(this);


        swipeRefresh = findViewById(R.id.swipeRefresh);

        databaseName = dbRef.getDatabaseName();
        LoadingBar = new ProgressDialog(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");
        drawerLayout = findViewById(R.id.drawer_layout);
        //  SharedPreferences sharedPreferences = getSharedPreferences("TuitionInfo", MODE_PRIVATE);
        // TuitionName = sharedPreferences.getString("Tuition Name", "");
        TuitionName = dbRef.getTuitionName();

        firebaseAuth = FirebaseAuth.getInstance();
        // TuitionNameFirebaseId=firebaseAuth.getCurrentUser().getUid();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();


        navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        View headerView = navigationView.getHeaderView(0);
        TextView tuiName;
        tuiName = headerView.findViewById(R.id.navHeadAdmin);
        TextView email = headerView.findViewById(R.id.navHeadEmail);
        CircularImageView proImg = headerView.findViewById(R.id.navImgId);


        Menu menu = navigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.fingerprint);
        View actionView = MenuItemCompat.getActionView(menuItem);

        switcher = actionView.findViewById(R.id.fPId);
        switcher.setChecked(false);
        boolean f_Enabled = getSharedPreferences("FINGERPRINT", MODE_PRIVATE)
                .getBoolean("F_Flag", true);
        if (f_Enabled) {
            switcher.setChecked(true);
            //Snackbar.make("Fingerprint Unlock Enabled",Snackbar.LENGTH_SHORT).setAction("Action",null).show();
        } else {
            switcher.setChecked(false);
        }
        switcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, (switcher.isChecked()) ? "Fingerprint Unlock Enabled!" : "Fingerprint Unlock Disabled!", Snackbar.LENGTH_SHORT).setAction("Action", null).show();

                if (switcher.isChecked()) {
                    BiometricManager biometricManager = BiometricManager.from(getApplicationContext());
                    switch (biometricManager.canAuthenticate()) {
                        case BiometricManager.BIOMETRIC_SUCCESS:
                            getSharedPreferences("FINGERPRINT", MODE_PRIVATE).edit()
                                    .putBoolean("F_Flag", true).apply();
                            break;
                        case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                            Toast.makeText(getApplicationContext(), "Device Doesn't have Fingerprint sensor", Toast.LENGTH_SHORT).show();
                            break;
                        case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                            Toast.makeText(getApplicationContext(), "Biometric sensor is currently unavailable", Toast.LENGTH_SHORT).show();
                            break;
                        case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                            Toast.makeText(getApplicationContext(), "No Fingerprint Enrolled.Please check device security setting", Toast.LENGTH_SHORT).show();
                            break;
                    }

                }
                if (!switcher.isChecked()) {
                    getSharedPreferences("FINGERPRINT", MODE_PRIVATE).edit()
                            .putBoolean("F_Flag", false).apply();
                    ;
                }
            }
        });


        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerId, new HomeFragment());
                fragmentTransaction.commit();
                swipeRefresh.setRefreshing(false);
            }
        });


        ActivityCompat.requestPermissions(MainActivity.this, new String[]
                        {Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}
                , PackageManager.PERMISSION_GRANTED);


        File direct = new File(this.getExternalFilesDir(null) + File.separator
                + this.getResources().getString(R.string.app_name)
                + File.separator + "/SQLiteDBFolder");


        if (!direct.exists()) {
            direct.mkdirs();
        }


        path = direct.getAbsolutePath();


        final DataBaseHelper db = new DataBaseHelper(MainActivity.this);
        ArrayList<String> allTableName = new ArrayList<>();


        allTableName = db.getAllTableName();

        for (int i = 0; i < allTableName.size(); i++) {


            LocalDate date = LocalDate.now();
            int isStaffEmpty = db.getStaffCount(allTableName.get(i));


            int curr_mont = date.getMonthValue();
            int curr_year = date.getYear();

            if (isStaffEmpty > 0) {

                int staff_month = db.getLastStaffMonth(allTableName.get(i));
                int staff_year = db.getLastStaffYear(allTableName.get(i));
                //int staffocc=db.getStaffOccurance(allTableName.get(i),curr_mont)

                if (curr_year == staff_year) {
                    if (curr_mont > (staff_month)) {


                        MaterialAlertDialogBuilder reconfirmBuilder = new MaterialAlertDialogBuilder(MainActivity.this);
                        //AlertDialog.Builder reconfirmBuilder = new AlertDialog.Builder(BatchesActivity.this);
                        reconfirmBuilder.setTitle("Alert");
                        reconfirmBuilder.setIcon(R.drawable.alert);
                        reconfirmBuilder.setMessage("Please perform Data-Backup for new Month!");
                        reconfirmBuilder.setCancelable(false);
                        reconfirmBuilder.setPositiveButton("Backup", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                exportDB();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();


                        boolean res2 = db.insertInStaffFeeTable(allTableName.get(i), curr_mont, curr_year);
                        if (!res2) {
                            Toast.makeText(this, "Staff  Updated", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                if (curr_year > (staff_year)) {
                    MaterialAlertDialogBuilder reconfirmBuilder = new MaterialAlertDialogBuilder(MainActivity.this);
                    //AlertDialog.Builder reconfirmBuilder = new AlertDialog.Builder(BatchesActivity.this);
                    reconfirmBuilder.setTitle("Alert");
                    reconfirmBuilder.setIcon(R.drawable.alert);
                    reconfirmBuilder.setMessage("Please perform Data-Backup for new month!");
                    reconfirmBuilder.setCancelable(false);
                    reconfirmBuilder.setPositiveButton("Backup", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            exportDB();
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();


                    boolean res2 = db.insertInStaffFeeTable(allTableName.get(i), curr_mont, curr_year);

                    if (!res2) {
                        Toast.makeText(this, "Staff Not Updated", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            int isEmpty = db.getStudentsCount(allTableName.get(i));
            if (isEmpty > 0) {

                int month = db.getLastMonth(allTableName.get(i));
                int year = db.getLastYear(allTableName.get(i));

                int curr_day = date.getDayOfMonth();


                int occ = db.getOccurrence(allTableName.get(i), curr_mont);
                if (occ != 1) {
                    if (curr_year == year) {
                        if (curr_mont > (month)) {

                            MaterialAlertDialogBuilder reconfirmBuilder1 = new MaterialAlertDialogBuilder(MainActivity.this);
                            //AlertDialog.Builder reconfirmBuilder = new AlertDialog.Builder(BatchesActivity.this);
                            reconfirmBuilder1.setTitle("Alert");
                            reconfirmBuilder1.setIcon(R.drawable.alert);
                            reconfirmBuilder1.setMessage("Please Perform Data Backup For new Month");
                            reconfirmBuilder1.setCancelable(false);
                            reconfirmBuilder1.setPositiveButton("Backup", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    exportDB();
                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();


                            boolean res1 = db.insertIntoFeesTable(allTableName.get(i), curr_mont, curr_year);
                            if (res1) {
                                MaterialAlertDialogBuilder reconfirmBuilder = new MaterialAlertDialogBuilder(MainActivity.this);
                                //AlertDialog.Builder reconfirmBuilder = new AlertDialog.Builder(BatchesActivity.this);
                                reconfirmBuilder.setTitle("Alert");
                                reconfirmBuilder.setIcon(R.drawable.alert);
                                reconfirmBuilder.setMessage("New month fee collection is due.\nDo you want to send fee reminder to students?\nCaution:This may cause you charges as messages will be sent by your network carrier!" +
                                        "\nPress Send to send the reminder.");
                                final ArrayList<String> finalAllTableName = allTableName;
                                final int finalI = i;
                                reconfirmBuilder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.O)
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ArrayList<SMSInfoClass> studentsNum = new ArrayList<>();
                                        studentsNum = db.getUnpaidStudentsPhoneNumber(finalAllTableName.get(finalI));

                                        for (int j = 0; j < studentsNum.size(); j++) {
                                            SmsManager smsManager = SmsManager.getDefault();
                                            String finalSMS = "Dear " + studentsNum.get(j).getName() + "," + "\n" + "You are requested to pay the Tuition fees for the date- " + "\n" +
                                                    studentsNum.get(j).getDate() + "/" + studentsNum.get(j).getMonth() + "/" + studentsNum.get(j).getYear() + "\n" +
                                                    " Ignore if already paid." + "\n" +
                                                    "-" + TuitionName;

                                            String contact = studentsNum.get(finalI).getContact();
                                            smsManager.sendTextMessage(contact, null, finalSMS, null, null);
                                        }

                                    }
                                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        MaterialAlertDialogBuilder reconfirmBuilder1 = new MaterialAlertDialogBuilder(getApplicationContext());
                                        //AlertDialog.Builder reconfirmBuilder = new AlertDialog.Builder(BatchesActivity.this);
                                        reconfirmBuilder1.setMessage("You can send reminders later by going to send notification portal.");
                                        reconfirmBuilder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @RequiresApi(api = Build.VERSION_CODES.O)
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }).setCancelable(false).show();
                                    }
                                }).show();

                                //Toast.makeText(MainActivity.this, "Ok", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "No Student in database!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                    if (curr_year > (year)) {

                        MaterialAlertDialogBuilder reconfirmBuilder1 = new MaterialAlertDialogBuilder(MainActivity.this);
                        //AlertDialog.Builder reconfirmBuilder = new AlertDialog.Builder(BatchesActivity.this);
                        reconfirmBuilder1.setTitle("Alert");
                        reconfirmBuilder1.setIcon(R.drawable.alert);
                        reconfirmBuilder1.setMessage("Please Perform Data Backup For new Month");
                        reconfirmBuilder1.setCancelable(false);
                        reconfirmBuilder1.setPositiveButton("Backup", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                exportDB();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();


                        boolean res2 = db.insertIntoFeesTable(allTableName.get(i), curr_mont, curr_year);
                        if (res2)
                        //Toast.makeText(MainActivity.this, "Ok", Toast.LENGTH_SHORT).show();
                        {
                            MaterialAlertDialogBuilder reconfirmBuilder = new MaterialAlertDialogBuilder(MainActivity.this);
                            //AlertDialog.Builder reconfirmBuilder = new AlertDialog.Builder(BatchesActivity.this);
                            reconfirmBuilder.setTitle("Alert");
                            reconfirmBuilder.setIcon(R.drawable.alert);
                            reconfirmBuilder.setMessage("New month fee collection is due.\nDo you want to send fee reminder to students?\n\nCaution:This may cause you charges as messages will be sent by your network carrier!" +
                                    "\n\nPress Send to send the reminder.");
                            final ArrayList<String> finalAllTableName = allTableName;
                            final int finalI = i;
                            reconfirmBuilder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ArrayList<SMSInfoClass> studentsNum = new ArrayList<>();
                                    studentsNum = db.getUnpaidStudentsPhoneNumber(finalAllTableName.get(finalI));

                                    for (int j = 0; j < studentsNum.size(); j++) {
                                        SmsManager smsManager = SmsManager.getDefault();
                                        String finalSMS = "Dear " + studentsNum.get(j).getName() + "," + "\n" + "You are requested to pay the Tuition fees for the date- " + "\n" +
                                                studentsNum.get(j).getDate() + "/" + studentsNum.get(j).getMonth() + "/" + studentsNum.get(j).getYear() + "\n" +
                                                " Ignore if already paid." + "\n" +
                                                "-" + TuitionName;

                                        String contact = studentsNum.get(finalI).getContact();
                                        smsManager.sendTextMessage(contact, null, finalSMS, null, null);
                                    }

                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    MaterialAlertDialogBuilder reconfirmBuilder1 = new MaterialAlertDialogBuilder(getApplicationContext());
                                    //AlertDialog.Builder reconfirmBuilder = new AlertDialog.Builder(BatchesActivity.this);
                                    reconfirmBuilder1.setMessage("You can send reminders later by going to send notification portal.");
                                    reconfirmBuilder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @RequiresApi(api = Build.VERSION_CODES.O)
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).setCancelable(false).show();
                                }
                            }).show();

                            //Toast.makeText(MainActivity.this, "Ok", Toast.LENGTH_SHORT).show();


                        }
                    }
                }
            }

        }


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        // SharedPreferences sharedPreferences=getSharedPreferences("TuitionInfo",MODE_PRIVATE);
        //   String name = sharedPreferences.getString("Tuition Name", "");
        String Email = dbRef.getTuitionEmail();


        Bitmap img = dbRef.getTuiProImage();
        if (img != null) {
            proImg.setImageBitmap(img);
        }

        tuiName.setText(TuitionName);
        email.setText(Email);


        navigationView.setNavigationItemSelectedListener(this);


        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.containerId, new HomeFragment());
            fragmentTransaction.commit();
            navigationView.setCheckedItem(R.id.nav_homeId);
        }


    }

    private void disableFingerPrint() {
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_homeId:
                //  FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                //fragmentTransaction.replace(R.id.containerId, new HomeFragment()).addToBackStack(null);
                //fragmentTransaction.commit();

                startActivity(new Intent(MainActivity.this, TuitionProfile.class));
                break;
            case R.id.nav_student_add_id:
                DataBaseHelper db = new DataBaseHelper(this);
                ArrayList<SubjectAdapter> subjectAdapters = new ArrayList<>();
                subjectAdapters = db.getDialogueLabelsAdapter();


                final ArrayList<SubjectAdapter> finalSubjectAdapters = subjectAdapters;


                if (finalSubjectAdapters.isEmpty()) {
                    MaterialAlertDialogBuilder reconfirmBuilder = new MaterialAlertDialogBuilder(getApplicationContext());

                    //  AlertDialog.Builder reconfirmBuilder = new AlertDialog.Builder(v.getContext());
                    reconfirmBuilder.setTitle("Alert");
                    reconfirmBuilder.setMessage("First Add Batches. \nClick on Ok to add Batches");
                    reconfirmBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            startActivity(new Intent(getApplicationContext(), NewBatchAddActivity.class));

                            // dialog.dismiss();
                        }
                    });
                    reconfirmBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    // AlertDialog cnfDialogue = reconfirmBuilder.create();
                    reconfirmBuilder.show();
                } else {

                    startActivity(new Intent(MainActivity.this, StudentAddActivity.class));
                }
                //  FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
                // fm.replace(R.id.containerId, new PersonalInfo_Fragment()).addToBackStack(null).commit();
                break;


            case R.id.nav_student_find_id:
                startActivity(new Intent(MainActivity.this, FindStudentActivity.class));
                break;

            case R.id.nav_tui_fee_add_id:
                startActivity(new Intent(MainActivity.this, AddFeesActivity.class));
                break;

            case R.id.nav_batch_add_id:
                startActivity(new Intent(MainActivity.this, NewBatchAddActivity.class));
                break;


            case R.id.nav_add_Extra_id:
                startActivity(new Intent(MainActivity.this, AddIncomeExenseActivity.class));
                break;


            case R.id.nav_profit_loss_id:
                startActivity(new Intent(MainActivity.this, IncomeExpenseActivity.class));
                break;


            case R.id.nav_import_id:
                //startActivity(new Intent(MainActivity.this, IncomeExpenseActivity.class));
                LoadingBar.setTitle("Importing..");
                LoadingBar.setMessage("Please Wait..");
                LoadingBar.show();
                LoadingBar.setCanceledOnTouchOutside(false);
                boolean res = importDB();
                if (res) {
                    MaterialAlertDialogBuilder mBuilder = new MaterialAlertDialogBuilder(MainActivity.this);
                    mBuilder.setTitle("Alert!")
                            .setIcon(R.drawable.alert)
                            .setMessage("Data Imported Successfully!")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();

                } else {
                    MaterialAlertDialogBuilder mBuilder = new MaterialAlertDialogBuilder(MainActivity.this);
                    mBuilder.setTitle("Alert!")
                            .setIcon(R.drawable.alert)
                            .setMessage("Data Importing Failed!\nMake sure you have exported database!\nIf problem continues please contact support! ")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();

                }
                LoadingBar.dismiss();
                break;

            case R.id.nav_export_id:
                // startActivity(new Intent(MainActivity.this, IncomeExpenseActivity.class));

                LoadingBar.setTitle("Exporting..");
                LoadingBar.setMessage("Please Wait..");
                LoadingBar.show();
                LoadingBar.setCanceledOnTouchOutside(false);
                boolean res1 = exportDB();
                LoadingBar.dismiss();
                if (res1) {

                    MaterialAlertDialogBuilder mBuilder = new MaterialAlertDialogBuilder(MainActivity.this);
                    mBuilder.setTitle("Alert!")
                            .setIcon(R.drawable.alert)
                            .setMessage("Data Exported Successfully!")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();


                    //   Toast.makeText(ImportExportActivity.this, "Data Exported Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    MaterialAlertDialogBuilder mBuilder = new MaterialAlertDialogBuilder(MainActivity.this);

                    mBuilder.setTitle("Alert!")
                            .setIcon(R.drawable.alert)
                            .setMessage("Data Export Failed!")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();

                    // Toast.makeText(ImportExportActivity.this, "Data Exporting Failed", Toast.LENGTH_SHORT).show();
                }
                break;


            case R.id.fingerprint:

                // Snackbar.make(menuItem.getActionView(), (switcher.isChecked()) ? "is checked" : "not checked", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                break;


        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private boolean exportDB() {

        try {
            //   Toast.makeText(this, "In Export", Toast.LENGTH_SHORT).show();

            File data = Environment.getDataDirectory();
            File sd = getApplicationContext().getExternalFilesDir(null);
            assert sd != null;
            if (sd.canWrite()) {

                String currentDBPath = "//data//" + this.getPackageName() + "//databases//" + databaseName;

                String backupDBPath = path + "/" + databaseName;


                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();


                Uri file = Uri.fromFile(new File(data, currentDBPath));
                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
                String format = simpleDateFormat.format(new Date());
        /*    UploadTask file_path=storageReference.child("user_db").child(TuitionNameFirebaseId+"_"+format+".db").putFile(file);
            file_path.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()){
                        storeData(task)
                    }else {
                        String error=task.getException().getMessage();
                        Toast.makeText(MainActivity.this, "Error: "+error, Toast.LENGTH_SHORT).show();
                    }
                }
            });*/


                final StorageReference riversRef = storageReference.child("user_db").child("HellixDataManager" + "_" + format + ".db");

                riversRef.putFile(file)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Uri downloadUrl = uri;
                                      //  Toast.makeText(getBaseContext(), "Upload success! URL - " + downloadUrl.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                });


                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                                // ...

                                Toast.makeText(MainActivity.this, "Upload UnSuccess! URL -", Toast.LENGTH_SHORT).show();
                            }
                        });


                // Toast.makeText(getBaseContext(), backupDB.toString(),
                //       Toast.LENGTH_LONG).show();
                //    getSharedPreferences("ImpExp",MODE_PRIVATE).edit()
                //          .putBoolean("ImpExpFlag",true).apply();
                return true;
            }


        } catch (Exception e) {

            //Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG)
            //      .show();

        }

        return false;

    }


    private boolean importDB() {

        try {
            // Toast.makeText(this, "In import", Toast.LENGTH_SHORT).show();


            File data = Environment.getDataDirectory();
            File sd = getApplicationContext().getExternalFilesDir(null);

            assert sd != null;
            if (sd.canWrite()) {

                String currentDBPath = "//data//" + this.getPackageName() + "//databases//" + databaseName;

                String backupDBPath = path + "/" + databaseName;


                File backupDB = new File(data, currentDBPath);
                File currentDB = new File(backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();

                //   Toast.makeText(getBaseContext(), backupDB.toString(),
                //         Toast.LENGTH_LONG).show();
                return true;
            }

        } catch (Exception e) {

            //  Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG)
            //        .show();


        }
        return false;

    }


}