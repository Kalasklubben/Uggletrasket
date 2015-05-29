package com.example.johan.uggletrasket.model;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by David on 27/04/2015.
 */

//Class representing a question
public class Question implements Serializable{

    //Declaration of all variables.
    private String ID;
    private String question;
    private String correctAnswer, userAnswer;
    private String[] alternatives = new String[3];
    private int numberOfAlts = 0;
    private int showTimes, noCorrectAnswers;

    //Constructor
    public Question(){
        ID = UUID.randomUUID().toString();
    }

    //Set question id.
    public void setID(String newID){
        this.ID = newID;
    }

    //Return question id.
    public String getID(){
        return ID;
    }

    //Set question.
    public void setQuestion(String newQuest){
        question = newQuest;
    }

    //Return question.
    public String getQuestion(){
        return question;
    }

    //Set correct answer.
    public void setAnswer(String answer){
        correctAnswer = answer;
    }

    //Return correct answer.
    public String getAnswer(){
        return correctAnswer;
    }

    //Set user answer.
    public void setUserAnswer(String answer) {
        userAnswer = answer;
    }

    //Return user answer.
    public String getUserAnswer() {
        return userAnswer;
    }

    //Add alternative.
    public void addAlternative(String alt) throws Exception{
        try {
            alternatives[numberOfAlts] = alt;
            numberOfAlts++;
        }catch(ArrayIndexOutOfBoundsException E) {
            throw new Exception("There is no room for more alternatives!");
        }
    }

    //Return all alternatives as an array.
    public String[] getAlternatives(){
        return alternatives.clone();
    }

    //Remove all alternatives.
    public void removeAllAlternatives(){
        alternatives = new String[3];
        numberOfAlts = 0;
    }

    //Set number of correct answers.
    public void setNoCorrectAnswers(int nca){
        noCorrectAnswers = nca;
    }

    //Return number of correct answers.
    public int getNoCorrectAnswers(){
        return noCorrectAnswers;
    }

    //Set number of times which the questions has been showed.
    public void setShowTimes(int st){
        showTimes = st;
    }

    //Return number of times which the questions has been showed.
    public int getShowTimes(){
        return showTimes;
    }
}
