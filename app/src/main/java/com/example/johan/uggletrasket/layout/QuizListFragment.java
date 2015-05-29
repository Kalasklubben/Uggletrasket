package com.example.johan.uggletrasket.layout;


import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.johan.uggletrasket.R;
import com.example.johan.uggletrasket.model.ItemList;
import com.example.johan.uggletrasket.model.Quiz;
import com.example.johan.uggletrasket.util.Database;

public class QuizListFragment extends DialogFragment{

    private ItemList<Quiz> quizzes;
    private ListView quizListView;
    private QuizListAdapter qla;
    private String quizId, quizName, quizPassword, userPassword;
    private ImageButton backButton;

    //help variable that decides where to direct the user after choosing a quiz
    private String nextActivity = "";

    public QuizListFragment(String next){
        nextActivity = next;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_quiz_list, container, false);

        quizListView = (ListView) v.findViewById(R.id.quiz_list_in_fragment);
        backButton = (ImageButton) v.findViewById(R.id.quiz_list_back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        //Fetches all quizzes from the database
        this.quizzes = Database.getQuizList(getResources().getString(R.string.getAllQuizzes));
        populateQuizListView();
        getDialog().setTitle("Choose quiz:");
        return v;
    }

    private void populateQuizListView(){
        qla = new QuizListAdapter(getActivity());

        for(int i = 0; i < quizzes.getSize(); i++)
            qla.add(quizzes.getNext(Quiz.class));

        //sets the adapter for all the list items
        if(quizListView != null){
            quizListView.setAdapter(qla);
        }

        quizListView.setOnItemClickListener(new QuizItemClickListener());
    }

    private void killFragment() {
        getActivity().getFragmentManager().popBackStack();
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

            // Fetching the views of the list item layout
            TextView quizName = (TextView) convertView.findViewById(R.id.quiz_list_quiz_name);

            // Populate the data into the template layout (quiz_item)
            quizName.setHint(getItem(position).getName());
            return convertView;
        }
    }

    //Decides the actions taken when clicking on a list item
    private class QuizItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {

            Quiz selectedQuiz = (Quiz) (quizListView.getItemAtPosition(position));
            quizId = selectedQuiz.getID();
            quizName = selectedQuiz.getName();
            quizPassword = selectedQuiz.getPassword();

            //Creates an pop-up window where the password should be specified
            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setTitle("Enter password");

            final EditText input = new EditText(getActivity());

            //Hides the password while typing
            input.setTransformationMethod(PasswordTransformationMethod.getInstance());

            alert.setView(input);
            alert.setIcon(R.drawable.locked58);
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    //Checks if the password is correct and checks where the user should be directed
                    //with help from the nextActivity string
                    userPassword = input.getText().toString();
                    if (isPasswordCorrect(userPassword, quizPassword)) {
                        if (nextActivity == "STAT") {
                            Intent i = new Intent(getActivity(), StatisticsActivity.class);
                            i.putExtra("quizId", quizId);
                            startActivity(i);
                        }else if (nextActivity == "EDIT") {
                            Intent i = new Intent(getActivity().getApplicationContext(), AddQuestionActivity.class);
                            i.putExtra("quizId", quizId);
                            i.putExtra("quizName", quizName);
                            startActivity(i);
                            killFragment();
                        }else {
                            Intent i = new Intent(getActivity(), QuestionActivity.class);
                            i.putExtra("quizId", quizId);
                            startActivity(i);
                        }
                        killFragment();
                    } else {
                        Toast.makeText(getActivity(), "Incorrect password!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alert.show();
        }
    }

    private boolean isPasswordCorrect(String userPassword, String quizPassword) {
        return userPassword.equals(quizPassword);
    }
}

