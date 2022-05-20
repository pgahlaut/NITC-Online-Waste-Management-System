package com.team15.nitcwastemanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class RegisterActivity extends AppCompatActivity {
    private String domainURL = "https://127.0.0.1:80";
    private boolean checkEqual(EditText new_pass, EditText conf_pass){
        if(conf_pass.getText().toString().equals(new_pass.getText().toString())==false){
            Toast.makeText(getApplicationContext(),"Passwords do not match!",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button reg_btn = findViewById(R.id.register_btn);
        EditText new_pass_et = findViewById(R.id.new_pass_field);
        EditText conf_passw_et = findViewById(R.id.confirm_pass_field);
        EditText email_et = findViewById(R.id.email_field);
        EditText name_et = findViewById(R.id.name_field);
        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkEqual(new_pass_et,conf_passw_et)) {
                    /*
                    Intent homeIntent = new Intent(getApplicationContext(),HomeActivity.class);
                    startActivity(homeIntent);
                    */
                    String email = email_et.getText().toString();
                    String name = name_et.getText().toString();
                    if(email.isEmpty()||name.isEmpty()){
                        Toast.makeText(getApplicationContext(),"Enter a valid email",Toast.LENGTH_LONG).show();
                        return;
                    }
                    String password = new_pass_et.getText().toString();
                    try{
                        JSONObject js = new JSONObject();
                        js.put("email", email);
                        js.put("password", password);
                        js.put("name",name);
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Admin.getInstance().getDomainURL()+"/register", js, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                String status = "unknown";
                                try {
                                    status = jsonObject.getString("status");
                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
                                if(status.equalsIgnoreCase("ok")) {
                                    Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(loginIntent);
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Toast.makeText(getApplicationContext(),volleyError.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                            }
                        });
                        requestQueue = Volley.newRequestQueue(getApplicationContext());
                        requestQueue.add(jsonObjectRequest);
                        //requestQueue.start();
                    }catch(Exception e){
                        Log.d("ERROR",e.getLocalizedMessage());
                    }

                }
            }
        });
    }
}