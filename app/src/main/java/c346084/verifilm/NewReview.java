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
import android.widget.TextView;
import android.widget.Toast;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_review);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(NewReview.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            return;
        }
        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
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
            longitudeString = "0.00";
            latitudeString = "0.00";
        }
        try {
            populate();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void selectFilm(View view) {
        startActivity(new Intent(this, SelectFilm.class));
    }


    public void populate() throws InterruptedException {
        response = false;
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute("cinemas", longitudeString, latitudeString);
        TextView textViewCinema = findViewById(R.id.textViewCinema);
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
        }
    }
}


