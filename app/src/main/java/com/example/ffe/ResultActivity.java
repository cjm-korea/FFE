package com.example.ffe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    final String TAG = "json";
    private ListView listview = null;
//    private ListViewAdapter adapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


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