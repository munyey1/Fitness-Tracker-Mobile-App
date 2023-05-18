package com.example.fitlog;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class WorkoutLog implements Parcelable {

    private String date;
    private ArrayList<Exercise> exercises;

    public WorkoutLog() {
    }

    public WorkoutLog(String date, ArrayList<Exercise> exercises) {
        this.date = date;
        this.exercises = exercises;
    }

    protected WorkoutLog(Parcel in) {
        date = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(date);
    }

    public static final Creator<WorkoutLog> CREATOR = new Creator<WorkoutLog>() {
        @Override
        public WorkoutLog createFromParcel(Parcel in) {
            return new WorkoutLog(in);
        }

        @Override
        public WorkoutLog[] newArray(int size) {
            return new WorkoutLog[size];
        }
    };

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Exercise> getExercises() {
        return exercises == null ? new ArrayList<>() : exercises;
    }

    public void addExercises(Exercise exercise){
        exercises.add(exercise);
    }

    public void setExercises(ArrayList<Exercise> exercises) {
        this.exercises = exercises;
    }

}
