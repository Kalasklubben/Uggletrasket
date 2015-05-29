package com.example.johan.uggletrasket.model;

import java.io.Serializable;
import java.util.UUID;

public class Quiz implements Serializable{

    //a unique randomly generated ID used for identifying the questions
    private String ID;
    private String name;
    private String password;

    public Quiz(){
        ID = UUID.randomUUID().toString();
    }

    public void setID(String newID){
        this.ID = newID;
    }

    public String getID(){
        return ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
