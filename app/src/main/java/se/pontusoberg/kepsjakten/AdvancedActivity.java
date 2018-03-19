package se.pontusoberg.kepsjakten;

import android.content.Intent;
import android.location.Location;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AdvancedActivity extends AppCompatActivity {

    String antalKontrollanter = "OkÃ¤nt";
    Button advancedButton;
    TextView numberText, wayText, otherinfoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced);

        final String deviceId2 = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        advancedButton = (findViewById(R.id.advancedReport));
        numberText = (findViewById(R.id.nummer));
        wayText = (findViewById(R.id.way));
        otherinfoText = (findViewById(R.id.otherInfo));

        // Spinner meny
        Spinner mySpinner = (Spinner) findViewById(R.id.spinner1);

        ArrayAdapter< String > myAdapter = new ArrayAdapter < String > (AdvancedActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.alternatives));
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

    advancedButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String number = numberText.getText().toString();
            String way = wayText.getText().toString();
            String otherinfo = otherinfoText.getText().toString();

            double lon = 0;
            double lat = 0;
            final String deviceId = Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID);


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
          //  MainActivity.reportAdvanced(lat, lon, deviceId, number, way, otherinfo, antalKontrollanter);
            finish();



        }
    });

    }





}
