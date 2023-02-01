package com.example.testing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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
import com.google.android.material.navigation.NavigationView;

public class StudentMainActivity extends AppCompatActivity {

    private ActivityStudentMainBinding binding;
    private Student student;
    private Attendance attendance;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    private ArrayList<Student> students;
    private StudentAdapter adapter;

    private DatePickerDialog datePicker;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
                        case R.id.nav_search:
                            intent = new Intent(getApplicationContext(),SearchStudentActivity.class);
                            startActivity(intent);
                        default:
                            return false;
                    }
                }
            });



        binding.fabAdd.setOnClickListener(v -> {
            fnAdd(v);
        });
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
                                binding.edtBirthdate.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                            }
                        },year,month,day);
                datePicker.show();
            }
        });

        //attendance = new Attendance(this);
        databaseHelper = new DatabaseHelper(this);

        students = new ArrayList<>();
        adapter = new StudentAdapter(getLayoutInflater(),students);

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
        /*
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
        long id = databaseHelper.saveData(student);

        if (id == -1) {
            Toast.makeText(this, "Error inserting data", Toast.LENGTH_SHORT).show();
        } else {
            students.add(student);
            adapter.notifyItemInserted(students.size());
            Toast.makeText(this, "Data inserted", Toast.LENGTH_SHORT).show();
        }

         */
        String fullname = binding.edtFullName.getText().toString();
        String studNo = binding.edtStudNum.getText().toString();
        String email = binding.edtEmail.getText().toString();
        String birth = binding.edtBirthdate.getText().toString();
        String gender = "";
        String state = binding.spnState.getSelectedItem().toString();

        if(binding.rbMale.isChecked())
            gender = binding.rbMale.getText().toString();
        else if(binding.rbFemale.isChecked())
            gender = binding.rbFemale.getText().toString();

        student = new Student(fullname, studNo, email, gender, birth, state);
        long id = databaseHelper.saveData(student);

        if (id == -1) {
            Toast.makeText(this, "Error inserting data", Toast.LENGTH_SHORT).show();
        } else {
            students.add(student);
            adapter.notifyItemInserted(students.size());
            Toast.makeText(this, "Data inserted", Toast.LENGTH_SHORT).show();
        }
    }

    private void fnAddToREST(){

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
                Toast.makeText(getApplicationContext(), "Cannot Save!", Toast.LENGTH_SHORT).show();
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

                Map<String,String> params = new HashMap<>();
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


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}