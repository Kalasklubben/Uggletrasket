package com.example.johan.uggletrasket.model;

import java.io.Serializable;
import java.util.ArrayList;

public class QuizList implements Serializable{

    //All inputs
    private ArrayList<Quiz> allQuizzes;
    private int cursor;
    private int noQuizzes;

    //Creates an empty quiz list
    public QuizList(){
        allQuizzes = new ArrayList<>();
        cursor = 0;
        noQuizzes = 0;
    }

    //Adds quiz to an quiz list
    public void addQuiz(Quiz q){
        allQuizzes.add(q);
        noQuizzes++;
    }

    //Returns the next quiz in the list, depending on the position of the cursor
    public Quiz getNext(){
        Quiz temp = new Quiz();
        if(cursor < noQuizzes){
            temp = allQuizzes.get(cursor);
            cursor++;
        }
        return temp;
    }

    //Returns the current  quiz in the list, depending on the position of the cursor
    public Quiz getCurrentQuiz(){
        return allQuizzes.get(cursor-1);
    }

    //Resets the position of the cursor
    public void resetCursor(){
        cursor = 0;
    }

    //Returns the position of the cursor
    public int getCursor(){
        return cursor;
    }

    //Returns the number of quizzes in a quiz list
    public int getSize(){
        return noQuizzes;
    }

    //Removes all quizzes from a quiz list
    public void removeAllQuizzes(){
        allQuizzes.removeAll(allQuizzes);
        noQuizzes = 0;
    }

    //Returns the list of the quizzes
    public ArrayList<Quiz> getQuizzes() {
        return allQuizzes;
    }

    //Indicates if the cursor is at the end of the list
    public boolean endOfList(){
        return cursor >= noQuizzes;
    }
}
