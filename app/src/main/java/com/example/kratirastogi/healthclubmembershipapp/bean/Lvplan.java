package com.example.kratirastogi.healthclubmembershipapp.bean;

public class Lvplan {
    String mid,mname,plan;

    public Lvplan(String mid, String mname, String plan) {
        this.mid = mid;
        this.mname = mname;
        this.plan = plan;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    @Override
    public String toString() {
        return mid+"    "+mname+"   "+plan;
    }
}
