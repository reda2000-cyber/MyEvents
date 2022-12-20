package com.example.myevents;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import domain.Event;

public class CustomBaseAdapter extends ArrayAdapter<Event> {

    private long millis;
    private Date dateBegin,dateEnd;


    public CustomBaseAdapter(@NonNull Context context, @NonNull ArrayList<Event> objects) {
        super(context, R.layout.activity_coustum_event_list, objects);
    }


    @Override
    public View getView(int i,@NonNull View view, @NonNull ViewGroup viewGroup) {

        view = LayoutInflater.from(getContext()).inflate(R.layout.activity_coustum_event_list,viewGroup,false);

        Event ev = getItem(i);

        TextView name=view.findViewById(R.id.event_name);
        TextView day=view.findViewById(R.id.event_day);
        TextView hour=view.findViewById(R.id.event_hour);
        TextView min=view.findViewById(R.id.event_min);
        TextView sec=view.findViewById(R.id.event_second);
        TextView date=view.findViewById(R.id.event_date);

        dateBegin = ev.getDateCreate();
        dateEnd = ev.getDateEvent();
        name.setText(ev.getName());
        date.setText(ev.getDateEvent().getDate()+" / "+(ev.getDateEvent().getMonth()+1));
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


            }
        }.start();

        return view;

    }
}
