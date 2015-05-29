package com.example.johan.uggletrasket.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by David on 01/05/2015.
 */
public class QuestionList implements Serializable{

    //All inputs
    private ArrayList<Question> allQuestions;
    private int cursor;
    private int noQuestions;
    private ArrayList<Boolean> answers = new ArrayList<Boolean>();

    //Constructor
    public QuestionList(){
        allQuestions = new ArrayList<>();
        cursor = 0;
        noQuestions = 0;
    }

    //Adds a question to the question list
    public void addQuestion(Question q){
        allQuestions.add(q);
        noQuestions++;
    }

    //Returns the next question in the list, depending on the position of the cursor
    public Question getNext(){
        Question temp = new Question();
        if(cursor < noQuestions){
            temp = allQuestions.get(cursor);
            cursor++;
        }
        return temp;
    }

    //Returns the current  question in the list, depending on the position of the cursor
    public Question getCurrentQuestion(){
        return allQuestions.get(cursor-1);
    }

    //Resets the position of the cursor
    public void resetCursor(){
        cursor = 0;
    }

    //Returns the position of the cursor
    public int getCursor(){
        return cursor;
    }

    //Returns the number of questions in a question list
    public int getSize(){
        return noQuestions;
    }

    //Removes all questions from a quiz list
    public void removeAllQuestion(){
        allQuestions.removeAll(allQuestions);
        noQuestions = 0;
    }

    //Removes a question from a list
    public void removeQuestion(Question q){
        allQuestions.remove(q);
        if(cursor == getSize())
            cursor--;
    }

    //Returns the list of the questions
    public ArrayList<Question> getQuestions() {
        return allQuestions;
    }

    //Indicates if the cursor is at the end of the list
    public boolean endOfList(){
        return cursor >= noQuestions;
    }
}
