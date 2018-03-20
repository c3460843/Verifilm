package c346084.verifilm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SelectFilm extends AppCompatActivity {
    public static String userID;
    String film;
    String filmID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_film);
        final BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute("films");
        final Spinner spinner1 = findViewById(R.id.spinner_film);
        try {
            Thread.sleep(1000);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,backgroundTask.films);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(adapter);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                System.out.println(spinner1.getSelectedItem().toString());

                film = spinner1.getSelectedItem().toString();
                System.out.println("Find " + film + "'s index for ID.");

                int i = backgroundTask.films.indexOf(film);
                System.out.println(i + " = filmID index.");

                filmID = backgroundTask.filmIDs.get(i);
                System.out.println(filmID +" = filmID value.");

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
