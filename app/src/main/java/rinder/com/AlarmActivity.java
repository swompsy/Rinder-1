package rinder.com;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import android.os.CountDownTimer;


public class AlarmActivity extends AppCompatActivity {
    private TextView alarmTimeTextView;
    private TextView arrivalTimeTextView;

    private CountDownTimer countDownTimer;

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

        // Calculate the duration in milliseconds
        long durationInMillis = minutes * 60 * 1000;

        countDownTimer = new CountDownTimer(durationInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Calculate the remaining time in minutes and seconds
                int minutes = (int) (millisUntilFinished / (1000 * 60));
                int seconds = (int) ((millisUntilFinished / 1000) % 60);

                // Update the arrival time
                String arrivalText = "You will be arriving in " + minutes + " minutes";
                arrivalTimeTextView.setText(arrivalText);
            }
            @Override
            public void onFinish() {
                // Perform any actions when the countdown finishes
                alarmTimeTextView.setText("00:00");
                // Add your desired actions here (e.g., play alarm sound, display notification)
            }
        };
        countDownTimer.start();
    }
}
