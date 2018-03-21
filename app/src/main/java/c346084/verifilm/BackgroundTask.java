package c346084.verifilm;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by C3460843 on 24/11/2017.
 */

public class BackgroundTask extends AsyncTask<String,Void,String> {
    AlertDialog alertDialog;
    Context ctx;
    String userID;
    String cinemaID;
    String cinemaName;
    String selectedSummary;
    String selectedReview;
    List<String> films = new ArrayList<String>();
    List<String> filmIDs = new ArrayList<String>();
    List<String> listings = new ArrayList<String>();
    List<String> listingIDs = new ArrayList<String>();
    List<String> reviewIDs = new ArrayList<String>();
    List<Float> reviewRatings = new ArrayList<Float>();
    List<String> reviewListingIDs = new ArrayList<String>();
    List<String> reviewUserIDs = new ArrayList<String>();
    List<String> reviewFilmNames = new ArrayList<String>();

    BackgroundTask(Context ctx)
    {
        this.ctx =ctx;
    }
    @Override
    protected void onPreExecute() {
        System.out.println("(1)BackgroundTask.onPreExecute");
        alertDialog = new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("Login Information....");
    }
    @Override
    protected String doInBackground(String... params) {
        System.out.println("(2)BackgroundTask.doInBackground("+params[0]+")");
        String localTestAddress = "10.0.2.2";
        String liveAddress = "mungovin.000webhostapp.com";
        String reg_url = "http://"+liveAddress+"/verifilm/register.php";
        String login_url = "http://"+liveAddress+"/verifilm/login.php";
        String gc_url = "http://"+liveAddress+"/verifilm/getCinemas.php";
        String gf_url = "http://"+liveAddress+"/verifilm/getFilms.php";
        String gl_url = "http://"+liveAddress+"/verifilm/getListings.php";
        String submit_url = "http://"+liveAddress+"/verifilm/submit.php";
        String gr_url = "http://"+liveAddress+"/verifilm/getReviews.php";
        String sr_url = "http://"+liveAddress+"/verifilm/getSelectedReview.php";
        String method = params[0];

        switch (method) {
            case "register":
                String name = params[1];
                String user_name = params[2];
                String user_pass = params[3];
                try {
                    URL url = new URL(reg_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    //httpURLConnection.setDoInput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                    String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                            URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&" +
                            URLEncoder.encode("user_pass", "UTF-8") + "=" + URLEncoder.encode(user_pass, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();
                    InputStream IS = httpURLConnection.getInputStream();
                    IS.close();
                    //httpURLConnection.connect();
                    httpURLConnection.disconnect();
                    return "Registration Success...";
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
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
                    String response = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        System.out.println("(3)BackgroundTask.doInBackground(login).line = "+line);
                        String[] splitLine = line.split("#");
                        userID = splitLine[0];
                        try
                        {
                            response += splitLine[1];
                            MainActivity.userAuthenticated=true;
                        }
                        catch(java.lang.ArrayIndexOutOfBoundsException ex)
                        {
                            MainActivity.userAuthenticated=false;
                        }
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return response;
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
                        System.out.println("---------------------------------------------------------");
                        System.out.println("line " + i);
                        System.out.println(line);
                        String[] splitLine = line.split("#");
                        i++;
                        System.out.println("---------------------------------------------------------");
                        System.out.println("cinemaName before: " + cinemaName);
                        try{
                            cinemaID = splitLine[0];
                            cinemaName = splitLine[1];
                        }catch (java.lang.ArrayIndexOutOfBoundsException e){

                        }
                        System.out.println("cinemaName after: " + cinemaName);
                    }
                    bufferedReader.close();
                    IS.close();
                    //httpURLConnection.connect();
                    httpURLConnection.disconnect();
                    //return "Success...";
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
                    InputStream IS = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                    String line;
                    int i = 0;
                    while ((line = bufferedReader.readLine()) != null) {
                        String[] splitLine = line.split("#");
                        filmIDs.add(i, splitLine[0]);
                        films.add(i, splitLine[1]);
                        i++;
                        System.out.println("---------------------------------------------------------");
                        System.out.println(line);
                        System.out.println(i);
                        System.out.println("---------------------------------------------------------");
                    }
                    bufferedReader.close();
                    IS.close();
                    //httpURLConnection.connect();
                    httpURLConnection.disconnect();
                    //return "Success...";
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
                    InputStream IS = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                    String line;
                    int i = 0;
                    while ((line = bufferedReader.readLine()) != null) {
                        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>LISTING>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+line);
                        String[] splitLine = line.split("#");
                        listingIDs.add(i, splitLine[0]);
                        listings.add(i, splitLine[1]);
                        i++;
                    }
                    bufferedReader.close();
                    IS.close();
                    //httpURLConnection.connect();
                    httpURLConnection.disconnect();
                    //return "Success...";
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
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                    String data = URLEncoder.encode("summary", "UTF-8") + "=" + URLEncoder.encode(summary, "UTF-8") +
                            "&" + URLEncoder.encode("rating", "UTF-8") + "=" + URLEncoder.encode(rating, "UTF-8") +
                            "&" + URLEncoder.encode("listingID", "UTF-8") + "=" + URLEncoder.encode(listingID, "UTF-8") +
                            "&" + URLEncoder.encode("userID", "UTF-8") + "=" + URLEncoder.encode(userID, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();
                    InputStream IS = httpURLConnection.getInputStream();
                    IS.close();
                    //httpURLConnection.connect();
                    httpURLConnection.disconnect();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                    String line;
                    int i = 0;
                    while ((line = bufferedReader.readLine()) != null) {
                        System.out.println(line);
                    }
                    bufferedReader.close();
                    IS.close();
                    return "Submission success...";
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
                    InputStream IS = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                    String line;
                    int i = 0;
                    while ((line = bufferedReader.readLine()) != null) {
                        System.out.println("---------------------------------------------------------");
                        System.out.println("line " + i);
                        System.out.println(line);
                        String[] splitLine = line.split("#");
                        System.out.println("---------------------------------------------------------");
                        try{
                            reviewIDs.add(i, splitLine[0]);
                            reviewRatings.add(i, Float.parseFloat(splitLine[1]));
                            reviewListingIDs.add(i, splitLine[2]);
                            reviewUserIDs.add(i, splitLine[3]);
                            reviewFilmNames.add(i, splitLine[4]);
                            i++;
                        }catch (java.lang.ArrayIndexOutOfBoundsException e){
                            //TODO
                        }
                    }
                    bufferedReader.close();
                    IS.close();
                    //httpURLConnection.connect();
                    httpURLConnection.disconnect();
                    //return "Success...";
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
                    InputStream IS = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                    String line;
                    line = bufferedReader.readLine();
                    try{
                        String[] splitLine = line.split("#");
                        selectedSummary = splitLine[0];
                        selectedReview = splitLine[1];
                    }catch (java.lang.ArrayIndexOutOfBoundsException e){
                        //TODO
                    }
                    bufferedReader.close();
                    IS.close();
                    //httpURLConnection.connect();
                    httpURLConnection.disconnect();
                    //return "Success...";
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
        return null;
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

/*
    @Override
    protected void onPostExecute(String result) {
        if(result.equals("Registration Success...")) {
            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
        }
        else {
            alertDialog.setMessage(result);
            alertDialog.show();
        }
    }
*/
}



