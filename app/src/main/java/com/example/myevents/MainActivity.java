package com.example.myevents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import domain.Event;

public class MainActivity extends AppCompatActivity implements EventDialog.EventDialogListener {

    FloatingActionButton fab;
    ListView listEvent ;
    ArrayList <Event> MyListEvent=new ArrayList<>();
    databaseHelper dbHelper;
    CustomBaseAdapter customBaseAdapter;

    Button historical;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);


        fab=findViewById(R.id.fab_add);
        listEvent = findViewById(R.id.list);
        historical = findViewById(R.id.historical);



        dbHelper = new databaseHelper(MainActivity.this);
        MyListEvent = dbHelper.getAllEvent();

        customBaseAdapter = new CustomBaseAdapter(MainActivity.this,MyListEvent);
        listEvent.setAdapter(customBaseAdapter);
        listEvent.setClickable(true);




        listEvent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent(MainActivity.this,MainActivity3.class);
                Event ev = MyListEvent.get(i);
                intent.putExtra("event", (Serializable) ev);
                startActivity(intent);
            }
        });

        fab.setOnClickListener((View v) -> {
            openDialog();
        });

        historical.setOnClickListener((View v) -> {
            Intent intent =new Intent(MainActivity.this,historical.class);
            startActivity(intent);
        });

    }


    public void openDialog(){
        EventDialog eventDialog =new EventDialog();
        eventDialog.show(getSupportFragmentManager(),"Event Name");
    }

    @Override
    public void apply(Event event) {
        dbHelper.addEvent(event);
        MyListEvent.clear();
        MyListEvent.addAll(dbHelper.getAllEvent());
        customBaseAdapter.notifyDataSetChanged();
        listEvent.invalidateViews();
        listEvent.refreshDrawableState();
        Toast.makeText(MainActivity.this, event.getName()+" is Created !!", Toast.LENGTH_SHORT).show();

    }


}