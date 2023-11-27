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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HikeAdapter extends RecyclerView.Adapter<HikeAdapter.viewHolder> {


    // Variable declarations
    Activity activity;
    private Context context;

    private ArrayList Hikeid, HikeName, HikeDate,
            HikeLength, HikeDifficulty, HikeLocation, HikeNoOfPeople,
            HikeDesc, HikeParking, HikeWeather;

    private List<String> searchList;
    private List<String> searchListFull;

    // This is a constructor that will pass 12 arguments to get the activity, context and the hike details from the respective Activity
    public HikeAdapter(Activity activity, Context context, ArrayList Hikeid, ArrayList HikeName, ArrayList HikeDate
    , ArrayList HikeLength, ArrayList HikeDifficulty, ArrayList HikeLocation, ArrayList HikeWeather,
                       ArrayList HikeNoOfPeople, ArrayList HikeParking, ArrayList HikeDesc) {

        this.activity = activity;
        this.context = context;
        this.Hikeid = Hikeid;
        this.HikeName = HikeName;
        this.HikeDate = HikeDate;
        this.HikeLength = HikeLength;
        this.HikeDifficulty = HikeDifficulty;
        this.HikeLocation = HikeLocation;
        this.HikeWeather = HikeWeather;
        this.HikeNoOfPeople = HikeNoOfPeople;
        this.HikeParking = HikeParking;
        this.HikeDesc = HikeDesc;
    }

    @NonNull
    @Override

    // The HikeAdapter is given a context below which fills in the layout with the respective information

    public HikeAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.hike_entries, parent, false);
        return new viewHolder(v);
    }

    // The onBindViewHolder will set the text of the text fields of the individual selections of the recycler view based on each row of the database
    @Override
    public void onBindViewHolder(@NonNull HikeAdapter.viewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.textId.setText(String.valueOf(Hikeid.get(position)));
        holder.textHike.setText(String.valueOf(HikeName.get(position)));
        holder.textDate.setText(String.valueOf(HikeDate.get(position)));
        holder.textLength.setText(String.valueOf(HikeLength.get(position)));
        holder.textDifficulty.setText(String.valueOf(HikeDifficulty.get(position)));
        holder.textLocation.setText(String.valueOf(HikeLocation.get(position)));
        holder.textWeather.setText(String.valueOf(HikeWeather.get(position)));
        holder.textParking.setText(String.valueOf(HikeParking.get(position)));
        holder.textPeople.setText(String.valueOf(HikeNoOfPeople.get(position)));
        holder.textDesc.setText(String.valueOf(HikeDesc.get(position)));

        // This is an onclick listener attached to each hike detail and will display the data to the updateHike Activity once it is clicked
        holder.chooseHike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // The each intent fills in data from the database row to the respective text field in the updateHikeActivity
                Intent intent = new Intent(context, updateHike.class);
                intent.putExtra("hike_id", String.valueOf(Hikeid.get(position)));
                intent.putExtra("hike_name", String.valueOf(HikeName.get(position)));
                intent.putExtra("hike_location", String.valueOf(HikeLocation.get(position)));
                intent.putExtra("hike_date", String.valueOf(HikeDate.get(position)));
                intent.putExtra("hike_length", String.valueOf(HikeLength.get(position)));
                intent.putExtra("hike_difficulty", String.valueOf(HikeDifficulty.get(position)));
                intent.putExtra("hike_desc", String.valueOf(HikeDesc.get(position)));
                intent.putExtra("hike_parking", String.valueOf(HikeParking.get(position)));
                intent.putExtra("hike_people", String.valueOf(HikeNoOfPeople.get(position)));
                intent.putExtra("hike_weather", String.valueOf(HikeWeather.get(position)));


                activity.startActivityForResult(intent, 1);
            }
        });
    }

    // Returns the number of rows in the database based on the id count
    @Override
    public int getItemCount() {
        return Hikeid.size();
    }

    // This viewHolder class casts the id of the text fields in the chooseHike layout to the declared text fields below.
    // This is backbone to how data from the database is stored to the particular text fields of this layout
    public class viewHolder extends RecyclerView.ViewHolder {
        TextView textId, textHike, textDate, textLength, textDifficulty, textLocation,
        textWeather, textParking, textPeople, textDesc;
       LinearLayout chooseHike;
        public viewHolder(@NonNull View itemView) {

            super(itemView);

            textId = itemView.findViewById(R.id.showHikeId);
            textHike = itemView.findViewById(R.id.showHikeName);
            textLocation = itemView.findViewById(R.id.showLocation);
            textDate = itemView.findViewById(R.id.showHikeDate);
            textLength = itemView.findViewById(R.id.showHikeLength);
            textDifficulty = itemView.findViewById(R.id.showDifficulty);
            textDesc = itemView.findViewById(R.id.showDesc);
            textParking = itemView.findViewById(R.id.showParking);
            textPeople = itemView.findViewById(R.id.showPeople);
            textWeather = itemView.findViewById(R.id.showWeather);

            chooseHike = itemView.findViewById(R.id.chooseHikeid);
        }
    }

    // This method allow filtering based on the hike id of the hike given in the search field
    void filterList(List<String> searchList){
        this.searchList = Hikeid;
        searchListFull = new ArrayList<>(searchList);
        notifyDataSetChanged();
    }


}
