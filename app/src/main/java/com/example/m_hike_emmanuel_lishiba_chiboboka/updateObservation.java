package com.example.m_hike_emmanuel_lishiba_chiboboka;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class updateObservation extends AppCompatActivity {

    //Variable declarations
    String Oberseveid, ObservationTitle, ObservationTime, observeComment, ObHikeId;

    EditText ObserveTitleText, ObserveTimeText, ObserveCommentText, ObHikeIdText;

    Button updateDetails, deleteDetails, back, pickTime;

    AlertDialog.Builder confirmUpdate;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_observation);

        // Method to store all id assignment of components
        setIds();

        //Important to call the intent data before using the updateData method
        GetSetObIntentData();

        confirmUpdate = new AlertDialog.Builder(this);

        // Brings a dialogue to the user to confirm an observation update
        updateDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbAssister2 dbAssist2 = new dbAssister2(updateObservation.this);

                String getObserveTitle = ObserveTitleText.getText().toString();
                String getObserveTime = ObserveTimeText.getText().toString();
                String getObserveComment = ObserveCommentText.getText().toString();
                String getObHikeId = ObHikeIdText.getText().toString();

                //Checks for empty fields
                if(getObserveTitle.length() == 0 || getObserveTime.length() == 0 || getObHikeId.length() == 0){
                    ObserveTitleText.setError("Please enter an observation title.");
                    ObserveTimeText.setError("Please enter a time.");
                    ObHikeIdText.setError("Please enter a hike id.");
                }
                else {
                    //This block will show a dialogue message to confirm the details of the observation.
                    confirmUpdate.setTitle("Update Observation Details")
                            .setMessage("Do you wish to store these details? \n\n" +
                                    "Observation Name:  " + getObserveTitle +
                                            "\nObservation Time:  " + getObserveTime + "\nComments :  " + getObserveComment
                                            + "\nHike Id :  " + getObHikeId )
                            .setCancelable(true)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    // This method is called from the dbAssister2 class and will update the data from the respective text fields,
                                    // and add them to the database
                                    dbAssist2.updateObserveData(Oberseveid ,getObserveTitle, getObserveTime, getObserveComment, getObHikeId);

                                    Intent intent = new Intent(updateObservation.this, viewObservations.class);
                                    startActivity(intent);
                                }
                            })
                            // Cancels the update request
                            .setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).show();

                }
            }
        });

        // Makes use of the confirmDelete() method which basically asks the user to carry on with deleting the observation
        deleteDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDelete();
            }
        });

        // The back button will take the user back to the viewObservation activity of the application
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainMenu = new Intent(updateObservation.this, viewObservations.class);
                startActivity(mainMenu);
            }
        });

        // Allows the to pick a time to add
        pickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker();
            }
        });

    }

    //Confirms the deletion of a particular observation
    private void confirmDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + ObservationTitle + " ?");
        builder.setMessage("Proceed to delete this information?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbAssister2 dbAssist2 = new dbAssister2(updateObservation.this);
                dbAssist2.deleteObRow(Oberseveid);
                finish();
            }
        });
        // Cancels the deletion request
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

    //Obtains the row data from the database and places the information in the respective text boxes
    private void GetSetObIntentData() {
        if(getIntent().hasExtra("ob_id") && getIntent().hasExtra("observation_title") && getIntent().hasExtra("observation_time")
                && getIntent().hasExtra("comments") && getIntent().hasExtra("hikeid")) {

            //Get data from the intent
            Oberseveid = getIntent().getStringExtra("ob_id");
            ObservationTitle = getIntent().getStringExtra("observation_title");
            ObservationTime = getIntent().getStringExtra("observation_time");
            observeComment = getIntent().getStringExtra("comments");
            ObHikeId = getIntent().getStringExtra("hikeid");


            //Set intent data to the edit texts
            ObserveTitleText.setText(ObservationTitle);
            ObserveTimeText.setText(ObservationTime);
            ObserveCommentText.setText(observeComment);
            ObHikeIdText.setText(ObHikeId);
        }
        else {
            Toast.makeText(this,"No data", Toast.LENGTH_SHORT).show();
        }
    }

    //Casts the ids of the respective components used in the activity
    private void setIds() {
        ObserveTitleText = findViewById(R.id.observationNameId2);
        ObserveTimeText = findViewById(R.id.observationTimeId2);
        ObserveCommentText = findViewById(R.id.commentsId2);
        ObHikeIdText = findViewById(R.id.obHikeId2);

       updateDetails = findViewById(R.id.updateObservationId);
       back = findViewById(R.id.ObserveBackId2);
       deleteDetails = findViewById(R.id.ObserveDeleteId2);
       pickTime = findViewById(R.id.pickTimeId2);
    }

    //Allows the user to pick a time when it is called
    public  void timePicker(){
        TimePickerDialog tpd = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                ObserveTimeText.setText(String.valueOf(hourOfDay)+":"+String.valueOf(minute));
            }
        }, 12, 32, false);
        tpd.show();
    }
}
