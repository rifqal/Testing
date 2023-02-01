package com.example.testing;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Attendance extends SQLiteOpenHelper {

    public static final String dbName="dbstudent";
    public static final String tblNameStudent="students";
    public static final String colStdName="stud_name";
    public static final String colStdNo="stud_no";
    public static final String colStdGender="stud_gender";
    public static final String colStdState="stud_state";
    public static final String colStdDob="stud_dob";
    public static final String colStdEmail="stud_email";

    static final String strCrtTblStudents = "CREATE TABLE " + tblNameStudent + "("+colStdNo+"INTEGER PRIMARY KEY, "+ colStdName+"TEXT, " +"REAL,"+colStdGender+"TEXT," +colStdState+"TEXT, "+colStdDob+" DATE)";

    public static final String strDropTblStudents="DROP TABLE IF EXISTS "+tblNameStudent;

    public static final int version =1;

    public Attendance(@Nullable Context context) {
        super(context,dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(strCrtTblStudents);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(strDropTblStudents);
        onCreate(db);
    }

    public float fnInsertStudent(Student student){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(colStdName,student.getStrFullname());
        values.put(colStdNo,student.getStrStudNo());
        values.put(colStdGender,student.getStrGender());
        values.put(colStdState,student.getStrState());
        values.put(colStdDob,student.getStrBirthdate());

        float retResult = db.insert(tblNameStudent,null,values);
        return retResult;
    }

    @SuppressLint("Range")
    public Student fnGetStudent(int intStdNo){

        SQLiteDatabase db = this.getReadableDatabase();

        String strSelQry = "SELECT * FROM"+tblNameStudent+"WHERE "+colStdNo+" = '" +intStdNo +"'";
        Cursor cursor = db.rawQuery(strSelQry, null);

        Student student = new Student();


        if (cursor.moveToFirst()) {
            int fullnameIndex = cursor.getColumnIndex(colStdName);
            int studNoIndex = cursor.getColumnIndex(colStdNo);
            int genderIndex = cursor.getColumnIndex(colStdGender);
            int stateIndex = cursor.getColumnIndex(colStdState);
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

    @SuppressLint("Range")
    public List<Student> fnGetAllStudents(){

        List<Student> listStd = new ArrayList<>();
        String strSelAll ="SELECT * FROM "+tblNameStudent;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(strSelAll, null);

        if(cursor.moveToFirst()){
            do {
                Student student = new Student();
                int fullnameIndex = cursor.getColumnIndex(colStdName);
                int studNoIndex = cursor.getColumnIndex(colStdNo);
                int genderIndex = cursor.getColumnIndex(colStdGender);
                int stateIndex = cursor.getColumnIndex(colStdState);
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
                listStd.add(student);
            } while (cursor.moveToNext());
        }
        return listStd;
    }

}

//IF WANT TO USE CURSOR MUST HAVE @SuppressLint("Range")
