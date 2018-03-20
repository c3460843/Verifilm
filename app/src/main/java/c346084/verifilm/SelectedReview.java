package c346084.verifilm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SelectedReview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_review);
        TextView editTextSummary = findViewById(R.id.editTextSummary);
        editTextSummary.setFocusable(false);
    }
}
