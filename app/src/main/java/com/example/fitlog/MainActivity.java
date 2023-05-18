package com.example.fitlog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private ArrayList<PageFragment> fragments;
    private final String installationId = SplashActivity.installationId;
    private final DatabaseReference mDatabase = FirebaseDatabase.getInstance()
            .getReference("Users").child(installationId).child("WorkoutLogs");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager2 = findViewById(R.id.viewPager2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_musclegroup_activity();
            }
        });
        setSupportActionBar(toolbar);

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Users");
        db.child(installationId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    db.child(installationId).child("WorkoutLogs").setValue("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fragments = new ArrayList<>();

        viewPager2.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragments.get(position);
            }

            @Override
            public int getItemCount() {
                return fragments.size();
            }
        });
    }

    private void displayData(){
        fragments.clear();
        final int totalQueries = 7;
        final int[] queriesCompleted = {0};

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        for(int i = 0; i < totalQueries; i++){
            Calendar cal = Calendar.getInstance(Locale.getDefault());
            cal.setFirstDayOfWeek(Calendar.MONDAY);
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            cal.add(Calendar.DATE, i);

            String dateString = dateFormat.format(cal.getTime());

            mDatabase.child(dateString).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        WorkoutLog workoutLog = snapshot.getValue(WorkoutLog.class);

                        PageFragment fragment = new PageFragment(workoutLog, cal.getTime(), MainActivity.this);
                        fragments.add(fragment);
                    }else{
                        PageFragment fragment = new PageFragment(new WorkoutLog(dateString, new ArrayList<>()), cal.getTime(), MainActivity.this);
                        fragments.add(fragment);
                    }
                    queriesCompleted[0]++;

                    if(queriesCompleted[0] == totalQueries){
                        fragments.sort(new Comparator<PageFragment>() {
                            @Override
                            public int compare(PageFragment fragment1, PageFragment fragment2) {
                                return fragment1.getDate().compareTo(fragment2.getDate());
                            }
                        });
                        viewPager2.getAdapter().notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        displayData();
        viewPager2.getAdapter().notifyDataSetChanged();
        Calendar calendar = Calendar.getInstance();
        switch(calendar.get(Calendar.DAY_OF_WEEK)){
            case Calendar.MONDAY:
                viewPager2.setCurrentItem(0);
                break;
            case Calendar.TUESDAY:
                viewPager2.setCurrentItem(1);
                break;
            case Calendar.WEDNESDAY:
                viewPager2.setCurrentItem(2);
                break;
            case Calendar.THURSDAY:
                viewPager2.setCurrentItem(3);
                break;
            case Calendar.FRIDAY:
                viewPager2.setCurrentItem(4);
                break;
            case Calendar.SATURDAY:
                viewPager2.setCurrentItem(5);
                break;
            case Calendar.SUNDAY:
                viewPager2.setCurrentItem(6);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_guide:
                Intent guideIntent = new Intent(this, AppGuideActivity.class);
                startActivity(guideIntent);
                return true;

            case R.id.app_bar_share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "I worked out today!");
                startActivity(Intent.createChooser(shareIntent, "Share"));
                return true;

            case R.id.app_bar_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void open_musclegroup_activity(){
        WorkoutLog workoutLog = fragments.get(viewPager2.getCurrentItem()).getWorkoutLog();
        Intent intent = new Intent(this, MuscleGroupActivity.class);
        intent.putExtra("Workout", workoutLog);
        startActivity(intent);
    }
}