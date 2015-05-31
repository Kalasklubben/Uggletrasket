package com.example.johan.uggletrasket.layout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.johan.uggletrasket.R;
import com.example.johan.uggletrasket.model.ItemList;
import com.example.johan.uggletrasket.model.Question;
import com.example.johan.uggletrasket.util.Database;

import java.text.DecimalFormat;

public class StatisticsActivity extends ActionBarActivity {

    private ImageButton returnButton;
    private String quizID = "";
    private ItemList<Question> questions;

    private QuizListAdapter qla;
    private ListView questionList;
    private TextView title;

;   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        returnButton = (ImageButton) findViewById(R.id.main_menu_return);
        title = (TextView) findViewById(R.id.result);

        Bundle quizInfo = getIntent().getExtras();
        if(quizInfo != null) {
            quizID = quizInfo.getString("quizId");
        }

        //Download questions from database
        questions =  Database.getQuestionList(getResources().getString(R.string.getQuizQuestions), quizID);

        //Listener for the return button
        View.OnClickListener listAdd = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StatisticsActivity.this, MainActivity.class));
                overridePendingTransition(R.animator.push_right_in,R.animator.push_right_out);
            }
        };
        returnButton.setOnClickListener(listAdd);
        populateQuestionListView();
        title.setText("Result of chosen quiz");
    }

    //An adapter class to assist the making of a list of Questions shown in activity_item_list
    //Takes a statistics_item layout and retrieves views from it. Then fills the views with
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
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.statistics_item, null);
            }

            // Fetching the views of the layout
            TextView question = (TextView) convertView.findViewById(R.id.quiz_item_question);
            TextView result = (TextView) convertView.findViewById(R.id.quiz_item_correct_answer);

            // Populate the data into the template layout (quiz_item)
            question.setHint(getItem(position).getQuestion());
            Double temp = getItem(position).getCorrectPercentage();
            DecimalFormat df = new DecimalFormat("0.00");

            result.setHint(df.format(temp) + " %");

            return convertView;
        }
    }

    // Adds all questions from an array of questions to the list view in the result activity
    // using the QuizListAdapter for every Question object
    private void populateQuestionListView(){
        questionList = (ListView) findViewById(R.id.question_list);
        qla = new QuizListAdapter(this);
        for(int i = 0; i < questions.getSize(); i++)
            qla.add(questions.getNext(Question.class));

        //sets the adapter for all the list items
        if(questionList != null){
            questionList.setAdapter(qla);
        }
    }
}