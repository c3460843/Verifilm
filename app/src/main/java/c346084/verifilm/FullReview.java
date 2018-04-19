package c346084.verifilm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by C3460843 on 17/03/2018.
 */

public class FullReview extends AppCompatActivity {

    static boolean response=false;
    int responseTimer=0;
    TextView editTextFull;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_review);
        editTextFull = findViewById(R.id.editTextFull);
        //editTextFull.setFocusable(false);
        editTextFull.setText(BrowseReviews.selectedFullReview);
    }

    public void save(View view) throws InterruptedException {
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute("fullReview", BrowseReviews.selectedReviewID, editTextFull.getText().toString());
        response = false;
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
        if (response == true) {
            responseTimer = 0;
            Toast.makeText(getApplicationContext(), backgroundTask.fullReviewMessage, Toast.LENGTH_LONG).show();
        }
    }
}
