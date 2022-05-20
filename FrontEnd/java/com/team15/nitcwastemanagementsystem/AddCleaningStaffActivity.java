package com.team15.nitcwastemanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

public class AddCleaningStaffActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    private boolean validate(EditText e1,EditText e2,EditText e3){
        if(e1.getText().toString().isEmpty()||e2.getText().toString().isEmpty()||e3.getText().toString().isEmpty()){
            return false;
        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cleaning_staff);

        EditText name_et = findViewById(R.id.staff_name_et);
        EditText password_et = findViewById(R.id.staff_password_et);
        EditText email_et = findViewById(R.id.staff_email_et);

        Button createStaff = findViewById(R.id.create_staff_btn);
        createStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate(name_et,password_et,email_et)){
                    JSONObject js = new JSONObject();
                    try {
                        js.put("name",name_et.getText().toString());
                        js.put("password",password_et.getText().toString());
                        js.put("email",email_et.getText().toString());
                        js.put("type",2);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Admin.getInstance().getDomainURL() + "/register", js, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            String status = "unknown";
                            try {
                                status = response.getString("status");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if(status.equalsIgnoreCase("ok")){
                                Toast.makeText(getApplicationContext(),"Successfully added staff",Toast.LENGTH_LONG).show();
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(),"Failed to add staff",Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(jsonObjectRequest);
                }
            }
        });
    }
}