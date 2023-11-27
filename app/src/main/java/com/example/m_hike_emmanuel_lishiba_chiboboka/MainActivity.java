package com.example.m_hike_emmanuel_lishiba_chiboboka;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Respective variable declarations
    RecyclerView recyclerView;
    ArrayList<String> Hikeid, HikeName, HikeDate, HikeLength, HikeDifficulty, HikeLocation, HikeWeather,
    HikeParking, HikePeople, HikeDesc;

    private ArrayList<String> searchList;

    dbAssister dbAssist;

    dbAssister2 dbAssist2;
    HikeAdapter hikeAdapter;

    ImageButton newHike, DeleteAll, ViewObservation, viewLocation;

    EditText searchBar;

    AlertDialog.Builder confirmDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbAssist = new dbAssister(this);

        confirmDelete = new AlertDialog.Builder(this);

        Hikeid=new ArrayList<>();
        HikeName =new ArrayList<>();
        HikeDate =new ArrayList<>();
        HikeLength =new ArrayList<>();
        HikeDifficulty =new ArrayList<>();
        HikeLocation =new ArrayList<>();
        HikeWeather =new ArrayList<>();
        HikeParking =new ArrayList<>();
        HikePeople =new ArrayList<>();
        HikeDesc =new ArrayList<>();

        setIds();

        //
        searchList = new ArrayList<>(Hikeid);

        hikeAdapter = new HikeAdapter(MainActivity.this,this, Hikeid , HikeName,HikeDate,HikeLength, HikeDifficulty, HikeLocation, HikeWeather,
                HikePeople, HikeParking, HikeDesc);

        //Setting hike adapter to the recycler view to show hike details
        recyclerView.setAdapter(hikeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //The newHike button takes the user to a new add hike activity
        newHike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hikeIntent = new Intent(MainActivity.this, addHike.class);
                startActivity(hikeIntent);
            }
        });

        //The viewObservation button takes the user to the viewObservations menu
        ViewObservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainMenu = new Intent(MainActivity.this, viewObservations.class);
                startActivity(mainMenu);
            }
        });

        //Confirms delete all hikes before carrying out the query
        DeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDelete.setTitle("Delete Hikes")
                        .setMessage("If you delete all hikes, all observations will be lost as well, do you wish to proceed?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                                dbAssist = new dbAssister(MainActivity.this);
                                dbAssist2 = new dbAssister2(MainActivity.this);


                                dbAssist.deleteAll();
                                dbAssist2.deleteAllObs();

                                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                    confirmDelete.create().show();
            }
        });

        //The viewLocation button will take the user to a new viewLocation activity
        viewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Show_Location.class);
                startActivity(intent);
            }
        });

        //The search bar will filter the recycler view based on the text in the search bar
       searchBar.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

           }

           @Override
           public void afterTextChanged(Editable s) {
               filter(s.toString());
           }
       });

        showHikeInfo();
    }

    //A method to filter the recycler view based on the hike Id
    private void filter(String text) {
        ArrayList<String> filteredList = new ArrayList<>();

        for(String item : searchList){
            if(item.contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }

        hikeAdapter.filterList(filteredList);
    }

    //Casts specific components used in this activity to ids declared in the respective xml layout
    private void setIds(){
        newHike = findViewById(R.id.saveHikebtnId);
        DeleteAll = findViewById(R.id.deleteAllId);
        ViewObservation = findViewById(R.id.viewObservationsId);
        viewLocation = findViewById(R.id.openLocation);

        searchBar = findViewById(R.id.searchHere);
        recyclerView = findViewById(R.id.hikeRecyclerVId);


    }

/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            recreate();
        }
    }*/

    //This method reads data from a respective row in the database and will allocate all the information
    //onto an appropriate text field of each item on the recycler view
    public void showHikeInfo() {
        Cursor cursor = dbAssist.readHikeData();
        if(cursor.getCount() == 0) {
            Toast.makeText(MainActivity.this, "No entries exist",Toast.LENGTH_SHORT).show();
            return ;
        }
        while (cursor.moveToNext()) {

            Hikeid.add(cursor.getString(0));
            HikeName.add(cursor.getString(1));
            HikeLocation.add(cursor.getString(2));
            HikeDate.add(cursor.getString(3));
            HikeParking.add(cursor.getString(4));
            HikeLength.add(cursor.getString(5));
            HikeDifficulty.add(cursor.getString(6));
            HikeDesc.add(cursor.getString(7));
            HikePeople.add(cursor.getString(8));
            HikeWeather.add(cursor.getString(9));
        }
    }

}