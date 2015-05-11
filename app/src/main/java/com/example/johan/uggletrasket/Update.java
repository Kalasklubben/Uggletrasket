package com.example.johan.uggletrasket;



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

/**
 * Created by Alexander on 2015-05-11.
 */
public class Update {


public static void updateShowtime(String Idnumber, String Showtime){
    String Id = "" + Idnumber;
    InputStream is = null;
    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);

    nameValuePairs.add(new BasicNameValuePair("Id", Id));
    nameValuePairs.add(new BasicNameValuePair("Showtimes", Showtime));


    try {
        HttpClient httpClient = new DefaultHttpClient();

        HttpPost httpPost = new HttpPost("http://www.ilmkandidat.byethost7.com/updateShow.php");

        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        HttpResponse response = httpClient.execute(httpPost);

        HttpEntity entity = response.getEntity();

        is = entity.getContent();

        String msg = "Data entered succesfully";

    } catch (ClientProtocolException e) {
        e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }

}


}
