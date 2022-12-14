package com.example.ffe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ResultActivity extends AppCompatActivity {
    final String TAG = "json";

    EquipmentAdapter adapter;
    EquipmentRequest equipmentRequest;
    RecyclerView recyclerView;
    FrameLayout resultFrame;
    LayoutInflater inflater;

    int total = 0;
    String search;
    String gdsClCds;

    static RequestQueue requestQueue;
    String api;
    String urlStr = "http://apis.data.go.kr/B552486/opnFsuplAprv/opnFsuplAprv01?serviceKey=";
    String authenticationKey = "Eh9moxtndpCkUG4jDK0Y3uKpoXwviN8JHPf%2Bp1IFB1xT3szmsNHNYNgDiuDfhkiZzNpc1KtrTZMp6%2FOqYJAdWQ%3D%3D";
    String option = "&pageNo=1&fromAprv=20020101&toAprv=20210218";
    String gdsClCd;
    String numOfRows = "&numOfRows=01";

    JSONObject jsonRoot = null;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //region Create custom Actionbar for alignment center title and make home Btn
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.cutom_actionbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //endregion

        recyclerView = findViewById(R.id.recyclerView);
        resultFrame = findViewById(R.id.result_frame);

        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new EquipmentAdapter();
        recyclerView.setAdapter(adapter);


        //region Get Intent for get information in Mainpage
        Intent myIntent = getIntent();
        search = myIntent.getStringExtra("search");
        gdsClCds = myIntent.getStringExtra("gdsClCds");
        //endregion

        // First call for set total item size
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        makeRequest();
    }

    //region First call is least size because get total item size and get All items
    private void makeRequest() {
        gdsClCd = "&gdsClCd=" + gdsClCds;
        api = urlStr+ authenticationKey + option + gdsClCd + "&numOfRows=01";
        StringRequest request = new StringRequest(Request.Method.GET, api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Parse for total item size
                JSONParseToObject(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "?????? : " + error);
            }
        });
        request.setShouldCache(false);
        requestQueue.add(request);
    }

    private void JSONParseToObject(String response) {
        try {
            jsonRoot = new JSONObject(response);
            total = Integer.parseInt(jsonRoot.getJSONObject("header").getString("totalCount"));
            // Get All items
            getAllRequest();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region Call every items in gdsClCd
    private void getAllRequest() {
        numOfRows = "&numOfRows="+ total;
        api = urlStr + authenticationKey + option + gdsClCd + numOfRows;
        StringRequest request = new StringRequest(Request.Method.GET, api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                processResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "?????? : " + error);
            }
        });
        request.setShouldCache(false);
        requestQueue.add(request);
    }

    private void processResponse(String response) {
        Gson gson = new Gson();
        equipmentRequest = gson.fromJson(response, EquipmentRequest.class);
        for(int i=0;i< equipmentRequest.data.size();i++){
            if(equipmentRequest.data.get(i).gdsNm.contains(search)){
                Equipment equipment = equipmentRequest.data.get(i);
                adapter.addItem(equipment);
            }
        }

        if(adapter.getItemCount() == 0){
            Log.d(TAG, "empty page");
            inflater.inflate(R.layout.empty_page, resultFrame, true);
        }else{
            adapter.notifyDataSetChanged();
        }
    }
    //endregion
}