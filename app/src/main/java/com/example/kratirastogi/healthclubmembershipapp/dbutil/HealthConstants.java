package com.example.kratirastogi.healthclubmembershipapp.dbutil;

public class HealthConstants {
    public static final String DBNM="health12345";
    public static final int DBVERSION=1;
    public static final String DBLOGINTABLE="login";
    public static final String DBCLERKTABLE="clerk";
    public static final String DBMEMBER="memberdetail";
    public static final String DBPLANTABLE="plan";
    public static final String DBRENEW="renew";
    public static final String COL_ID="id";
    public static final String COL_PASS="pass";
    public static final String COL_TYPE="type";
    public static final String COL_MEMID="memberid";
    public static final String COL_MEMNAME="membername";
    public static final String COL_EMAIL="email";
    public static final String COL_GENDER="gender";
    public static final String COL_ADD="address";
    public static final String COL_PHNO="phno";
    public static final String COL_DOB="dob";
    public static final String COL_OCC="occupation";
    public static final String COL_PLANID="planid";
    public static final String COL_DATEMEM="dateofmembership";
    public static final String COL_DATEEXP="dateofexpiry";
    public static final String COL_PLANAME="planname";
    public static final String COL_FAC="facilities";
    public static final String COL_CHARGE="charge";
    public static final String COL_DURATION="duration";
    public static final String COL_CLKNAME="clkname";
    public static final String COL_CLKPHNO="clkphno";
    public static final String COL_CLKMAIL="mail";
    public static final String COL_CLKADD="clkaddress";
    public static final String COL_MEMNUM="num";



    public static final String STQUERY="create table login(id text primary key,pass text)";
    public static final String STQUERY1="create table memberdetail(memberid text primary key,membername text,email text,gender text,address text,phno text,dob date,occupation text,planid text,dateofmembership date,dateofexpiry date,num text)";
    public static final String STQUERY2="create table plan(planid text primary key,planname text,facilities text,charge text,duration text)";
    public static final String STQUERY3="create table renew(memberid text,planid text,dateofmembership date,dateofexpiry date,  foreign key(memberid) references memberdetail (memberid))";
    public static final String STQUERY4="create table clerk(id text primary key,clkname text,clkphno text,mail text,clkaddress text)";


}
