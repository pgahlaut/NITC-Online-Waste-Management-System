package com.team15.nitcwastemanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private boolean checkValidFields(EditText e1,EditText e2){
        if(e1.getText().toString().isEmpty()||e2.getText().toString().isEmpty()){
            return false;
        }
        return true;
    }
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText email_et = findViewById(R.id.login_email_edittext);
        EditText pass_et = findViewById(R.id.login_password_edittext);

        Button login_btn =  findViewById(R.id.login_button);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkValidFields(email_et,pass_et)){
                    JSONObject js = new JSONObject();
                    try{
                        js.put("email", email_et.getText().toString());
                        js.put("password", pass_et.getText().toString());
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Admin.getInstance().getDomainURL()+"/login", js, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            String status = "unknown";
                            Admin a;
                            String name="##",email="##@##";
                            int perm=-1;
                            try{
                                status = response.getString("status");
                                name = response.getString("name");
                                email = response.getString("email");
                                perm = response.getInt("type");
                                String id = response.getString("uuid");
                                Log.d(getCallingPackage(),id);
                                Admin.getInstance().setEmail(email);
                                Admin.getInstance().setName(name);
                                Admin.getInstance().setPermissionLevel(perm);
                                Admin.getInstance().setUuid(id);
                            }catch(Exception e){
                                e.printStackTrace();
                            }

                            if(status.equalsIgnoreCase("ok")){
                                if(Admin.getInstance().getPermissionLevel()==1) {
                                    Intent i = new Intent(getApplicationContext(), UserHome.class);
                                    startActivity(i);
                                    finish();
                                }else if(Admin.getInstance().getPermissionLevel()==0){
                                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                                    startActivity(i);
                                    finish();
                                }else if(Admin.getInstance().getPermissionLevel()==2){
                                    Intent i = new Intent(getApplicationContext(), StaffHomeActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(),"Error: "+error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                    requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(jsonObjectRequest);

                }else{
                    Toast.makeText(getApplicationContext(),"Error! Empty field!",Toast.LENGTH_LONG).show();
                }
            }
        });
        Button reg_btn = findViewById(R.id.register_button);
        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(i);
            }
        });
    }
}