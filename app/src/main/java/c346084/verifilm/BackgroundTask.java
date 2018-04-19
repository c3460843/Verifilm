package c346084.verifilm;

import android.content.Context;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by C3460843 on 24/01/2018.
 */

public class BackgroundTask extends AsyncTask<String,Void,String> {
    Context ctx;
    String userID;
    String cinemaID;
    String cinemaName;
    String selectedSummary;
    String selectedReview;
    static String loginMessage="";
    String fullReviewMessage="";
    List<String> films = new ArrayList<String>();
    List<String> filmIDs = new ArrayList<String>();
    List<String> listings = new ArrayList<String>();
    List<String> listingIDs = new ArrayList<String>();
    List<String> reviewIDs = new ArrayList<String>();
    List<Float> reviewRatings = new ArrayList<Float>();
    List<String> reviewListingIDs = new ArrayList<String>();
    List<String> reviewUserIDs = new ArrayList<String>();
    List<String> reviewFilmIDs = new ArrayList<String>();
    List<String> reviewFilmNames = new ArrayList<String>();

    BackgroundTask(Context ctx)
    {
        this.ctx =ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String localIP = "10.0.2.2";
        String liveIP = "mungovin.000webhostapp.com";
        String login_url = "http://" + liveIP + "/verifilm/login.php";
        String gc_url = "http://" + liveIP + "/verifilm/getCinemas.php";
        String gf_url = "http://" + liveIP + "/verifilm/getFilms.php";
        String gl_url = "http://" + liveIP + "/verifilm/getListings.php";
        String submit_url = "http://" + liveIP + "/verifilm/submit.php";
        String gr_url = "http://" + liveIP + "/verifilm/getReviews.php";
        String sr_url = "http://" + liveIP + "/verifilm/getSelectedReview.php";
        String fr_url = "http://" + liveIP + "/verifilm/setFullReview.php";
        String method = params[0];

        switch (method) {
            case "login":
                String login_name = params[1];
                String login_pass = params[2];
                try {
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("login_name", "UTF-8") + "=" + URLEncoder.encode(login_name, "UTF-8") + "&" +
                            URLEncoder.encode("login_pass", "UTF-8") + "=" + URLEncoder.encode(login_pass, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        String[] splitLine = line.split("#");
                        userID = splitLine[0];
                        try {
                            loginMessage = splitLine[1];
                            MainActivity.userAuthenticated = true;
                        } catch (java.lang.ArrayIndexOutOfBoundsException ex) {
                            MainActivity.userAuthenticated = false;
                        }
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    MainActivity.response = true;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "cinemas":
                String longitude = params[1];
                String latitude = params[2];
                try {
                    URL url = new URL(gc_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("longitude", "UTF-8") + "=" + URLEncoder.encode(longitude, "UTF-8") + "&" +
                            URLEncoder.encode("latitude", "UTF-8") + "=" + URLEncoder.encode(latitude, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream IS = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                    String line;
                    int i = 0;
                    while ((line = bufferedReader.readLine()) != null) {
                        String[] splitLine = line.split("#");
                        i++;
                        try {
                            cinemaID = splitLine[0];
                            cinemaName = splitLine[1];
                        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                            //TODO
                        }
                    }
                    bufferedReader.close();
                    IS.close();
                    //httpURLConnection.connect();
                    httpURLConnection.disconnect();
                    NewReview.response = true;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "films":
                try {
                    URL url = new URL(gf_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    //httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String line;
                    int i = 0;
                    while ((line = bufferedReader.readLine()) != null) {
                        String[] splitLine = line.split("#");
                        try
                        {
                            filmIDs.add(i, splitLine[0]);
                            films.add(i, splitLine[1]);
                            i++;
                        }catch(java.lang.IndexOutOfBoundsException e)
                        {
                            System.out.println("filmIDS size:"+filmIDs.size());
                            System.out.println("films size:"+films.size());
                        }
                    }
                    bufferedReader.close();
                    inputStream.close();
                    //httpURLConnection.connect();
                    httpURLConnection.disconnect();
                    SelectFilm.response = true;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "listings":
                String cinema_id = params[1];
                String film_id = params[2];
                try {
                    URL url = new URL(gl_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("cinema_id", "UTF-8") + "=" + URLEncoder.encode(cinema_id, "UTF-8") + "&" +
                            URLEncoder.encode("film_id", "UTF-8") + "=" + URLEncoder.encode(film_id, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String line;
                    int i = 0;
                    while ((line = bufferedReader.readLine()) != null) {
                        String[] splitLine = line.split("#");
                        try
                        {
                        listingIDs.add(i, splitLine[0]);
                        listings.add(i, splitLine[1]);
                        i++;
                        }catch(java.lang.IndexOutOfBoundsException e)
                        {
                            System.out.println("listingIDs size:"+listingIDs.size());
                            System.out.println("listings size:"+listings.size());
                        }
                    }
                    bufferedReader.close();
                    inputStream.close();
                    //httpURLConnection.connect();
                    httpURLConnection.disconnect();
                    SelectListing.response = true;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "submit":
                String summary = params[1];
                String rating = params[2];
                String listingID = params[3];
                String userID = params[4];
                try {
                    URL url = new URL(submit_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    //httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("summary", "UTF-8") + "=" + URLEncoder.encode(summary, "UTF-8") +
                            "&" + URLEncoder.encode("rating", "UTF-8") + "=" + URLEncoder.encode(rating, "UTF-8") +
                            "&" + URLEncoder.encode("listingID", "UTF-8") + "=" + URLEncoder.encode(listingID, "UTF-8") +
                            "&" + URLEncoder.encode("userID", "UTF-8") + "=" + URLEncoder.encode(userID, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    httpURLConnection.connect();
                    httpURLConnection.disconnect();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String line;
                    int i = 0;
                    while ((line = bufferedReader.readLine()) != null) {
                        //System.out.println(line);
                    }
                    bufferedReader.close();
                    inputStream.close();
                    ReviewForm.response = true;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "reviews":
                String user_id = params[1];
                try {
                    URL url = new URL(gr_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String line;
                    int i = 0;
                    while ((line = bufferedReader.readLine()) != null) {
                        System.out.println(line);
                        String[] splitLine = line.split("#");
                        try {
                            reviewRatings.add(i, Float.parseFloat(splitLine[1]));
                            reviewListingIDs.add(i, splitLine[2]);
                            reviewUserIDs.add(i, splitLine[3]);
                            reviewFilmIDs.add(i, splitLine[4]);
                            reviewFilmNames.add(i, splitLine[5]);
                            reviewIDs.add(i, splitLine[0]); //Will add blank entry to array list which causes BrowseReviews to display 1 extra review if splitLine[1,2,3,4,5] aren't added first.
                            i++;
                        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                            //TODO
                        }
                    }
                    bufferedReader.close();
                    inputStream.close();
                    //httpURLConnection.connect();
                    httpURLConnection.disconnect();
                    BrowseReviews.response = true;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "selectedReview":
                String review_id = params[1];
                try {
                    URL url = new URL(sr_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("review_id", "UTF-8") + "=" + URLEncoder.encode(review_id, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String line;
                    line = bufferedReader.readLine();
                    try {
                        String[] splitLine = line.split("#");
                        selectedSummary = splitLine[0];
                        selectedReview = splitLine[1];
                    } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                        //TODO
                    }
                    bufferedReader.close();
                    inputStream.close();
                    //httpURLConnection.connect();
                    httpURLConnection.disconnect();
                    BrowseReviews.response = true;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "fullReview":
                String full_review_id = params[1];
                String full_review = params[2];
                try {
                    URL url = new URL(fr_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    //httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("full_review_id", "UTF-8") + "=" + URLEncoder.encode(full_review_id, "UTF-8") +
                            "&" + URLEncoder.encode("full_review", "UTF-8") + "=" + URLEncoder.encode(full_review, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    httpURLConnection.connect();
                    //httpURLConnection.disconnect();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        fullReviewMessage = line;
                    }
                    bufferedReader.close();
                    httpURLConnection.disconnect();
                    inputStream.close();
                    FullReview.response = true;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
        return null;
    }
}



