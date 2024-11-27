package com.example.mycity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity {
    String[] items = {"Talaba","O'qituvchi","Davlat xizmatchisi","Nafaqada","Vaqtincha ishsiz","Uy bekasi","Boshqa"};
    String[] transport = {"Taksi","Shaxsiy avtomobil","Avtobus","Mikroavtobus","Xizmat avtomobili","Velosiped","Odatda piyoda yuraman"};
    TextView backbtn,login;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        AutoCompleteTextView kasb = findViewById(R.id.kasb);
        AutoCompleteTextView moshin = findViewById(R.id.auto);
        AutoCompleteTextView viloyatAutoComplete = findViewById(R.id.viloyat);
        AutoCompleteTextView tumanAutoComplete = findViewById(R.id.tuman);
        TextInputLayout tumanLayout = findViewById(R.id.spinner3);
        backbtn = findViewById(R.id.nazad);
        backbtn.setOnClickListener(v -> {
            finish();
        });
        login = findViewById(R.id.login);
        login.setOnClickListener(v -> {
            Intent intent = new Intent(Register.this,Login.class);
            startActivity(intent);
        });

        ArrayAdapter<String> kasbAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, items);
        kasb.setAdapter(kasbAdapter);

        ArrayAdapter<String> transportAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, transport);
        moshin.setAdapter(transportAdapter);

        kasb.setOnItemClickListener((parent, view, position, id) -> {
            String selectedTransport = parent.getItemAtPosition(position).toString();
            Toast.makeText(this, "Tanlangan kasb: " + selectedTransport, Toast.LENGTH_SHORT).show();
        });

        moshin.setOnItemClickListener((parent, view, position, id) -> {
            String selectedTransport = parent.getItemAtPosition(position).toString();
            Toast.makeText(this, "Tanlangan transport: " + selectedTransport, Toast.LENGTH_SHORT).show();
        });

        List<String> regionList = getRegionsFromRaw();
        viloyatAutoComplete.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, regionList));

        viloyatAutoComplete.setOnItemClickListener((parentView, selectedItemView, position, id) -> {
            tumanLayout.setEnabled(true);

            String selectedRegion = parentView.getItemAtPosition(position).toString();
            int regionId = getRegionIdByName(selectedRegion);

            List<String> districtList = getDistrictsByRegionId(regionId);
            tumanAutoComplete.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, districtList));
        });

        tumanAutoComplete.setOnItemClickListener((parentView, selectedItemView, position, id) -> {
            AutoCompleteTextView mahallaAutoComplete = findViewById(R.id.mfy);
            TextInputLayout mahallaLayout = findViewById(R.id.spinner4);

            mahallaLayout.setEnabled(true);

            String selectedDistrict = parentView.getItemAtPosition(position).toString();
            int districtId = getDistrictIdByName(selectedDistrict);

            List<String> quartersList = getQuartersByDistrictId(districtId);
            mahallaAutoComplete.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, quartersList));
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

    private List<String> getRegionsFromRaw() {
        List<String> regionList = new ArrayList<>();
        String jsonString = loadJSONFromRaw();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray regionsArray = jsonObject.getJSONArray("regions");

            for (int i = 0; i < regionsArray.length(); i++) {
                JSONObject regionObject = regionsArray.getJSONObject(i);
                String name = regionObject.getString("name");
                regionList.add(name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return regionList;
    }
    private String loadJSONFromRaw() {
        String json = null;
        try {
            InputStream is = getResources().openRawResource(R.raw.regions);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }
    private List<String> getDistrictsByRegionId(int regionId) {
        List<String> districtList = new ArrayList<>();
        String jsonString = loadJSONFromRaw();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray districtsArray = jsonObject.getJSONArray("districts");

            for (int i = 0; i < districtsArray.length(); i++) {
                JSONObject districtObject = districtsArray.getJSONObject(i);
                if (districtObject.getInt("region_id") == regionId) {
                    districtList.add(districtObject.getString("name"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return districtList;
    }
    private int getRegionIdByName(String regionName) {
        String jsonString = loadJSONFromRaw();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray regionsArray = jsonObject.getJSONArray("regions");

            for (int i = 0; i < regionsArray.length(); i++) {
                JSONObject regionObject = regionsArray.getJSONObject(i);
                if (regionObject.getString("name").equals(regionName)) {
                    return regionObject.getInt("id");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return -1;
    }
    private List<String> getQuartersByDistrictId(int districtId) {
        List<String> quartersList = new ArrayList<>();
        String jsonString = loadJSONFromRaw();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray quartersArray = jsonObject.getJSONArray("quarters");

            for (int i = 0; i < quartersArray.length(); i++) {
                JSONObject quarterObject = quartersArray.getJSONObject(i);
                if (quarterObject.getInt("district_id") == districtId) {
                    quartersList.add(quarterObject.getString("name"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return quartersList;
    }
    private int getDistrictIdByName(String districtName) {
        String jsonString = loadJSONFromRaw();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray districtsArray = jsonObject.getJSONArray("districts");

            for (int i = 0; i < districtsArray.length(); i++) {
                JSONObject districtObject = districtsArray.getJSONObject(i);
                if (districtObject.getString("name").equals(districtName)) {
                    return districtObject.getInt("id");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return -1; // Topilmasa
    }
}