package com.example.fitlog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CardRecViewAdapter extends RecyclerView.Adapter<CardRecViewAdapter.ViewHolder> {

    private ArrayList<Exercise> exercises;
    private Context context;

    public CardRecViewAdapter(ArrayList<Exercise> exercises, Context context) {
        this.exercises = exercises;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.txtExercise.setText(exercise.getName());
        holder.recyclerView.setAdapter(new CardTextRecViewAdapter(exercise.getSets()));
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtExercise;
        public RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtExercise = itemView.findViewById(R.id.txtExercise);
            recyclerView = itemView.findViewById(R.id.cardRecView);
        }
    }
}
