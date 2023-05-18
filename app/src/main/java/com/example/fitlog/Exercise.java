package com.example.fitlog;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Exercise implements Parcelable {

    private String name;
    private ArrayList<Sets> sets;

    public Exercise() {
    }

    public Exercise(String name, ArrayList<Sets> sets) {
        this.name = name;
        this.sets = sets;
    }

    protected Exercise(Parcel in) {
        name = in.readString();
    }

    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Sets> getSets() {
        return sets == null ? new ArrayList<>() : sets;
    }

    public void setSets(ArrayList<Sets> sets) {
        this.sets = sets;
    }

    public void addSet(Sets set){
        if(sets == null){
            this.sets = new ArrayList<>();
        }
        sets.add(set);
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
