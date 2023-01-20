package com.example.testing;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testing.databinding.ActivityRegistrationBinding;

import java.util.Calendar;

public class RegistrationActivity extends AppCompatActivity {

    DatePickerDialog datePicker;
    ActivityRegistrationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.edtBirthdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                fnInvokeDatePicker();
            }
        });
        binding.edtBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fnInvokeDatePicker();
            }
        });

        binding.fabAddUser.setOnClickListener(this::fnAddUser);
    }

    private void fnInvokeDatePicker() {

        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);

        datePicker = new DatePickerDialog(RegistrationActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int
                            dayOfMonth) {
                        binding.edtBirthdate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
        datePicker.show();
    }

    private void fnAddUser(View view) {


        String strFullname = binding.edtFullName.getText().toString();
        String strPwd = binding.edtPwd.getText().toString();
        String strEmail = binding.edtEmail.getText().toString();
        String strBirth = binding.edtBirthdate.getText().toString();
        String strAddress = binding.edtAddress.getText().toString();
        String strGender = "";

        if (binding.rbMale.isChecked())
            strGender = binding.rbMale.getText().toString();
        else if (binding.rbFemale.isChecked())
            strGender = binding.rbFemale.getText().toString();

        User user = new User(strFullname, strPwd, strAddress, strEmail, strBirth, strGender);

        if ("".equals(strFullname) || "".equals(strPwd) || "".equals(strEmail) || "".equals(strAddress) || "".equals(strBirth) || "".equals(strGender)) {
            Toast.makeText(this, "You cannot leave this field empty!", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra("objUser", user);
            startActivity(intent);
        }

    }
}