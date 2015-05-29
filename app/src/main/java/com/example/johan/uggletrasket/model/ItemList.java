package com.example.johan.uggletrasket.model;

/**
 * Created by JohanN on 29/05/15.
 */

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by David on 01/05/2015.
 */
public class ItemList<T> implements Serializable {

    //All inputs
    private ArrayList<T> allItems;
    private int cursor;
    private int noItems;

    //Constructor
    public ItemList(){
        allItems = new ArrayList<>();
        cursor = 0;
        noItems = 0;
    }

    //Adds a question to the question list
    public void addItem(T item){
        allItems.add(item);
        noItems++;
    }

    //Returns the next question in the list, depending on the position of the cursor
    public T getNext(Class<T> itemClass){
        T temp = null;
        try {
            temp = itemClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if(cursor < noItems){
            temp = allItems.get(cursor);
            cursor++;
        }
        return temp;
    }

    //Returns the current  question in the list, depending on the position of the cursor
    public T getCurrentItem(){
        return allItems.get(cursor-1);
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
        return noItems;
    }

    //Removes all questions from a quiz list
    public void removeAllItems(){
        allItems.removeAll(allItems);
        noItems = 0;
    }

    //Removes a question from a list
    public void removeItem(T q){
        allItems.remove(q);
        if(cursor == getSize())
            cursor--;
    }

    //Returns the list of the questions
    public ArrayList<T> getItems() {
        return allItems;
    }

    //Indicates if the cursor is at the end of the list
    public boolean endOfList(){
        return cursor >= noItems;
    }
}
