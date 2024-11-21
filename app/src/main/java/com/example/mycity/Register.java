package com.example.mycity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class Register extends AppCompatActivity {
    String[] items = {"Talaba","O'qituvchi","Davlat xizmatchisi","Nafaqada","Vaqtincha ishsiz","Uy bekasi","Boshqa"};

//    AutoCompleteTextView autoCompleteTextView;
//    ArrayAdapter<String> adapterItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        String[] kasblar = new String[] {
                "Talaba", "O'qituvchi", "Davlat xizmatchisi", "Nafaqada", "Vaqtincha ishsiz", "Uy bekasi", "Boshqa"
        };

        // AutoCompleteTextViewni topish
        AutoCompleteTextView kasbTextView = findViewById(R.id.kasb);

        // ArrayAdapter yaratish va AutoCompleteTextView-ga qo'yish
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, kasblar);
        kasbTextView.setAdapter(adapter);


        TextInputEditText phoneEditText = findViewById(R.id.phone);

        phoneEditText.addTextChangedListener(new TextWatcher() {
            private boolean isFormatting;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // O'chirish bo'lsa, formatlashni to'xtatamiz
                if (count > 0) {
                    isFormatting = false;
                } else {
                    isFormatting = true; // Yangi kiritilish bo'lsa formatlashni boshlaymiz
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Faqat yangi kiritilayotgan ma'lumot uchun formatlashni amalga oshiramiz
                if (!isFormatting) return; // O'chirishda formatlashni bajarishni cheklaymiz

                String rawInput = s.toString().replaceAll("[^\\d]", ""); // Faqat raqamlarni olish
                StringBuilder formatted = new StringBuilder();

                // Formatlash qoidalari
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

                // Natijani o'zgartirish
                phoneEditText.setText(formatted.toString());
                // Kursorni oxiriga o'rnatish
                phoneEditText.setSelection(formatted.length());
            }
        });

    }
}