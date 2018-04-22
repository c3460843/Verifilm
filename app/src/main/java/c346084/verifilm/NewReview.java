package c346084.verifilm;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by C3460843 on 26/01/2018.
 */

public class NewReview extends AppCompatActivity {

    String cinemaID;
    String cinemaName;
    double longitude;
    double latitude;
    String longitudeString;
    String latitudeString;
    static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    static boolean response = false;
    int responseTimer = 0;
    Button continueButton;
    TextView textViewCinema;
    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_review);
        continueButton = findViewById(R.id.continue_button);
        textViewCinema = findViewById(R.id.textViewCinema);

        getLocation();

        try {
            populate();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(NewReview.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            return;
        }
        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (location == null) {
            location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        if (location != null) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.DOWN);
            longitudeString = df.format(longitude);
            latitudeString = df.format(latitude);
            System.out.println(longitudeString + " " + latitudeString);
        } else {
            Toast.makeText(getApplicationContext(), "Cannot get last known location.", Toast.LENGTH_LONG).show();
            continueButton.setEnabled(false);
        }

        System.out.println(location);
    }

    public void selectFilm(View view) {
        startActivity(new Intent(this, SelectFilm.class));
    }

    public void refresh(View view) {
        Toast.makeText(getApplicationContext(), "Refreshing location...", Toast.LENGTH_LONG).show();
        getLocation();
        try {
            populate();
            Toast.makeText(getApplicationContext(), "Location refreshed.", Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void populate() throws InterruptedException {
        if(longitudeString != null)
        {
            response = false;
            BackgroundTask backgroundTask = new BackgroundTask(this);
            backgroundTask.execute("cinemas", longitudeString, latitudeString);
            while (response == false) {
                Thread.sleep(100);
                responseTimer++;
                if (responseTimer >= BackgroundSettings.responseLimit) {
                    startActivity(new Intent(this, Home.class));
                    Toast.makeText(getApplicationContext(), "Connection timed out.", Toast.LENGTH_LONG).show();
                    break;
                }
            }
            if (response == true) {
                responseTimer = 0;
                cinemaName = backgroundTask.cinemaName;
                cinemaID = backgroundTask.cinemaID;
                SelectFilm.userID = backgroundTask.userID;
                SelectListing.cinemaName = cinemaName;
                SelectListing.cinemaID = cinemaID;
                textViewCinema.setText(cinemaName);
                continueButton.setEnabled(true);
            }
        }
        if(cinemaID == null){
            textViewCinema.setText("No cinema");
            continueButton.setEnabled(false);
        }

    }
}


