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


public class ViewStaffReAdapter extends RecyclerView.Adapter<ViewStaffReAdapter.ViewHolder> {

    ArrayList<Staff> requests;
    RequestQueue requestQueue;
    public ViewStaffReAdapter(ArrayList<Staff> req, Context context) {
        this.requests = req;
        requestQueue = Volley.newRequestQueue(context);
    }

    @NonNull
    @Override
    public ViewStaffReAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View item = layoutInflater.inflate(R.layout.staff_card,parent,false);
        ViewHolder viewHolder = new ViewHolder(item);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewStaffReAdapter.ViewHolder holder, int position) {
        holder.name.setText(requests.get(position).getName());
        holder.email.setText(requests.get(position).getEmail());
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject js =new JSONObject();
                try {
                    js.put("uuid",requests.get(position).getUuid());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Admin.getInstance().getDomainURL() + "/removestaff", js, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String status = "unknown";
                        try {
                            status = response.getString("status");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(status.equalsIgnoreCase("ok")){
                            Toast.makeText(view.getContext(), "Successfully removed staff",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(view.getContext(), "Failed to remove staff",Toast.LENGTH_LONG).show();
                        }
                        requests.remove(position);
                        notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue = Volley.newRequestQueue(view.getContext());
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
        TextView name,email;
        Button remove;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.staff_name);
            this.email = itemView.findViewById(R.id.staff_email);
            this.remove = itemView.findViewById(R.id.remove_staff_btn);
        }
    }
}

