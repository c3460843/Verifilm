package c346084.verifilm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static c346084.verifilm.ReviewForm.film;

public class BrowseReviews extends AppCompatActivity {

    int counter=0;
    int interval=5;
    Button buttonNext;
    Button buttonPrevious;
    ImageButton imageButton1;
    ImageButton imageButton2;
    ImageButton imageButton3;
    ImageButton imageButton4;
    ImageButton imageButton5;
    TextView textViewFilmTitle1;
    TextView textViewFilmTitle2;
    TextView textViewFilmTitle3;
    TextView textViewFilmTitle4;
    TextView textViewFilmTitle5;
    RatingBar ratingBar1;
    RatingBar ratingBar2;
    RatingBar ratingBar3;
    RatingBar ratingBar4;
    RatingBar ratingBar5;
    List<Float> reviewRatings = new ArrayList<Float>();
    List<String> reviewListingIDs = new ArrayList<String>();
    List<String> reviewIDs = new ArrayList<String>();
    List<String> reviewFilmNames = new ArrayList<String>();
    static String selectedSummary;
    static String selectedFullReview;
    static String selectedReviewFilm;
    static Float selectedReviewRating;

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

        Thread.sleep(1000);
        //TimeUnit.SECONDS.sleep(1);

        buttonNext = findViewById(R.id.buttonNext);
        buttonPrevious = findViewById(R.id.buttonPrevious);

        buttonNext.setEnabled(false);
        buttonPrevious.setEnabled(false);

        imageButton1 = findViewById(R.id.imageButton1);
        imageButton2 = findViewById(R.id.imageButton2);
        imageButton3 = findViewById(R.id.imageButton3);
        imageButton4 = findViewById(R.id.imageButton4);
        imageButton5 = findViewById(R.id.imageButton5);
        textViewFilmTitle1 = findViewById(R.id.textViewFilmTitle1);
        textViewFilmTitle2 = findViewById(R.id.textViewFilmTitle2);
        textViewFilmTitle3 = findViewById(R.id.textViewFilmTitle3);
        textViewFilmTitle4 = findViewById(R.id.textViewFilmTitle4);
        textViewFilmTitle5 = findViewById(R.id.textViewFilmTitle5);
        ratingBar1 = findViewById(R.id.ratingBar1);
        ratingBar2 = findViewById(R.id.ratingBar2);
        ratingBar3 = findViewById(R.id.ratingBar3);
        ratingBar4 = findViewById(R.id.ratingBar4);
        ratingBar5 = findViewById(R.id.ratingBar5);
        reviewRatings = backgroundTask.reviewRatings;
        reviewListingIDs = backgroundTask.reviewListingIDs;
        reviewIDs = backgroundTask.reviewIDs;
        reviewFilmNames = backgroundTask.reviewFilmNames;

        if(reviewIDs.size()>5)
        {
            buttonNext.setEnabled(true);
        }

        setVisabilityFields();

