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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Created by David on 27/04/2015.
 */
public class QuestionActivity extends ActionBarActivity{

    //Declaring
    private Button[] buttonArray = new Button[4];
    private TextView questionView;
    private QuestionList questions;
    private int correctButtonID;
    private int correctAnswers = 0, wrongAnswers = 0;
    InputStream is = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        //Alex code
        StrictMode.enableDefaults(); //STRICT MODE ENABLED

        //Get the quizId that is used to get the correct questions.
        String quizId = "";
        Bundle quizInfo = getIntent().getExtras();
        if(quizInfo != null) {
            quizId = quizInfo.getString("quizId");
        }
        //Get question from LoadQuestion class, using string from strings.xml and the quizId
        this.questions = LoadQuestions.getData(getResources().getString(R.string.getQuizQuestions), quizId);

        //Connect ID to buttons
        buttonArray[0] = (Button) findViewById(R.id.alternativeA);
        buttonArray[1] = (Button) findViewById(R.id.alternativeB);
        buttonArray[2] = (Button) findViewById(R.id.alternativeC);
        buttonArray[3] = (Button) findViewById(R.id.alternativeD);
        questionView = (TextView) findViewById(R.id.question);

        //Listener for buttons
        final String finalQuizId = quizId;
        View.OnClickListener list = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               updateShowtime();

                Button answer = (Button) findViewById(v.getId());
                questions.getCurrentQuestion().setUserAnswer(answer.getText().toString());

                if (v.getId() == correctButtonID) {
                    correctAnswers++;
                    updateNoCurrentAnswers();
                } else {
                    wrongAnswers++;
                }
                //if there are no more questions, sends data containing number of correct answer
                //together with the questions themselves. The questions are then listed in the result activity
                if (questions.endOfList()) {
                    Intent i = new Intent(QuestionActivity.this, QuizResult.class);
                    i.putExtra("correct", correctAnswers);
                    i.putExtra("wrong", wrongAnswers);
                    i.putExtra("questions", questions);
                    i.putExtra("showRes",1);
                    i.putExtra("ID", finalQuizId);

                    startActivity(i);
                } else {
                    displayQuestion(questions.getNext());
                }

            }
        };

        //Listener on buttons
        for (int i = 0; i < 4; i++)
            buttonArray[i].setOnClickListener(list);

        displayQuestion(questions.getNext());
    }


    //TODO
    //
    //Some of the following methods might be more suitable in LoadQuestions
//    private void uploadAnswers() {
//        questions.resetCursor();
//        for(int i = 0; i < questions.getSize(); i++){
//            Question temp = questions.getNext();
//            if(temp.getAnswer()== temp.getUserAnswer()) {
//                updateNoCurrentAnswers(temp);
//                updateShowtime(temp);
//            }else
//                updateShowtime(temp);
//        }
//    }

    private void updateShowtime(){
        int temp = questions.getCurrentQuestion().getShowTimes() + 1;
        String showtime = "" + temp;
        String Id = questions.getCurrentQuestion().getID();
        Update.updateShowtime(Id, showtime);


    }

    private void updateNoCurrentAnswers(){
        int temp = questions.getCurrentQuestion().getNoCorrectAnswers() + 1;
        String NoCorrectAnswer = "" + temp;
        String Id = questions.getCurrentQuestion().getID();
        Update.updateNoCorrectAnswer(Id, NoCorrectAnswer);
    }

   /* private Question getQuestion(String ID){
        //TODO
        //Load single question
        return new Question();
    }

    private void updateQuestion(Question q){
        //TODO
        //Update question
    }
    */
    
    //Method to display question to private instance
    private void displayQuestion(Question question) {

        //Displaying alternatives random
        questionView.setText(question.getQuestion());
        ArrayList<String> displayArray = new ArrayList<String>();

        displayArray.add(question.getAnswer());
        String[] alts = question.getAlternatives();

        for(int i = 0; i < 3; i++)
            displayArray.add(alts[i]);

        Collections.shuffle(displayArray);

        for(int i = 0; i < 4 ; i++) {
            buttonArray[i].setText(displayArray.get(i));
            if(buttonArray[i].getText() == question.getAnswer())
                correctButtonID = buttonArray[i].getId();
        }
    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to exit the quiz?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(QuestionActivity.this, MainActivity.class));
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}


