package rinder.com;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText usernameInput;
    private EditText passwordInput;
    private boolean isValidUsername(String username) {
        return username.matches("^[a-zA-Z\\d]{5,}$");
    }
    private boolean isValidPassword(String password) {
        return password.matches("^[a-zA-Z\\d!@$&*+=-]{10,}$");
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameInput = findViewById(R.id.usernameBar);
        passwordInput = findViewById(R.id.passwordBar);
        Button loginButton = findViewById(R.id.loginButton);
        Button forgotPasswordButton = findViewById(R.id.forgotPasswordButton);
        Button registerButton = findViewById(R.id.registerButton);

        loginButton.setOnClickListener(v -> {
            String username = usernameInput.getText().toString();
            String password = passwordInput.getText().toString();

            //validasi kalo ga diisi
            if(TextUtils.isEmpty(username)){
                usernameInput.setError("Please enter your username");
                return;
            }
            if(TextUtils.isEmpty(password)){
                passwordInput.setError("Please enter your password");
                return;
            }

            //validasi kalo ga sesuai syarat
            if(!isValidUsername(username)){
                usernameInput.setError("Please enter a valid username");
                return;
            }
            if (!isValidPassword(password)) {
                passwordInput.setError("Please enter a valid password");
                return;
            }

            //kalau benar semua
            if (isValidUsername(username) && isValidPassword(password)) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        });

        forgotPasswordButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ForgotPassActivity.class);
            startActivity(intent);
            finish();
        });

        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterPageActivity.class);
            startActivity(intent);
            finish();
        });
    }
}