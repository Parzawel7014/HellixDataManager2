package com.example.atilagapps.hellixdatamanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;

import com.example.atilagapps.hellixdatamanager.Fragments.ConfirmationFragment;
import com.example.atilagapps.hellixdatamanager.Fragments.HomeFragment;
import com.example.atilagapps.hellixdatamanager.Fragments.PersonalInfo_Fragment;
import com.google.android.material.navigation.NavigationView;




public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    DataBaseHelper dbRef;
    //Button nextButton;
    private DrawerLayout drawerLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbRef = new DataBaseHelper(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");
        drawerLayout = findViewById(R.id.drawer_layout);




        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView=navigationView.getHeaderView(0);
        TextView tuiName=headerView.findViewById(R.id.navHeadAdmin);
        TextView email=headerView.findViewById(R.id.navHeadEmail);

        SharedPreferences sharedPreferences=getSharedPreferences("TuitionInfo",MODE_PRIVATE);
        String name=sharedPreferences.getString("Tuition Name","");
        String Email=sharedPreferences.getString("Tuition Email","");

        tuiName.setText(name);
        email.setText(Email);


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


}