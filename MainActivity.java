/**This program creates a notepad where users can create notes and then see them in a listView to either add
 * a new note or delete them
 * CPSC 312-02, FALL 2017
 * Programming Assignment #5
 * Sources to cite: https://stackoverflow.com/questions/16119841/create-a-spinner-programmatically-android
 * @authors Nicole Howard, Mackenzie Brown
 *
 * On PA5 work was split as such:
 * Nicole: Completed MainActivity, Note
 * Mackenzie: Completed NoteActivity
 * Nicole and Mackenzie: combined the code, worked to fix errors (mostly related to saving spinner content).
 * We both added comments to all of the code

 */


package com.example.mack.notepad;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    final int CHANGE_ACTIVITY = 1;
    private List<Note> noteList = new ArrayList<>();    //List of Notes
    ArrayAdapter<Note> arrayAdapter;                    //arrayAdapter for ListView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);




        //Make a GridLayout
        GridLayout gridLayout = new GridLayout(this);
        gridLayout.setColumnCount(1);
        gridLayout.setBackgroundColor(Color.WHITE);

        //Add a button
        Button button = createButton();
        //Create a button Listener
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //When button is clicked, go to NoteActivity
                //TODO: REMOVE TEST
                //Toast.makeText(MainActivity.this, "Go To other activity", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                startActivityForResult(intent, CHANGE_ACTIVITY);

            }
        });


        //Create a ListView to display the user’s notes.
        ListView listView = createListView();

        //Add an ArrayAdaptor - allows dynamically change of list view
        arrayAdapter = new ArrayAdapter<Note>(this,
                android.R.layout.simple_list_item_1, noteList);
        listView.setAdapter(arrayAdapter);

        //Listen for a selection on the ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Get the title, type, and content and send it to NoteActivity
                String title = adapterView.getItemAtPosition(i).toString();
                String type = noteList.get(i).getType();
                String content = noteList.get(i).getContent();
                int intType = noteList.get(i).getIntType();

                //Creates an intent and deletes the current note since it will not be changed
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("type", type);
                intent.putExtra("content", content);
                intent.putExtra("intType", intType);
                noteList.remove(i);
                startActivityForResult(intent, CHANGE_ACTIVITY);
            }
        });

        //Listen for a long click on a ListView item
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Save the position of the item clicked on
                final int position = i;
                //Create a Dialog asking if they want to delete the note or not
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
                //Set the title, set the message, and the buttons
                alertBuilder.setTitle("You selected the note: " + adapterView.getItemAtPosition(i).toString())
                        .setMessage("Did you want to delete this note?")
                        //Yes Button Click Listener anonymous class
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //return true, aka delete the note
                                Toast.makeText(MainActivity.this, "delete", Toast.LENGTH_SHORT).show();
                                removeFromNoteList(position);
                                arrayAdapter.notifyDataSetChanged();
                            }
                        })
                        //No Button click Listener Anonymous Class, do nothing if this is clicked
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //TODO: DELETE TEST TOAST
                                //Toast.makeText(MainActivity.this, "Keep", Toast.LENGTH_SHORT).show();
                            }
                        });
                alertBuilder.show();
                return true;
            }
        });

        //Display the Layout
        gridLayout.addView(button);
        gridLayout.addView(listView);
        setContentView(gridLayout);

    }

    //Add a Note to the arrayList and update the Adapter
    public void addToNoteList(Note note) {
        noteList.add(note);
        arrayAdapter.notifyDataSetChanged();
    }

    //Remove a note from the array list and update the adapter
    public void removeFromNoteList(int position) {
        noteList.remove(position);
        arrayAdapter.notifyDataSetChanged();
    }

    //Create the List View, set its parameters
    public ListView createListView() {
        ListView listView = new ListView(this);
        //Set layout parameters for the listView
        GridLayout.LayoutParams listViewLayoutParams = new GridLayout.LayoutParams();
        listViewLayoutParams.width = GridLayout.LayoutParams.MATCH_PARENT;
        listViewLayoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT;
        listViewLayoutParams.setGravity(Gravity.TOP);
        listView.setLayoutParams(listViewLayoutParams);
        return listView;
    }

    //Create the button View, set its parameters
    public Button createButton() {
        //Make a button
        Button button = new Button(this);
        button.setText("Create new note");
        //Set the layout parameters
        //we need to define a GridLayout.LayoutParams object
        //LayoutParams is a static nested class of GridLayout
        GridLayout.LayoutParams buttonLayoutParams = new GridLayout.LayoutParams();
        //we will set the layout params
        buttonLayoutParams.width = GridLayout.LayoutParams.MATCH_PARENT;
        buttonLayoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT;
        buttonLayoutParams.setGravity(Gravity.TOP);
        button.setLayoutParams(buttonLayoutParams);
        return button;
    }

    //When NoteActivity returns to the MainActivity, add the new Note to the list, update the
    //array adapter
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String title = "";
        String type = "";
        String content = "";
        int intType = -1;
        //Get the data from the intent returned from Note Activity
        if (data != null) {
            title = data.getStringExtra("title");
            type = data.getStringExtra("type");
            content = data.getStringExtra("content");
            intType = data.getIntExtra("intType", 0);
            addToNoteList(new Note(title, type, content, intType));
            //TEST
            //Toast.makeText(this, intType, Toast.LENGTH_SHORT).show();
        }
        arrayAdapter.notifyDataSetChanged();
        //TODO:REMOVE TEST
        //Toast.makeText(this, title + type + content, Toast.LENGTH_SHORT).show();

    }
}
