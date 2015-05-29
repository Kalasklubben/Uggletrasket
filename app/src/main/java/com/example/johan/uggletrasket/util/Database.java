package com.example.johan.uggletrasket.util;



import android.os.StrictMode;
import android.util.Log;

import com.example.johan.uggletrasket.model.ItemList;
import com.example.johan.uggletrasket.model.Question;
import com.example.johan.uggletrasket.model.Quiz;

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
 * Database utility class for handling the connection to the Database used
 * to recieve and upload various kinds of data
 */
public class Database {

    /**
     * Creates a new entry in a given table with provided data
     *
     * @param nameValuePairs  the user provided date to be inserted
     * @param script  the address to a specified PHP file on the database
     * @return the resulting input stream
     */
    public static InputStream addTableEntry(List<NameValuePair> nameValuePairs, String script){

        StrictMode.enableDefaults();
        InputStream is = null;

        //Communication with the database
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(script);
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

    /**
     * Fetches a list of questions from the database
     * @param script the address to a specified PHP file on the database
     * @param quizId the unique id for the quiz containing the questions
     * @return The questions
     */
    public static ItemList<Question> getQuestionList(String script, String quizId){

        String result = "";
        String url = script + "?QuizId=" + quizId;

        //Download from database
        InputStream isr = Database.addTableEntry(new ArrayList<NameValuePair>(1), url);

        //Convert responding data to string format
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(isr,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            isr.close();
            result=sb.toString();
        }
        catch(Exception e){
            Log.e("log_tag", "Error  converting result " + e.toString());
        }

        ItemList<Question> loadedQuestions = new ItemList<>();

        //parse json data, and convert to question object
        try {
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
                    temp.addAnswerAlternative(json.getString("Wrong1"));
                    temp.addAnswerAlternative(json.getString("Wrong2"));
                    temp.addAnswerAlternative(json.getString("Wrong3"));
                }catch (Exception e) {
                    e.printStackTrace();
                }

                loadedQuestions.addItem(temp);

            }
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("log_tag", "Error Parsing Data "+e.toString());
        }

        return loadedQuestions;
    }

    /**
     * Fetches a list of quizzes from the database
     * @param script the address to a specified PHP file on the database
     * @return The quizzes
     */
    public static ItemList<Quiz> getQuizList(String script){

        String result = "";

        //Download from database
        InputStream isr = Database.addTableEntry(new ArrayList<NameValuePair>(1), script);

        //Convert responding data from database to string format
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(isr,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            isr.close();
            result=sb.toString();
        }
        catch(Exception e){
            Log.e("log_tag", "Error  converting result "+e.toString());
        }

        ItemList<Quiz> loadedQuizzes = new ItemList();

        //parse json data, and convert to quiz object
        try {
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
                loadedQuizzes.addItem(temp);
            }
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("log_tag", "Error Parsing Data "+e.toString());
        }
        return loadedQuizzes;
    }
}

