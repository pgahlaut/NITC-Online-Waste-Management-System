package com.team15.nitcwastemanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewCleaningStaffActivity extends AppCompatActivity {
    ArrayList<Staff> staffs;
    ViewStaffReAdapter rva;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cleaning_staff);
        staffs =new ArrayList<>();
        //get staff accounts
        getStaffs();
        RecyclerView rv = findViewById(R.id.view_staff_recycler_view);
        rva = new ViewStaffReAdapter(staffs,getApplicationContext());
        rv.setAdapter(rva);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rva.notifyDataSetChanged();
    }

    private void getStaffs() {
        //Grab accounts from api
        JSONObject js = new JSONObject();
        try {

            js.put("email",Admin.getInstance().getEmail());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, Admin.getInstance().getDomainURL() + "/getstaffs", js, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String status = "unknown";
                try {
                    status = response.getString("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(status.equalsIgnoreCase("ok")){
                    try {
                        JSONArray posts = response.getJSONArray("post");
                        for(int i=0;i<posts.length();i++){
                            JSONObject object = posts.getJSONObject(i);
                            staffs.add(new Staff(object.getString("_id"),object.getString("name"),object.getString("email")));
                            Log.d("Staff RET:",object.getString("name"));
                            rva.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else if(status.equalsIgnoreCase("NA")){
                    Toast.makeText(getApplicationContext(),"No accounts",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Error getting list of accounts",Toast.LENGTH_LONG).show();
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