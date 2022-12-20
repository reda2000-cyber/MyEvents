package com.example.myevents;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import domain.Event;

public class MainActivity3 extends AppCompatActivity implements EventDialog.EventDialogListener {

    TextView name, day, hour, min, sec, date;
    Button modify, delete;
    Event event;
    Date dateBegin, dateEnd;
    Long millis;
    private NotificationHelper mNotificationHelper;
    static CountDownTimer countDownTimer = null;
    databaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        day = findViewById(R.id.event_day);
        hour = findViewById(R.id.event_hour);
        min = findViewById(R.id.event_min);
        date = findViewById(R.id.event_date);
        sec = findViewById(R.id.event_second);
        name = findViewById(R.id.event_name);
        modify = findViewById(R.id.modify);
        delete = findViewById(R.id.delete);
        mNotificationHelper = new NotificationHelper(this);

        dbHelper = new databaseHelper(MainActivity3.this);

        Intent intent = this.getIntent();

        if (intent != null) {

            event = (Event) intent.getSerializableExtra("event");

            dateBegin = event.getDateCreate();
            dateEnd = event.getDateEvent();
            name.setText(event.getName());
            date.setText("Countdown will finish on " + event.getDateEvent().toString());
            millis = dateEnd.getTime() - dateBegin.getTime();
            countDownTimer=new CountDownTimer(millis, 1000) {

                @Override
                public void onTick(long l) {
                    Long days = TimeUnit.MILLISECONDS.toDays(l);
                    Long hours = TimeUnit.MILLISECONDS.toHours(l) - TimeUnit.MILLISECONDS.toHours(TimeUnit.DAYS.toMillis(days));
                    Long minutes = TimeUnit.MILLISECONDS.toMinutes(l) - TimeUnit.MILLISECONDS.toMinutes(TimeUnit.HOURS.toMillis(hours) + TimeUnit.DAYS.toMillis(days));
                    Long seconds = TimeUnit.MILLISECONDS.toSeconds(l) - TimeUnit.MILLISECONDS.toSeconds(TimeUnit.MINUTES.toMillis(minutes) + TimeUnit.HOURS.toMillis(hours) + TimeUnit.DAYS.toMillis(days));
                    day.setText(days.toString());
                    hour.setText(hours.toString());
                    min.setText(minutes.toString());
                    sec.setText(seconds.toString());
                }

                @Override
                public void onFinish() {
                    dbHelper.historicalEvent(event);
                    sendOnChannel();
                }
            }.start();
        }

        modify.setOnClickListener((View v) -> {
            openDialog();
        });

        delete.setOnClickListener((View v) -> {
            dbHelper.deleteEvent(event);
            this.onBackPressed();
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainActivity3.this,MainActivity.class);
        startActivity(intent);
    }

    public void sendOnChannel() {
        NotificationCompat.Builder nb = mNotificationHelper.getChannelNotification("CountDown", "CountDown for your event : " + event.getName()+" has finished !");
        mNotificationHelper.getManager().notify(1, nb.build());
    }


    public void openDialog() {
        EventDialog eventDialog = new EventDialog();
        eventDialog.show(getSupportFragmentManager(), "Event Name");
    }

    @Override
    public void apply(Event event2) {

        dbHelper.updateEvent(event, event2);

        countDownTimer.cancel();
        name.setText(event2.getName());
        dateBegin = event2.getDateCreate();
        dateEnd = event2.getDateEvent();
        name.setText(event2.getName());
        date.setText("Countdown will finish on " + event2.getDateEvent().toString());
        millis = dateEnd.getTime() - dateBegin.getTime();

        new CountDownTimer(millis, 1000) {

            @Override
            public void onTick(long l) {
                Long days = TimeUnit.MILLISECONDS.toDays(l);
                Long hours = TimeUnit.MILLISECONDS.toHours(l) - TimeUnit.MILLISECONDS.toHours(TimeUnit.DAYS.toMillis(days));
                Long minutes = TimeUnit.MILLISECONDS.toMinutes(l) - TimeUnit.MILLISECONDS.toMinutes(TimeUnit.HOURS.toMillis(hours) + TimeUnit.DAYS.toMillis(days));
                Long seconds = TimeUnit.MILLISECONDS.toSeconds(l) - TimeUnit.MILLISECONDS.toSeconds(TimeUnit.MINUTES.toMillis(minutes) + TimeUnit.HOURS.toMillis(hours) + TimeUnit.DAYS.toMillis(days));
                day.setText(days.toString());
                hour.setText(hours.toString());
                min.setText(minutes.toString());
                sec.setText(seconds.toString());
            }

            @Override
            public void onFinish() {
                dbHelper.historicalEvent(event);
                sendOnChannel();
            }
        }.start();
    }


}