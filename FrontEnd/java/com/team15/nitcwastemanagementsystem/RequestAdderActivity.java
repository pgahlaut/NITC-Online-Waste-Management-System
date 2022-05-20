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
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

public class RequestAdderActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    private final String postURL = Admin.getInstance().getDomainURL()+"/postreq";
    private boolean checkEmpty(EditText e1,EditText e2){
        if(e1.getText().toString().isEmpty()||e2.getText().toString().isEmpty()){
            return false;
        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_adder);
        EditText title = findViewById(R.id.req_title_et);
        EditText loc = findViewById(R.id.req_loc_et);
        EditText sugg = findViewById(R.id.req_sugg_et);
        Button submitReqBtn = findViewById(R.id.submit_req_btn);
        submitReqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkEmpty(title,loc)){
                    JSONObject js = new JSONObject();
                    try{
                        js.put("title", title.getText().toString());
                        js.put("location", loc.getText().toString());
                        if (!sugg.getText().toString().isEmpty()) {
                            js.put("suggestion", sugg.getText().toString());
                        }
                        js.put("uuid",Admin.getInstance().getUuid());
                        js.put("status","Pending");
                        //Log.d("Admin id: ",Admin.getInstance().getUuid());
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postURL, js, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            String status = "unknown";
                            try {
                                status = response.getString("status");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if(status.equalsIgnoreCase("ok")){
                                Toast.makeText(getApplicationContext(),"Request added successfully!",Toast.LENGTH_LONG).show();
                                Intent i=new Intent(getApplicationContext(),UserHome.class);
                                startActivity(i);
                                finish();
                            }else if(status.equalsIgnoreCase("error")){
                                Toast.makeText(getApplicationContext(),"Error in creating a request!",Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(),"Error adding request!",Toast.LENGTH_LONG).show();
                            Log.d("RequestAdderActivity::","Error : "+error.getLocalizedMessage());
                        }
                    });
                    requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(jsonObjectRequest);
                }

            }
        });
    }
}