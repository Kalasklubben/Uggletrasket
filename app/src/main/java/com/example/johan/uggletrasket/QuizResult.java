package com.example.johan.uggletrasket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Johan on 2015-05-04.
 */
public class QuizResult extends ActionBarActivity {

    ArrayList<Question> questions = new ArrayList<>();
    QuizListAdapter qla;
    ListView questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        int correctAnswers = 0, wrongAnswers = 0;

        TextView result = (TextView) findViewById(R.id.result);
        questionList = (ListView) findViewById(R.id.question_list);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            correctAnswers = extras.getInt("correct");
            wrongAnswers = extras.getInt("wrong");
        }
        result.setText("Score " + correctAnswers + "/" + (wrongAnswers + correctAnswers));
        questions = (ArrayList<Question>) getIntent().getSerializableExtra("questions");
        populateMealList();
        };

    // Provides an adapter for the list items. Basically inflates a custom view
    //with values from a Question object


    private void populateMealList(){
        questionList = (ListView) findViewById(R.id.question_list);
        qla = new QuizListAdapter(this);
        for (Question da: questions){
            qla.add(da);
        }
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
            //TextView userAnswer = (TextView) convertView.findViewById(R.id.quiz_item_your_answer);

            // Populate the data into the template layout (quiz_item)
            question.setHint(getItem(position).getQuestion());
            correctAnswer.setHint(getItem(position).getAnswer());
            //userAnswer.setHint(getItem(position).getUserAnswer());

            return convertView;
        }
    }

}
