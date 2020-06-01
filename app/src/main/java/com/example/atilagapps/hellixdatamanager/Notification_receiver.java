package com.example.atilagapps.hellixdatamanager;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.atilagapps.hellixdatamanager.TuitionFess.AddFeesActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static android.content.Context.ALARM_SERVICE;

public class Notification_receiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

       /* NotificationManager notificationManager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent1=new Intent(context, AddFeesActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent=PendingIntent.getActivity(context,100,intent1,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder=new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setContentTitle("Monthly Fee Reminder")
                .setContentText("Click here to pay the fee")
                .setAutoCancel(true);

        notificationManager.notify(100,builder.build());*/


        Long futureTimeDifference = intent.getLongExtra("futureTimeDifference", 0); // Receive the time difference in milliseconds from currenttime in milliseconds and the future set date milliseconds
        futureTimeDifference = futureTimeDifference + System.currentTimeMillis();// get the next schedule date time inmilliseconds
        String repeatType = intent.getStringExtra("getRepeatType");// Receive the repeat type


        Date todaysDate = new Date();// initialize a new date object
        Calendar getCurrentDate = Calendar.getInstance();// Initialize a new Calendar object
        getCurrentDate.setTime(todaysDate); //Set the calendar to todays date
        int currentMonth = getCurrentDate.get(Calendar.MONTH); // Assign the current month in integer

        if (currentMonth == Calendar.JANUARY || currentMonth == Calendar.MARCH || currentMonth == Calendar.MAY || currentMonth == Calendar.JULY || currentMonth == Calendar.AUGUST || currentMonth == Calendar.OCTOBER || currentMonth == Calendar.DECEMBER) {
            futureTimeDifference = System.currentTimeMillis() + (AlarmManager.INTERVAL_DAY * 31);
        }
        if (currentMonth == Calendar.APRIL || currentMonth == Calendar.JUNE || currentMonth == Calendar.SEPTEMBER || currentMonth == Calendar.NOVEMBER) {
            futureTimeDifference = System.currentTimeMillis() + (AlarmManager.INTERVAL_DAY * 30);
        }

        if (currentMonth == Calendar.FEBRUARY) {//for february month)
            GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
            if (cal.isLeapYear(cal.get(Calendar.YEAR))) {//for leap year february month
                futureTimeDifference = System.currentTimeMillis() + (AlarmManager.INTERVAL_DAY * 29);
            } else { //for non leap year february month
                futureTimeDifference = System.currentTimeMillis() + (AlarmManager.INTERVAL_DAY * 28);
            }
        }

        final int monthly_id = (int) System.currentTimeMillis();

        Log.e("MonthlyNotification", futureTimeDifference + "");

        Intent intent1=new Intent(context, AddFeesActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent displayIntent = PendingIntent.getBroadcast(
                context, monthly_id, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, futureTimeDifference, displayIntent);


    }
}
