package c346084.verifilm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.CompoundButton;

/**
 * Created by C3460843 on 17/04/2018.
 */

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        switchResponseListener();
    }


    public void switchResponseListener() {
        Switch switchResponse = findViewById(R.id.switchResponse);
        switchResponse.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    BackgroundSettings.responseLimit = 300;
                    System.out.println("responseLimit = 300");
                } else {
                    BackgroundSettings.responseLimit = 50;
                    System.out.println("responseLimit = 50");
                }
            }
        });
    }
}
