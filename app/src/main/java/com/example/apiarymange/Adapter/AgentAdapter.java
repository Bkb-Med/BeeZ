package com.example.apiarymange.Adapter;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apiarymange.EditAgent;
import com.example.apiarymange.ListAgent;
import com.example.apiarymange.ListLocation;
import com.example.apiarymange.Model.Agent;
import com.example.apiarymange.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

class AgentItemViewHolder extends RecyclerView.ViewHolder{

    public TextView name,adresse,email,phone;
    public CircleImageView imgView;
    public Spinner agSpinner;
    public AgentItemViewHolder(View itemView) {
        super(itemView);
        imgView =  itemView.findViewById(R.id.profile_image);
        name = itemView.findViewById(R.id.username);
        adresse = itemView.findViewById(R.id.useradresse);
        email = itemView.findViewById(R.id.useremail);
        phone =   itemView.findViewById(R.id.userphone);
        agSpinner = itemView.findViewById(R.id.agSpinner);
    }
}
public class AgentAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    Activity activity;
    List<Agent> agents;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference(fAuth.getCurrentUser().getUid()+"/"+"agents");

    public AgentAdapter(Activity activity, List<Agent> agents) {
        this.activity = activity;
        this.agents = agents;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity)
                .inflate(R.layout.item_agents, parent, false);
        return new AgentItemViewHolder(view);

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AgentItemViewHolder) {

            final Agent agent = agents.get(position);
            AgentItemViewHolder viewHolder = (AgentItemViewHolder) holder;
            viewHolder.agSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                    switch (position){
                        case 1:
                            Intent intent1 = new Intent(activity.getApplicationContext(), EditAgent.class);
                            intent1.putExtra("agentId", agent.getAgentId());
                            activity.startActivity(intent1);
                            activity.finish();
                            break;
                        case 2:
                            showDialog(agent.getAgentId());
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

            viewHolder.agSpinner.setAdapter(adapter);
            viewHolder.agSpinner.setSelection(0);


            viewHolder.name.setText(agent.getFullname());
            viewHolder.adresse.setText(agent.getAdresse());
            viewHolder.email.setText(agent.getAgentEmail());
            viewHolder.phone.setText(agent.getAgentPhone());

           Picasso.get()
                    .load(agent.getmImageUrl())
                     .error( R.drawable.ic_error )
                     .placeholder( R.drawable.progress_placeholder )
                    .into(((AgentItemViewHolder) holder).imgView);

        }

    }

    @Override
    public int getItemCount() {
        return  agents.size();
    }


    /**
     * @throws Resources.NotFoundException
     */
    private void showDialog(final String agentid) throws Resources.NotFoundException {
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
                                ref.child( agentid).removeValue();
                                Toast.makeText(activity.getApplicationContext(), "Agent successfully removed ", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(activity.getApplicationContext(), ListAgent.class);
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
