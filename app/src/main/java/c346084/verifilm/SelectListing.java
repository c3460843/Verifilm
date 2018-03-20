package c346084.verifilm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SelectListing extends AppCompatActivity {
    public static String film;
    public static String filmID;
    public static String cinemaID;
    public static String cinemaName;
    String listing;
    String listingID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_listing);
        final BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute("listings", cinemaID, filmID);
        final Spinner spinner2 = findViewById(R.id.spinner_listings);
        try {
            Thread.sleep(1000);

            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,backgroundTask.listings);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                System.out.println(spinner2.getSelectedItem().toString());
                listing = spinner2.getSelectedItem().toString();

                int i = backgroundTask.listings.indexOf(listing);
                System.out.println(i + " = listingID index.");

                listingID = backgroundTask.listingIDs.get(i);
                System.out.println(listingID +" = listingID value.");
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
