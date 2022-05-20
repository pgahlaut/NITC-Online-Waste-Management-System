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

public class RaiseAlertActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_raise_alert);
        EditText alert_et = findViewById(R.id.alert_et);
        EditText dustbin_id = findViewById(R.id.dustbin_id_alert_et);
        Button submit_alert_btn = findViewById(R.id.submit_alert_btn);
        submit_alert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate(alert_et,dustbin_id)){
                    int d_id = Integer.parseInt(dustbin_id.getText().toString());
                    String feedback = alert_et.getText().toString();
                    JSONObject js = new JSONObject();

                    try {
                        js.put("dustbin_id",d_id);
                        js.put("alert",feedback);
                        js.put("uuid",Admin.getInstance().getUuid());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Admin.getInstance().getDomainURL() + "/postalert",
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
                }else{
                    Toast.makeText(getApplicationContext(),"Fill in details before submitting!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}