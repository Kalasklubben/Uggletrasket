package com.example.johan.uggletrasket;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by David on 01/05/2015.
 */
public class CreateQuestionActivity extends ActionBarActivity{

    private Button create, back;
    private TextView questionTextView,correctTextView,alt1TextView,alt2TextView,alt3TextView;
    private EditText questionEditText,correctEditText, alt1EditText, alt2EditText, alt3EditText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createquestion);

        //Connect ID to Buttons, TextViews and EditTexts
        create = (Button) findViewById(R.id.createQuestionButton);
        back = (Button) findViewById(R.id.backButton);

        questionTextView = (TextView) findViewById(R.id.questionTextView);
        correctTextView = (TextView) findViewById(R.id.correctTextView);
        alt1TextView = (TextView) findViewById(R.id.alt1TextView);
        alt2TextView = (TextView) findViewById(R.id.alt2TextView);
        alt3TextView = (TextView) findViewById(R.id.alt3TextView);

        questionEditText = (EditText) findViewById(R.id.questionEditText);
        correctEditText = (EditText) findViewById(R.id.correctEditText);
        alt1EditText = (EditText) findViewById(R.id.alt1EditText);
        alt2EditText = (EditText) findViewById(R.id.alt2EditText);
        alt3EditText = (EditText) findViewById(R.id.alt3EditText);

        //Listener for next button
        View.OnClickListener list = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == create.getId()){
                    if(correctQuestion())
                        saveQuestion();
                    else
                        showMessage(getResources().getString(R.string.errorCreateQuestion));
                }
                else if (v.getId() == back.getId())
                    startActivity(new Intent(CreateQuestionActivity.this, MainActivity.class));
            }
        };

        create.setOnClickListener(list);
        back.setOnClickListener(list);
    }

    private boolean correctQuestion() {
        //showMessage(questionEditText.getText().toString());
        //Check that no field is empty
        if(questionEditText.getText().toString().equals("") || correctEditText.getText().toString().equals("") ||
            alt1EditText.getText().toString().equals("") || alt2EditText.getText().toString().equals("") ||
                alt3EditText.getText().toString().equals(""))
            return false;
        else
            return true;
    }

    private void saveQuestion() {
            Question newQuestion = new Question();

        try{
            newQuestion.setQuestion(questionEditText.getText().toString());
            newQuestion.setAnswer(correctEditText.getText().toString());

            newQuestion.addAlternative(alt1EditText.getText().toString());
            newQuestion.addAlternative(alt2EditText.getText().toString());
            newQuestion.addAlternative(alt3EditText.getText().toString());

        }catch (Exception e) {
            e.printStackTrace();
        }

        clearDisplay();

        //TODO Save question in some way....

    }

    private void clearDisplay() {
        questionEditText.setText("");
        correctEditText.setText("");
        alt1EditText.setText("");
        alt2EditText.setText("");
        alt3EditText.setText("");
    }

    //Method for showing pop up, for the time being
    private void showMessage(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(CreateQuestionActivity.this).create();
        alertDialog.setTitle(getResources().getString(R.string.app_name));
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
