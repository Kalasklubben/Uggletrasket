package com.example.johan.uggletrasket;




import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by JohanN on 09/05/15.
 */

public class QuizListFragment extends DialogFragment {

    QuizList quizzes;
    ListView quizListView;
    QuizListAdapter qla;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_quiz_list, container, false);
        quizListView = (ListView) v.findViewById(R.id.quiz_list_in_fragment);

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
}
