package com.example.m_hike_emmanuel_lishiba_chiboboka;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class viewObservations extends AppCompatActivity {

    //Variable declarations
    EditText searchObserves;

    dbAssister2 dbAssist2;

    ArrayList<String> Observeid, ObservationName, ObservationTime, ObservationComments, ObHikeId;
    ImageButton NewObservation, DeleteAll, back_to_hike;

    observationAdapter obAdapter;
    RecyclerView viewObs;

    AlertDialog.Builder confirmDeleteAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice_observations);
        dbAssist2 = new dbAssister2(this);

        //Declarations of arraylist objects
         Observeid = new ArrayList<>();
         ObservationName = new ArrayList<>();
         ObservationTime = new ArrayList<>();
         ObservationComments = new ArrayList<>();
         ObHikeId = new ArrayList<>();

         //Casting of the ids of the various components utilized in the activity
        searchObserves = findViewById(R.id.searchHere2);
        NewObservation = findViewById(R.id.newObservationId);
        DeleteAll = findViewById(R.id.deleteAllObservations);
        back_to_hike = findViewById(R.id.back_Observations);
        viewObs = findViewById(R.id.observationRecyclerVId);

        //This below attaches the adapter object to the recycler view, viewObs.
        obAdapter = new observationAdapter(viewObservations.this, this, Observeid, ObservationName
        , ObservationTime, ObservationComments, ObHikeId);

        viewObs.setAdapter(obAdapter);
        viewObs.setLayoutManager(new LinearLayoutManager(this));

        //This button will take the user to the add observation activity
        NewObservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainMenu = new Intent(viewObservations.this, addObservation.class);
                startActivity(mainMenu);
            }
        });

        //Takes the user back to the main menu
        back_to_hike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainMenu = new Intent(viewObservations.this, MainActivity.class);
                startActivity(mainMenu);
            }
        });

        //Prompts a delete all observations dialogue for the user to confirm the deletion of all observations
        DeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDeleteAll = new AlertDialog.Builder(viewObservations.this);

                // Confirms the deletion of all observations in the activity
                confirmDeleteAll.setTitle("Delete Observations")
                        .setMessage("Delete all observations?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dbAssist2 = new dbAssister2(viewObservations.this);

                                //Uses the method deleteAllObs() method coming from the dbAssister2 class
                                dbAssist2.deleteAllObs();

                                Intent intent = new Intent(viewObservations.this, viewObservations.class);
                                startActivity(intent);
                            }
                        })

                        // cancels the request for deletion of all observations
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                confirmDeleteAll.create().show();
            }
        });

        showObserveData();
    }

    //Displays from database to the recyclerview of the viewObservations activity
    public void showObserveData() {

        // cursor object used to read data from the database and displays each row from the database table for observations and shows them in the activity
        Cursor cursor = dbAssist2.readObserveData();

        // A condition used to check if there are no items in the observations table
        if(cursor.getCount() == 0) {
            Toast.makeText(viewObservations.this, "No entries exist",Toast.LENGTH_SHORT).show();
            return ;
        }

        //The cursor.moveToNext() reads each row in the database table and will display them
        while (cursor.moveToNext()) {
            Observeid.add(cursor.getString(0));
            ObservationName.add(cursor.getString(1));
            ObservationTime.add(cursor.getString(2));
            ObservationComments.add(cursor.getString(3));
            ObHikeId.add(cursor.getString(4));
        }
    }

}
