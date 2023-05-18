package com.example.fitlog;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddExerciseActivity extends AppCompatActivity {

    private String date;
    private Exercise exercise;
    private RecyclerView recyclerView;
    private TextView txtWeight;
    private TextView txtReps;
    private ArrayList<Sets> sets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        date = getIntent().getStringExtra("Date");
        exercise = getIntent().getParcelableExtra("Exercise");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.exerciseListRecView);
        TextView txtExerciseName = findViewById(R.id.txtExerciseEdit);
        txtExerciseName.setText(exercise.getName());

        txtWeight = findViewById(R.id.editTxtWeight);
        Button weightIncrease = findViewById(R.id.weightIncreaseBtn);
        Button weightDecrease = findViewById(R.id.weightDecreaseBtn);

        txtReps = findViewById(R.id.editTxtReps);
        Button repsIncrease = findViewById(R.id.repsIncreaseBtn);
        Button repsDecrease = findViewById(R.id.repsDecreaseBtn);

        Button saveBtn = findViewById(R.id.saveBtn);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users").
                child(SplashActivity.installationId).child("WorkoutLogs");
        sets = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CardTextRecViewAdapter(sets));

        mDatabase.child(date).child("exercises").orderByChild("name").equalTo(exercise.getName()).
                addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot snapshot1 : snapshot.child("sets").getChildren()) {
                        Sets set = snapshot1.getValue(Sets.class);
                        sets.add(set);
                    }
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        weightIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float weight = Float.parseFloat(txtWeight.getText().toString());
                weight += 0.5f;
                txtWeight.setText(String.valueOf(weight));
            }
        });

        weightDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float weight = Float.parseFloat(txtWeight.getText().toString());
                if(weight > 0){
                    weight -= 0.5f;
                    txtWeight.setText(String.valueOf(weight));
                }
            }
        });

        repsIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int reps = Integer.parseInt(txtReps.getText().toString());
                reps++;
                txtReps.setText(String.valueOf(reps));
            }
        });

        repsDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int reps = Integer.parseInt(txtReps.getText().toString());
                if(reps > 0) {
                    reps--;
                    txtReps.setText(String.valueOf(reps));
                }
            }
        });

        txtWeight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String weightText = txtWeight.getText().toString();
                    if (weightText.isEmpty()) {
                        txtWeight.setText("0.0");
                    }
                }
            }
        });

        txtReps.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String repsText = txtReps.getText().toString();
                    if (repsText.isEmpty()) {
                        txtReps.setText("0");
                    }
                }
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txtWeight.getText().toString().equals("") || !txtReps.getText().toString().equals("")) {
                    if(!txtWeight.getText().toString().equals("0.0") || !txtReps.getText().toString().equals("0")){
                        String weight = txtWeight.getText().toString();
                        String reps = txtReps.getText().toString();
                        Sets set = new Sets(weight, reps);
                        sets.add(set);
                        recyclerView.getAdapter().notifyDataSetChanged();

                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users").
                                child(SplashActivity.installationId).child("WorkoutLogs");
                        mDatabase.child(date).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        // if workoutlog exists
                                        if(snapshot.exists()){
                                            boolean exerciseExists = false;
                                            // check if exercise exists
                                            for(DataSnapshot snapshot1 : snapshot.child("exercises").getChildren()){
                                                if(snapshot1.child("name").getValue().equals(exercise.getName())){
                                                    // if exercise exists add set to exercise
                                                    DatabaseReference setsRef = snapshot1.child("sets").getRef();
                                                    long nextSet = snapshot1.child("sets").getChildrenCount();
                                                    setsRef.child(String.valueOf(nextSet)).setValue(set);
                                                    exerciseExists = true;
                                                    break;
                                                }
                                            }
                                            if(!exerciseExists){
                                                // if exercise does not exist, add exercise
                                                DatabaseReference exerciseRef = snapshot.child("exercises").getRef();
                                                long nextExercise = snapshot.child("exercises").getChildrenCount();
                                                Exercise ex = new Exercise(exercise.getName(), sets);
                                                exerciseRef.child(String.valueOf(nextExercise)).setValue(ex);
                                            }
                                        }else{
                                            // if workoutlog does not exist, create workoutlog
                                            Exercise ex = new Exercise(exercise.getName(), sets);
                                            ArrayList<Exercise> exercises = new ArrayList<>();
                                            exercises.add(ex);
                                            WorkoutLog workoutLog = new WorkoutLog(date, exercises);
                                            mDatabase.child(date).setValue(workoutLog);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                    }
                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.simple_menu, menu);
        return true;
    }
}