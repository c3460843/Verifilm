package c346084.verifilm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class SelectListing extends AppCompatActivity {
    public static String film;
    public static String filmID;
    public static String cinemaID;
    public static String cinemaName;
    String listing;
    String listingID;
    static boolean response=false;
    int responseTimer=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_listing);
        final BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute("listings", cinemaID, filmID);
        final Spinner spinner2 = findViewById(R.id.spinner_listings);
        response = false;
        try {
            while (response == false)
            {
                Thread.sleep(100);
                responseTimer++;
                if (responseTimer >=BackgroundSettings.responseLimit)
                {
                    startActivity(new Intent(this, Home.class));
                    Toast.makeText(getApplicationContext(), "Connection timed out.", Toast.LENGTH_LONG).show();
                    break;
                }
            }
            if (response == true)
            {
                responseTimer = 0;
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, backgroundTask.listings);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter2);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                listing = spinner2.getSelectedItem().toString();
                int i = backgroundTask.listings.indexOf(listing);
                listingID = backgroundTask.listingIDs.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //TODO
            }
        });
    }

    public void reviewForm(View view) {
        startActivity(new Intent(this, ReviewForm.class));
        ReviewForm.listingID = listingID;
    }
}
