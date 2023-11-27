package com.example.m_hike_emmanuel_lishiba_chiboboka;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class addObservation extends AppCompatActivity {
    
    // variable declarations
    AlertDialog.Builder confirmObservation;

    EditText ObserveTitleText, ObserveTimeText, ObserveComments, ObHikeIdText;
    Button back, storeData, pickTime;

    dbAssister2 dbAssist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_observation);

        // Sets Ids to declared components
        setIds();

        // Stores the button click events of the activity
        buttonClicks();
        confirmObservation = new AlertDialog.Builder(this);

    }

    //Allows the user to select a time
    public  void timePicker(){
        TimePickerDialog tpd = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                ObserveTimeText.setText(String.valueOf(hourOfDay)+":"+String.valueOf(minute));
            }
        }, 12, 32, false);
        tpd.show();
    }

    //Sets the id of all the components being used in this activity
    private void setIds() {
        ObserveTitleText = findViewById(R.id.observationNameId);
        ObserveTimeText = findViewById(R.id.observationTimeId);
        ObserveComments = findViewById(R.id.commentsId);
        ObHikeIdText = findViewById(R.id.obHikeId);

        back = findViewById(R.id.ObserveBackId);
        storeData = findViewById(R.id.saveObservationId);
        pickTime = findViewById(R.id.pickTimeId);
    }

    // Method that stores all the button click events of the activity
    private void buttonClicks() {

        // Takes the user back to the main menu of the application
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainMenu = new Intent(addObservation.this, viewObservations.class);
                startActivity(mainMenu);
            }
        });

        //Stores observation data when pressed
        storeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbAssist = new dbAssister2(addObservation.this);

                //Gets the string values of the respective edit text fields
                String getTitle = ObserveTitleText.getText().toString();
                String getTime = ObserveTimeText.getText().toString();
                String getComments = ObserveComments.getText().toString();
                String getHikeId = ObHikeIdText.getText().toString();

                //Checks if required fields are empty
                if(getTitle.length() == 0 || getTime.length() == 0 || getHikeId .length() == 0){
                    ObserveTitleText.setError("Please set a title");
                    ObserveTimeText.setError("Please set a time");
                    ObHikeIdText.setError("Please enter a hike id");
                }
                else{
                    // Confirms the addition of an observation by displaying a dialogue to the user
                    confirmObservation.setTitle("Proceed?")
                            .setMessage("Do you wish to store these details? \n\n" +
                                    "Observation Name:  " + getTitle +
                                    "\nObservation Time:  " + getTime + "\nComments :  " + getComments
                                    + "\nHike Id :  " + getHikeId)
                            .setCancelable(true)

                            // Will add the data if yes has been selected
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    // makes use of the addObservation() method from
                                    // the dbAssister2 class to add the respective information from text fields in the activity
                                    dbAssist.addObservation(getTitle, getTime, getComments, getHikeId);
                                }
                            })
                            //This cancels the add observation confirmation
                            .setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).show();
                }
            }
        });

        // Allows the user to be able to pick a time
        pickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker();
            }
        });
    }
}
