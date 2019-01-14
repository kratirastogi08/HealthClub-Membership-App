package com.example.kratirastogi.healthclubmembershipapp.bean;

public class Health {
    String mname,mphno;

    public Health(String mname, String mphno) {

        this.mname = mname;
        this.mphno = mphno;
    }



    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getMphno() {
        return mphno;
    }

    public void setMphno(String mphno) {
        this.mphno = mphno;
    }

    @Override
    public String toString() {
        return mname+"       "+mphno;
    }
}
