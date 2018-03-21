package c346084.verifilm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class FullReview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_review);
        TextView editTextFull = findViewById(R.id.editTextFull);
        //editTextFull.setFocusable(false);
        editTextFull.setText(BrowseReviews.selectedFullReview);
    }
}
