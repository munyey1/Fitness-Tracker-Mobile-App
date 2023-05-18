package com.example.fitlog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.installations.FirebaseInstallations;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    public static String installationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseInstallations.getInstance().getId()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (task.isSuccessful()) {
                            installationId = task.getResult();
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            intent.putExtra("InstallationId", installationId);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SplashActivity.this, "Firebase Installation Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}