package com.example.johan.uggletrasket;


import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by JohanN on 09/05/15.
 */

public class QuizListFragment extends DialogFragment{

    QuizList quizzes;
    ListView quizListView;
    QuizListAdapter qla;
    String quizId, quizPassword, userPassword;
    Button backButton, startButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_quiz_list, container, false);
        quizListView = (ListView) v.findViewById(R.id.quiz_list_in_fragment);
        backButton = (Button) v.findViewById(R.id.quiz_list_back_button);
        startButton = (Button) v.findViewById(R.id.quiz_list_start_button);
        startButton.setEnabled(false);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent i = new Intent(getActivity(), QuestionActivity.class);
                    i.putExtra("quizId", quizId);
                    startActivity(i);
            }
        });

        //Fetches all quizzes from the database
        StrictMode.enableDefaults();
        this.quizzes = LoadQuizzes.getData(getResources().getString(R.string.getAllQuizzes));

        populateQuizListView();
        getDialog().setTitle("Välj quiz!");

        return v;
    }


    private void populateQuizListView(){
        qla = new QuizListAdapter(getActivity());

        for(int i = 0; i < quizzes.getSize(); i++)
            qla.add(quizzes.getNext());

        if(quizListView != null){
            quizListView.setAdapter(qla);
        }
        quizListView.setOnItemClickListener(new QuizItemClickListener());

    }

    private class QuizListAdapter extends ArrayAdapter<Quiz> {
        public QuizListAdapter(Context context)
        {
            super(context, 0);
        }

        public View getView(int position, View convertView, ViewGroup parent)
        {
            if (convertView == null)
            {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.quiz_item, null);
            }

            // Fetching the views of the layout
            TextView quizName = (TextView) convertView.findViewById(R.id.quiz_list_quiz_name);
            //TextView noQuestions = (TextView) convertView.findViewById(R.id.quiz_list_no_questions);

            // Populate the data into the template layout (quiz_item)
            quizName.setHint(getItem(position).getName());
            //noQuestions.setHint(getItem(position).getNoQuestions());

            return convertView;
        }
    }

    //Decides what happens on click on meal items in the ListView
    private class QuizItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            Quiz selectedQuiz = (Quiz) (quizListView.getItemAtPosition(position));

            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setTitle("Enter password");
            final EditText input = new EditText(getActivity());
            alert.setView(input);
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    userPassword = input.getText().toString();
                    if (isPasswordCorrect(userPassword, quizPassword)) {
                        Toast.makeText(getActivity(), "Ok!", Toast.LENGTH_SHORT).show();
                        startButton.setEnabled(true);
                    } else {
                        Toast.makeText(getActivity(), "Incorrect password!", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // Canceled.
                }
            });

            alert.show();


            quizId = selectedQuiz.getID();
            quizPassword = selectedQuiz.getPassword();
        }
    }

    private boolean isPasswordCorrect(String userPassword, String quizPassword) {
        return userPassword.equals(quizPassword);
    }
}
