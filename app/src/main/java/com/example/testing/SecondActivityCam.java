package com.example.testing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SecondActivityCam extends AppCompatActivity {

    Executor executor;
    Handler handler;
    Bitmap bitmap = null;
    ImageView imageView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_cam);
        executor = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());
        imageView=findViewById(R.id.imgVwSelfie);

        drawerLayout=findViewById(R.id.cam_drawer_layout);
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
        /*
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try{
                        URL ImageUrl = new URL("https://ftmk.utem.edu.my/web/wp-content/uploads/2020/02/cropped-Logo-FTMK.png");
                        HttpURLConnection connection = (HttpURLConnection)
                                ImageUrl.openConnection();
                        connection.setDoInput(true);
                        connection.connect();
                        InputStream inputStream=connection.getInputStream();
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inPreferredConfig = Bitmap.Config.RGB_565;
                        bitmap= BitmapFactory.decodeStream(inputStream,null,options);

                    }catch (MalformedURLException e){
                        e.printStackTrace();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(bitmap);
                        }
                    });
                }
            });

        } else {
            Toast.makeText(getApplicationContext(), "No Network!! Please add data plan or connect to wifi network!", Toast.LENGTH_SHORT).show();
        }
        */
        Picasso.get()
                .load("https://ftmk.utem.edu.my/web/wp-content/uploads/2020/02/cropped-Logo-FTMK.png")
                .resize(1000, 300)
                .into(imageView);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}