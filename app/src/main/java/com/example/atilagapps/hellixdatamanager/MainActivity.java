package com.example.atilagapps.hellixdatamanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;

import com.example.atilagapps.hellixdatamanager.Fragments.CourseDetails;
import com.example.atilagapps.hellixdatamanager.Fragments.HomeFragment;
import com.example.atilagapps.hellixdatamanager.Fragments.PersonalInfo_Fragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    DataBaseHelper dbRef;
    Button nextButton;
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbRef = new DataBaseHelper(this);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.containerId, new HomeFragment());
            fragmentTransaction.commit();
            navigationView.setCheckedItem(R.id.nav_homeId);
        }


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