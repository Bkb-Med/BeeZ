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
import com.example.apiarymange.R;

import java.util.List;

class TempItemViewHolder extends RecyclerView.ViewHolder{

    public TextView Value,DateandTime,State;

    public TempItemViewHolder(View itemView) {
        super(itemView);

        DateandTime = itemView.findViewById(R.id.dateTemp);
        Value = itemView.findViewById(R.id.TempValue);
        State = itemView.findViewById(R.id.stateTemp);

    }
}
public class TempAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Activity activity;
    List<Temperature> temperatures;


    public TempAdapter(Activity activity, List<Temperature> temperatures) {
        this.activity = activity;
        this.temperatures = temperatures;

    }





    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.item_temp, parent, false);
            return new TempItemViewHolder(view);

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof TempItemViewHolder) {



            Temperature temperature = temperatures.get(position);
            TempItemViewHolder viewHolder = (TempItemViewHolder) holder;

            viewHolder.Value.setText(temperature.getTempvalue());
            viewHolder.DateandTime.setText(temperature.getTempDate() + "  " + temperature.getTempTime());
            int Tempvalue = Integer.parseInt(temperature.getTempvalue());
               if(Tempvalue<=0){
                   viewHolder.State.setText("LOW");
                   viewHolder.State.setBackgroundColor(Color.parseColor("#6200EE"));
                   viewHolder.State.invalidate();
               }else if(Tempvalue>0 && Tempvalue <=30){
                   viewHolder.State.setText("MEDIUM");
                   viewHolder.State.setBackgroundColor(Color.parseColor("#00bfa5"));
                   viewHolder.State.invalidate();
               }else if(Tempvalue>30) {
                   viewHolder.State.setText("HIGH");
                   viewHolder.State.setBackgroundColor(Color.parseColor("#fe104d"));
                   viewHolder.State.invalidate();
               }

        }

    }

    @Override
    public int getItemCount() {
        return  temperatures.size();
    }


}