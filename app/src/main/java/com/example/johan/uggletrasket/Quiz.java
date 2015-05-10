package com.example.johan.uggletrasket;

import java.io.Serializable;
import java.util.UUID;

//Class representing a question
public class Quiz implements Serializable{
    private String ID;
    private String name;
    private String password;

    //Constructor
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
