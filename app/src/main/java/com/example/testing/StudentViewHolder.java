package com.example.testing;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StudentViewHolder extends RecyclerView.ViewHolder {

    private final TextView lblFullName,lblStudNo,lblGender,lblBirthdate,lblEmail,lblState;

    private Student student;

    public StudentViewHolder(@NonNull View itemView){
        super(itemView);
        this.lblFullName=itemView.findViewById(R.id.txtFullName);
        this.lblStudNo=itemView.findViewById(R.id.txtStudNo);
        this.lblGender=itemView.findViewById(R.id.txtGender);
        this.lblBirthdate=itemView.findViewById(R.id.txtBirthdate);
        this.lblEmail=itemView.findViewById(R.id.txtEmail);
        this.lblState=itemView.findViewById(R.id.txtState);
    }

    public void setStudent(Student student)
    {

        this.student=student;
        lblFullName.setText(student.getStrFullname());
        lblStudNo.setText(student.getStrStudNo());
        lblEmail.setText(student.getStrEmail());
        lblBirthdate.setText(student.getStrBirthdate());
        lblGender.setText(student.getStrGender());
        lblState.setText(student.getStrState());
    }

}
