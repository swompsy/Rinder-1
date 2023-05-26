package rinder.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class HomePageActivity extends AppCompatActivity {

    Button alarmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        alarmButton = findViewById(R.id.AlarmButton);

        alarmButton.setOnClickListener(a -> {
            Intent intent = new Intent(HomePageActivity.this, AlarmActivity.class);
            startActivity(intent);
            finish();
        });
    }
}