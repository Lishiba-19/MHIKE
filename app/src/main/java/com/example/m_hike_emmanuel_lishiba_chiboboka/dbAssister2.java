package com.example.m_hike_emmanuel_lishiba_chiboboka;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class dbAssister2 extends SQLiteOpenHelper {

    private static dbAssister dbAssist;
    private final Context context;

    // This context will create a database file that the application will be using.
    dbAssister2(Context context) {
        super(context, "MHIKE2.db", null, 21);
        this.context = context;
    }

    // This code creates a "Observations" table that stores all the data from the user
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table if not exists Observations(ob_id integer not null primary key autoincrement, " +
                "observation_title varchar(30), observation_time varchar(30),  comments varchar(30), hikeid varchar(30) not null)");
    }

    // Prevents duplicate tables to be added then creates the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists Observations");
        onCreate(db);
    }

    // The addObservation() method passes 4 arguments from the respective activity and inserts the data into the database
    void addObservation(String observation_title, String observation_time, String comments, String hikeid){
        // Local variable for the SQLite database
        SQLiteDatabase litedb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        // Gets respective observation information from the activity and stores it in the Observations table of the database
        cv.put("observation_title", observation_title);
        cv.put("observation_time", observation_time);
        cv.put("comments", comments);
        cv.put("hikeid", hikeid);

        // Inserts content value information into the database
        long rowInsert = litedb.insert("Observations", null, cv);

        // Sends a toast message if data has not been inserted
        if(rowInsert == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        // Sends a toast message if data has been inserted
        else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
        }
    }

    //  A method that reads data from the Observations table
    Cursor readObserveData(){
        // Local variable for the SQLite database
        SQLiteDatabase sqDb = this.getWritableDatabase();
        Cursor cursor = sqDb.rawQuery("SELECT * FROM Observations", null);
        return  cursor;
    }

    // This method passes 5 arguments from the respective activity and updates the row of the database
    void updateObserveData(String rowId, String observation_title, String observation_time, String comments, String hikeid){
        // Local variable for the SQLite database
        SQLiteDatabase litedb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        // Gets respective observation information from the activity and updates it in the Observations table of the database
        // based on the given Observation id
        cv.put("observation_title", observation_title);
        cv.put("observation_time", observation_time);
        cv.put("comments", comments);
        cv.put("hikeid", hikeid);

        // SQL statement to perform the update in the actual database
        long rowUpdate = litedb.update("Observations", cv, "ob_id=?", new String[]{rowId});

        // Toast message shown if information is not updated
        if(rowUpdate == -1){
            Toast.makeText(context, "Update Failed", Toast.LENGTH_SHORT).show();
        }

        // Toast message shown if information is updated
        else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
        }
    }

    // This a method used to delete a particular row / observation from the database
    void deleteObRow(String rowId) {
        // Local variable for the SQLite database
        SQLiteDatabase liteDb = this.getWritableDatabase();

        // SQL statement to perform the deletion of a row in the database
        long deletion = liteDb.delete("Observations", "ob_id=?", new String[]{rowId});

        if(deletion == -1){

            // Toast message shown if information is not deleted
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else {

            // Toast message shown if information is deleted
            Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteSpecObs(){
        // Local variable for the SQLite database
        SQLiteDatabase liteDb = this.getWritableDatabase();
        liteDb.execSQL("DELETE FROM Observations ");
    }

    // Method used to delete all observation data from the database
    void deleteAllObs(){
        // Local variable for the SQLite database
        SQLiteDatabase liteDb = this.getWritableDatabase();

        //SQL statement that will perform the actual delete all query
        liteDb.execSQL("DELETE FROM Observations");
    }
}
