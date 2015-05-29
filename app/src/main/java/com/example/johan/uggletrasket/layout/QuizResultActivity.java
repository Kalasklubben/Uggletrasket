package com.example.johan.uggletrasket.layout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.johan.uggletrasket.model.Question;
import com.example.johan.uggletrasket.model.QuestionList;
import com.example.johan.uggletrasket.R;

/**
 * Created by Johan on 2015-05-04.
 */
public class QuizResultActivity extends ActionBarActivity {

    //All inputs and buttons
    private QuestionList questions;
    private QuizListAdapter qla;
    private ListView questionList;
    private ImageButton mainMenuButton;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        //Resets the result to zero
        int correctAnswers = 0, wrongAnswers = 0;

        //Connect all inputs and buttons with the layout id
        result = (TextView) findViewById(R.id.result);
        questionList = (ListView) findViewById(R.id.question_list);
        mainMenuButton = (ImageButton) findViewById(R.id.main_menu_return);

        //Listener for the main menu button
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuizResultActivity.this, MainActivity.class));
                overridePendingTransition(R.animator.push_right_in,R.animator.push_right_out);
            }
        });

        //Retrieves result from another activity (QuestionActivity)
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            correctAnswers = extras.getInt("correct");
            wrongAnswers = extras.getInt("wrong");
            questions = (QuestionList) extras.get("questions");
            questions.resetCursor();
        }
        result.setText("Result " + correctAnswers + "/" + (wrongAnswers + correctAnswers));
        populateQuestionListView();
        }

    // Adds all questions from an array of questions to the list view in the result activity
    // using the QuizListAdapter for every Question object
    private void populateQuestionListView(){
        questionList = (ListView) findViewById(R.id.question_list);
        qla = new QuizListAdapter(this);
        for(int i = 0; i < questions.getSize(); i++)
            qla.add(questions.getNext());

        if(questionList != null){
            questionList.setAdapter(qla);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //An adapter class to assist the making of a list of Questions shown in activity_quiz_result
    //Takes a question_item layout and retrieves views from it. Then fills the views with
    //appropriate values from the Question object. The result corresponds to an item in the list
    private class QuizListAdapter extends ArrayAdapter<Question>
    {
        public QuizListAdapter(Context context)
        {
            super(context,0);
        }
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if (convertView == null)
            {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.question_item, null);
            }

            // Fetching the views of the layout
            TextView question = (TextView) convertView.findViewById(R.id.quiz_item_question);
            TextView correctAnswer = (TextView) convertView.findViewById(R.id.quiz_item_correct_answer);
            TextView userAnswer = (TextView) convertView.findViewById(R.id.quiz_item_your_answer);

            // Populate the data into the template layout (quiz_item)
            question.setHint(getItem(position).getQuestion());
            correctAnswer.setHint(getItem(position).getAnswer());
            userAnswer.setHint(getItem(position).getUserAnswer());

            return convertView;
        }
    }

    // Manage the android backward button
    public void onBackPressed() {
        startActivity(new Intent(QuizResultActivity.this, MainActivity.class));
    }
}
