package com.example.johan.uggletrasket.model;

import java.io.Serializable;
import java.util.UUID;

/**
 * Represents a question contained in a quiz
 */

public class Question implements Serializable{

    //a unique randomly generated ID used for identifying the questions
    private String ID;
    private String question;
    private String correctAnswer, userAnswer;
    private String[] alternatives = new String[3];
    private int numberOfAlts = 0;
    private int showTimes, noCorrectAnswers;

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

    public void addAnswerAlternative(String alt) throws Exception{
        try {
            alternatives[numberOfAlts] = alt;
            numberOfAlts++;
        }catch(ArrayIndexOutOfBoundsException E) {
            throw new Exception("There is no room for more alternatives!");
        }
    }

    public String[] getAnswerAlternatives(){
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

    /**
     * calculates the percentages of correct answers
     * since the question was created.
     * @return the correct answer percentage
     */
    public double getCorrectPercentage() {

        double perc;
        if (getShowTimes() < 1) {
            perc = 0;
        }else{
            perc = (double) getNoCorrectAnswers()/ (double) getShowTimes();
        }
        perc = perc * 100;
        return perc;
    }
}
