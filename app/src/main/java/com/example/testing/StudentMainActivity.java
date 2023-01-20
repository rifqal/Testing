package com.example.testing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.testing.databinding.ActivityStudentMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import com.example.testing.Attendance;

public class StudentMainActivity extends AppCompatActivity {

    private ActivityStudentMainBinding binding;
    private Student student;

    private ArrayList<Student> students;
    private StudentAdapter adapter;

    private DatePickerDialog datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityStudentMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //binding.fabAdd.setOnClickListener(this::fnAdd);
        //binding.fabAdd.setOnClickListener(this::fnAddToREST);
        binding.edtBirthdate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);


                datePicker = new
                        DatePickerDialog(StudentMainActivity.this,
                        new DatePickerDialog.OnDateSetListener(){
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                binding.edtBirthdate.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                            }
                        },year,month,day);
                datePicker.show();
            }
        });

        Attendance attendance = new Attendance(this);

        students = new ArrayList<>();
        adapter = new StudentAdapter(getLayoutInflater(),students);

        attendance.fnInsertStudent(student);

        binding.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fnAdd(view);

            }
        });

        binding.rcvStud.setAdapter(adapter);
        binding.rcvStud.setLayoutManager(new LinearLayoutManager(this));

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT){
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                students.remove(position);
                adapter.notifyItemRemoved(position);
            }
        }).attachToRecyclerView(binding.rcvStud);
    }

    private void fnAdd(View view)
    {
        String fullName= binding.edtFullName.getText().toString();
        String studNo = binding.edtStudNum.getText().toString();
        String email = binding.edtEmail.getText().toString();
        String birth = binding.edtBirthdate.getText().toString();
        String gender ="";
        String state = binding.spnState.getSelectedItem().toString();

        if(binding.rbMale.isChecked())
            gender=binding.rbMale.getText().toString();
        else if(binding.rbFemale.isChecked())
            gender=binding.rbFemale.getText().toString();

        student = new Student(fullName,studNo,email,gender,birth,state);

        students.add(student);
        adapter.notifyItemInserted(students.size() );

    }

    private void fnAddToREST(View view){

        String strURL = "http://192.168.171.1/RESTAPI/rest_api.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, strURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;

                try {
                    jsonObject = new JSONObject(response);
                    Toast.makeText(getApplicationContext(), "Respond from server: " +
                            jsonObject.getString("respond"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{

                String fullname = binding.edtFullName.getText().toString();
                String studNo = binding.edtStudNum.getText().toString();
                String email = binding.edtEmail.getText().toString();
                String birth = binding.edtBirthdate.getText().toString();
                String gender ="";
                String state=binding.spnState.getSelectedItem().toString();

                if(binding.rbMale.isChecked())
                    gender=binding.rbMale.getText().toString();
                else if(binding.rbFemale.isChecked())
                    gender=binding.rbFemale.getText().toString();

                Map<String,String> params = new HashMap<String,String>();
                params.put("selectFn","fnSaveData");
                params.put("studName",fullname);
                params.put("studGender",gender);
                params.put("studDob",birth);
                params.put("studNo",studNo);
                params.put("studState",state);
                params.put("studEmail",email);
                return params;
            }
        };
    requestQueue.add(stringRequest);
    }


}