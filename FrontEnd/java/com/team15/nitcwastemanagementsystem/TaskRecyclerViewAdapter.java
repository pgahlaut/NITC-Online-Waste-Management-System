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


public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder> {

    ArrayList<Task> requests;
    RequestQueue requestQueue;
    public TaskRecyclerViewAdapter(ArrayList<Task> req, Context context) {
        this.requests = req;
        requestQueue = Volley.newRequestQueue(context);
    }

    @NonNull
    @Override
    public TaskRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View item = layoutInflater.inflate(R.layout.task_card,parent,false);
        ViewHolder viewHolder = new ViewHolder(item);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.title.setText(requests.get(position).getTitle());
        holder.loc.setText(requests.get(position).getLocation());
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject js =new JSONObject();
                try {
                    js.put("task_id",requests.get(position).getTaskid());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Admin.getInstance().getDomainURL() + "/removetask", js, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String status = "unknown";
                        try {
                            status = response.getString("status");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(status.equalsIgnoreCase("ok")){
                            Toast.makeText(view.getContext(), "Successfully removed task",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(view.getContext(), "Failed to remove task",Toast.LENGTH_LONG).show();
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
        TextView title,loc;
        Button remove;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.task_title);
            this.loc = itemView.findViewById(R.id.task_location);
            this.remove = itemView.findViewById(R.id.mark_done_btn);
        }
    }
}

