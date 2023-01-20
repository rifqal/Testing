package com.example.testing;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class ThreadedActivity extends AppCompatActivity {

    ImageView imgVwPic;
    TextView tvGreet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threaded);

        imgVwPic = findViewById(R.id.imgVwPic);
        Intent intent = getIntent();

        String strMsg = intent.getStringExtra("varStr1");
        tvGreet= findViewById(R.id.tvGreet);
        tvGreet.setText("Welcome to second Activity " + strMsg);
    }

    public void fnTakePic(View vw)
    {
        Runnable run = new Runnable(){

            @Override
            public void run(){

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);

                runOnUiThread(new Runnable(){

                    @Override
                    public void run(){
                        tvGreet.setText(tvGreet.getText().toString()+ ".. This is your Picture heheh..");
                    }
                });
            }
        };

        Thread thr = new Thread(run);
        thr.start();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(0,resultCode,data);

        Bitmap bp = (Bitmap) data.getExtras().get("data");
        imgVwPic.setImageBitmap(bp);

        ByteArrayOutputStream bStream = new ByteArrayOutputStream();

        bp.compress(Bitmap.CompressFormat.PNG,100,bStream);

        byte[] byteArray = bStream.toByteArray();

        Intent intent = new Intent(this,FirstActivity.class);
        intent.putExtra("bp",byteArray);
        startActivity(intent);
        finish();
    }
}