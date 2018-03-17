package c346084.verifilm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends Activity{
    static String userID;
    static boolean userAuthenticated;
    EditText ET_NAME,ET_PASS;
    String login_name,login_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("(0)MainActivity.onCreate");
        setContentView(R.layout.activity_main);
        ET_NAME = findViewById(R.id.user_name);
        ET_PASS = findViewById(R.id.user_pass);
    }

    public void userReg(View view)
    {
        System.out.println("(0)MainActivity.userReg.startActivity(Register)");
        startActivity(new Intent(this,Register.class));
    }

    public void userLogin(View view) throws InterruptedException {
        System.out.println("(1)MainActivity.userLogin");
        login_name = ET_NAME.getText().toString();
        login_pass = ET_PASS.getText().toString();
        String method = "login";
        System.out.println("(2)MainActivity.userLogin.backgroundTask");
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute(method,login_name,login_pass);
        Thread.sleep(1000);
        userID=backgroundTask.userID;
        System.out.println("(3)MainActivity.userLogin.backgroundTask.userID = "+backgroundTask.userID);
        System.out.println("(4)MainActivity.userLogin.MainActivity.userID = "+userID);
        System.out.println("(5)MainActivity.userLogin.userAuthenticated = "+userAuthenticated);
        if(userAuthenticated==true) {
            System.out.println("(6)MainActivity.userLogin.startActivity(Home)");
            startActivity(new Intent(this, Home.class));
        }
    }
}