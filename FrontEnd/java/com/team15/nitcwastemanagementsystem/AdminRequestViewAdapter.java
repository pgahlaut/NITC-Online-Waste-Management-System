package com.team15.nitcwastemanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class AdminRequestViewAdapter extends RecyclerView.Adapter<AdminRequestViewAdapter.ViewHolder> {

    ArrayList<UserRequest> requests;
    RequestQueue requestQueue;
    public AdminRequestViewAdapter(ArrayList<UserRequest> req, Context context) {
        this.requests = req;
        requestQueue = Volley.newRequestQueue(context);
    }

    @NonNull
    @Override
    public AdminRequestViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View item = layoutInflater.inflate(R.layout.admin_req_card,parent,false);
        ViewHolder viewHolder = new ViewHolder(item);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdminRequestViewAdapter.ViewHolder holder, int position) {
        holder.title.setText(requests.get(position).getTitle());
        holder.location.setText(requests.get(position).getLocation());
        holder.status.setText(requests.get(position).getStatus());
        holder.approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //remove the req and add in tasks
                JSONObject js = new JSONObject();
                try {
                    js.put("req_id",requests.get(position).getRequestId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Admin.getInstance().getDomainURL() + "/addtask", js, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String status = "unknown";
                        try {
                            status = response.getString("status");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(status.equalsIgnoreCase("ok")){
                            Toast.makeText(view.getContext(), "Added to task ",Toast.LENGTH_LONG).show();
                            requests.remove(position);
                            notifyDataSetChanged();
                        }else{
                            Toast.makeText(view.getContext(), "Failed to add to task ",Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue.add(jsonObjectRequest);

            }
        });
        holder.decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //simply remove the request;
                JSONObject js = new JSONObject();
                try {
                    js.put("req_id",requests.get(position).getRequestId());
                }catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Admin.getInstance().getDomainURL() + "/deleterequest", js, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String status = "unknown";
                        try {
                            status = response.getString("status");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(status.equalsIgnoreCase("ok")){
                            Toast.makeText(view.getContext(), "Request declined ",Toast.LENGTH_LONG).show();
                            requests.remove(position);
                            notifyDataSetChanged();
                        }else{
                            Toast.makeText(view.getContext(), "Failed to decline ",Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue.add(jsonObjectRequest);
            }
        });

    }

    @Override
    public int getItemCount() {
        Log.d("ITEM COUNT:",""+requests.size());
        return requests.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,location,status;
        Button approve,decline;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.req_title);
            this.location = itemView.findViewById(R.id.req_location);
            this.approve = itemView.findViewById(R.id.approve_req_btn);
            this.decline = itemView.findViewById(R.id.decline_req_btn);
            this.status = itemView.findViewById(R.id.req_status);
        }
    }
}

