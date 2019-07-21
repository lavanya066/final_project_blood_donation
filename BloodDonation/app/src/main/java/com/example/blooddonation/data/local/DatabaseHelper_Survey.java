package com.example.blooddonation.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.blooddonation.Models.Model_Survey.Create_Table;
import static com.example.blooddonation.Models.Model_Survey.create;
import static com.example.blooddonation.Models.Model_Survey.phonenumber;
import static com.example.blooddonation.Models.Model_Survey.registration;
import static com.example.blooddonation.Models.Model_Survey.result;
import static com.example.blooddonation.Models.Model_Survey.survey_test_result;


public class DatabaseHelper_Survey extends SQLiteOpenHelper {
        public static final int databaeversion=1;
        public static final String databasename="survey_db";
        public DatabaseHelper_Survey(Context context){
            super(context,databasename,null,databaeversion);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(Create_Table);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(String.format("drop table if exists %s", Create_Table));
            onCreate(db);
        }
        public void insertvalue(String phno,String Survey_result_true_or_false,String registration_enter,String survey_test)
        {
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues contentValues=new ContentValues();
            contentValues.put(phonenumber,phno);
            contentValues.put(result,Survey_result_true_or_false);
            contentValues.put(registration,registration_enter);
            contentValues.put(survey_test_result,survey_test);
            db.insert(create,null,contentValues);
            db.close();


        }
        public Cursor checkSurvey(String phno){
            String[] column={result};
            SQLiteDatabase db=this.getReadableDatabase();
            String select=phonenumber+"=?";
            String[] selectionargs={phno};
            Cursor cursor = (Cursor)db.query(create, column, select, selectionargs,null,null,null,null);

            //db.close();
            return cursor;


        }
    public Cursor SendSurvey_test(String phno){
        String[] column={survey_test_result};
        SQLiteDatabase db=this.getWritableDatabase();
        String select=phonenumber+"=?";
        String[] selectionargs={phno};
        Cursor cursor = (Cursor)db.query(create, column, select, selectionargs,null,null,null,null);

        //db.close();
        return cursor;


    }
    public Cursor SendRegistration(String phno){
        String[] column={registration};
        SQLiteDatabase db=this.getWritableDatabase();
        String select=phonenumber+"=?";
        String[] selectionargs={phno};
        Cursor cursor = (Cursor)db.query(create, column, select, selectionargs,null,null,null,null);

        //db.close();
        return cursor;


    }



    }




