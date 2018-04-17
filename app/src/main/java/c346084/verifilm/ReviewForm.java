package c346084.verifilm;

    import android.content.Intent;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.EditText;
    import android.widget.ImageView;
    import android.widget.RatingBar;
    import android.widget.TextView;
    import android.widget.Toast;
    import com.squareup.picasso.Picasso;



public class ReviewForm extends AppCompatActivity {

    public static String filmID;
    public static String film;
    public static String listingID;
    public static String userID;
    EditText editTextSummary;
    String summary;
    String rating;
    private RatingBar ratingBar;
    static boolean response=false;
    int responseTimer=0;
    ImageView imageView;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_form);

        TextView film_name = findViewById(R.id.textViewFilm);
        film_name.setText(film);

        editTextSummary = findViewById(R.id.editTextSummary);
        userID=MainActivity.userID;
        filmID=SelectListing.filmID;
        addListenerOnRatingBar();

        try {
            imageView = (ImageView) findViewById(R.id.imageView);
            url = "http://mungovin.000webhostapp.com/verifilm/banners/" + filmID + ".ban.jpg";
            loadImageFromUrl(url);
        }catch (Exception e){
            //TODO
        }
    }

    private void loadImageFromUrl(String url) {
        Picasso.with(this).load(url).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView, new com.squareup.picasso.Callback() {

                    @Override
                    public void onSuccess() {
                        //TODO
                    }

                    @Override
                    public void onError() {
                        //TODO
                    }
                });
    }

    public void addListenerOnRatingBar()
    {
        ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener()
        {
            public void onRatingChanged(RatingBar ratingBar, float ratingValue, boolean fromUser)
            {
                rating=String.valueOf(ratingValue);
            }
        });
    }

    public void submit(View view) throws InterruptedException {
        summary = editTextSummary.getText().toString();
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute("submit",summary,rating,listingID, userID);
        while (response == false)
        {
            Thread.sleep(100);
            responseTimer++;
            if (responseTimer >=BackgroundSettings.responseLimit)
            {
                Toast.makeText(getApplicationContext(), "Connection timed out.", Toast.LENGTH_LONG).show();
                break;
            }
        }
        if (response == true) {
            responseTimer = 0;
            Toast.makeText(getApplicationContext(), "Submission successful.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, Home.class));
        }
        response = false;
    }
}
