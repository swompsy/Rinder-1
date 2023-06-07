package rinder.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import rinder.com.Database;
import rinder.com.mod.user;

public class RegisterPageActivity extends AppCompatActivity {

    private Button backButton, registerButton;

    private EditText rname, runame, remail, rpass, rdob;

    private String name, uname, email, pass, dob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        Database.sqlite = new Database(this);

        registerButton = findViewById(R.id.registerBtn);
        registerButton.setOnClickListener(e -> {
            hook();
            Toast toast = val();
            if(toast != null){
                toast.show();
            } else{
                user.INSERT(new user(name, uname, email, pass, dob));
                toast = success();
                toast.show();
                Intent intent = new Intent(RegisterPageActivity.this, MainActivity.class);
                intent.putExtra("user", user.GET_USER(email, pass));
                startActivity(intent);
                finish();
            }
        });

        backButton = findViewById(R.id.backBtn);
        backButton.setOnClickListener(e -> {
            Intent homePage = new Intent(this, MainActivity.class);
            startActivity(homePage);
        });
    }

    private void hook(){
        rname = findViewById(R.id.FullnameFill);
        name = rname.getText().toString();
        runame = findViewById(R.id.UsernameFill);
        uname = runame.getText().toString();
        remail = findViewById(R.id.EmailFill);
        email = remail.getText().toString();
        rpass = findViewById(R.id.PasswordFill);
        pass = rpass.getText().toString();
        rdob = findViewById(R.id.DOBFill);
        dob = rdob.getText().toString();
    }

    private Toast val(){
        if (name.isEmpty() || uname.isEmpty() || email.isEmpty() || pass.isEmpty() || dob.isEmpty())
            return Toast.makeText(this, "All fields should be filled", Toast.LENGTH_SHORT);
        else if (name.length()<5)
            return Toast.makeText(this, "Name length should be at least 5 characters", Toast.LENGTH_SHORT);
        else if (uname.length()>25)
            return Toast.makeText(this, "Username length should not exceed 25 characters", Toast.LENGTH_SHORT);
        else if (!email.endsWith(".com"))
            return Toast.makeText(this, "Email should ends with '.com'", Toast.LENGTH_SHORT);
        else if (!alphanum(pass))
            return Toast.makeText(this, "Password should have letters and numbers", Toast.LENGTH_SHORT);
        else if (!isValidDateOfBirth(dob))
            return Toast.makeText(this, "Date of birth format should be yyyy/mm/dd", Toast.LENGTH_SHORT);
        return null;
    }

    private Toast success(){
        return Toast.makeText(this, "User successfully registered.", Toast.LENGTH_SHORT);
    }

    private boolean alphanum(String str){
        boolean Char = false, Num = false;
        for (int i=0; i<str.length(); i++) {
            if (str.charAt(i) >= 'a' && str.charAt(i) <= 'z' ) Char = true;
            if (str.charAt(i) >= 'A' && str.charAt(i) <= 'Z' ) Char = true;
            if (str.charAt(i) >= '0' && str.charAt(i) <= '9' ) Num = true;
            if (Char && Num) break;
        }
        return Char && Num;
    }

    public static boolean isValidDateOfBirth(String dob) {
        // Check the length and format of the input string
        if (dob.length() != 10 || dob.charAt(4) != '/' || dob.charAt(7) != '/') {
            return false;
        }

        // Extract the year, month, and day components
        int year = Integer.parseInt(dob.substring(0, 4));
        int month = Integer.parseInt(dob.substring(5, 7));
        int day = Integer.parseInt(dob.substring(8, 10));

        // Validate the year, month, and day ranges
        if (year < 1000 || year > 2023 || month < 1 || month > 12 || day < 1 || day > 31) {
            return false;
        }

        // Validate the day for each month
        int[] maxDays = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (day > maxDays[month - 1]) {
            return false;
        }

        return true;

    }
}