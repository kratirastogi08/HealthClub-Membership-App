package com.example.kratirastogi.healthclubmembershipapp.bean;

public class Plan {
    String planid,planname;

    public Plan(String planid, String planname) {
        this.planid = planid;
        this.planname = planname;
    }

    public String getPlanid() {
        return planid;
    }

    public void setPlanid(String planid) {
        this.planid = planid;
    }

    public String getPlanname() {
        return planname;
    }

    public void setPlanname(String planname) {
        this.planname = planname;
    }

    @Override
    public String toString() {
        return planname;
    }
}
