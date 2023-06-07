package rinder.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class RingingActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ringing);

        ImageButton turnOffButton = findViewById(R.id.turnOffButton);

        // Play sound
        mediaPlayer = MediaPlayer.create(this, R.raw.cyber_aponism);
        mediaPlayer.start();

        // Display notification
        createNotificationChannel();
        showNotification();

        // Vibrate device
        vibrator = getSystemService(Vibrator.class);
        if (vibrator != null && vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                VibrationEffect vibrationEffect = VibrationEffect.createWaveform(new long[]{0, 1000, 1000}, -1);
                if (checkSelfPermission(Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED) {
                    vibrator.vibrate(vibrationEffect);
                }
            } else {
                if (checkSelfPermission(Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED) {
                    vibrator.vibrate(new long[]{0, 1000, 1000}, -1);
                }
            }
        }

        turnOffButton.setOnClickListener(v -> {
            Intent homePage = new Intent(this, HomePageActivity.class);
            startActivity(homePage);
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop playing sound and vibrating
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        if (vibrator != null) {
            vibrator.cancel();
        }
    }

    private void createNotificationChannel() {
        // Create a notification channel (for Android 8.0 and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channel_id", "Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    private void showNotification() {
        // Create an intent for the button action
        Intent Intent = new Intent(this, RingingActivity.class);
        Intent.setAction("TURN_OFF");
        PendingIntent turnOffPendingIntent = PendingIntent.getActivity(this, 0, Intent, PendingIntent.FLAG_IMMUTABLE);

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                .setSmallIcon(R.drawable.logo_purple)
                .setContentTitle("You Have Arrived")
                .addAction(0, "Turn Off", turnOffPendingIntent);

        // Display the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, PERMISSION_REQUEST_CODE);
            return;
        }
        notificationManager.notify(1, builder.build());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with showing the notification
                showNotification();
            } else {
                // Permission denied, handle accordingly (e.g., show an error message)
                Toast.makeText(this, "Permission denied. Cannot show notification.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent != null && "TURN_OFF".equals(intent.getAction())) {
            // Stop playing sound and vibrating
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
            if (vibrator != null) {
                vibrator.cancel();
            }

            // Close the notification
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.cancel(1);

            // Finish the activity
            finish();
        }
    }
}