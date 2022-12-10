package com.example.myevents;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import domain.Event;

public class historical extends AppCompatActivity {

    ArrayList<Event> MyListEvent=new ArrayList<>();
    databaseHelper dbHelper;
    ListView listEvent;
    CustomHistoricalBaseAdapter customHistoricalBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical);

        listEvent = findViewById(R.id.list);
        dbHelper=new databaseHelper(historical.this);
        MyListEvent=dbHelper.getAllHistoricalEvent();
        customHistoricalBaseAdapter=new CustomHistoricalBaseAdapter(historical.this,MyListEvent);
        listEvent.setAdapter(customHistoricalBaseAdapter);
    }
}