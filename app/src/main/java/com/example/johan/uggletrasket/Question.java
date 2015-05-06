package com.example.johan.uggletrasket;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by David on 27/04/2015.
 */

//Class representing a question
public class Question implements Serializable{
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

    public void setID(String newID){
        this.ID = newID;
    }

    public String getID(){
        return ID;
    }

    public void setQuestion(String newQuest){
        question = newQuest;
    }

    public String getQuestion(){
        return question;
    }

    public void setAnswer(String answer){
        correctAnswer = answer;
    }

    public String getAnswer(){
        return correctAnswer;
    }

    public void setUserAnswer(String answer) {
        userAnswer = answer;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void addAlternative(String alt) throws Exception{
        try {
            alternatives[numberOfAlts] = alt;
            numberOfAlts++;
        }catch(ArrayIndexOutOfBoundsException E) {
            throw new Exception("There is no room for more alternatives!");
        }
    }

    public String[] getAlternatives(){
        return alternatives.clone();
    }

    public void removeAllAlternatives(){
        alternatives = new String[3];
        numberOfAlts = 0;
    }

    public void setNoCorrectAnswers(int nca){
        noCorrectAnswers = nca;
    }

    public int getNoCorrectAnswers(){
        return noCorrectAnswers;
    }

    public void setShowTimes(int st){
        showTimes = st;
    }

    public int getShowTimes(){
        return showTimes;
    }
}
