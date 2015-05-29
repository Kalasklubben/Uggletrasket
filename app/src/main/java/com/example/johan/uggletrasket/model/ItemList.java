package com.example.johan.uggletrasket.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A generic class used for lists of items. The items are stored in an arrayList
 * and a cursor is used to handle the items' positions.
 */
public class ItemList<T> implements Serializable {

    private ArrayList<T> allItems;
    private int cursor;
    private int noItems;

    public ItemList(){
        allItems = new ArrayList<>();
        cursor = 0;
        noItems = 0;
    }

    public void addItem(T item){
        allItems.add(item);
        noItems++;
    }

    /**
     * Moves the cursor forward in the list to retrieve the next item
     * @param itemClass the class of the object used as a generic object
     * @return
     */
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

    public T getCurrentItem(){
        return allItems.get(cursor-1);
    }

    public void resetCursor(){
        cursor = 0;
    }

    public int getCursorPosition(){
        return cursor;
    }

    public int getSize(){
        return noItems;
    }

    public void removeAllItems(){
        allItems.removeAll(allItems);
        noItems = 0;
    }

    public void removeItem(T q){
        allItems.remove(q);
        if(cursor == getSize())
            cursor--;
    }

    public ArrayList<T> getItems() {
        return allItems;
    }

    public boolean isEndOfList(){
        return cursor >= noItems;
    }
}
