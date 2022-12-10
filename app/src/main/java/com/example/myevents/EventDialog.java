package com.example.myevents;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import domain.Event;

public class EventDialog extends AppCompatDialogFragment{

    EditText name;
    EventDialogListener eventDialogListener;
    Event event;
    Date dateEvent;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

       event = new Event();
       dateEvent=new Date();


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.name_event_dialog,null);
        builder.setView(view);
        builder.setTitle("Event Name");
        builder.setPositiveButton("Create Event", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(name.getText().toString() == null || name.getText().toString() =="" ){

                    eventDialogListener.error("Invalide Event name");
                }else {

                    event.setName(name.getText().toString());

                    final Calendar c = Calendar.getInstance();
                    int mHour = c.get(Calendar.HOUR_OF_DAY);
                    int mMinute = c.get(Calendar.MINUTE);
                    int mYear = c.get(Calendar.YEAR);
                    int mMonth = c.get(Calendar.MONTH);
                    int mDay = c.get(Calendar.DAY_OF_MONTH);

                    TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {

                                    dateEvent.setHours(hourOfDay);
                                    dateEvent.setMinutes(minute);
                                    dateEvent.setSeconds(0);
                                    event.setId(new Random().nextInt());
                                    event.setStatus(1);
                                    event.setDateEvent(dateEvent);
                                    event.setDateCreate(new Date());
                                    eventDialogListener.apply(event);
                                }
                            }, mHour, mMinute, false);
                    timePickerDialog.show();


                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    dateEvent.setMonth(monthOfYear);
                                    dateEvent.setYear(year-1900);
                                    dateEvent.setDate(dayOfMonth);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        name= view.findViewById(R.id.name);

        return builder.create();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            eventDialogListener= (EventDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context +"must implement");
        }

    }

    public interface EventDialogListener {
        void apply(Event event);
        void error(String msg);
    }

}
