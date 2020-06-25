package com.example.apiarymange.Adapter;


import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.apiarymange.ListLocation;
import com.example.apiarymange.ListTemperature;
import com.example.apiarymange.Model.Location;
import com.example.apiarymange.Model.Temperature;
import com.example.apiarymange.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

class LocationItemViewHolder extends RecyclerView.ViewHolder{

    public TextView txtlocation;
    public Spinner lcSpinner;
    public LocationItemViewHolder(View itemView) {
        super(itemView);

        txtlocation = itemView.findViewById(R.id.locations);
        lcSpinner = itemView.findViewById(R.id.locSpinner);

    }
}
public class LocationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("locations");
    Activity activity;
    List<Location> locations;
    EditText txtloc;
    public static String idLoc =null;
    public LocationAdapter(Activity activity, List<Location> locations, EditText txtloc) {
        this.activity = activity;
        this.locations =  locations;
        this.txtloc = txtloc;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity)
                .inflate(R.layout.item_location, parent, false);
        return new LocationItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof LocationItemViewHolder) {



            final Location location = locations.get(position);
            final LocationItemViewHolder viewHolder = (LocationItemViewHolder) holder;
            viewHolder.lcSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                    switch (position){
                        case 1:
                                txtloc.setText(location.getApLocation());
                                idLoc = location.getId();
                            break;
                        case 2:
                            System.out.println("id::"+location.getId());
                            ref.child( location.getId()).removeValue();
                            Toast.makeText(activity.getApplicationContext(), "Locations successfully removed ", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(activity.getApplicationContext(), ListLocation.class);
                            activity.startActivity(intent);
                            activity.finish();
                             break;}

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            ArrayList<CustomItemSpinner> customList;
            customList = new ArrayList<>();
            customList.add(new CustomItemSpinner("Actions", R.drawable.ic_settings_aplist));
            customList.add(new CustomItemSpinner("Edit", R.drawable.ic_edit_black));
            customList.add(new CustomItemSpinner("Delete", R.drawable.ic_delete_black));
            SpinnerAdapter adapter = new SpinnerAdapter(activity.getApplicationContext(), customList){
                @Override
                public boolean isEnabled(int position) {
                    // TODO Auto-generated method stub
                    if (position ==0) {
                        return false;
                    }
                    return true;
                }};
            viewHolder.lcSpinner.setAdapter(adapter);
            viewHolder.lcSpinner.setSelection(0);
            viewHolder.txtlocation.setText(location.getApLocation());

        }

    }

    @Override
    public int getItemCount() {
        return  locations.size();
    }


}