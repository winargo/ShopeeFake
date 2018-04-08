package com.fake.shopee.shopeefake;

/**
 * Created by riandy on 1/12/2018.
 */

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "searchhistory";

    // Contacts table name
    private static final String TABLE_HISTORY = "history";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "list";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_HISTORY + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT)";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    public void addContact(sqliteclass contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.get_searchhistory()); // Contact Phone

        // Inserting Row
        db.insert(TABLE_HISTORY, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    sqliteclass getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_HISTORY, new String[] { KEY_ID,
                        KEY_NAME, KEY_NAME}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        sqliteclass contact = new sqliteclass(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));
        // return contact
        return contact;
    }

    // Getting All Contacts
    public List<sqliteclass> getAllContacts() {
        List<sqliteclass> contactList = new ArrayList<sqliteclass>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_HISTORY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                sqliteclass contact = new sqliteclass();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.set_searchhistory(cursor.getString(1));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // Updating single contact
    public int updateContact(sqliteclass contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.get_searchhistory());

        // updating row
        return db.update(TABLE_HISTORY, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }

    // Deleting single contact
    public void deleteContact(sqliteclass contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HISTORY, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_HISTORY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}