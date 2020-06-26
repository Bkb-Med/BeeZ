package com.example.apiarymange.Adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.apiarymange.Model.Temperature;
import com.example.apiarymange.Model.Traffic;
import com.example.apiarymange.R;

import java.util.List;



class TrafficItemViewHolder extends RecyclerView.ViewHolder{

    public TextView Value,DateandTime,State;

    public TrafficItemViewHolder(View itemView) {
        super(itemView);

        DateandTime = itemView.findViewById(R.id.DateTraffic);
        Value = itemView.findViewById(R.id.trafficValue);
        State = itemView.findViewById(R.id.stateTraffic);

    }
}
public class TrafficAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Activity activity;
    List<Traffic> traffics;


    public TrafficAdapter(Activity activity,List<Traffic> traffics) {
        this.activity = activity;
        this.traffics = traffics;

    }





    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity)
                .inflate(R.layout.item_traffic, parent, false);
        return new TrafficItemViewHolder(view);

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof TrafficItemViewHolder) {



            Traffic traffic = traffics.get(position);
            TrafficItemViewHolder viewHolder = (TrafficItemViewHolder) holder;

            viewHolder.Value.setText(traffic.getTfvalue());
            viewHolder.DateandTime.setText(traffic.getTfDate() + "  " + traffic.getTfTime());
            int Trafficvalue = Integer.parseInt(traffic.getTfvalue());
            if(Trafficvalue<=20){
                viewHolder.State.setText("LOW");
                viewHolder.State.setBackgroundColor(Color.parseColor("#6200EE"));
                viewHolder.State.invalidate();
            }else if(Trafficvalue>20 && Trafficvalue <=100){
                viewHolder.State.setText("MEDIUM");
                viewHolder.State.setBackgroundColor(Color.parseColor("#00bfa5"));
                viewHolder.State.invalidate();
            }else if(Trafficvalue>100) {
                viewHolder.State.setText("HIGH");
                viewHolder.State.setBackgroundColor(Color.parseColor("#fe104d"));
                viewHolder.State.invalidate();
            }

        }

    }

    @Override
    public int getItemCount() {
        return  traffics.size();
    }


}