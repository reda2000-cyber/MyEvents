package com.example.myevents;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import domain.Event;

public class CustomHistoricalBaseAdapter extends ArrayAdapter<Event> {



    public CustomHistoricalBaseAdapter(@NonNull Context context, @NonNull ArrayList<Event> objects) {
        super(context, R.layout.historical_list, objects);
    }

    @Override
    public View getView(int i,@NonNull View view, @NonNull ViewGroup viewGroup) {

        view = LayoutInflater.from(getContext()).inflate(R.layout.historical_list,viewGroup,false);

        Event ev = getItem(i);

        TextView name=view.findViewById(R.id.event_name);
        TextView date=view.findViewById(R.id.event_date);
        TextView time=view.findViewById(R.id.event_time);

        name.setText(ev.getName());
        date.setText(ev.getDateEvent().getDate()+"/"+ev.getDateEvent().getMonth()+"/"+(ev.getDateEvent().getYear()+1900));
        time.setText(ev.getDateEvent().getHours()+" : "+ev.getDateEvent().getMinutes());

        return view;

    }
}
