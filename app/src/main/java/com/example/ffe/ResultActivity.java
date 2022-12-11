package com.example.ffe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    RecyclerView recyclerView;

    static RequestQueue requestQueue;
    String autenticationKey = "Eh9moxtndpCkUG4jDK0Y3uKpoXwviN8JHPf%2Bp1IFB1xT3szmsNHNYNgDiuDfhkiZzNpc1KtrTZMp6%2FOqYJAdWQ%3D%3D";
    String option = "&pageNo=1&fromAprv=20020101&toAprv=20210218";
    String gdsClCd = "&gdsClCd=02";
    String numOfRows = "&numOfRows=01";
    
    int total = 0;
    String search;

    TextView textView;
    JSONObject jsonRoot = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new EquipmentAdapter();
        recyclerView.setAdapter(adapter);
        // Make 분류코드 intent for search sector
        Intent myIntent = getIntent();
        search = myIntent.getStringExtra("search");
        Log.d(TAG,search);
        
        // First call for set total item size
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        makeRequest();
    }

    
    private void makeRequest() {
        // First call is least size because get total item size and get All items
        String numOfRows = "&numOfRows=01";
        String urlStr = "http://apis.data.go.kr/B552486/opnFsuplAprv/opnFsuplAprv01?serviceKey="+ autenticationKey + option + gdsClCd + numOfRows;
        StringRequest request = new StringRequest(Request.Method.GET, urlStr, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Parse for total item size
                JSONParseToObject(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "에러 : " + error);
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

    private void getAllRequest() {
        String numOfRows = "&numOfRows="+ Integer.toString(2000);
        String urlStr = "http://apis.data.go.kr/B552486/opnFsuplAprv/opnFsuplAprv01?serviceKey="+ autenticationKey + option + gdsClCd + numOfRows;
        StringRequest request = new StringRequest(Request.Method.GET, urlStr, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                processResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "에러 : " + error);
            }
        });
        request.setShouldCache(false);
        requestQueue.add(request);
    }

    private void processResponse(String response) {
        Gson gson = new Gson();
        EquipmentRequest equipmentRequest = gson.fromJson(response, EquipmentRequest.class);
        for(int i=0;i< equipmentRequest.data.size();i++){
            if(equipmentRequest.data.get(i).gdsNm.contains(search)){
                Equipment equipment = equipmentRequest.data.get(i);
                adapter.addItem(equipment);
            }
            adapter.notifyDataSetChanged();
        }
    }


//    public class ListViewAdapter extends BaseAdapter {
//        ArrayList<Equipment> itmes = new ArrayList<Equipment>();
//
//        @Override
//        public int getCount() {
//            return items.size();
//        }
//
//        @Override
//        public Object getItem(int i) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int position, View view, ViewGroup viewGroup) {
//            final Context context = viewGroup.getContext();
//            final Equipment
//            return null;
//        }
//    }
    }