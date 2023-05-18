package com.example.fitlog;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExerciseRecViewAdapter extends RecyclerView.Adapter<ExerciseRecViewAdapter.ViewHolder>{

    private ArrayList<Exercise> exercises;
    private OnExerciseClickListener listener;

    public ExerciseRecViewAdapter(ArrayList<Exercise> exercises, OnExerciseClickListener listener) {
        this.exercises = exercises;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Exercise exercise = exercises.get(position);
        holder.txtExerciseName.setText(exercise.getName());

        holder.exerciseCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(exercises.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtExerciseName;
        public CardView exerciseCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtExerciseName = itemView.findViewById(R.id.txtExerciseName);
            exerciseCard = itemView.findViewById(R.id.exerciseCard);
        }
    }
}
