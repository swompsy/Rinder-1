package rinder.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class RegisterPageActivity extends AppCompatActivity {

    Button backBtn;
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        init();

        backBtn.setOnClickListener(e -> {
            Intent homePage = new Intent(this, MainActivity.class);
            startActivity(homePage);
        });

        registerBtn.setOnClickListener(e -> {
            Intent homePage = new Intent(this, MainActivity.class);
            startActivity(homePage);
        });
    }

    private void init(){
        backBtn = findViewById(R.id.backBtn);
        registerBtn = findViewById(R.id.registerBtn);
    }
}