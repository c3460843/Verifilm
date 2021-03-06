package c346084.verifilm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by C3460843 on 24/01/2018.
 */

public class MainActivity extends Activity{
    static String userID;
    static boolean userAuthenticated;
    EditText user_name,user_pass;
    String login_name,login_pass;
    static boolean response=false;
    int responseTimer=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user_name = findViewById(R.id.user_name);
        user_pass = findViewById(R.id.user_pass);
    }

    public void userLogin(View view) throws InterruptedException {
        login_name = user_name.getText().toString();
        login_pass = user_pass.getText().toString();
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute("login",login_name,login_pass);
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
        if (response == true)
        {
            responseTimer = 0;
            userID = backgroundTask.userID;
            if (userAuthenticated == true) {
                startActivity(new Intent(this, Home.class));
            }else if (userAuthenticated == false) {
                Toast.makeText(getApplicationContext(), userID, Toast.LENGTH_LONG).show();
            }
        }
    }
}