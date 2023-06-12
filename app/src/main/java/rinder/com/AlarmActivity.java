package rinder.com;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
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
        ImageButton backButton = findViewById(R.id.BackButton);

        currentTime = Calendar.getInstance();
        initialMinutes = 1;
        remainingMinutes = initialMinutes;
        updateApproximateTime();

        backButton.setOnClickListener(v -> {
            // Navigate back to the HomePageActivity
            Intent intent = new Intent(AlarmActivity.this, HomePageActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clear the activity stack
            startActivity(intent);
        });

        snoozeButton.setOnClickListener(v -> {
            countDownTimer.cancel();
            int additionalMinutes = 5;
            initialMinutes += additionalMinutes;
            remainingMinutes += additionalMinutes + 1;
            updateApproximateTime();
            startCountdownTimer(remainingMinutes);
        });

        turnOffButton.setOnClickListener(v -> {
            countDownTimer.cancel();
            Intent intent = new Intent(AlarmActivity.this, HomePageActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clear the activity stack
            startActivity(intent);
            finish();
        });
        startCountdownTimer(remainingMinutes);
    }

    private void updateApproximateTime() {
        String approximateTime = remainingMinutes + 1 + " minutes";
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
            @Override
            public void onFinish() {
                Intent intent = new Intent(AlarmActivity.this, RingingActivity.class);
                startActivity(intent);
                finish();
            }
        };
        countDownTimer.start();
    }
}