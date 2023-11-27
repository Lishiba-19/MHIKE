package com.example.m_hike_emmanuel_lishiba_chiboboka;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class addHike extends AppCompatActivity {

    //Variable declarations of components
    EditText hikeText, locationText, dateText, lengthText, difficultyText,
    descText,  noOfPeople, weatherText;

    Button mainMenu, saveDetails, pickDate;
    TextView parkOption;

    RadioButton selectedOption;
    RadioGroup yes_no;

    dbAssister dbAssist;

    AlertDialog.Builder confirmHike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_hike);

        // Method to store all id assignment of components
        setIds();

        // Method to store all the click events of the buttons
        buttonClicks();
        confirmHike = new AlertDialog.Builder(this);

    }

    //Sets the id of all the components being used in this activity
    private void setIds() {
        hikeText = findViewById(R.id.hikeNameId);
        locationText = findViewById(R.id.locationNameId);
        dateText = findViewById(R.id.hikeDateId);
        lengthText = findViewById(R.id.hikeLengthId);
        difficultyText = findViewById(R.id.hikeDifficultyId);
        descText = findViewById(R.id.hikeDescId);
        noOfPeople = findViewById(R.id.hikeNoPeopleId);
        weatherText = findViewById(R.id.hikeWeatherId);
        parkOption = findViewById(R.id.parkOptionId);
        yes_no = findViewById(R.id.parkingR_GroupId);
        mainMenu = findViewById(R.id.backBtnId);
        saveDetails = findViewById(R.id.saveHikebtnId);
        pickDate = findViewById(R.id.pickDate);
    }

    //Contains the events of the button clicks being used in this activity
    private void buttonClicks() {

        //The mainMenu button will take the user back to the main menu of the application
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainMenu = new Intent(addHike.this, MainActivity.class);
                startActivity(mainMenu);
            }
        });

        //The saveDetails button displays a confirmation message to the user to save the details to the
        //database by displaying a dialogue
        saveDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbAssist = new dbAssister(addHike.this);

                //Gets the string values of the respective edit text fields
                String getHikeName = hikeText.getText().toString();
                String getLocation = locationText.getText().toString();
                String getDate = dateText.getText().toString();
                String getLength = lengthText.getText().toString();
                String getDifficulty = difficultyText.getText().toString();
                String getDesc = descText.getText().toString();
                String getNoOfPeople = noOfPeople.getText().toString();
                String getWeather = weatherText.getText().toString();
                String getParking = parkOption.getText().toString();

                //Checks for empty fields
                if(getHikeName.length() == 0){
                    hikeText.setError("Please enter a hike name.");
                }else if( getLocation.length() == 0 ){
                    locationText.setError("Please enter a location.");
                }else if( getDate.length() == 0){
                    dateText.setError("Please enter a date.");
                }else if( getLength.length() == 0){
                    lengthText.setError("Please enter a hike length.");
                }
                else if (getDifficulty.length() == 0){
                    difficultyText.setError("Please enter a hike difficulty.");
                }else if( getNoOfPeople.length() == 0){
                    noOfPeople.setError("Please enter the number of people.");
                } else if(getWeather.length() == 0){

                    weatherText.setError("Please enter the weather.");
                }
                else {
                    //The confirmHike is a dialogue
                    confirmHike.setTitle("Proceed?")
                            .setMessage("Do you wish to store these details? \n\n" +
                                    "Hike Name:  " + getHikeName +
                                    "\nHike Location:  " + getLocation + "\nHike Date :  " + getDate + "\nParking :  " + getParking
                                    + "\nHike Length:  " + getLength + "\nHike Difficulty:  " + getDifficulty +
                                    "\nHike Description:  " + getDesc + "\nHike Distance Covered:  " + getNoOfPeople +
                                    "\nHike Weather:  " + getWeather)
                            .setCancelable(true)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    // This method is called from the dbAssister class and will add the data from the respective text fields,
                                    // and add them to the database
                                    dbAssist.addHike(getHikeName, getLocation, getDate, getParking, getLength, getDifficulty, getDesc
                                            , getNoOfPeople, getWeather);

                                    Intent hikeIntent = new Intent(addHike.this, MainActivity.class);
                                    startActivity(hikeIntent);
                                }

                            })

                            //Cancels the dialogue prompt
                            .setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).show();
                }

            }
        });

        // Allows the user to pick a date
        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate();
            }
        });
    }

    //Sets the text of a corresponding text field depending on the chosen radio button
    public void parkOption(View v){
        int radioSelect = yes_no.getCheckedRadioButtonId();
        selectedOption = findViewById(radioSelect);

        parkOption.setText(selectedOption.getText().toString());

    }

    //Allows the user to pick a date for their particular Hike
    public void chooseDate(){
        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateText.setText(String.valueOf(year)+"/ "+String.valueOf(month)+"/ "+String.valueOf(dayOfMonth));
            }
        }, 2023, 11, 15);

        dpd.show();
    }



}
