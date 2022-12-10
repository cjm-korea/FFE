package com.example.ffe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    final String TAG = "json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Need to make dropdown list for gdsClCd

        Button searchBtn = findViewById(R.id.searchBtn);
        EditText searchName = findViewById(R.id.searchName);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                // If not editText is null
                if(!searchName.toString().equals("")){
                    intent.putExtra("search", searchName.toString());
                    startActivity(intent);
                }else{
                    // if editText is null popup Toast
                    Toast.makeText(MainActivity.this, "검색할 대상을 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
