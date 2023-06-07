package rinder.com;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.content.Intent;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;
import rinder.com.Database;
import rinder.com.mod.user;

import android.Manifest;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private Button loginButton;
    private EditText luname, lpass;
    private String uname, pass;
    private ActivityResultLauncher<String[]> locationPermissionRequest;
    public static boolean fineLocationGranted;
    public static boolean coarseLocationGranted;
//    private boolean isValidUsername(String username) {
//        return username.matches("^[a-zA-Z\\d]{5,}$");
//    }
//    private boolean isValidPassword(String password) {
//        return password.matches("^[a-zA-Z\\d!@$&*+=-]{10,}$");
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationPermissionRequest = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                this::handleLocationPermissionResult);

        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            fineLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_FINE_LOCATION, false);
                            coarseLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_COARSE_LOCATION,false);
                            if (fineLocationGranted) {
                                Toast.makeText(MainActivity.this, "fine location granted", Toast.LENGTH_SHORT).show();
                            } else if (coarseLocationGranted) {
                                Toast.makeText(MainActivity.this, "coarse location granted", Toast.LENGTH_SHORT).show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                                builder.setTitle("Location Permissions")
                                        .setMessage("Location permissions are required to use this app.")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                // Close the app or perform any other action
                                                finish();
                                            }
                                        })
                                        .show();
                            }
                        }
                );
        locationPermissionRequest.launch(new String[] {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });

        Database.sqlite = new Database(this);

//        usernameInput = findViewById(R.id.editText);
//        passwordInput = findViewById(R.id.userPassword);
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> {
                    hook();
                    Toast toast = val();
                    if (toast != null) {
                        toast.show();
                    } else {
                        toast = success();
                        if (toast != null) {
                            toast.show();
                            Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
                            intent.putExtra("user", user.GET_USER(uname, pass));
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }
                });
            // Create a clickable hyperlink for the "Register" button
            TextView registerTextView = findViewById(R.id.registerButton);
            String registerText = getResources().getString(R.string.registerText);
            SpannableString spannableString = new SpannableString(registerText);
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View view) {
                    // Handle the click event for the "Register" text
                    Intent intent = new Intent(MainActivity.this, RegisterPageActivity.class);
                    startActivity(intent);
                    finish();
                }
            };
            int startIndex = registerText.indexOf("here!");
            int endIndex = startIndex + "here!".length();
            spannableString.setSpan(clickableSpan, startIndex, endIndex, 0);
            registerTextView.setText(spannableString);
            registerTextView.setMovementMethod(LinkMovementMethod.getInstance());
        }

        private void hook(){
            luname = findViewById(R.id.editText);
            uname = luname.getText().toString();
            lpass = findViewById(R.id.userPassword);
            pass = lpass.getText().toString();
        }

        private Toast val(){
            if (uname.isEmpty() || pass.isEmpty())
                return Toast.makeText(this, "All fields should be filled", Toast.LENGTH_SHORT);
            else if (isValidUsername(uname)){
                return Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT);
            }
            else if (isValidPassword(pass)){
                return Toast.makeText(this, "Please enter a valid password", Toast.LENGTH_SHORT);
            }
            else {
                user user = rinder.com.mod.user.GET_USER(uname, pass);
                if (user==null)
                    return Toast.makeText(this, "User is not found", Toast.LENGTH_SHORT);
                else if (!user.getPassword().equals(pass))
                    return Toast.makeText(this, "Credential is invalid", Toast.LENGTH_SHORT);
                else rinder.com.mod.user.curr = user;
            }
            return null;
        }

        private Toast success(){
            if (!user.curr.getVerified()) return Toast.makeText(this, "Successful.", Toast.LENGTH_SHORT);
            return null;
        }

        private boolean isValidUsername(String username) {
            return username.matches("^[a-zA-Z\\d]{5,}$");
        }
        private boolean isValidPassword(String password) {
            return password.matches("^[a-zA-Z\\d!@$&*+=-]{10,}$");
        }


    private void handleLocationPermissionResult(Map<String, Boolean> result) {
        Boolean fineLocationGranted = result.getOrDefault(
                Manifest.permission.ACCESS_FINE_LOCATION, false);
        Boolean coarseLocationGranted = result.getOrDefault(
                Manifest.permission.ACCESS_COARSE_LOCATION, false);

        if (fineLocationGranted) {
            // Precise location access granted.
        } else if (coarseLocationGranted) {
            // Only approximate location access granted.
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Location Permissions")
                    .setMessage("Location permissions are required to use this app.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Close the app or perform any other action
                            finish();
                        }
                    })
                    .show();
        }
    }
}