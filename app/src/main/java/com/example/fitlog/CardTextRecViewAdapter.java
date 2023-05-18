package com.example.fitlog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CardTextRecViewAdapter extends RecyclerView.Adapter<CardTextRecViewAdapter.ViewHolder> {

    private ArrayList<Sets> sets;

    public CardTextRecViewAdapter(ArrayList<Sets> sets) {
        this.sets = sets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_sets_weight_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // for each exercise in the list it will display for both exercises
        String weight = holder.itemView.getContext().getString(R.string.weight, sets.get(position).getWeight());
        String reps = holder.itemView.getContext().getString(R.string.reps, sets.get(position).getReps());
        holder.txtWeight.setText(weight);
        holder.txtReps.setText(reps);
    }

    @Override
    public int getItemCount() {
        return sets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtExercise;
        public TextView txtWeight;
        public TextView txtReps;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtExercise = itemView.findViewById(R.id.txtExercise);
            txtWeight = itemView.findViewById(R.id.txtWeight);
            txtReps = itemView.findViewById(R.id.txtReps);
        }
    }
}
