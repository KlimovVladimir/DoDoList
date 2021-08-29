package com.example.dodolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.dodolist.ui.calendar.DateActivity;
import com.example.dodolist.ui.calendar.DateFragment;
import com.example.dodolist.ui.schedule.ScheduleFragment;
import com.example.dodolist.ui.today.TodayFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public Activity act = this;
    Button today;
    Button calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        //BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        //bottomNav.setOnNavigationItemSelectedListener(navListener);

        /*Thread welcomeThread = new Thread() {
            @Override
            public void run() {
                try {
                    super.run();
                    sleep(500);
                } catch (Exception e) {

                } finally {
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                    Intent intent1 = new Intent(MainActivity.this, ScheduleFragment.class);
                    startActivity(intent1);
                }
            }
        };
        welcomeThread.start();*/

        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                finish();
                Intent intent1 = new Intent(MainActivity.this, TodayFragment.class);
                startActivity(intent1);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        };
        handler.postDelayed(r, 500);

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.schedule:
                    finish();
                    Intent intent1 = new Intent(MainActivity.this, ScheduleFragment.class);
                    startActivity(intent1);
                    return true;
                case R.id.tasks:
                    finish();
                    Intent intent2 = new Intent(MainActivity.this, TodayFragment.class);
                    startActivity(intent2);
                    return true;
                case R.id.calendar:
                    finish();
                    Intent intent3 = new Intent(MainActivity.this, DateFragment.class);
                    startActivity(intent3);
                    return true;
            }

            return true;
        }
    };
}