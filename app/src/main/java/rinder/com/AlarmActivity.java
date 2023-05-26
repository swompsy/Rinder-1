package rinder.com;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class AlarmActivity extends AppCompatActivity {
    private TextView alarmTimeTextView;
    private TextView arrivalTimeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        alarmTimeTextView = findViewById(R.id.alarmTimeTextView);
        arrivalTimeTextView = findViewById(R.id.ArrivalTimeTextView);
        Button snoozeButton = findViewById(R.id.snoozeButton);
        Button turnOffButton = findViewById(R.id.turnOffButton);

        // Get the current time
        Calendar currentTime = Calendar.getInstance();

        // Add 7 minutes to the current time
        int minutes = 7;
        currentTime.add(Calendar.MINUTE, minutes);

        // Format the new time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String alarmTime = sdf.format(currentTime.getTime());

        // Set the updated time to the TextView
        alarmTimeTextView.setText(alarmTime);

        // Set the arrival time to the TextView
        String arrivalText = "You will be arriving in " + minutes + " minutes ";
        arrivalTimeTextView.setText(arrivalText);
            }
        };