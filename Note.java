package com.example.mack.notepad;

/**
 * Created by Mack on 10/26/2017.
 */


/**
 * Created by nicolehoward on 10/25/17.
 */

public class Note {
    //create variables for title, current selection of spinner, and note content
    private String title;
    private String type;
    private String content;
    private int intType;

    //default constructor
    public Note() {
        title = "";
        type = "";
        content = "";
        intType =-1;
    }
    //parameterized constructor
    public Note(String title, String type, String content, int intType) {
        this.title = title;
        this.type = type;
        this.content = content;
        this.intType = intType;
    }

    //retrieves the current title
    //@return: title
    public String getTitle() {
        return title;
    }

    //sets the title
    //@param: title
    public void setTitle(String title) {
        this.title = title;
    }

    //retrieves the current spinner selection
    //@return: type
    public String getType() {
        return type;
    }

    //sets the spinner selection
    //@param: type
    public void setType(String type) {
        this.type = type;
    }

    //retrieves the current note content
    //@return: content
    public String getContent() {
        return content;
    }

    //sets the note content
    //@param: content
    public void setContent(String content) {
        this.content = content;
    }

    //retrieves the current spinner index
    //@return: intType
    public int getIntType() {
        return intType;
    }

    //sets the spinner index
    //@param: intType
    public void setIntType(int intType) {
        this.intType = intType;
    }

    @Override
    public String toString() {
        return title;
    }
}
