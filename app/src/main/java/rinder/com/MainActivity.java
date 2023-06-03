package rinder.com;

import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {
    private EditText usernameInput;
    private EditText passwordInput;
    private boolean isValidUsername(String username) {
        return username.matches("^[a-zA-Z\\d]{5,}$");
    }
    private boolean isValidPassword(String password) {
        return password.matches("^[a-zA-Z\\d!@$&*+=-]{10,}$");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameInput = findViewById(R.id.editText);
        passwordInput = findViewById(R.id.userPassword);
        Button loginButton = findViewById(R.id.loginButton);

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
                Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
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
}