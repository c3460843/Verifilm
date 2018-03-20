package c346084.verifilm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import static c346084.verifilm.ReviewForm.film;

public class BrowseReviews extends AppCompatActivity {

    int counter=0;
    Button buttonNext;
    Button buttonPrevious;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_reviews);
        try {
            populate();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void populate() throws InterruptedException {

        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute("reviews", MainActivity.userID);

        Thread.sleep(100);
        TimeUnit.SECONDS.sleep(1);

        buttonNext = findViewById(R.id.buttonNext);
        buttonPrevious = findViewById(R.id.buttonPrevious);

        buttonNext.setEnabled(false);
        buttonPrevious.setEnabled(false);

        if(backgroundTask.reviewIDs.size()>2)
        {
            buttonNext.setEnabled(true);
        }

        TextView textViewFilmTitle1 = findViewById(R.id.textViewFilmTitle1);
        TextView textViewFilmTitle2 = findViewById(R.id.textViewFilmTitle2);
        TextView textViewFilmTitle3 = findViewById(R.id.textViewFilmTitle3);
        TextView textViewFilmTitle4 = findViewById(R.id.textViewFilmTitle4);
        TextView textViewFilmTitle5 = findViewById(R.id.textViewFilmTitle5);

        try {
            textViewFilmTitle1.setText(backgroundTask.reviewFilmNames.get(0));
            textViewFilmTitle2.setText(backgroundTask.reviewFilmNames.get(1));
            textViewFilmTitle3.setText(backgroundTask.reviewFilmNames.get(2));
            textViewFilmTitle4.setText(backgroundTask.reviewFilmNames.get(3));
            textViewFilmTitle5.setText(backgroundTask.reviewFilmNames.get(4));
        } catch (java.lang.IndexOutOfBoundsException ex) {
            //TODO
        }

        RatingBar ratingBar1 = findViewById(R.id.ratingBar1);
        RatingBar ratingBar2 = findViewById(R.id.ratingBar2);
        RatingBar ratingBar3 = findViewById(R.id.ratingBar3);
        RatingBar ratingBar4 = findViewById(R.id.ratingBar4);
        RatingBar ratingBar5 = findViewById(R.id.ratingBar5);
        try {
            ratingBar1.setRating(backgroundTask.reviewRatings.get(0));
            ratingBar2.setRating(backgroundTask.reviewRatings.get(1));
            ratingBar3.setRating(backgroundTask.reviewRatings.get(2));
            ratingBar4.setRating(backgroundTask.reviewRatings.get(3));
            ratingBar5.setRating(backgroundTask.reviewRatings.get(4));
        } catch (java.lang.IndexOutOfBoundsException ex) {
            //TODO
        }
        ratingBar1.setIsIndicator(true);
        ratingBar2.setIsIndicator(true);
        ratingBar3.setIsIndicator(true);
        ratingBar4.setIsIndicator(true);
        ratingBar5.setIsIndicator(true);
    }

    public void next(View view) {
        counter++;
        System.out.println(counter);
        buttonPrevious.setEnabled(true);
    }

    public void previous(View view) {
        counter--;
        System.out.println(counter);
        if(counter==0) {buttonPrevious.setEnabled(false);}
    }

    public void selectedReview(View view) {
        startActivity(new Intent(this, SelectedReview.class));
    }
}



