package com.example.johan.uggletrasket;

import android.os.Bundle;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 28/04/2015.
 */
public class LoadQuestions {

    public static QuestionList getData(String script, String quizId){

        String result = "";
        String url = script + "?QuizId=" + quizId;

        //Download from database
        InputStream isr = Database.update(new ArrayList<NameValuePair>(1), url );

        //Convert response to string
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(isr,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            isr.close();
            result=sb.toString();
        }
        catch(Exception e){
            Log.e("log_tag", "Error  converting result "+e.toString());
        }

        QuestionList loadedQuestions = new QuestionList();

        //parse json data, and convert to question object
        try {
            String s = "";
            JSONArray jArray = new JSONArray(result);

            for(int i=0; i<jArray.length();i++){
                JSONObject json = jArray.getJSONObject(i);

                Question temp = new Question();

                temp.setQuestion(json.getString("Question"));
                temp.setAnswer(json.getString("Correct"));
                temp.setID(json.getString("Id"));
                temp.setNoCorrectAnswers(Integer.parseInt(json.getString("NoCorrectAnswer")));
                temp.setShowTimes(Integer.parseInt(json.getString("Showtimes")));

                try {
                    temp.addAlternative(json.getString("Wrong1"));
                    temp.addAlternative(json.getString("Wrong2"));
                    temp.addAlternative(json.getString("Wrong3"));
                }catch (Exception e) {
                    e.printStackTrace();
                }

                loadedQuestions.addQuestion(temp);

            }
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("log_tag", "Error Parsing Data "+e.toString());
        }

        return loadedQuestions;
    }
}
