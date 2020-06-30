package com.example.apiarymange.Adapter;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apiarymange.ListAgent;
import com.example.apiarymange.ListLocation;
import com.example.apiarymange.ListTemperature;
import com.example.apiarymange.Model.Location;
import com.example.apiarymange.Model.Temperature;
import com.example.apiarymange.R;
import com.google.firebase.auth.FirebaseAuth;
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
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference(fAuth.getCurrentUser().getUid()+"/"+"locations");
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
                            showDialog( location.getId());

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
    /**
     * @throws Resources.NotFoundException
     */
    private void showDialog(final String agentid) throws Resources.NotFoundException {
        new AlertDialog.Builder(activity)
                .setTitle("Confirmation")
                .setMessage(
                        "Do you really want to remove this Location?")
                .setIcon(
                        activity.getResources().getDrawable(
                                android.R.drawable.ic_dialog_alert))
                .setPositiveButton(
                        android.R.string.yes,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                ref.child( agentid).removeValue();
                                Toast.makeText(activity.getApplicationContext(), "Location successfully removed ", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(activity.getApplicationContext(), ListLocation.class);
                                activity.startActivity(intent);
                                activity.finish();
                            }
                        })
                .setNegativeButton(
                        android.R.string.no,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                //Do Something Here
                            }
                        }).show();
    }}

