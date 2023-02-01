package com.example.testing;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "student.db";
    public static final String TABLE_NAME = "student_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "FULLNAME";
    public static final String COL_3 = "STUDNO";
    public static final String COL_4 = "EMAIL";
    public static final String COL_5 = "GENDER";
    public static final String COL_6 = "BIRTH";
    public static final String COL_7 = "STATE";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,FULLNAME TEXT,STUDNO TEXT,EMAIL TEXT,GENDER TEXT,BIRTH TEXT,STATE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long saveData(Student student){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, student.getStrFullname());
        contentValues.put(COL_3, student.getStrStudNo());
        contentValues.put(COL_4, student.getStrEmail());
        contentValues.put(COL_5, student.getStrGender());
        contentValues.put(COL_6, student.getStrBirthdate());
        contentValues.put(COL_7, student.getStrState());

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result;
    }

    public List<Student> searchAllStudents() {
        List<Student> students = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                int fullnameIndex = cursor.getColumnIndex(COL_2);
                int studNoIndex = cursor.getColumnIndex(COL_3);
                int genderIndex = cursor.getColumnIndex(COL_5);
                int stateIndex = cursor.getColumnIndex(COL_7);
                if (fullnameIndex != -1) {
                    student.setStrFullname(cursor.getString(fullnameIndex));
                }
                if (studNoIndex != -1) {
                    student.setStrStudNo(cursor.getString(studNoIndex));
                }
                if (genderIndex != -1) {
                    student.setStrGender(cursor.getString(genderIndex));
                }
                if (stateIndex != -1) {
                    student.setStrState(cursor.getString(stateIndex));
                }
                students.add(student);
            } while (cursor.moveToNext());
        }
        cursor.close();


        return students;
    }

    public Student searchStudent(String studNo) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_3 + " = '" + studNo + "'", null);

        Student student = new Student();
        if (cursor.moveToFirst()) {
            int fullnameIndex = cursor.getColumnIndex(COL_2);
            int studNoIndex = cursor.getColumnIndex(COL_3);
            int genderIndex = cursor.getColumnIndex(COL_5);
            int stateIndex = cursor.getColumnIndex(COL_7);
            if (fullnameIndex != -1) {
                student.setStrFullname(cursor.getString(fullnameIndex));
            }
            if (studNoIndex != -1) {
                student.setStrStudNo(cursor.getString(studNoIndex));
            }
            if (genderIndex != -1) {
                student.setStrGender(cursor.getString(genderIndex));
            }
            if (stateIndex != -1) {
                student.setStrState(cursor.getString(stateIndex));
            }
        }
        cursor.close();
        return student;
    }



}
