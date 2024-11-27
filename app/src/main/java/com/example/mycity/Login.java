package com.example.mycity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {
    TextView backbtn, registerbtn, registerbtn2;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerbtn = findViewById(R.id.register);
        registerbtn2 = findViewById(R.id.register2);
        backbtn = findViewById(R.id.nazad);
        backbtn.setOnClickListener(v -> {
            finish();
        });

        registerbtn.setOnClickListener(v -> {
            Intent intent= new Intent(Login.this, Register.class);
            startActivity(intent);
        });
        registerbtn2.setOnClickListener(v -> {
            Intent intent= new Intent(Login.this, Register.class);
            startActivity(intent);
        });

        TextInputEditText phoneEditText = findViewById(R.id.phone);

        phoneEditText.addTextChangedListener(new TextWatcher() {
            private boolean isFormatting;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (count > 0) {
                    isFormatting = false;
                } else {
                    isFormatting = true;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isFormatting) return;

                String rawInput = s.toString().replaceAll("[^\\d]", "");
                StringBuilder formatted = new StringBuilder();

                if (rawInput.length() >= 1) {
                    formatted.append("(").append(rawInput.charAt(0));
                }
                if (rawInput.length() >= 2) {
                    formatted.append(rawInput.charAt(1)).append(") ");
                }
                if (rawInput.length() > 2) {
                    formatted.append(rawInput.substring(2, Math.min(5, rawInput.length())));
                }
                if (rawInput.length() > 5) {
                    formatted.append("-").append(rawInput.substring(5, Math.min(7, rawInput.length())));
                }
                if (rawInput.length() > 7) {
                    formatted.append("-").append(rawInput.substring(7, Math.min(9, rawInput.length())));
                }

                phoneEditText.setText(formatted.toString());
                phoneEditText.setSelection(formatted.length());
            }
        });
    }
}