package com.team15.nitcwastemanagementsystem;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class RequestViewAdapter extends RecyclerView.Adapter<RequestViewAdapter.ViewHolder> {

    ArrayList<UserRequest> requests;
    public RequestViewAdapter(ArrayList<UserRequest> req) {
        this.requests = req;
    }

    @NonNull
    @Override
    public RequestViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View item = layoutInflater.inflate(R.layout.request_card,parent,false);
        ViewHolder viewHolder = new ViewHolder(item);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewAdapter.ViewHolder holder, int position) {
        holder.title.setText(requests.get(position).getTitle());
        holder.location.setText(requests.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        Log.d("ITEM COUNT:",""+requests.size());
        return requests.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,location;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.req_title);
            this.location = itemView.findViewById(R.id.req_location);
        }
    }
}

