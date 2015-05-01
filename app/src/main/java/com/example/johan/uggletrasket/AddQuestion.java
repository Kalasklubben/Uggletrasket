package com.example.johan.uggletrasket;

import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class AddQuestion extends ActionBarActivity {

    EditText editId, editQuestion, editCorrect, editWrong1, editWrong2, editWrong3;
    Button bSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_add_question);

        editId = (EditText) findViewById(R.id.id);
        editQuestion = (EditText) findViewById(R.id.question);
        editCorrect = (EditText) findViewById(R.id.correct);
        editWrong1 = (EditText) findViewById(R.id.wrong1);
        editWrong2 = (EditText) findViewById(R.id.wrong2);
        editWrong3 = (EditText) findViewById(R.id.wrong3);

        bSubmit = (Button) findViewById(R.id.button);

        bSubmit.setOnClickListener(new View.OnClickListener() {

            InputStream is = null;

            @Override
            public void onClick(View arg0){
                String Id = "" + editId.getText().toString();
                String Question = "" + editQuestion.getText().toString();
                String Correct = "" + editCorrect.getText().toString();
                String Wrong1 = "" + editWrong1.getText().toString();
                String Wrong2 = "" + editWrong2.getText().toString();
                String Wrong3 = "" + editWrong3.getText().toString();

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("Id", Id));
                nameValuePairs.add(new BasicNameValuePair("Question", Question));
                nameValuePairs.add(new BasicNameValuePair("Correct", Correct));
                nameValuePairs.add(new BasicNameValuePair("Wrong1", Wrong1));
                nameValuePairs.add(new BasicNameValuePair("Wrong2", Wrong2));
                nameValuePairs.add(new BasicNameValuePair("Wrong3", Wrong3));

                try{
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost("http://www.ilmkandidat.byethost7.com/addQuestion.php");

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();

                    String msg = "Data entered succesfully";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_question, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
