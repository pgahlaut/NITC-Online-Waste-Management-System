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

public class AdminViewFeedbackActivity extends AppCompatActivity {
    ViewFeedbackAdapter rva;
    RequestQueue requestQueue;
    ArrayList<Feedback> feedbacks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_feedback);
        feedbacks = new ArrayList<>();
        //getFeedback
        getFeedbacks();
        RecyclerView rv = findViewById(R.id.feedback_recyclerView);
        rva = new ViewFeedbackAdapter(feedbacks,getApplicationContext());
        rv.setAdapter(rva);

        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rva.notifyDataSetChanged();
    }

    private void getFeedbacks() {
        //Grab requests from api
        JSONObject js = new JSONObject();
        try {

            js.put("email",Admin.getInstance().getEmail());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, Admin.getInstance().getDomainURL() + "/listfeedbacks", js, new Response.Listener<JSONObject>() {
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
                            feedbacks.add(new Feedback(Integer.parseInt(object.getString("dustbin_id")),object.getString("feedback"),
                                    object.getString("_id")));
                            Log.d("REQUEST RET:",object.getString("feedback"));
                            rva.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else if(status.equalsIgnoreCase("NA")){
                    Toast.makeText(getApplicationContext(),"No Feedbacks",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Error getting list of feedbacks",Toast.LENGTH_LONG).show();
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