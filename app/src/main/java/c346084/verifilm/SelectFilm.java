package c346084.verifilm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class SelectFilm extends AppCompatActivity {
    public static String userID;
    String film;
    String filmID;
    static boolean response = false;
    int responseTimer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_film);
        final BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute("films");
        final Spinner spinner1 = findViewById(R.id.spinner_film);
        try {
            response = false;
            while (response == false) {
                Thread.sleep(100);
                responseTimer++;
                if (responseTimer >= 50) {
                    startActivity(new Intent(this, Home.class));
                    Toast.makeText(getApplicationContext(), "Connection timed out.", Toast.LENGTH_LONG).show();
                    break;
                }
            }
            if (response == true) {
                responseTimer = 0;
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, backgroundTask.films);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner1.setAdapter(adapter);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                film = spinner1.getSelectedItem().toString();
                int i = backgroundTask.films.indexOf(film);
                filmID = backgroundTask.filmIDs.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //TODO
            }
        });
    }

    public void selectListing(View view) {
        startActivity(new Intent(this, SelectListing.class));
        SelectListing.filmID = filmID;
        SelectListing.film = film;
        ReviewForm.film = film;
    }
}
