package c346084.verifilm;

    import android.content.Intent;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.EditText;
    import android.widget.RatingBar;
    import android.widget.TextView;
    import android.widget.Toast;

public class ReviewForm extends AppCompatActivity {

    public static String film;
    public static String listingID;
    public static String userID;
    EditText editTextSummary;
    String summary;
    String rating;
    private RatingBar ratingBar;
    static boolean response=false;
    int responseTimer=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_form);

        TextView film_name = findViewById(R.id.textViewFilm);
        film_name.setText(film);

        editTextSummary = findViewById(R.id.editTextSummary);
        userID=MainActivity.userID;
        addListenerOnRatingBar();
    }

    public void addListenerOnRatingBar()
    {
        ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener()
        {
            public void onRatingChanged(RatingBar ratingBar, float ratingValue, boolean fromUser)
            {
                rating=String.valueOf(ratingValue);

                System.out.println("Summary: "+summary);
                System.out.println("Rating: "+rating);
                System.out.println("ListingID: "+listingID);
                System.out.println("UserID: "+userID);
            }
        });
    }

    public void submit(View view) throws InterruptedException {
        summary = editTextSummary.getText().toString();
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute("submit",summary,rating,listingID, userID);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+response);
        while (response == false)
        {
            Thread.sleep(100);
            responseTimer++;
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+responseTimer);
            if (responseTimer >=50)
            {
                Toast.makeText(getApplicationContext(), "Connection timed out.", Toast.LENGTH_LONG).show();
                break;
            }
        }
        if (response == true) {
            responseTimer = 0;
            Toast.makeText(getApplicationContext(), "Submission Successful.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, Home.class));
        }
    }
}
