package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

public class FirstActivity extends AppCompatActivity {

    TextView txtvwAge;
    EditText edtName,edtYear;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        txtvwAge=(TextView) findViewById(R.id.txtvwAge);
        edtName=(EditText) findViewById(R.id.edtTxtName);
        edtYear=(EditText) findViewById(R.id.edtYear);
        imageView=(ImageView) findViewById(R.id.imageView);

        if(getIntent().getExtras()!=null) {
            fnSetPic();
        }
    }

    public void fnGreet(View vw)
    {
        String strName= edtName.getText().toString();
        String strAge =edtYear.getText().toString();
        int age = Integer.parseInt(strAge);//Calendar.YEAR - edtYear.getText();
        txtvwAge.setText("Hellooo and welcome " + strName + ". You are "+ age +" years old!");
    }

    public void fnThreadActivity(View vw){
    Intent intent = new Intent(this, ThreadedActivity.class);
    String strMsg = ((EditText) findViewById(R.id.edtTxtName)).getText().toString();
    intent.putExtra("varStr1", strMsg);
    startActivity(intent);
}

    public void fnGoNext(View view)
    {
        Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
        startActivity(intent);
    }

    public void fnSetPic()
    {
            Bitmap bmp;

            byte[] byteArray = getIntent().getByteArrayExtra("bp");
            bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            imageView.setImageBitmap(bmp);
    }
}