        ratingBar1.setIsIndicator(true);
        ratingBar2.setIsIndicator(true);
        ratingBar3.setIsIndicator(true);
        ratingBar4.setIsIndicator(true);
        ratingBar5.setIsIndicator(true);
    }

    public void setVisabilityFields(){
        try {
            textViewFilmTitle1.setText(reviewFilmNames.get(0+counter));
            textViewFilmTitle2.setText(reviewFilmNames.get(1+counter));
            textViewFilmTitle3.setText(reviewFilmNames.get(2+counter));
            textViewFilmTitle4.setText(reviewFilmNames.get(3+counter));
            textViewFilmTitle5.setText(reviewFilmNames.get(4+counter));
        } catch (java.lang.IndexOutOfBoundsException ex) {
            //TODO
        }

        try {
            ratingBar1.setRating(reviewRatings.get(0+counter));
            ratingBar2.setRating(reviewRatings.get(1+counter));
            ratingBar3.setRating(reviewRatings.get(2+counter));
            ratingBar4.setRating(reviewRatings.get(3+counter));
            ratingBar5.setRating(reviewRatings.get(4+counter));
        } catch (java.lang.IndexOutOfBoundsException ex) {
            //TODO
        }

        if(reviewIDs.size()-counter==1&&buttonNext.isEnabled()==false) {
            imageButton2.setVisibility(View.INVISIBLE);
            imageButton3.setVisibility(View.INVISIBLE);
            imageButton4.setVisibility(View.INVISIBLE);
            imageButton5.setVisibility(View.INVISIBLE);
            textViewFilmTitle2.setVisibility(View.INVISIBLE);
            textViewFilmTitle3.setVisibility(View.INVISIBLE);
            textViewFilmTitle4.setVisibility(View.INVISIBLE);
            textViewFilmTitle5.setVisibility(View.INVISIBLE);
            ratingBar2.setVisibility(View.INVISIBLE);
            ratingBar3.setVisibility(View.INVISIBLE);
            ratingBar4.setVisibility(View.INVISIBLE);
            ratingBar5.setVisibility(View.INVISIBLE);

        }
        else if(reviewIDs.size()-counter==2&&buttonNext.isEnabled()==false) {
            imageButton3.setVisibility(View.INVISIBLE);
            imageButton4.setVisibility(View.INVISIBLE);
            imageButton5.setVisibility(View.INVISIBLE);
            textViewFilmTitle3.setVisibility(View.INVISIBLE);
            textViewFilmTitle4.setVisibility(View.INVISIBLE);
            textViewFilmTitle5.setVisibility(View.INVISIBLE);
            ratingBar3.setVisibility(View.INVISIBLE);
            ratingBar4.setVisibility(View.INVISIBLE);
            ratingBar5.setVisibility(View.INVISIBLE);
        }
        else if(reviewIDs.size()-counter==3&&buttonNext.isEnabled()==false) {

            imageButton4.setVisibility(View.INVISIBLE);
            imageButton5.setVisibility(View.INVISIBLE);
            textViewFilmTitle4.setVisibility(View.INVISIBLE);
            textViewFilmTitle5.setVisibility(View.INVISIBLE);
            ratingBar4.setVisibility(View.INVISIBLE);
            ratingBar5.setVisibility(View.INVISIBLE);
        }
        else if(reviewIDs.size()-counter==4&&buttonNext.isEnabled()==false) {
            imageButton5.setVisibility(View.INVISIBLE);
            textViewFilmTitle5.setVisibility(View.INVISIBLE);
            ratingBar5.setVisibility(View.INVISIBLE);
        }

        ratingBar1.setEnabled(true);
    }

    public void next(View view) {
        counter=counter+interval;
        System.out.println("counter = "+counter);
        System.out.println("reviewIDs.size() = "+reviewIDs.size());
        buttonPrevious.setEnabled(true);
        if(counter+interval>=reviewIDs.size())
        {
            buttonNext.setEnabled(false);
        }
        setVisabilityFields();
    }

    public void previous(View view) throws InterruptedException {
        counter=counter-interval;
        System.out.println("counter = "+counter);
        System.out.println("reviewIDs.size() = "+reviewIDs.size());
        if(counter==0) {buttonPrevious.setEnabled(false);}
        buttonNext.setEnabled(true);
        imageButton2.setVisibility(View.VISIBLE);
        imageButton3.setVisibility(View.VISIBLE);
        imageButton4.setVisibility(View.VISIBLE);
        imageButton5.setVisibility(View.VISIBLE);
        textViewFilmTitle2.setVisibility(View.VISIBLE);
        textViewFilmTitle3.setVisibility(View.VISIBLE);
        textViewFilmTitle4.setVisibility(View.VISIBLE);
        textViewFilmTitle5.setVisibility(View.VISIBLE);
        ratingBar2.setVisibility(View.VISIBLE);
        ratingBar3.setVisibility(View.VISIBLE);
        ratingBar4.setVisibility(View.VISIBLE);
        ratingBar5.setVisibility(View.VISIBLE);
        setVisabilityFields();
    }

    public void selectedReviewButton1(View view) throws InterruptedException {
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute("selectedReview", reviewIDs.get(0+counter));
        Thread.sleep(1000);
        selectedSummary = backgroundTask.selectedSummary;
        selectedFullReview = backgroundTask.selectedReview;
        selectedReviewFilm = textViewFilmTitle1.getText().toString();
        selectedReviewRating = ratingBar1.getRating();
        startActivity(new Intent(this, SelectedReview.class));
    }

    public void selectedReviewButton2(View view) throws InterruptedException {
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute("selectedReview", reviewIDs.get(1+counter));
        Thread.sleep(1000);
        selectedSummary = backgroundTask.selectedSummary;
        selectedFullReview = backgroundTask.selectedReview;
        selectedReviewFilm = textViewFilmTitle2.getText().toString();
        selectedReviewRating = ratingBar2.getRating();
        startActivity(new Intent(this, SelectedReview.class));
    }

    public void selectedReviewButton3(View view) throws InterruptedException {
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute("selectedReview", reviewIDs.get(2+counter));
        Thread.sleep(1000);
        selectedSummary = backgroundTask.selectedSummary;
        selectedFullReview = backgroundTask.selectedReview;
        selectedReviewFilm = textViewFilmTitle3.getText().toString();
        selectedReviewRating = ratingBar3.getRating();
        startActivity(new Intent(this, SelectedReview.class));
    }

    public void selectedReviewButton4(View view) throws InterruptedException {
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute("selectedReview", reviewIDs.get(3+counter));
        Thread.sleep(1000);
        selectedSummary = backgroundTask.selectedSummary;
        selectedFullReview = backgroundTask.selectedReview;
        selectedReviewFilm = textViewFilmTitle4.getText().toString();
        selectedReviewRating = ratingBar4.getRating();
        startActivity(new Intent(this, SelectedReview.class));
    }

    public void selectedReviewButton5(View view) throws InterruptedException {
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute("selectedReview", reviewIDs.get(4+counter));
        Thread.sleep(1000);
        selectedSummary = backgroundTask.selectedSummary;
        selectedFullReview = backgroundTask.selectedReview;
        selectedReviewFilm = textViewFilmTitle5.getText().toString();
        selectedReviewRating = ratingBar5.getRating();
        startActivity(new Intent(this, SelectedReview.class));
    }
}



