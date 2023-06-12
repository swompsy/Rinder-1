package rinder.com;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PnPPageActivity extends AppCompatActivity {

    private CheckBox checkboxAgree;
    private Button continueButton;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pnp);

        checkboxAgree = findViewById(R.id.checkbox_agree);
        continueButton = findViewById(R.id.continue_button);
        scrollView = findViewById(R.id.privacy_policy);

        continueButton.setEnabled(false); // Disable the button initially
        checkboxAgree.setEnabled(true); // Enable the checkbox initially

        checkboxAgree.setOnCheckedChangeListener((buttonView, isChecked) -> {
            continueButton.setEnabled(isChecked); // Enable/disable the button based on checkbox state
        });

        // Retrieve the HTML-formatted privacy policy text from string resources
        String privacyPolicyText = getString(R.string.privacy_policy_content);

        // Convert HTML string to Spanned object
        CharSequence formattedText = Html.fromHtml(privacyPolicyText, Html.FROM_HTML_MODE_COMPACT);

        // Set the formatted text to the TextView
        TextView privacyPolicyTextView = findViewById(R.id.privacy_policy_TextView);
        privacyPolicyTextView.setText(formattedText);

        continueButton.setOnClickListener(v -> {
            // Handle the continue button click
            if (checkboxAgree.isChecked()) {
                // Navigate back to the previous activity (RegisterPageActivity)
                finish();
            }
        });
    }
}
