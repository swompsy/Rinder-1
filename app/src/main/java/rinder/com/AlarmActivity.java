package rinder.com;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import android.os.CountDownTimer;

public class AlarmActivity extends AppCompatActivity {
    private TextView approximateTimeTextView;
    private TextView arrivalTimeTextView;

    private CountDownTimer countDownTimer;
    private Calendar currentTime;
    private int initialMinutes;
    private int remainingMinutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        approximateTimeTextView = findViewById(R.id.approximateTimeTextView);
        arrivalTimeTextView = findViewById(R.id.arrivalTimeTextView);
        Button snoozeButton = findViewById(R.id.snoozeButton);
        Button turnOffButton = findViewById(R.id.turnOffButton);

        currentTime = Calendar.getInstance();
        initialMinutes = 7;
        remainingMinutes = initialMinutes;
        updateApproximateTime();

        snoozeButton.setOnClickListener(v -> {
            countDownTimer.cancel();
            int additionalMinutes = 5;
            initialMinutes += additionalMinutes;
            remainingMinutes = initialMinutes;
            updateApproximateTime();
            startCountdownTimer(remainingMinutes);
        });

        turnOffButton.setOnClickListener(v -> {
            Intent homePage = new Intent(this, HomePageActivity.class);
            startActivity(homePage);
        });
        startCountdownTimer(initialMinutes);
    }

    private void updateApproximateTime() {
        String approximateTime = remainingMinutes + " minutes";
        approximateTimeTextView.setText(approximateTime);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Calendar updatedTime = (Calendar) currentTime.clone();
        updatedTime.add(Calendar.MINUTE, initialMinutes);
        String arrivalTime = sdf.format(updatedTime.getTime());
        arrivalTimeTextView.setText(arrivalTime);
    }

    private void startCountdownTimer(int minutes) {
        long durationInMillis = (long) minutes * 60 * 1000;
        countDownTimer = new CountDownTimer(durationInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingMinutes = (int) (millisUntilFinished / (1000 * 60));
                updateApproximateTime();
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {
                approximateTimeTextView.setText("00:00");
                // Add your desired actions here (e.g., play alarm sound, display notification)
            }
        };
        countDownTimer.start();
    }
}
