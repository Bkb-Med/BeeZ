package com.example.apiarymange.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apiarymange.Model.Apiary;
import com.example.apiarymange.Model.ListFrames;
import com.example.apiarymange.Model.Temperature;
import com.example.apiarymange.Model.Traffic;
import com.example.apiarymange.R;

import java.util.List;

class AddViewHolder extends RecyclerView.ViewHolder{

    public TextView Reference,Location,DateandTime,Temp,Traffic,fc1,fc2,fc3,fc4;
    public RelativeLayout parentLayout;

    //public Spinner apSpinner;
    public ProgressBar pc1,pc2,pc3,pc4;
    public AddViewHolder(View itemView) {
        super(itemView);



    }
}
public class AddApiaryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Activity activity;

    public AddApiaryAdapter(Activity activity){this.activity = activity;}
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity)
                .inflate(R.layout.activity_addapiaries,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
