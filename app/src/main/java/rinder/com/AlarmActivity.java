package rinder.com;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AlarmActivity extends AppCompatActivity {

    private TextView textViewTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        textViewTime = findViewById(R.id.textViewTime);

        // Get the current time
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String currentTime = sdf.format(calendar.getTime());

        // Update the TextView with the current time
        textViewTime.setText(currentTime);
    }
}