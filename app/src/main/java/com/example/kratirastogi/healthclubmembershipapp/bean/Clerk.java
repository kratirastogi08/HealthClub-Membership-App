package com.example.kratirastogi.healthclubmembershipapp.bean;

public class Clerk
{
    String id;

    public Clerk(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;

    }
}
