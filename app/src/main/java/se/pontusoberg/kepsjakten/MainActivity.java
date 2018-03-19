package se.pontusoberg.kepsjakten;


import java.util.Timer;
import java.util.TimerTask;

import java.lang.Object;
import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.Object;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    Boolean canPost = true;
    //Boolean firstPost = true;

    //JSON data url
    // ändra från Api.php
    private static final String URL_REPORTS = "http://pontusoberg.se/kepsjakten/GetData.php";

    //Lagra report
    List < Report > reportList;

    RecyclerView recyclerView;

 /* public void startGps(){     // Vad gör detta ens

  } */

    String antalKontrollanter = "OkÃ¤nt";
    String number = null;
    String way = null;
    String otherinfo = null;

    // Ta ut unikt ID för användaren


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final String deviceId = Secure.getString(getContentResolver(),
                Secure.ANDROID_ID);





        // gps
        ActivityCompat.requestPermissions(MainActivity.this, new String[] {
                Manifest.permission.ACCESS_FINE_LOCATION
        }, 123);


        // För in i recyclerViewen
        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        reportList = new ArrayList < > ();
        loadReports();

        //Get intent if it exists
        final Intent intent;
        intent = getIntent();
        if(intent != null && intent.getExtras() != null){
            Bundle b = intent.getExtras();
            number = (String) b.get("number");
            way = (String) b.get("way");
            otherinfo = (String) b.get("otherinfo");

        }




        //Report Button
        final Button btn = findViewById(R.id.reportButton);

        btn.setOnClickListener(new View.OnClickListener() {



            public void onClick(View v) {


                new CountDownTimer(30000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        Log.w("myApp", "en sekund");
                       canPost = false;
                       btn.setEnabled(false);
                    }

                    public void onFinish() {
                        Log.w("myApp", "klar");
                        canPost = true;
                        btn.setEnabled(true);
                        //firstPost = false;
                    }
                }.start();

                double lat = 11;
                double lon = 22;
                report(lat,lon,  deviceId,  antalKontrollanter,  number,  way,  otherinfo);
            }
        });


        //Refresh Button
        ImageButton btn2 = findViewById(R.id.refreshButton);

        btn2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });

        //FAQ Button
        ImageButton faqButton = findViewById(R.id.faqButton);
        faqButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                startFaqActivity();

            }
        });

        //Advanced Button
        Button advancedButton = findViewById(R.id.advancedButton);
        advancedButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                startAdvancedActivity();

            }
        });


    } // Oncreate stängs

    public void report(double lat,double lon, String deviceId, String antalKontrollanter, String number, String way, String otherinfo){
        lat = 11;
        lon = 22;
        GpsTracker gt = new GpsTracker(getApplicationContext());
        Location l = gt.getLocation();
        if (l == null) {
            Toast.makeText(getApplicationContext(), "GPS ger inget värde. Sätt på platstjänst.", Toast.LENGTH_SHORT).show();
        } else {
            lat = l.getLatitude();
            lon = l.getLongitude();
            //Toast.makeText(getApplicationContext(),"GPS Lat = "+lat+"\n lon = "+lon,Toast.LENGTH_SHORT).show();
        }
            reportButton reportButton = new reportButton();
            reportButton.InsertData(lat, lon, deviceId, antalKontrollanter, number, way, otherinfo);

            Log.w("myApp", "Du postade!");

    }


    public void startAdvancedActivity() {
        Intent intent = new Intent(this, AdvancedActivity.class);
        startActivity(intent);
    }

    public void startFaqActivity() {
        Intent intent = new Intent(this, FaqActivity.class);
        startActivity(intent);
    }
    public void loadReports() {
        // Skapa STRING request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_REPORTS,
                new Response.Listener < String > () {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //String till JSON array
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {

                                JSONObject report = array.getJSONObject(i);

                                //Lägg till reportlist
                                reportList.add(new Report(
                                        report.getInt("id"),
                                        report.getString("station"),
                                        report.getString("amount"),
                                        report.getString("formatted_timer"),
                                        report.getString("city"),
                                        report.getString("number"),
                                        report.getString("way"),
                                        report.getString("otherinfo")
                                ));
                            }

                            ReportsAdapter adapter = new ReportsAdapter(MainActivity.this, reportList);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //Skicka
        Volley.newRequestQueue(this).add(stringRequest);
    }


}