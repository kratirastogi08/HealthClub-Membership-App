package com.example.kratirastogi.healthclubmembershipapp.bean;

public class Spplan {

    String planid;

    public Spplan(String planid) {
        this.planid = planid;
    }

    public String getPlanid() {
        return planid;
    }

    public void setPlanid(String planid) {
        this.planid = planid;
    }

    @Override
    public String toString() {
        return planid;
    }
}
