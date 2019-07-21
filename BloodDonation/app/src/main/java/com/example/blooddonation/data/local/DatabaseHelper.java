package com.example.blooddonation.data.local;



    import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.blooddonation.Models.Model_Registration.Create_Table;
import static com.example.blooddonation.Models.Model_Registration.age;
import static com.example.blooddonation.Models.Model_Registration.create;
import static com.example.blooddonation.Models.Model_Registration.email;
import static com.example.blooddonation.Models.Model_Registration.gender;
import static com.example.blooddonation.Models.Model_Registration.id;
import static com.example.blooddonation.Models.Model_Registration.location;
import static com.example.blooddonation.Models.Model_Registration.names;
import static com.example.blooddonation.Models.Model_Registration.password;
import static com.example.blooddonation.Models.Model_Registration.phonenumber;
import static com.example.blooddonation.Models.Model_Registration.username;

    public class DatabaseHelper extends SQLiteOpenHelper {
        public static final int databaeversion=1;
        public static final String databasename="register_db";
        public DatabaseHelper(Context context){
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
        public void insertval(String name,Integer ag,String gdr,String el,String usr,String pwd,String phno,String loc)
        {
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues contentValues=new ContentValues();
            contentValues.put(names,name);
            contentValues.put(password,pwd);
            contentValues.put(username,usr);
            contentValues.put(age,ag);
            contentValues.put(email,el);
            contentValues.put(gender,gdr);
            contentValues.put(phonenumber,phno);
            contentValues.put(location,loc);

            db.insert(create,null,contentValues);
            db.close();


        }
        public int checkUser(String el,String pwd){
            String[] column={id};
            SQLiteDatabase db=this.getReadableDatabase();
            String select=email+"=?"+" AND "+password+"=?";
            String[] selectionargs={el,pwd};
            Cursor cursor = (Cursor)db.query(create, column, select, selectionargs,null,null,null,null);
            int cursorcount=cursor.getCount();
            db.close();
           if(cursorcount>=1)
           {
               return 1;
           }
           else {
               return 0;
           }


        }
        public int checkPhoneNumber(String phno){
            String[] column={id};
            SQLiteDatabase db=this.getReadableDatabase();
            String select=phonenumber+"=?";
            String[] selectionargs={phno};
            Cursor cursor = (Cursor)db.query(create, column, select, selectionargs,null,null,null,null);
            int cursorcount=cursor.getCount();
            db.close();
            if(cursorcount==1)
            {
                return 1;
            }
            else if(cursorcount==0){
                return 0;
            }
            else {
                return 2;
            }

        }
        public Cursor EnterPhoneNumber(String el,String pwd){
            String[] column={phonenumber};
            SQLiteDatabase db=this.getWritableDatabase();
            String select=email+"=?"+" AND "+password+"=?";
            String[] selectionargs={el,pwd};
            Cursor cursor = (Cursor)db.query(create, column, select, selectionargs,null,null,null,null);

           // db.close();
            return cursor;


        }
        public Cursor getAge_db(String phno){
            String[] column={age};
            SQLiteDatabase db=this.getReadableDatabase();
            String select=phonenumber+"=?";
            String[] selectionargs={phno};
            Cursor cursor = (Cursor)db.query(create, column, select, selectionargs,null,null,null,null);
            //Integer age=Integer.valueOf(cursor.getColumnName(0));
            //db.close();
            return cursor;


        }
        public Cursor getGender_db(String phno){
            String[] column={gender};
            SQLiteDatabase db=this.getReadableDatabase();
            String select=phonenumber+"=?";
            String[] selectionargs={phno};
            Cursor cursor = (Cursor)db.query(create, column, select, selectionargs,null,null,null,null);

            //db.close();
            return cursor;


        }
        public Cursor getEmail_db(String phno){
            String[] column={email};
            SQLiteDatabase db=this.getReadableDatabase();
            String select=phonenumber+"=?";
            String[] selectionargs={phno};
            Cursor cursor = (Cursor)db.query(create, column, select, selectionargs,null,null,null,null);

            //db.close();
            return cursor;


        }
        public Cursor getUsername_db(String phno){
            String[] column={username};
            SQLiteDatabase db=this.getReadableDatabase();
            String select=phonenumber+"=?";
            String[] selectionargs={phno};
            Cursor cursor = (Cursor)db.query(create, column, select, selectionargs,null,null,null,null);

            //db.close();
            return cursor;


        }
        public Cursor getPassword_db(String phno){
            String[] column={password};
            SQLiteDatabase db=this.getReadableDatabase()  ;
            String select=phonenumber+"=?";
            String[] selectionargs={phno};
            Cursor cursor = (Cursor)db.query(create, column, select, selectionargs,null,null,null,null);

            //db.close();
            return cursor;


        }




    }


