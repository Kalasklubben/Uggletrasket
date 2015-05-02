package com.example.johan.uggletrasket;

import java.util.ArrayList;

/**
 * Created by David on 01/05/2015.
 */
public class QuestionList {

    private ArrayList<Question> allQuestions;
    private int cursor = 0;
    private String ID;

    public QuestionList(){
        ID = "ID" + System.currentTimeMillis();
    }

    public void addQuestion(Question q){
        allQuestions.add(q);
    }

    public Question getNext(){
        if(cursor < allQuestions.size()-1)
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
        return allQuestions.size();
    }

    public void removeAllQuestion(){
        allQuestions.removeAll(allQuestions);
    }

    public void removeQuestion(Question q){
        allQuestions.remove(q);
        if(cursor == getSize())
            cursor--;
    }
}
