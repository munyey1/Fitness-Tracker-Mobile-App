package com.example.fitlog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MuscleGroupActivity extends AppCompatActivity implements OnMuscleGroupClickListener {

    private RecyclerView recyclerView;
    private ArrayList<MuscleGroup> muscleGroups;
    private WorkoutLog workoutLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musclegroup);
        workoutLog = getIntent().getParcelableExtra("Workout");

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Muscle Groups");

        Toolbar toolbar = findViewById(R.id.category_toolbar);
        recyclerView = findViewById(R.id.muscleGroupRecycler);
        setSupportActionBar(toolbar);

        muscleGroups = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MuscleGroupAdapter(muscleGroups, this));

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MuscleGroup muscleGroup = dataSnapshot.getValue(MuscleGroup.class);
                    muscleGroups.add(muscleGroup);
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.simple_menu, menu);
        return true;
    }

    @Override
    public void onItemClick(MuscleGroup muscleGroup) {
        open_exercise_activity(muscleGroup);
    }

    public void open_exercise_activity(MuscleGroup muscleGroup) {
        Intent intent = new Intent(this, ExerciseActivity.class);
        intent.putExtra("Workout", workoutLog);
        intent.putParcelableArrayListExtra("Exercises", muscleGroup.getExercises());
        startActivity(intent);
    }
}