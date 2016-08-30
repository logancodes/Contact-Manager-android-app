package com.example.ushanthloganathan.contactmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ushanthloganathan on 2016-08-16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    private static  final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "contactmanager",
    TABLE_CONTACTS = "contacts",
    KEY_ID = "id",
    KEY_NAME ="name",
    KEY_PHONE = "phone",
    KEY_EMAIL = "email",
    KEY_ADDRESS="address",
    KEY_IMAGEURI="imageUri";

    public DatabaseHandler(Context context){ /*Contructor from the super class*/
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TABLE_CONTACTS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + KEY_PHONE + " TEXT," + KEY_EMAIL + " TEXT," + KEY_ADDRESS + " TEXT," + KEY_IMAGEURI + " TEXT)");

        //db.execSQL("CREATE TABLE"+TABLE_CONTACTS+"("+KEY_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME+ " TEXT,"+KEY_PHONE + " TEXT," + KEY_EMAIL + " TEXT,"+ KEY_ADDRESS+ " TEXT"+ KEY_IMAGEURI + "TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_CONTACTS);

        onCreate(db);
    }
    public void createContact(Content contact){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME,contact.getName());
        values.put(KEY_PHONE,contact.get_phone());
        values.put(KEY_EMAIL,contact.getMail());
        values.put(KEY_ADDRESS,contact.getAddress());
        values.put(KEY_IMAGEURI,contact.getImageURI().toString());

        db.insert(TABLE_CONTACTS,null, values);
        db.close();
    }

    public Content getContact(int id){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS,new String[]{KEY_ID,KEY_NAME, KEY_PHONE, KEY_EMAIL, KEY_ADDRESS, KEY_IMAGEURI},KEY_ID+"=?", new String[]{String.valueOf(id)},null, null, null, null); /*Question mark to replace it with the id*/

        if(cursor!=null)
            cursor.moveToFirst();

        Content contact= new Content(Integer.parseInt(cursor.getString(0)), cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4), Uri.parse(cursor.getString(5)));
        db.close();//should close the cursor to free the resource
        cursor.close();
        return  contact;

    }

    public void deleteContact(Content contact){
        SQLiteDatabase db = getWritableDatabase();

        db.delete(TABLE_CONTACTS,KEY_ID+"=?",new String[]{String.valueOf(contact.getId())});
        db.close();
    }

    public int getContactsCount(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_CONTACTS, null);

        int count = cursor.getCount();
        db.close();
        cursor.close(); //cursor also needs to be closed after the db is closed

        return count;
    }

    public int updateContact(Content contact){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NAME,contact.getName());
        values.put(KEY_PHONE,contact.get_phone());
        values.put(KEY_EMAIL,contact.getMail());
        values.put(KEY_ADDRESS,contact.getAddress());
        values.put(KEY_IMAGEURI,contact.getImageURI().toString());

        int rowsAffected = db.update(TABLE_CONTACTS,values,KEY_ID+"=?",new String[]{String.valueOf(contact.getId()) });
        db.close();

        return rowsAffected;

    }

    public List<Content> getAllContacts(){
        List<Content> contacts = new ArrayList<Content>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_CONTACTS, null);

        if (cursor.moveToFirst()){ //its true based on the cursor being selected the whole data in the database
            do{
                contacts.add(new Content(Integer.parseInt(cursor.getString(0)), cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4), Uri.parse(cursor.getString(5))));
            }while(cursor.moveToNext());
        }
        db.close();
        return contacts;
    }
}
