package com.example.johan.uggletrasket;

import java.io.Serializable;
import java.util.UUID;

//Class representing a question
public class Quiz implements Serializable{

    //All inputs
    private String ID;
    private String name;
    private String password;

    //Constructor, where a quiz ID is randomized
    public Quiz(){
        ID = UUID.randomUUID().toString();
    }

    //Sets the quiz ID
    public void setID(String newID){
        this.ID = newID;
    }

    //Returns the quiz ID
    public String getID(){
        return ID;
    }

    //Sets the name of the quiz
    public void setName(String name) {
        this.name = name;
    }

    //Returns the name of the quiz
    public String getName() {
        return name;
    }

    //Sets the quiz password
    public void setPassword(String password) {
        this.password = password;
    }

    //Returns the quiz password
    public String getPassword() {
        return password;
    }
}
