package com.example.atilagapps.hellixdatamanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.atilagapps.hellixdatamanager.Fragments.PersonalInfo_Fragment;

public class MainActivity extends AppCompatActivity {


    DataBaseHelper dbRef;
    Button nextButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbRef=new DataBaseHelper(this);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.add(R.id.containerId, new PersonalInfo_Fragment());
        fragmentTransaction.commit();

    }





    /*private void ShowData() {


            Cursor res= dbRef.getAllData();
            if (res.getCount()==0){
                Toast.makeText(this, "No Data to return", Toast.LENGTH_SHORT).show();
            }else{
                StringBuffer buffer=new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("ID:"+res.getString(0)+"\n");
                    buffer.append("SUBJECT:"+res.getString(1)+"\n");
                    buffer.append("MONTHS:"+res.getString(2)+"\n");
                    buffer.append("TEACHER:"+res.getString(3)+"\n\n");
                }
                showMessage("Data Dialogue",buffer.toString());

        }
    }*/

    /*private void showMessage(String title, String message) {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }*/
}