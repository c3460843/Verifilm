package c346084.verifilm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void newReview(View view)
    {
        startActivity(new Intent(this,NewReview.class));
    }

    public void browseReviews(View view)
    {
        startActivity(new Intent(this,BrowseReviews.class));
    }
}
