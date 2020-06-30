package com.example.apiarymange.Adapter;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.example.apiarymange.Editapiary;
import com.example.apiarymange.ListApiaries;
import com.example.apiarymange.ListTemperature;
import com.example.apiarymange.ListTraffic;
import com.example.apiarymange.Model.Apiary;
import com.example.apiarymange.Model.Frame;
import com.example.apiarymange.Model.ListFrames;
import com.example.apiarymange.Model.Temperature;
import com.example.apiarymange.Model.Traffic;
import com.example.apiarymange.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;



class ItemViewHolder extends RecyclerView.ViewHolder{

    public TextView Reference,Location,DateandTime,Temp,Traffic,fc1,fc2,fc3,fc4;
    public RelativeLayout parentLayout;

    public Spinner apSpinner;
    public ProgressBar pc1,pc2,pc3,pc4;
    public ItemViewHolder(View itemView) {
        super(itemView);

        parentLayout = itemView.findViewById(R.id.parentlayout);
        Reference = itemView.findViewById(R.id.txtapiaryreference);
        Location = itemView.findViewById(R.id.txtLocation);
        DateandTime = itemView.findViewById(R.id.txtDateandTime);
        Temp = itemView.findViewById(R.id.txtTemp);
        Traffic = itemView.findViewById(R.id.txtTraffic);
        Traffic = itemView.findViewById(R.id.txtTraffic);
        fc1 = itemView.findViewById(R.id.txtc1);
        fc2 = itemView.findViewById(R.id.txtc2);
        fc3 = itemView.findViewById(R.id.txtc3);
        fc4 = itemView.findViewById(R.id.txtc4);
        pc1 = itemView.findViewById(R.id.prgs1);
        pc2 = itemView.findViewById(R.id.prgs2);
        pc3 = itemView.findViewById(R.id.prgs3);
        pc4 = itemView.findViewById(R.id.prgs4);
        apSpinner = itemView.findViewById(R.id.apSpinner);
    }
}
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM=0,VIEW_TYPE_LOADING=1;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref;
    Activity activity;
    List<Apiary> apiaries;
    List<Traffic> traffics;
    List<Temperature> temperatures;
    List<ListFrames> listFrames ;
    //List<RelativeLayout> cardViewList = new ArrayList<>();
    public MyAdapter(Activity activity, List<Apiary> apiaries, List<Temperature> temperatures, List<Traffic> traffics, List<ListFrames> listFrames) {
        this.activity = activity;
        this.apiaries = apiaries;
        this.temperatures = temperatures;
        this.traffics = traffics;
        this.listFrames = listFrames ;
        ref = database.getReference(fAuth.getCurrentUser().getUid()+"/"+"apiaries");


            }
    @Override
    public int getItemViewType(int position){
            return apiaries.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_ITEM;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_ITEM){
            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.item_appiaries,parent,false);
            return new ItemViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
         if(holder instanceof ItemViewHolder){

             final Apiary apiary = apiaries.get(position);
             ItemViewHolder viewHolder =(ItemViewHolder)holder;
             viewHolder.apSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                 @Override
                 public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                     switch (position){
                         case 1:
                             Intent intent1 = new Intent(activity.getApplicationContext(), ListTemperature.class);
                             intent1.putExtra("apreference", apiary.getAppReference());
                             activity.startActivity(intent1);
                             activity.finish();
                             break;
                         case 2:
                             Intent intent2 = new Intent(activity.getApplicationContext(), ListTraffic.class);
                             intent2.putExtra("apreference", apiary.getAppReference());
                             activity.startActivity(intent2);
                             activity.finish();
                             break;
                         case 3:
                             Intent intent3 = new Intent(activity.getApplicationContext(), Editapiary.class);
                             intent3.putExtra("apreference", apiary.getAppReference());
                             activity.startActivity(intent3);
                             activity.finish();
                             break;
                         case 4:
                             showDialog(apiary.getAppReference());
                             break;
                     }

                 }

                 @Override
                 public void onNothingSelected(AdapterView<?> adapterView) {

                 }
             });
             ArrayList<CustomItemSpinner> customList;
             customList = new ArrayList<>();
             customList.add(new CustomItemSpinner("Actions", R.drawable.ic_settings_aplist));
             customList.add(new CustomItemSpinner("Temp history", R.drawable.ic_filter_drama_black));
             customList.add(new CustomItemSpinner("Traffic history", R.drawable.ic_import_export_black));
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
             viewHolder.apSpinner.setAdapter(adapter);
             viewHolder.apSpinner.setSelection(0);


             viewHolder.Reference.setText(apiary.getAppReference());
             viewHolder.Location.setText(apiary.getApLocation());
             viewHolder.DateandTime.setText(apiary.getApDate()+"  "+apiary.getApTime());

             if(temperatures.size() == apiaries.size()){
             Temperature temperature = temperatures.get(position);
             viewHolder.Temp.setText(temperature.getTempvalue()+"°C");}
             if(traffics.size() == apiaries.size()){
                 Traffic traffic = traffics.get(position);
                 viewHolder.Traffic.setText(traffic.getTfvalue());}
             if(listFrames.size() == apiaries.size()){
                 ListFrames lf = listFrames.get(position);
                 List<Frame> frames = lf.getFrames();
                 Frame fr1 = frames.get(0);
                 Frame fr2 = frames.get(1);
                 Frame fr3 = frames.get(2);
                 Frame fr4 = frames.get(3);
                 viewHolder.fc1.setText(String.valueOf(fr1.getValue())+"%");
                 viewHolder.fc2.setText(String.valueOf(fr2.getValue())+"%");
                 viewHolder.fc3.setText(String.valueOf(fr3.getValue())+"%");
                 viewHolder.fc4.setText(String.valueOf(fr4.getValue())+"%");
                 viewHolder.pc1.setProgress(Integer.valueOf(fr1.getValue().intValue()));
                 viewHolder.pc2.setProgress(Integer.valueOf(fr2.getValue().intValue()));
                 viewHolder.pc3.setProgress(Integer.valueOf(fr3.getValue().intValue()));
                 viewHolder.pc4.setProgress(Integer.valueOf(fr4.getValue().intValue()));
             }

         }

    }

    @Override
    public int getItemCount() {
        return apiaries.size();
    }

    /**
     * @throws Resources.NotFoundException
     */
    private void showDialog(final String refap) throws Resources.NotFoundException {
        new AlertDialog.Builder(activity)
                .setTitle("Confirmation")
                .setMessage(
                        "Do you really want to remove this agent?")
                .setIcon(
                        activity.getResources().getDrawable(
                                android.R.drawable.ic_dialog_alert))
                .setPositiveButton(
                        android.R.string.yes,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                ref.child(refap).removeValue();
                                Toast.makeText(activity.getApplicationContext(), "Apiary successfully removed ", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(activity.getApplicationContext(), ListApiaries.class);
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
    }
}


