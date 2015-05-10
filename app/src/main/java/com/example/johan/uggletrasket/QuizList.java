package com.example.johan.uggletrasket;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class QuizList implements Serializable{

    private ArrayList<Quiz> allQuizzes;
    private int cursor;
    private String ID;
    private int noQuizzes;

    public QuizList(){
        ID = UUID.randomUUID().toString();
        allQuizzes = new ArrayList<>();
        cursor = 0;
        noQuizzes = 0;
    }

    public void addQuiz(Quiz q){
        allQuizzes.add(q);
        noQuizzes++;
    }

    public Quiz getNext(){
        Quiz temp = new Quiz();
        if(cursor < noQuizzes){
            temp = allQuizzes.get(cursor);
            cursor++;
        }
        return temp;
    }

    public Quiz getCurrentQuestion(){
        return allQuizzes.get(cursor-1);
    }

    public void resetCursor(){
        cursor = 0;
    }

    public int getCursor(){
        return cursor;
    }

    public int getSize(){
        return noQuizzes;
    }

    public void removeAllQuizzes(){
        allQuizzes.removeAll(allQuizzes);
        noQuizzes = 0;
    }

    public int getNoQuizzes() {
        return noQuizzes;
    }

    public void removeQuiz(Quiz q){
        allQuizzes.remove(q);
        if(cursor == getSize())
            cursor--;
    }

    public ArrayList<Quiz> getQuizzes() {
        return allQuizzes;
    }

    public boolean endOfList(){
        return cursor >= noQuizzes;
    }
}
