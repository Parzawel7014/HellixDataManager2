package com.example.atilagapps.hellixdatamanager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;

import com.example.atilagapps.hellixdatamanager.Fragments.ConfirmationFragment;
import com.example.atilagapps.hellixdatamanager.Fragments.HomeFragment;
import com.example.atilagapps.hellixdatamanager.Fragments.PersonalInfo_Fragment;
import com.example.atilagapps.hellixdatamanager.SendSMS.SMSInfoClass;
import com.example.atilagapps.hellixdatamanager.SendSMS.SendSMSActivity;
import com.example.atilagapps.hellixdatamanager.TuitionFess.AddFeesActivity;
import com.google.android.material.navigation.NavigationView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    DataBaseHelper dbRef;
    //Button nextButton;
    private DrawerLayout drawerLayout;
    String TuitionName;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbRef = new DataBaseHelper(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");
        drawerLayout = findViewById(R.id.drawer_layout);
        SharedPreferences sharedPreferences = getSharedPreferences("TuitionInfo", MODE_PRIVATE);
       // TuitionName = sharedPreferences.getString("Tuition Name", "");
        TuitionName=dbRef.getTuitionName();

        ActivityCompat.requestPermissions(MainActivity.this, new String[]
                        {Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}
                , PackageManager.PERMISSION_GRANTED);


        generateNotification();

        DataBaseHelper db = new DataBaseHelper(MainActivity.this);
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
                        boolean res2 = db.insertInStaffFeeTable(allTableName.get(i), curr_mont, curr_year);
                        if (!res2){
                            Toast.makeText(this, "Staff Not Updated", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                if (curr_year > (staff_year)) {
                    boolean res2 = db.insertInStaffFeeTable(allTableName.get(i), curr_mont, curr_year);

                    if (!res2){
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
                        if (curr_mont > (month )) {
                            boolean res1 = db.insertIntoFeesTable(allTableName.get(i), curr_mont, curr_year);

                            if (res1) {

                                ArrayList<SMSInfoClass> studentsNum = new ArrayList<>();
                                studentsNum = db.getUnpaidStudentsPhoneNumber(allTableName.get(i));

                                for (int j = 0; j < studentsNum.size(); j++) {


                                    SmsManager smsManager = SmsManager.getDefault();
                                    String finalSMS = "Dear " + studentsNum.get(j).getName() + "," + "\n" + "You are requested to pay the Tuition fees for the date- " + "\n" +
                                            studentsNum.get(j).getDate() + "/" + studentsNum.get(j).getMonth() + "/" + studentsNum.get(j).getYear() + "\n" +
                                            " Ignore if already paid." + "\n" +
                                            "-" + TuitionName;

                                    String contact = studentsNum.get(i).getContact();
                                    smsManager.sendTextMessage(contact, null, finalSMS, null, null);
                                }


                                Toast.makeText(MainActivity.this, "Ok", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "No Student", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                    if (curr_year > (year)) {
                        boolean res2 = db.insertIntoFeesTable(allTableName.get(i), curr_mont, curr_year);
                        if (res2) {
                            Toast.makeText(MainActivity.this, "Ok", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            }

        }


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView tuiName;
        tuiName = headerView.findViewById(R.id.navHeadAdmin);
        TextView email = headerView.findViewById(R.id.navHeadEmail);

        // SharedPreferences sharedPreferences=getSharedPreferences("TuitionInfo",MODE_PRIVATE);
        String name = sharedPreferences.getString("Tuition Name", "");
        String Email = dbRef.getTuitionEmail();

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

    private void generateNotification() {

       /* Calendar calendar=Calendar.getInstance();

        Intent intent=new Intent(getApplicationContext(),Notification_receiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.In);

*/


        Calendar calender = Calendar.getInstance(TimeZone.getDefault());
        int cDay = calender.get(Calendar.DAY_OF_MONTH);

        calender.set(Calendar.HOUR_OF_DAY, 8); //hour you have selected
        calender.set(Calendar.MINUTE, 0); //min you have selected
        calender.set(Calendar.SECOND, 0);
        calender.set(Calendar.MILLISECOND, 0);

        calender.set(Calendar.DATE, cDay);
        calender.get(Calendar.MONTH);

        Calendar now = Calendar.getInstance();
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);

        int days = now.getActualMaximum(Calendar.DAY_OF_MONTH);

        if (calender.before(now)) {  //this condition is used for future alarm only
            calender.add(Calendar.DATE, days);
        }

        final int _id = (int) System.currentTimeMillis();

        Intent i = new Intent(getApplicationContext(), Notification_receiver.class);
        i.putExtra("type", "month");

        PendingIntent displayIntent = PendingIntent.getBroadcast(
                getApplicationContext(), 100, i, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calender.getTimeInMillis(), AlarmManager.INTERVAL_DAY * calender.getActualMaximum(Calendar.DAY_OF_MONTH), displayIntent);

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
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.containerId, new HomeFragment()).addToBackStack(null);
                fragmentTransaction.commit();

                break;
            case R.id.nav_Add_Student_Id:

                FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.containerId, new PersonalInfo_Fragment()).addToBackStack(null).commit();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}