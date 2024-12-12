package com.example.mycity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class NoInternetActivity extends AppCompatActivity {

    private Button retryButton;
    private ImageView noInternetIcon;
    private TextView noInternetMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);

        retryButton = findViewById(R.id.retry_button);
        noInternetIcon = findViewById(R.id.no_internet_icon);
        noInternetMessage = findViewById(R.id.no_internet_message);

        // Tugmaga bosilganda internet holatini tekshirish
        retryButton.setOnClickListener(v -> {
            if (isInternetAvailable()) {
                // Internet mavjud bo'lsa, Home sahifasiga o'tish
                Intent intent = new Intent(NoInternetActivity.this, Loader.class);
                startActivity(intent);
                finish();
            } else {
                // Internet yo'q bo'lsa, foydalanuvchiga xabar berish
                noInternetMessage.setText("Internetga ulanish mavjud emas. Wi-Fi yoki mobil tarmoqqa ulanganingizni tekshirib qayta urining.");
            }
        });
    }

    // Internet mavjudligini tekshirish funksiyasi
    private boolean isInternetAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }
}
