package com.example.ffe;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    final String TAG = "json";
    // gdsClCd Code {01,02,03,04,07}
    String[] gdsClCds = {"소화기류", "경보기류", "기계류", "방염류", "소방장비류"};
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        Button searchBtn = findViewById(R.id.searchBtn);
        EditText searchName = findViewById(R.id.searchName);
        spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_list, gdsClCds);
        adapter.setDropDownViewResource(R.layout.spinner_list);
        spinner.setAdapter(adapter);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                if (searchName.getText().toString().equals("")) {
                    // if editText is null popup Toast
                    Toast.makeText(MainActivity.this, "검색할 대상을 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    // If not editText is null
                    intent.putExtra("search", searchName.getText().toString());
                    getSpinnerItem_intent();
                    intent.putExtra("gdsClCds", getSpinnerItem_intent());
                    startActivity(intent);

                }
            }
        });
    }

    private String getSpinnerItem_intent() {
        switch (spinner.getSelectedItem().toString()) {
            case "소화기류":
                return "01";
            case "경보기류":
                return "02";
            case "기계류":
                return "03";
            case "방염류":
                return "04";
            case "소방장비류":
                return "07";
        }
        return "01";
    }
}
