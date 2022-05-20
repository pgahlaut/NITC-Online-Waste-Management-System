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

public class ListRequestActivity extends AppCompatActivity {
    ArrayList<UserRequest> requests;
    RequestQueue requestQueue;
    RequestViewAdapter rva;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_request);
        requests=new ArrayList<>();
        if(Admin.getInstance().getPermissionLevel()==0)
            getRequests(true);
        else
            getRequests(false);
        RecyclerView recyclerView = findViewById(R.id.requestRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rva = new RequestViewAdapter(requests);
        recyclerView.setAdapter(rva);
        rva.notifyDataSetChanged();
    }

    public void getRequests(boolean isAdmin){
        //Grab requests from api
        JSONObject js = new JSONObject();
        try {
            if(!isAdmin)
            js.put("uuid",Admin.getInstance().getUuid());
            else
                js.put("email",Admin.getInstance().getEmail());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, Admin.getInstance().getDomainURL() + "/listreq", js, new Response.Listener<JSONObject>() {
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
                            requests.add(new UserRequest(object.getString("_id"),object.getString("title"),
                                    object.getString("uuid"),object.getString("location")));
                            Log.d("REQUEST RET:",object.getString("title"));
                            rva.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else if(status.equalsIgnoreCase("NA")){
                    Toast.makeText(getApplicationContext(),"No Requests",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Error getting list of requests",Toast.LENGTH_LONG).show();
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