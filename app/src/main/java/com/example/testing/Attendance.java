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
        float retResult = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(colStdName,student.getStrFullname());
        values.put(colStdNo,student.getStrStudNo());
        values.put(colStdGender,student.getStrGender());
        values.put(colStdState,student.getStrState());
        values.put(colStdDob,student.getStrBirthdate());

        retResult = db.insert(tblNameStudent,null,values);
        return retResult;
    }

    @SuppressLint("Range")
    public Student fnGetStudent(int intStdNo){

        Student student = new Student();

        String strSelQry = "Select * from"+tblNameStudent+"where "+colStdNo+" = " +intStdNo;
        Cursor cursor = this.getReadableDatabase().rawQuery(strSelQry,null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        student.setStrFullname(cursor.getString(cursor.getColumnIndex(colStdName)));
        student.setStrStudNo(cursor.getString(cursor.getColumnIndex(colStdNo)));
        student.setStrGender(cursor.getString(cursor.getColumnIndex(colStdGender)));
        student.setStrState(cursor.getString(cursor.getColumnIndex(colStdState)));
        student.setStrBirthdate(cursor.getString(cursor.getColumnIndex(colStdDob)));

        return student;
    }

    @SuppressLint("Range")
    public List<Student> fnGetAllStudents(){

        List<Student> listStd = new ArrayList<Student>();

        String strSelAll ="Select * from "+tblNameStudent;

        Cursor cursor = this.getReadableDatabase().rawQuery(strSelAll,null);
        if(cursor.moveToFirst()){
            do{
                Student student = new Student();

                student.setStrFullname(cursor.getString(cursor.getColumnIndex(colStdName)));
                student.setStrStudNo(cursor.getString(cursor.getColumnIndex(colStdNo)));
                student.setStrGender(cursor.getString(cursor.getColumnIndex(colStdGender)));
                student.setStrState(cursor.getString(cursor.getColumnIndex(colStdState)));
                student.setStrBirthdate(cursor.getString(cursor.getColumnIndex(colStdDob)));

                listStd.add(student);
            }while(cursor.moveToNext());
        }
        return listStd;
    }

    public int fnUpdateStudent(Student student){
        int retResult = 0;

        ContentValues values = new ContentValues();
        values.put(colStdName,student.getStrFullname());
        values.put(colStdNo,student.getStrStudNo());
        values.put(colStdGender,student.getStrGender());
        values.put(colStdState,student.getStrState());
        values.put(colStdDob,student.getStrBirthdate());

        String [] args = {String.valueOf(student.getStrStudNo())};

        this.getWritableDatabase().update(tblNameStudent,values,colStdNo+" = ?",args);

        return retResult;
    }

}

//IF WANT TO USE CURSOR MUST HAVE @SuppressLint("Range")
