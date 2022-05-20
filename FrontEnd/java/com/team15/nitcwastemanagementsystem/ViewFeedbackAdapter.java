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


public class ViewFeedbackAdapter extends RecyclerView.Adapter<ViewFeedbackAdapter.ViewHolder> {

    ArrayList<Feedback> requests;
    RequestQueue requestQueue;
    public ViewFeedbackAdapter(ArrayList<Feedback> req, Context context) {
        this.requests = req;
        requestQueue = Volley.newRequestQueue(context);
    }

    @NonNull
    @Override
    public ViewFeedbackAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View item = layoutInflater.inflate(R.layout.feedback_card,parent,false);
        ViewHolder viewHolder = new ViewHolder(item);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewFeedbackAdapter.ViewHolder holder, int position) {
        holder.id.setText(requests.get(position).getDustbin_id());
        holder.msg.setText(requests.get(position).getMsg());


    }

    @Override
    public int getItemCount() {
        Log.d("ITEM COUNT:",""+requests.size());
        return requests.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView id,msg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.id = itemView.findViewById(R.id.feedback_dust_id);
            this.msg = itemView.findViewById(R.id.feedback_message);
       }
    }
}

