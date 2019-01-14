package com.example.kratirastogi.healthclubmembershipapp.bean;

public class Member {
    String Memberid,Membername;

    public Member(String memberid, String membername) {
        Memberid = memberid;
        Membername = membername;
    }

    public String getMemberid() {
        return Memberid;
    }

    public void setMemberid(String memberid) {
        Memberid = memberid;
    }

    public String getMembername() {
        return Membername;
    }

    public void setMembername(String membername) {
        Membername = membername;
    }

    @Override
    public String toString() {
        return Memberid+"       "+Membername;
    }
}
