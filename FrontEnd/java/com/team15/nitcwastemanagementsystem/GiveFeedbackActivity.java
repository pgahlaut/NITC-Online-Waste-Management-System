package com.team15.nitcwastemanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import java.lang.reflect.Method;

public class GiveFeedbackActivity extends AppCompatActivity {

    public boolean validate(EditText e1, EditText e2){
        if(e1.getText().toString().isEmpty()||e2.getText().toString().isEmpty()){
            return false;
        }
        return true;
    }
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_feedback);
        EditText dust_et = findViewById(R.id.dustbin_id_et);
        EditText feed_et = findViewById(R.id.feedback_et);
        Button submit_btn = findViewById(R.id.submit_feedback_btn);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate(dust_et,feed_et)){
                    int d_id = Integer.parseInt(dust_et.getText().toString());
                    String feedback = feed_et.getText().toString();
                    JSONObject js = new JSONObject();

                    try {
                        js.put("dustbin_id",d_id);
                        js.put("feedback",feedback);
                        js.put("uuid",Admin.getInstance().getUuid());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Admin.getInstance().getDomainURL() + "/postfeedback",
                            js, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            String status = "unknown";
                            try {
                                status = response.getString("status");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if(status.equalsIgnoreCase("ok")){
                                Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_LONG).show();
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
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