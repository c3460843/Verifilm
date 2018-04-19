package c346084.verifilm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by C3460843 on 17/03/2018.
 */

public class SelectedReview extends AppCompatActivity {

    String url;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_review);
        TextView editTextSummary = findViewById(R.id.editTextSummary);
        editTextSummary.setFocusable(false);
        editTextSummary.setText(BrowseReviews.selectedSummary);

        TextView textViewFilm  = findViewById(R.id.textViewFilm);
        textViewFilm.setText(BrowseReviews.selectedReviewFilmName);
        RatingBar ratingBar  = findViewById(R.id.ratingBar);
        ratingBar.setRating(BrowseReviews.selectedReviewRating);


        imageView = (ImageView)findViewById(R.id.imageView);
        url = "http://mungovin.000webhostapp.com/verifilm/banners/"+ BrowseReviews.selectedReviewFilmID+".ban.jpg";
        loadImageFromUrl(url);
        System.out.println(BrowseReviews.selectedReviewFilmID);

    }

    private void loadImageFromUrl(String url){
        Picasso.with(this).load(url).placeholder(R.mipmap.ic_launcher)
        .error(R.mipmap.ic_launcher)
                .into(imageView,new com.squareup.picasso.Callback(){

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

    public void fullReview(View view){
        startActivity(new Intent(this, FullReview.class));
    }
}
