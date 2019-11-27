package com.example.contact_v1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDatabase extends SQLiteOpenHelper {
    private static int VERSION = 1;
    private static String DATABASE_NAME = "Contact_Magin";

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    SQLiteDatabase db = this.getWritableDatabase();

    @Override
    public void onCreate(SQLiteDatabase db) {
        String script = "Create table contact (id INTEGER Primary Key, name TEXT,phone TEXT)";
        db.execSQL(script);
    }

    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", contact.getName());
        values.put("phone", contact.getPhone());
        db.insert("contact", null, values);
    }

    public ArrayList<Contact> getAllContact() {
        ArrayList<Contact> contacts = new ArrayList<Contact>();
        String script = "Select * from contact";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(script, null);
        while (cursor.moveToNext()){
            Contact contact =  new Contact();
            contact.setId(cursor.getInt(0));
            contact.setName(cursor.getString(1));
            contact.setPhone(cursor.getString(2));
            contacts.add(contact);
        }
        return contacts;
    }
    public ArrayList<Contact> search(CharSequence keyword){
        ArrayList<Contact> temp = null;
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select*from contact",null);
        if(cursor.moveToFirst()){
            temp = new ArrayList<Contact>();
            do {
                Contact contact = new Contact();
                if(cursor.getString(2).toLowerCase().contains(keyword.toString().toLowerCase())){
                    contact.setId(cursor.getInt(0));
                    contact.setName(cursor.getString(1));
                    contact.setPhone(cursor.getString(2));
                    temp.add(contact);
                }else
                if(cursor.getString(1).toLowerCase().contains(keyword.toString().toLowerCase())){
                    contact.setId(cursor.getInt(0));
                    contact.setName(cursor.getString(1));
                    contact.setPhone(cursor.getString(2));
                    temp.add(contact);
                }
            }while (cursor.moveToNext());
        }
        return temp;
    }
    public void deleteContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("contact","id = ?", new String[]{String.valueOf(contact.getId())});
        db.close();
    }
    public void updateContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues =  new ContentValues();
        contentValues.put("name", contact.getName());
        contentValues.put("phone", contact.getPhone());
        db.update("contact",contentValues,"id = ?", new String[]{String.valueOf(contact.getId())});
        db.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}