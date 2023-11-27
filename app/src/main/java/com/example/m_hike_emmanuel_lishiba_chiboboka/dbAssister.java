package com.example.m_hike_emmanuel_lishiba_chiboboka;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class dbAssister extends SQLiteOpenHelper {

    //
    private static dbAssister dbAssist;
    private final Context context;

    // This context will create a database file that the application will be using.
    dbAssister(Context context) {
        super(context, "MHIKE.db", null, 21);
        this.context = context;
    }

    // This code creates a "Hikes" table that stores all the data from the user
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists Hikes(hike_id integer not null primary key autoincrement, " +
                "hike_name varchar(30) not null, location_name varchar(30) not null, hike_date varchar(30) not null, " +
                "parking varchar(30), hike_length varchar(30) not null, hike_difficulty varchar(30) not null, hike_desc varchar(300)," +
                "no_of_people varchar(30), weather varchar(30))");

    }

    // Prevents duplicate tables to be added then creates the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists Hikes");
        onCreate(db);
    }

    // The addHike methods passes 9 arguments from the respective activity and inserts the data into the database
    void addHike(String hikeName, String hikeLocation, String hikeDate, String parking, String hikeLength, String hikeDifficulty
    , String hikeDesc, String noOfPeople, String weather) {
        // Local variable for the SQLite database
        SQLiteDatabase litedb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        // Gets respective hike information from the activity and stores it in the Hikes table of the database
        cv.put("hike_name", hikeName);
        cv.put("location_name", hikeLocation);
        cv.put("hike_length", hikeLength);
        cv.put("hike_date", hikeDate);
        cv.put("parking", parking);
        cv.put("hike_difficulty", hikeDifficulty);
        cv.put("hike_desc", hikeDesc);
        cv.put("no_of_people", noOfPeople);
        cv.put("weather", weather);

        // Inserts content value information into the database
        long rowInsert = litedb.insert("Hikes", null, cv);

        // Sends a toast message if data has not been inserted
        if(rowInsert == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else {
            // Sends a toast message if data has been inserted
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
        }

    }

    //  A method that reads data from the Hikes table
    Cursor readHikeData (){
        SQLiteDatabase sqDb = this.getWritableDatabase();
        Cursor cursor = sqDb.rawQuery("SELECT * FROM Hikes", null);
        return  cursor;
    }

    // This method passes 10 arguments the respective activity and updates the row of the database
    void updateData(String rowId, String hikeName, String hikeLocation, String hikeDate, String parking, String hikeLength, String hikeDifficulty
            , String hikeDesc, String noOfPeople, String weather) {
        // Local variable for the SQLite database
        SQLiteDatabase liteDb = this.getWritableDatabase();

        // Gets respective hike information from the activity and updates it in the Hikes table of the database
        // based on the given Hike id
        ContentValues cv = new ContentValues();
        cv.put("hike_name", hikeName);
        cv.put("location_name", hikeLocation);
        cv.put("hike_date", hikeDate);
        cv.put("parking", parking);
        cv.put("hike_length", hikeLength);
        cv.put("hike_difficulty", hikeDifficulty);
        cv.put("hike_desc", hikeDesc);
        cv.put("no_of_people", noOfPeople);
        cv.put("weather", weather);

        // SQL statement to perform the update in the actual database
        long rowUpdate = liteDb.update("Hikes", cv, "hike_id=?", new String[]{rowId});
        if(rowUpdate == -1){

            // Toast message shown if information is not updated
            Toast.makeText(context, "Update Failed", Toast.LENGTH_SHORT).show();
        }
        else {

            // Toast message shown if information is updated
            Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
        }
    }
/*
    void getRow(String rowId) {
        SQLiteDatabase liteDb = this.getWritableDatabase();
        liteDb.execSQL("SELECT hike_id FROM Hikes");
    }*/

    // This a method used to delete a particular row / hike from the database
    void deleteRow(String rowId) {

        // Local variable for the SQLite database
        SQLiteDatabase liteDb = this.getWritableDatabase();

        // SQL statement to perform the deletion of a row in the database
        long deletion = liteDb.delete("Hikes", "hike_id=?", new String[]{rowId});

        if(deletion == -1){

            // Toast message shown if information is not deleted
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else {

            // Toast message shown if information is deleted
            Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    // Method used to delete all data from the database
    void deleteAll(){
        // Local variable for the SQLite database
        SQLiteDatabase liteDb = this.getWritableDatabase();

        // SQL statement to perform the deletion of all database content
        liteDb.execSQL("DELETE FROM Hikes");
    }

}
