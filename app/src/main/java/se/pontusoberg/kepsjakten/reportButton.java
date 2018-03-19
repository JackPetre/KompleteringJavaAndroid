package se.pontusoberg.kepsjakten;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.EditText;
import android.widget.Toast;
import android.view.Gravity;
import android.os.AsyncTask;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackskola on 2017-12-14.
 */

public class reportButton extends AppCompatActivity {

    String PostURL = "http://pontusoberg.se/kepsjakten/PostData.php";



    public void InsertData(final double lat, final double lon, final String deviceId, final String kontrollAntal, final String number, final String way, final String otherinfo) {
        Log.i("ew", "Knappen funkar insertData");
        class SendPostReqAsyncTask extends AsyncTask < String, String, String > {


            @Override
            protected String doInBackground(String...params) {


                String StationHolder = String.valueOf(lat);
                String AmountHolder = String.valueOf(lon);
                String deviceId2 = deviceId;

                String kontrollAntal2 = kontrollAntal;
                String kontrollKoll = kontrollAntal2;



                List < NameValuePair > nameValuePairs = new ArrayList < NameValuePair > ();

                nameValuePairs.add(new BasicNameValuePair("lat", StationHolder));
                nameValuePairs.add(new BasicNameValuePair("lon", AmountHolder));
                nameValuePairs.add(new BasicNameValuePair("deviceId", deviceId2));
                nameValuePairs.add(new BasicNameValuePair("amount", kontrollKoll));
                nameValuePairs.add(new BasicNameValuePair("number", number));
                nameValuePairs.add(new BasicNameValuePair("way", way));
                nameValuePairs.add(new BasicNameValuePair("otherinfo", otherinfo));


                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(PostURL);

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse httpResponse = httpClient.execute(httpPost);

                    HttpEntity httpEntity = httpResponse.getEntity();


                } catch (ClientProtocolException e) {} catch (IOException e) {}
                return "Data Inserted Successfully";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute();
    }
}