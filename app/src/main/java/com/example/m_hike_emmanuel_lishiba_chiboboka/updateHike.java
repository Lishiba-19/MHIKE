package com.example.m_hike_emmanuel_lishiba_chiboboka;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

public class updateHike extends AppCompatActivity {

    //Variable declarations
    EditText HikenameInput, HikeLocationInput, HikedateInput, HikelengthInput, HikedifficultyInput, descInput,  noOfPeopleInput, weatherInput;
    String Hikeid, Hikename, Hikedate, Hikelength, Hikedifficulty, Hikeweather, Hikelocation, Hikeparking ,Hikepeople, Hikedesc, ObId, ObhikeId;
    TextView parkOption;
    RadioButton selectedOption;
    RadioGroup yes_no;

    Button updateDetails, deleteDetails, back, pickDate;

    AlertDialog.Builder confirmUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_hike);
        setIds();

        //Important to call the intent data before using the updateData method
        GetSetIntentData();

        confirmUpdate = new AlertDialog.Builder(this);

        // Brings a dialogue to the user to confirm a hike update
        updateDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dbAssist is called here to make use of its methods
                dbAssister dbAssist = new dbAssister(updateHike.this);

                //Sting variables declared to hold the data from the respective text fields
                String getHikeName = HikenameInput.getText().toString();
                String getLocation = HikeLocationInput.getText().toString();
                String getDate = HikedateInput.getText().toString();
                String getLength = HikelengthInput.getText().toString();
                String getDifficulty = HikedifficultyInput.getText().toString();
                String getDesc = descInput.getText().toString();
                String getNoOfPeople = noOfPeopleInput.getText().toString();
                String getWeather = weatherInput.getText().toString();
                String getParking = parkOption.getText().toString();

                //Checks for empty fields
                if(getHikeName.length() == 0 || getLocation.length() == 0 || getDate.length() == 0 || getLength.length() == 0
                        || getDifficulty.length() == 0 || getNoOfPeople.length() == 0 || getWeather.length() == 0){
                    HikenameInput.setError("Please enter a hike name.");
                    HikeLocationInput.setError("Please enter a location.");
                    HikedateInput.setError("Please enter a date.");
                    HikelengthInput.setError("Please enter a hike length.");
                    HikedifficultyInput.setError("Please enter a hike difficulty.");
                    noOfPeopleInput.setError("Please enter the number of people.");
                    weatherInput.setError("Please enter the weather.");
                }
                else {
                    //This block will show a dialogue message to confirm the details of the Hike.
                    confirmUpdate.setTitle("Update Hike Details")
                                    .setMessage("Do you wish to update these details? \n\n" +
                                            "Hike Name:  " + getHikeName +
                                            "\nHike Location:  " + getLocation + "\nHike Date :  " + getDate + "\nParking :  " + getParking
                                            + "\nHike Length:  " + getLength + "\nHike Difficulty:  " + getDifficulty +
                                            "\nHike Description:  " + getDesc + "\nHike Distance Covered:  " + getNoOfPeople +
                                            "\nHike Weather:  " + getWeather)
                                    .setCancelable(true)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            // This method is called from the dbAssister class and will update the data from the respective text fields,
                                            // and add them to the database
                                            dbAssist.updateData(Hikeid ,getHikeName, getLocation, getDate, getParking, getLength, getDifficulty, getDesc
                                                    , getNoOfPeople, getWeather);

                                            finish();
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

        // Makes use of the confirmDelete() method which basically asks the user to carry on with deleting the hike
        deleteDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDelete();
            }
        });

        // The back button will take the user back to the MainActivity layout of the application
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainMenu = new Intent(updateHike.this, MainActivity.class);
                startActivity(mainMenu);
            }
        });

        // Allows the users to select a date
        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate();
            }
        });

    }

    // Casts declared ids from the xml layouts and sets them to the declared components of this activity
    private void setIds() {
        HikenameInput = findViewById(R.id.hikeNameId2);
        HikedateInput = findViewById(R.id.hikeDateId2);
        HikelengthInput = findViewById(R.id.hikeLengthId2);
        HikedifficultyInput = findViewById(R.id.hikeDifficultyId2);
        HikeLocationInput= findViewById(R.id.locationNameId2);
        descInput = findViewById(R.id.hikeDescId2);
        noOfPeopleInput = findViewById(R.id.hikePeopleId2);
        weatherInput = findViewById(R.id.hikeWeatherId2);
        yes_no = findViewById(R.id.parkingR_GroupId2);
        parkOption = findViewById(R.id.parkOptionId2);
        updateDetails = findViewById(R.id.updateHikebtnId);
        deleteDetails = findViewById(R.id.deleteHikebtnId);
        back = findViewById(R.id.backBtnId);
        pickDate = findViewById(R.id.pickDate2);
    }


    // Radio button functionality to enable selection and text assignment
    public void parkOption2(View v){
        int radioSelect = yes_no.getCheckedRadioButtonId();
        selectedOption = findViewById(radioSelect);

        parkOption.setText(selectedOption.getText().toString());

    }

    // This method gets a row from the database based on the Id given.
    // And it passes the row data to the respective text fields in the activity
    void GetSetIntentData() {
        if(getIntent().hasExtra("hike_id") && getIntent().hasExtra("hike_name") && getIntent().hasExtra("hike_location") && getIntent().hasExtra("hike_date") &&
                getIntent().hasExtra("hike_parking") && getIntent().hasExtra("hike_length") && getIntent().hasExtra("hike_difficulty")&& getIntent().hasExtra("hike_desc")
          && getIntent().hasExtra("hike_people") && getIntent().hasExtra("hike_weather")) {

            //Get data from the intent
            Hikeid = getIntent().getStringExtra("hike_id");
            Hikename = getIntent().getStringExtra("hike_name");
            Hikelocation = getIntent().getStringExtra("hike_location");
            Hikedate = getIntent().getStringExtra("hike_date");
            Hikeparking = getIntent().getStringExtra("hike_parking");
            Hikelength = getIntent().getStringExtra("hike_length");
            Hikedifficulty = getIntent().getStringExtra("hike_difficulty");
            Hikedesc = getIntent().getStringExtra("hike_desc");
            Hikepeople = getIntent().getStringExtra("hike_people");
            Hikeweather = getIntent().getStringExtra("hike_weather");


            //Set intent data to the edit texts
            HikenameInput.setText(Hikename);
            HikeLocationInput.setText(Hikelocation);
            HikedateInput.setText(Hikedate);
            parkOption.setText(Hikeparking);
            HikelengthInput.setText(Hikelength);
            HikedifficultyInput.setText(Hikedifficulty);
            descInput.setText(Hikedesc);
            noOfPeopleInput.setText(Hikepeople);
            weatherInput.setText(Hikeweather);

        }
        else {
            //Displays a toast message showing no data if there is nothing to show
            Toast.makeText(this,"No data", Toast.LENGTH_SHORT).show();
        }
    }

    // This method confirms the deletion of a chosen hike
    void confirmDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Setting up of the alert dialogue message
        builder.setTitle("Delete " + Hikename+ " ?");
        builder.setMessage("Proceed to delete this information?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbAssister dbAssist = new dbAssister(updateHike.this);
                dbAssister2 dbAssister2 = new dbAssister2(updateHike.this);

                // This condition will check if a hike id matches any observation,
                // and deletes the corresponding observations of the hike, i.e, observations with a matching hike id
                if(getIntent().hasExtra("ob_id") && getIntent().hasExtra("hikeid")){
                    ObhikeId = getIntent().getStringExtra("hikeid");
                    ObId = getIntent().getStringExtra("ob_id");
                    if(Hikeid == ObhikeId){
                        dbAssist.deleteRow(Hikeid);
                        dbAssister2.deleteObRow(ObId);
                    }
                }
                else{
                    // Deletes the selected hike even though no observations have been found
                    dbAssist.deleteRow(Hikeid);
                }
                finish();
            }
        });
        //This cancels the delete hike confirmation
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    //Allows the user to pick a date for their particular Hike
    public void chooseDate(){
        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                HikedateInput.setText(String.valueOf(year)+"/ "+String.valueOf(month)+"/ "+String.valueOf(dayOfMonth));
            }
        }, 2023, 11, 15);

        dpd.show();
    }

}
