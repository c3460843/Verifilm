package c346084.verifilm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

public class SelectedReview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_review);
        TextView editTextSummary = findViewById(R.id.editTextSummary);
        editTextSummary.setFocusable(false);
        editTextSummary.setText(BrowseReviews.selectedSummary);

        TextView textViewFilm  = findViewById(R.id.textViewFilm);
        textViewFilm.setText(BrowseReviews.selectedReviewFilm);
        RatingBar ratingBar  = findViewById(R.id.ratingBar);
        ratingBar.setRating(BrowseReviews.selectedReviewRating);
    }

    public void fullReview(View view){
        startActivity(new Intent(this, FullReview.class));
    }
}
