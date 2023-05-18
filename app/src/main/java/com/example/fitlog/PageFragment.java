package com.example.fitlog;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.Locale;

public class PageFragment extends Fragment {

    private WorkoutLog workoutLog;
    private RecyclerView recyclerView;
    private Date date;
    private Context context;
    public TextView txtDate;
    public PageFragment(WorkoutLog workoutLog, Date date, Context context) {
        this.workoutLog = workoutLog;
        this.date = date;
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewpager_layout, container, false);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd MMMM", Locale.getDefault());

        txtDate = view.findViewById(R.id.txtDate);
        txtDate.setText(dateFormat.format(date));

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new CardRecViewAdapter(workoutLog.getExercises(), context));

        return view;
    }

    public Date getDate() {
        return date;
    }

    public WorkoutLog getWorkoutLog() {
        return workoutLog;
    }
}