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

public class MuscleGroupAdapter extends RecyclerView.Adapter<MuscleGroupAdapter.ViewHolder> {

    private ArrayList<MuscleGroup> muscleGroups;
    private OnMuscleGroupClickListener listener;

    public MuscleGroupAdapter(ArrayList<MuscleGroup> muscleGroups, OnMuscleGroupClickListener listener) {
        this.muscleGroups = muscleGroups;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.muscle_group_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        MuscleGroup muscleGroup = muscleGroups.get(position);
        holder.txtMuscleGroup.setText(muscleGroup.getName());

        holder.muscleGroupCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(muscleGroups.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return muscleGroups.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtMuscleGroup;
        public CardView muscleGroupCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMuscleGroup = itemView.findViewById(R.id.txtMuscleGroup);
            muscleGroupCard = itemView.findViewById(R.id.muscleGroupCard);
        }
    }
}
