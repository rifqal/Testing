package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity  {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textView=(TextView) findViewById(R.id.txtStudentData);

        if(getIntent().getExtras()!=null)
        {
            User user =(User) getIntent().getSerializableExtra("objUser");
            fnPopulateInfo(user);
        }
    }

    public void fnPopulateInfo(User user)
    {
        textView.setText("Full Name: " + user.getFullname() +
                "\nEmail: "+user.getEmail()+
                "\nBirthday: "+ user.getBirthdate()+
                "\nAddress" + user.getAddress() +
                "\nGender: "+ user.getGender());
    }
}