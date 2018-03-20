package c346084.verifilm;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class NewReview extends AppCompatActivity {

    String cinemaID;
    String cinemaName;
    double longitude;
    double latitude;
    String longitudeString;
    String latitudeString;
    static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_review);

        System.out.println("start<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(NewReview.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            System.out.println("permission check<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            return;
        }
            System.out.println("loc<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            LocationManager lm = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location == null) { location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER); }
            if (location != null) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                DecimalFormat df = new DecimalFormat("#.##");
                //df.setRoundingMode(RoundingMode.CEILING);
                longitudeString = df.format(longitude);
                latitudeString = df.format(latitude);
            } else {
                longitudeString = "0.00";
                latitudeString = "0.00";
            }

            System.out.println("middle<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            System.out.println(longitudeString);
            System.out.println(latitudeString);
            System.out.println("end<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

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

        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute("cinemas", longitudeString, latitudeString);
        TextView textViewCinema = findViewById(R.id.textViewCinema);

        Thread.sleep(1000);

        cinemaName = backgroundTask.cinemaName;
        cinemaID = backgroundTask.cinemaID;
        SelectFilm.userID=backgroundTask.userID;
        SelectListing.cinemaName = cinemaName;
        SelectListing.cinemaID = cinemaID;
        textViewCinema.setText(backgroundTask.cinemaName);
        System.out.println("cinema name: " +backgroundTask.cinemaName);
    }
}


