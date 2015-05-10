package com.example.johan.uggletrasket;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Johan on 09/05/2015.
 */
public class LoadQuizzes {

    public static QuizList getData(String script){

        String result = "";
        InputStream isr = null;

        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(script); //YOUR PHP SCRIPT ADDRESS
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            isr = entity.getContent();
        }
        catch(Exception e){
            Log.e("log_tag", "Error in http connection " + e.toString());
        }

        //convert response to string
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
