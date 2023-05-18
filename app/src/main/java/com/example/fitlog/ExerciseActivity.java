package com.example.fitlog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import java.util.ArrayList;

public class ExerciseActivity extends AppCompatActivity implements OnExerciseClickListener{

    private WorkoutLog workoutLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        workoutLog = getIntent().getParcelableExtra("Workout");
        ArrayList<Exercise> exercises = getIntent().getParcelableArrayListExtra("Exercises");

        Toolbar toolbar = findViewById(R.id.toolbar);
        RecyclerView recyclerView = findViewById(R.id.exerciseRecView);
        setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ExerciseRecViewAdapter(exercises, this));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.simple_menu, menu);
        return true;
    }

    @Override
    public void onItemClick(Exercise exercise) {
        Intent intent = new Intent(this, AddExerciseActivity.class);
        intent.putExtra("Exercise", exercise);
        intent.putExtra("Date", workoutLog.getDate());
        startActivity(intent);
    }
}