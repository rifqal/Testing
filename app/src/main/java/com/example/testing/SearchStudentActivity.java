package com.example.testing;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.testing.databinding.ActivitySearchStudentBinding;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchStudentActivity extends AppCompatActivity {

    private ActivitySearchStudentBinding binding;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchStudentBinding.inflate(getLayoutInflater());
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


        databaseHelper = new DatabaseHelper(this);

        binding.btnSearch.setOnClickListener(v -> {
            fnSearchSqlite(v);
        });
    }

    private void fnSearchSqlite(View view){
        String strStudNo = binding.edtStudID.getText().toString();
        List<Student> students = databaseHelper.searchAllStudents();

        if (!strStudNo.isEmpty()) {
            Student student = databaseHelper.searchStudent(strStudNo);

            binding.txtVwStudName2.setText(student.getStrFullname());
            binding.txtVwStudGender.setText(student.getStrGender());
            binding.txtVwStudNo.setText(student.getStrStudNo());
            binding.txtVwStudState.setText(student.getStrState());
        } else {
            Toast.makeText(this, "Enter a student ID", Toast.LENGTH_SHORT).show();
        }
    }

    /*
    private void fnSearch(View view){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String strURL = "http://192.168.171.1/RESTAPI/rest_api.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, strURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Toast.makeText(getApplicationContext(), "Getting some response here", Toast.LENGTH_SHORT).show();

                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        binding.txtVwStudName2.setText(jsonObject.getString("stud_name"));
                        binding.txtVwStudGender.setText(jsonObject.getString("stud_gender"));
                        binding.txtVwStudNo.setText(jsonObject.getString("stud_no"));
                        binding.txtVwStudState.setText(jsonObject.getString("stud_state"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Unable to fetch student info",Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String,String> getParams() throws AuthFailureError{

                String strStudNo = binding.edtStudID.getText().toString();
                Map<String,String> params = new HashMap<String,String>();
                params.put("selectFn","fnSearchStud");
                params.put("stud_no",strStudNo);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

     */

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}