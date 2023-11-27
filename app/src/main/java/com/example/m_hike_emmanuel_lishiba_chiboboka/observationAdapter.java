package com.example.m_hike_emmanuel_lishiba_chiboboka;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.LauncherActivity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class observationAdapter extends RecyclerView.Adapter<observationAdapter.viewHolder>{

    // Variable declarations
    Activity activity;
    private Context context;
    private ArrayList Observeid, ObservationName, ObservationTime, ObservationComments, ObHikeId;


    // This is a constructor that will pass 7 arguments to get the activity, context and the observation details from the respective Activity
    public observationAdapter(Activity activity, Context context, ArrayList Observeid, ArrayList ObservationName, ArrayList ObservationTime
            , ArrayList ObservationComments, ArrayList ObHikeId) {

        this.activity = activity;
        this.context = context;
        this.Observeid = Observeid;
        this.ObservationName = ObservationName;
        this.ObservationTime = ObservationTime;
        this.ObservationComments = ObservationComments;
        this.ObHikeId = ObHikeId;

    }

    // The observationAdapter is given a context below which fills in the layout with the respective information
    @NonNull
    @Override
    public observationAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.observe_entries, parent, false);
        return new viewHolder(v);
    }

    // The onBindViewHolder will set the text of the text fields of the individual selections of the recycler view based on each row of the database
    @Override
    public void onBindViewHolder(@NonNull observationAdapter.viewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.ObtextId.setText(String.valueOf(Observeid.get(position)));
        holder.ObTitleText.setText(String.valueOf(ObservationName.get(position)));
        holder.ObTimeText.setText(String.valueOf(ObservationTime.get(position)));
        holder.ObCommentsText.setText(String.valueOf(ObservationComments.get(position)));
        holder.OBHikeIdText.setText(String.valueOf(ObHikeId.get(position)));

        // This is an onclick listener attached to each observation detail and will display the data to the updateObservation Activity once it is clicked
        holder.chooseObservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // The each intent fills in data from the database row to the respective text field in the updateObservation Activity
                Intent intent2 = new Intent(context, updateObservation.class);
                intent2.putExtra("ob_id", String.valueOf(Observeid.get(position)));
                intent2.putExtra("observation_title", String.valueOf(ObservationName.get(position)));
                intent2.putExtra("observation_time", String.valueOf(ObservationTime.get(position)));
                intent2.putExtra("comments", String.valueOf(ObservationComments.get(position)));
                intent2.putExtra("hikeid", String.valueOf(ObHikeId.get(position)));

                activity.startActivityForResult(intent2, 1);
            }
        });
    }

    // Returns the number of rows in the database based on the id count
    @Override
    public int getItemCount() {
        return Observeid.size();
    }

    // This method allow filtering based on the observation id of the observation given in the search field
    public void filterList(ArrayList<String> filteredlist) {
        ObHikeId = filteredlist;
        notifyDataSetChanged();
    }

    // This viewHolder class casts the id of the text fields in the chooseObservation layout to the declared text fields below.
    // This is backbone to how data from the database is stored to the particular text fields of this layout
    public class viewHolder extends RecyclerView.ViewHolder {
        TextView ObtextId, ObTitleText , ObTimeText , ObCommentsText, OBHikeIdText;
        LinearLayout chooseObservation;
        public viewHolder(@NonNull View itemView) {

            super(itemView);

            ObtextId = itemView.findViewById(R.id.showObservationId);
            ObTitleText = itemView.findViewById(R.id.showObservationTitle);
            ObTimeText = itemView.findViewById(R.id.showObservationTime);
            ObCommentsText = itemView.findViewById(R.id.showComments);
            OBHikeIdText = itemView.findViewById(R.id.showOBHikeId);


            chooseObservation = itemView.findViewById(R.id.chooseObservationid);
        }


    }
}
