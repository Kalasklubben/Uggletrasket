package com.example.johan.uggletrasket.layout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.johan.uggletrasket.R;
import com.example.johan.uggletrasket.model.ItemList;
import com.example.johan.uggletrasket.model.Question;
import com.example.johan.uggletrasket.util.Database;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionActivity extends ActionBarActivity{

    private Button[] buttonArray = new Button[4];
    private TextView questionView;
    private ItemList<Question> questions;
    private int correctButtonID;
    private int correctAnswers = 0, wrongAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Enables database connectivity
        StrictMode.enableDefaults();

        setContentView(R.layout.activity_question);

        buttonArray[0] = (Button) findViewById(R.id.alternativeA);
        buttonArray[1] = (Button) findViewById(R.id.alternativeB);
        buttonArray[2] = (Button) findViewById(R.id.alternativeC);
        buttonArray[3] = (Button) findViewById(R.id.alternativeD);
        questionView = (TextView) findViewById(R.id.question);

        String quizId = "";
        Bundle quizInfo = getIntent().getExtras();
        if(quizInfo != null) {
            quizId = quizInfo.getString("quizId");
        }

        //Get the list of questions to be answered
        this.questions = Database.getQuestionList(getResources().getString(R.string.getQuizQuestions), quizId);

        final String finalQuizId = quizId;
        View.OnClickListener list = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                updateShowtime();

                Button answer = (Button) findViewById(v.getId());
                //when pressing an alternative, the answer is saved within the Question object
                questions.getCurrentItem().setUserAnswer(answer.getText().toString());

                if(v.getId() == correctButtonID) {
                    correctAnswers++;
                    updateNoCorrectAnswers();
                }else {
                    wrongAnswers++;
                }

                //If there are no more questions, sends data containing number of correct answers
                //together with the questions themselves. Then navigate to the result activity.
                if (questions.isEndOfList()) {
                    Intent i = new Intent(QuestionActivity.this, QuizResultActivity.class);
                    i.putExtra("correct", correctAnswers);
                    i.putExtra("wrong", wrongAnswers);
                    i.putExtra("questions", questions);
                    i.putExtra("showRes",1);
                    i.putExtra("ID", finalQuizId);
                    startActivity(i);
                }else {
                    displayQuestion(questions.getNext(Question.class));
                }
            }
        };

        for (int i = 0; i < 4; i++)
            buttonArray[i].setOnClickListener(list);

        displayQuestion(questions.getNext(Question.class));
    }

    //Updates the number of times a question has been answered.
    private void updateShowtime(){
        int temp = questions.getCurrentItem().getShowTimes() + 1;
        String showtime = "" + temp;
        String Id = questions.getCurrentItem().getID();
        List<NameValuePair> nameValuePairs = new ArrayList<>(1);
        nameValuePairs.add(new BasicNameValuePair("Id", Id));
        nameValuePairs.add(new BasicNameValuePair("Showtimes", showtime));
        Database.addTableEntry(nameValuePairs, "http://www.ilmkandidat.byethost7.com/updateShow.php");
    }

    //Updates the number of times a question has been answered correctly.
    private void updateNoCorrectAnswers(){
        int temp = questions.getCurrentItem().getNoCorrectAnswers() + 1;
        String NoCorrectAnswer = "" + temp;
        String Id = questions.getCurrentItem().getID();
        List<NameValuePair> nameValuePairs = new ArrayList<>(1);
        nameValuePairs.add(new BasicNameValuePair("Id", Id));
        nameValuePairs.add(new BasicNameValuePair("NoCorrectAnswer", NoCorrectAnswer));
        Database.addTableEntry(nameValuePairs, "http://www.ilmkandidat.byethost7.com/updateNoCorrectAnswer.php");
    }

    private void displayQuestion(Question question) {

        questionView.setText(question.getQuestion());

        //Load all alternatives.
        ArrayList<String> displayArray = new ArrayList<String>();
        displayArray.add(question.getAnswer());
        String[] alts = question.getAnswerAlternatives();

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

    //Ask the user if they want to exit the quiz and return to main menu if back button is pressed.
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
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();

        alert.show();
    }
}


