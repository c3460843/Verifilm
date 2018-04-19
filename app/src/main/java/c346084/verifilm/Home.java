package c346084.verifilm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * Created by C3460843 on 25/01/2018.
 */

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toast.makeText(getApplicationContext(), BackgroundTask.loginMessage, Toast.LENGTH_LONG).show();
    }

    public void newReview(View view)
    {
        startActivity(new Intent(this,NewReview.class));
    }

    public void browseReviews(View view)
    {
        startActivity(new Intent(this,BrowseReviews.class));
    }

    public void settings(View view)
    {
        startActivity(new Intent(this,Settings.class));
    }
}
