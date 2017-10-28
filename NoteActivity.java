package com.example.mack.notepad;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class NoteActivity extends AppCompatActivity {

    //global variables for three views
    EditText title;
    Spinner spinner;
    EditText note;

    //content of the views after creating a note
    String noteTitle;
    String noteType;
    String noteContent;
    //for spinner use
    int intType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_note);

        //create three views
        title = new EditText(this);
        spinner = new Spinner(this);
        note = new EditText(this);

        //retrieving note information from the note that was clicked on in main activity
        Intent intent = getIntent();
        if (intent!= null) {
            noteTitle = intent.getStringExtra("title");
            noteType = intent.getStringExtra("type");
            noteContent = intent.getStringExtra("content");
            intType = intent.getIntExtra("intType", 0);
            //TEST
            //TEst to see if it gets the stuff

            //reset the note information
            title.setText(noteTitle);
            note.setText(noteContent);
            //String x = spinner.getItemAtPosition(intType).toString();

            //set the spinner back to it's index
            spinner.setSelection(intType);
            //Toast.makeText(this, noteTitle+noteType+noteContent+intType, Toast.LENGTH_SHORT).show();
        }

        //create grid layout with 3 columns
        GridLayout gridLayout = new GridLayout(this);
        gridLayout.setColumnCount(3);

        //title textview that spans two columns
        title.setHint("Title");

        GridLayout.Spec columnSpec = GridLayout.spec(0,2);
        GridLayout.Spec rowSpec = GridLayout.spec(0,1);

        //give title LayoutParams
        GridLayout.LayoutParams titleLayoutParams = new GridLayout.LayoutParams(rowSpec, columnSpec);
        titleLayoutParams.setGravity(Gravity.FILL_HORIZONTAL);
        title.setLayoutParams(titleLayoutParams);

        //add title to gridlayout
        gridLayout.addView(title);

        //create LayoutParams for spinner
        GridLayout.Spec columnSpec2 = GridLayout.spec(2,1);
        GridLayout.Spec rowSpec2 = GridLayout.spec(0,1);

        //add choices to spinner
        ArrayList<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("PERSONAL");
        spinnerArray.add("SCHOOL");
        spinnerArray.add("WORK");
        spinnerArray.add("OTHER");

        //assign adapter to spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, spinnerArray);
        spinner.setAdapter(spinnerAdapter);


        spinner.setSelection(intType);

        //create/assign layoutParams for spinner
        GridLayout.LayoutParams spinnerLayoutParams = new GridLayout.LayoutParams(rowSpec2, columnSpec2);
        spinner.setLayoutParams(spinnerLayoutParams);

        //add spinner to gridLayout
        gridLayout.addView(spinner);

        //create layoutParams for note
        GridLayout.Spec columnSpec3 = GridLayout.spec(0,3);
        GridLayout.Spec rowSpec3 = GridLayout.spec(1,1);

        note.setHint("Content");
        //assign layout params to note
        GridLayout.LayoutParams noteLayoutParams = new GridLayout.LayoutParams(rowSpec3, columnSpec3);
        note.setLayoutParams(noteLayoutParams);
        //noteLayoutParams.height = GridLayout.LayoutParams.MATCH_PARENT;
        noteLayoutParams.setGravity(Gravity.TOP);
        //noteLayoutParams.width = GridLayout.LayoutParams.MATCH_PARENT;
        //note.setLayoutParams(noteLayoutParams);

        //add note to gridLayout
        gridLayout.addView(note);

        Button button = new Button(this);

        //create layoutParams for button
        button.setText("DONE");
        GridLayout.Spec columnSpec4 = GridLayout.spec(0,3);
        GridLayout.Spec rowSpec4 = GridLayout.spec(3,1);
        //assign layout params to button
        GridLayout.LayoutParams buttonLayoutParams = new GridLayout.LayoutParams(rowSpec4,columnSpec4);
        //stretch button and move to the bottom of the screen
        buttonLayoutParams.setGravity(Gravity.BOTTOM);
        //buttonLayoutParams.width=GridLayout.LayoutParams.MATCH_PARENT;
        buttonLayoutParams.setGravity(Gravity.FILL_HORIZONTAL);
        button.setLayoutParams(buttonLayoutParams);

        gridLayout.addView(button);
        //set app to the gridLayout we created
        setContentView(gridLayout);

        //assign button to the class we implement below
        ButtonClickListener listener = new ButtonClickListener();
        button.setOnClickListener(listener);
    }

    private class ButtonClickListener implements View.OnClickListener{
        public void onClick(View view){
            //get current note information
            String noteTitle = title.getText().toString();
            String noteType = spinner.getSelectedItem().toString();
            int noteTypeInt = spinner.getSelectedItemPosition();
            String noteContent = note.getText().toString();

            //make sure the title has something written
            if(noteTitle.equals("")){
                Toast.makeText(NoteActivity.this, "Please enter a title", Toast.LENGTH_LONG).show();
            }
            else{
                //assign note info as extras and return back to mainActivity
                Intent intent = new Intent(NoteActivity.this, MainActivity.class);
                intent.putExtra("title", noteTitle);
                intent.putExtra("type", noteType);
                intent.putExtra("intType", noteTypeInt);
                intent.putExtra("content", noteContent);
                setResult(Activity.RESULT_OK, intent);
                NoteActivity.this.finish();
            }


        }
    }
}
