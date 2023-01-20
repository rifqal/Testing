package com.example.testing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    TextView txtVw1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout=findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.nav_open,R.string.nav_close);
        actionBarDrawerToggle.syncState();

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView=findViewById(R.id.nav_menu);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.nav_main_activity:
                        intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_camera_activity:
                        intent = new Intent(getApplicationContext(),SecondActivityCam.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_settings:
                        Toast.makeText(getApplicationContext(),"You navigated to Setting Screen",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_logout:
                        Toast.makeText(getApplicationContext(),"You are logged out! See ya!",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_registration:
                        intent = new Intent(getApplicationContext(),StudentMainActivity.class);
                        startActivity(intent);
                        return true;
                    default:
                        return false;
                }
            }
        });

        txtVw1 = findViewById(R.id.textView);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void fnChangeText(View view){
        txtVw1.setText("Wooha its worked! the text view changed!");
    }

    public void fnFirstActivity(View view){
        Intent intent = new Intent(getApplicationContext(), FirstActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Toast.makeText(this,"This happens when app starts",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Toast.makeText(this,"This happens when app is restarting",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Toast.makeText(this,"This happens when app is resuming",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this,"This happens when app is about to pause",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();

        Toast.makeText(this,"This happens when app is about to stop",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Toast.makeText(this,"This happens when app is about to be destroyed",
                Toast.LENGTH_SHORT).show();
    }
}