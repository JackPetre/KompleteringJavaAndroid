package se.pontusoberg.kepsjakten;


import java.util.Timer;
import java.util.TimerTask;


import android.Manifest;
import android.content.Intent;
import android.location.Location;
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

    //JSON data url
    // ändra från Api.php
    private static final String URL_REPORTS = "http://pontusoberg.se/kepsjakten/GetData.php";

    //Lagra report
    List < Report > reportList;

    RecyclerView recyclerView;

 /* public void startGps(){     // Vad gör detta ens

  } */

    String antalKontrollanter = "Okänt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Ta ut unikt ID för användaren
        final String deviceId = Secure.getString(this.getContentResolver(),
                Secure.ANDROID_ID);



        // Spinner meny
        Spinner mySpinner = (Spinner) findViewById(R.id.spinner1);

        ArrayAdapter < String > myAdapter = new ArrayAdapter < String > (MainActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.alternatives));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView < ? > adapterView, View view, int i, long l) {
                if (i == 0) {
                    antalKontrollanter = "OkÃ¤nt";

                } else if (i == 1) {

                    antalKontrollanter = "1-2";
                } else if (i == 2) {

                    antalKontrollanter = "2-4";
                } else if (i == 3) {

                    antalKontrollanter = "4+";
                }
            }
            @Override
            public void onNothingSelected(AdapterView < ? > adapterView) {

            }
        });


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




        //Report Button
        Button btn = findViewById(R.id.reportButton);

        btn.setOnClickListener(new View.OnClickListener() {



            public void onClick(View v) {


                //Declare the timer
                Timer myTimer = new Timer();
                //Set the schedule function and rate
                myTimer.scheduleAtFixedRate(new TimerTask() {
                                                @Override
                                                public void run() {
                                                    //Called at every 1000 milliseconds (1 second)
                                                    Log.i("MainActivity", "Repeated task");
                                                }
                                            },
                        //set the amount of time in milliseconds before first execution
                        0,
                        //Set the amount of time between each execution (in milliseconds)
                        1000);


                
                double lat = 11;
                double lon = 22;
                GpsTracker gt = new GpsTracker(getApplicationContext());
                Location l = gt.getLocation();
                if (l == null) {
                    Toast.makeText(getApplicationContext(), "GPS ger inget värde", Toast.LENGTH_SHORT).show();
                } else {
                    lat = l.getLatitude();
                    lon = l.getLongitude();
                    //Toast.makeText(getApplicationContext(),"GPS Lat = "+lat+"\n lon = "+lon,Toast.LENGTH_SHORT).show();
                }

                reportButton reportButton = new reportButton();
                reportButton.InsertData(lat, lon, deviceId, antalKontrollanter);
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




    } // Oncreate stängs

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
                                        report.getString("city")
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