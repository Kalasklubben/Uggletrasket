package com.example.johan.uggletrasket;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by David on 27/04/2015.
 */
public class QuestionActivity extends ActionBarActivity{

    //Declaration of all inputs and buttons.
    private Button[] buttonArray = new Button[4];
    private TextView questionView;
    private QuestionList questions;
    private int correctButtonID;
    private int correctAnswers = 0, wrongAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //STRICT MODE ENABLED
        StrictMode.enableDefaults();

        //Connect activity with layout.
        setContentView(R.layout.activity_question);

        //Connect all inputs and buttons with respective layout id.
        buttonArray[0] = (Button) findViewById(R.id.alternativeA);
        buttonArray[1] = (Button) findViewById(R.id.alternativeB);
        buttonArray[2] = (Button) findViewById(R.id.alternativeC);
        buttonArray[3] = (Button) findViewById(R.id.alternativeD);
        questionView = (TextView) findViewById(R.id.question);

        //Get the quizId that is used to get the correct questions.
        String quizId = "";
        Bundle quizInfo = getIntent().getExtras();
        if(quizInfo != null) {
            quizId = quizInfo.getString("quizId");
        }

        //Get the list of questions.
        this.questions = Database.getQuestionList(getResources().getString(R.string.getQuizQuestions), quizId);

        //Listeners for buttons.
        final String finalQuizId = quizId;
        View.OnClickListener list = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                updateShowtime();

                Button answer = (Button) findViewById(v.getId());
                questions.getCurrentQuestion().setUserAnswer(answer.getText().toString());

                //Update number of correct answers if the right alternative was chosen.
                //Else update number of wrong answers if the incorrect alternative was chosen.
                if(v.getId() == correctButtonID) {
                    correctAnswers++;
                    updateNoCorrectAnswers();
                }else {
                    wrongAnswers++;
                }

                //If there are no more questions, sends data containing number of correct answers
                //together with the questions themselves. Then navigate to the result activity.
                if (questions.endOfList()) {
                    Intent i = new Intent(QuestionActivity.this, QuizResult.class);
                    i.putExtra("correct", correctAnswers);
                    i.putExtra("wrong", wrongAnswers);
                    i.putExtra("questions", questions);
                    i.putExtra("showRes",1);
                    i.putExtra("ID", finalQuizId);
                    startActivity(i);
                }else {
                    displayQuestion(questions.getNext());
                }
            }
        };

        //Add listeners to buttons.
        for (int i = 0; i < 4; i++)
            buttonArray[i].setOnClickListener(list);

        //Display question to private instance.
        displayQuestion(questions.getNext());
    }

    //Method to update the number of times a question has been answered.
    private void updateShowtime(){
        int temp = questions.getCurrentQuestion().getShowTimes() + 1;
        String showtime = "" + temp;
        String Id = questions.getCurrentQuestion().getID();
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("Id", Id));
        nameValuePairs.add(new BasicNameValuePair("Showtimes", showtime));
        Database.update(nameValuePairs, "http://www.ilmkandidat.byethost7.com/updateShow.php");
    }

    //Method to update the number of times a question has been answered correctly.
    private void updateNoCorrectAnswers(){
        int temp = questions.getCurrentQuestion().getNoCorrectAnswers() + 1;
        String NoCorrectAnswer = "" + temp;
        String Id = questions.getCurrentQuestion().getID();
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("Id", Id));
        nameValuePairs.add(new BasicNameValuePair("NoCorrectAnswer", NoCorrectAnswer));
        Database.update(nameValuePairs, "http://www.ilmkandidat.byethost7.com/updateNoCorrectAnswer.php");
    }

    //Method to display question to private instance
    private void displayQuestion(Question question) {

        //Show the question.
        questionView.setText(question.getQuestion());

        //Load all alternatives.
        ArrayList<String> displayArray = new ArrayList<String>();
        displayArray.add(question.getAnswer());
        String[] alts = question.getAlternatives();

        for(int i = 0; i < 3; i++)
            displayArray.add(alts[i]);

        //Randomize order of alternatives.
        Collections.shuffle(displayArray);

        //Show the alternatives in the randomized order.
        for(int i = 0; i < 4 ; i++) {
            buttonArray[i].setText(displayArray.get(i));
            if(buttonArray[i].getText() == question.getAnswer())
                correctButtonID = buttonArray[i].getId();
        }
    }

    //Display a dialog asking if the user wants to exit. If yes, then navigate to the main menu.
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to exit the quiz?");

        //If user selects "yes", then navigate to main activity.
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(QuestionActivity.this, MainActivity.class));
            }
        });

        //If user select "No", then cancel the dialog.
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();

        //Show the dialog.
        alert.show();
    }
}


