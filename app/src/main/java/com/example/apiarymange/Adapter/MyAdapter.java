package com.example.apiarymange.Adapter;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apiarymange.Interface.ILoadMore;
import com.example.apiarymange.ListApiaries;
import com.example.apiarymange.Model.Apiary;
import com.example.apiarymange.Model.Frame;
import com.example.apiarymange.Model.Item;
import com.example.apiarymange.Model.ListFrames;
import com.example.apiarymange.Model.Temperature;
import com.example.apiarymange.Model.Traffic;
import com.example.apiarymange.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



class ItemViewHolder extends RecyclerView.ViewHolder{

    public TextView Reference,Location,DateandTime,Temp,Traffic,fc1,fc2,fc3,fc4;
    public Spinner apSpinner;
    public ProgressBar pc1,pc2,pc3,pc4;
    public ItemViewHolder(View itemView) {
        super(itemView);
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
        apSpinner = itemView.findViewById(R.id.apiarySpinner);
    }
}
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM=0,VIEW_TYPE_LOADING=1;

    ILoadMore loadMore;
    boolean isLoading;
    Activity activity;
    List<Apiary> apiaries;
    List<Traffic> traffics;
    List<Temperature> temperatures;
    List<ListFrames> listFrames ;
    int visibleThreshold=5;
    int lastVisibleItem,totalItemCount;

    public MyAdapter(RecyclerView recyclerView,Activity activity,List<Apiary> apiaries,List<Temperature> temperatures,List<Traffic> traffics,    List<ListFrames> listFrames ) {
        this.activity = activity;
        this.apiaries = apiaries;
        this.temperatures = temperatures;
        this.traffics = traffics;
        this.listFrames = listFrames ;





        final LinearLayoutManager linearLayoutManager =(LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem=linearLayoutManager.findLastVisibleItemPosition();
                if(!isLoading && totalItemCount <= (lastVisibleItem+visibleThreshold)){
                    if(loadMore != null)
                        loadMore.OnLoadMore();
                        isLoading=true;
                }

            }
        });
    }
    @Override
    public int getItemViewType(int position){
            return apiaries.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_ITEM;
    }

    public void setLoadMore(ILoadMore loadMore) {
        this.loadMore = loadMore;
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
         if(holder instanceof ItemViewHolder){

             Apiary apiary = apiaries.get(position);
              ItemViewHolder viewHolder =(ItemViewHolder)holder;
             setSpinner(viewHolder);
             viewHolder.Reference.setText(apiary.getAppReference());
             viewHolder.Location.setText(apiary.getLocation());
             viewHolder.DateandTime.setText(apiary.getAppDate()+"  "+apiary.getAppTime());

             if(temperatures.size() == apiaries.size()){
             Temperature temperature = temperatures.get(position);
             viewHolder.Temp.setText(temperature.getValue());}
             if(traffics.size() == apiaries.size()){
                 Traffic traffic = traffics.get(position);
                 viewHolder.Traffic.setText(traffic.getValue());}
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
    private void setSpinner( ItemViewHolder viewHolder){
        // Spinner click listener
        viewHolder.apSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //String ASIN = gameDataArr.get(position).getAmazonId();
                System.out.println(parent.getId());     // <--- prints the same value for each item.
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Item 1");
        categories.add("Item 2");
        categories.add("Item 3");
        categories.add("Item 4");
        categories.add("Item 5");
        categories.add("Item 6");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(activity.getApplicationContext(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        viewHolder.apSpinner.setAdapter(dataAdapter);
    }
    public void setLoaded() {
        isLoading = false;
    }
}
