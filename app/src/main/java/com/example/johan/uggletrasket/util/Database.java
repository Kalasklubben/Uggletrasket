package com.example.johan.uggletrasket.util;



import android.os.StrictMode;
import android.util.Log;

import com.example.johan.uggletrasket.model.Question;
import com.example.johan.uggletrasket.model.QuestionList;
import com.example.johan.uggletrasket.model.Quiz;
import com.example.johan.uggletrasket.model.QuizList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander on 2015-05-11.
 */
public class Database {

    public static InputStream update(List<NameValuePair> nameValuePairs, String url){

        StrictMode.enableDefaults();
        InputStream is = null;

        //Communication with the database
        try {
            HttpClient httpClient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(url);

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = httpClient.execute(httpPost);

            HttpEntity entity = response.getEntity();

            is = entity.getContent();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }

    public static QuestionList getQuestionList(String script, String quizId){

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
            Log.e("log_tag", "Error  converting result " + e.toString());
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

    public static QuizList getQuizList(String script){

        String result = "";

        //Download from database
        InputStream isr = Database.update(new ArrayList<NameValuePair>(1),script);

        //Convert response from database to string
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

        QuizList loadedQuizzes = new QuizList();

        //parse json data, and convert to question object
        try {
            String s = "";
            JSONArray jArray = new JSONArray(result);

            for(int i=0; i<jArray.length();i++){
                JSONObject json = jArray.getJSONObject(i);
                Quiz temp = new Quiz();

                try {
                    temp.setName(json.getString("Name"));
                    temp.setPassword(json.getString("Password"));
                    temp.setID(json.getString("Id"));
                }catch (Exception e) {
                    e.printStackTrace();
                }
                loadedQuizzes.addQuiz(temp);
            }
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("log_tag", "Error Parsing Data "+e.toString());
        }
        return loadedQuizzes;
    }

}

