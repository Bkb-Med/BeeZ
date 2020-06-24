package com.example.apiarymange.Adapter;


import android.app.Activity;
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


        }

    }

    @Override
    public int getItemCount() {
        return  temperatures.size();
    }


}