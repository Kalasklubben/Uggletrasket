package com.example.johan.uggletrasket;

import java.util.ArrayList;

/**
 * Created by David on 01/05/2015.
 */
public class QuestionList {

    private ArrayList<Question> allQuestions;
    private int cursor;
    private String ID;
    private int noQuestions;

    public QuestionList(){
        ID = "ID" + System.currentTimeMillis( );
        allQuestions = new ArrayList<>();
        cursor = 0;
        noQuestions = 0;
    }

    public void addQuestion(Question q){
        allQuestions.add(q);
        noQuestions++;
    }

    public Question getNext(){
        if(cursor < noQuestions)
            cursor++;
        return allQuestions.get(cursor-1);
    }

    public void resetCursor(){
        cursor = 0;
    }

    public int getCursor(){
        return cursor;
    }

    public int getSize(){
        return noQuestions;
    }

    public void removeAllQuestion(){
        allQuestions.removeAll(allQuestions);
        noQuestions = 0;
    }

    public void removeQuestion(Question q){
        allQuestions.remove(q);
        if(cursor == getSize())
            cursor--;
    }

    public boolean endOfList(){
        return cursor >= noQuestions;
    }
}
