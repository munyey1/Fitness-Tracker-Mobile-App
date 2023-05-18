package com.example.fitlog;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class MuscleGroup implements Parcelable {

    private ArrayList<Exercise> exercises;
    private String name;

    public MuscleGroup() {
    }

    public MuscleGroup(String name, ArrayList<Exercise> exercises) {
        this.name = name;
        this.exercises = exercises;
    }

    protected MuscleGroup(Parcel in) {
        name = in.readString();
    }

    public static final Creator<MuscleGroup> CREATOR = new Creator<MuscleGroup>() {
        @Override
        public MuscleGroup createFromParcel(Parcel in) {
            return new MuscleGroup(in);
        }

        @Override
        public MuscleGroup[] newArray(int size) {
            return new MuscleGroup[size];
        }
    };

    public ArrayList<Exercise> getExercises() {
        return exercises == null ? new ArrayList<>() : exercises;
    }

    public void setExercises(ArrayList<Exercise> exercises) {
        this.exercises = exercises;
    }

    public void addExercises(Exercise exercise) {
        if(exercises == null){
            exercises = new ArrayList<>();
        }
        exercises.add(exercise);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
    }
}
