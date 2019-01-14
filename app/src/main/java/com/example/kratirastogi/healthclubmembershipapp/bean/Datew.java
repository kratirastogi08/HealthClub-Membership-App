package com.example.kratirastogi.healthclubmembershipapp.bean;

public class Datew {
    String id,dom,doe;

    public Datew(String id, String dom, String doe) {
        this.id = id;
        this.dom = dom;
        this.doe = doe;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDom() {
        return dom;
    }

    public void setDom(String dom) {
        this.dom = dom;
    }

    public String getDoe() {
        return doe;
    }

    public void setDoe(String doe) {
        this.doe = doe;
    }

    @Override
    public String toString() {
        return id+"                "+dom+"                     "+doe;
    }
}
