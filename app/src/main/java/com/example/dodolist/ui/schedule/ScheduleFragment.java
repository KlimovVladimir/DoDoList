package com.example.dodolist.ui.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dodolist.R;
import com.example.dodolist.model.Schedule;
import com.example.dodolist.ui.calendar.DateFragment;
import com.example.dodolist.ui.today.TodayFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class ScheduleFragment extends AppCompatActivity {

    private ScheduleViewModel todayViewModel;
    private RecyclerView recyclerViewMonday;
    private RecyclerView recyclerViewTuesday;
    private RecyclerView recyclerViewWednesday;
    private RecyclerView recyclerViewThursday;
    private RecyclerView recyclerViewFriday;
    private RecyclerView recyclerViewSaturday;
    private TextView textWeek;
    private TextView nosubj_date1;
    private TextView nosubj_date2;
    private TextView nosubj_date3;
    private TextView nosubj_date4;
    private TextView nosubj_date5;
    private TextView nosubj_date6;
    //private TextView textCurrentDate;
    private static final String EXTRA_WEEK = "ScheduleFragment.EXTRA_WEEK";
    private int weeknum;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_schedule);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);
        setTitle(getString(R.string.schedule));
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        MenuItem item = bottomNav.getMenu().findItem(R.id.schedule);
        item.setChecked(true);
        textWeek = findViewById(R.id.text_week);
        nosubj_date1 = findViewById(R.id.nosubj_date1);
        nosubj_date2 = findViewById(R.id.nosubj_date2);
        nosubj_date3 = findViewById(R.id.nosubj_date3);
        nosubj_date4 = findViewById(R.id.nosubj_date4);
        nosubj_date5 = findViewById(R.id.nosubj_date5);
        nosubj_date6 = findViewById(R.id.nosubj_date6);

        if(getIntent().hasExtra(EXTRA_WEEK)) {
            weeknum = getIntent().getIntExtra(EXTRA_WEEK, 1);
        }
        else {
            weeknum = 1;
        }

        if (weeknum == 1)
            textWeek.setText(R.string.change_week1);
        else if (weeknum == 2)
            textWeek.setText(R.string.change_week2);

        todayViewModel =
                new ViewModelProvider(this).get(ScheduleViewModel.class);

        recyclerViewMonday = findViewById(R.id.list_monday);
        LinearLayoutManager linearLayoutManagerMonday = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerViewMonday.setLayoutManager(linearLayoutManagerMonday);
        recyclerViewMonday.addItemDecoration(new DividerItemDecoration(this,0));

        AdapterSchedule adatperTodayMonday = new AdapterSchedule();
        recyclerViewMonday.setAdapter(adatperTodayMonday);

        ScheduleViewModel todayViewModelMonday = ViewModelProviders.of(this).get(ScheduleViewModel.class);
        todayViewModelMonday.getNodeLiveData(0, weeknum).observe(this, new Observer<List<Schedule>>() {
            @Override
            public void onChanged(List<Schedule> notes) {
                adatperTodayMonday.setItems(notes);
                if(adatperTodayMonday.getItemCount() == 0) {
                    nosubj_date1.setVisibility(View.VISIBLE);
                    recyclerViewMonday.setVisibility(View.GONE);
                }
                else {
                    nosubj_date1.setVisibility(View.GONE);
                    recyclerViewMonday.setVisibility(View.VISIBLE);
                }
            }
        });

        recyclerViewTuesday = findViewById(R.id.list_tuesday);
        LinearLayoutManager linearLayoutManagerTuesday = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerViewTuesday.setLayoutManager(linearLayoutManagerTuesday);
        recyclerViewTuesday.addItemDecoration(new DividerItemDecoration(this,0));

        AdapterSchedule adatperTodayTuesday = new AdapterSchedule();
        recyclerViewTuesday.setAdapter(adatperTodayTuesday);

        ScheduleViewModel todayViewModelTuesday = ViewModelProviders.of(this).get(ScheduleViewModel.class);
        todayViewModelTuesday.getNodeLiveData(1, weeknum).observe(this, new Observer<List<Schedule>>() {
            @Override
            public void onChanged(List<Schedule> notes) {
                adatperTodayTuesday.setItems(notes);
                if(adatperTodayTuesday.getItemCount() == 0) {
                    nosubj_date2.setVisibility(View.VISIBLE);
                    recyclerViewTuesday.setVisibility(View.GONE);
                }
                else {
                    nosubj_date2.setVisibility(View.GONE);
                    recyclerViewTuesday.setVisibility(View.VISIBLE);
                }
            }
        });

        recyclerViewWednesday = findViewById(R.id.list_wednesday);
        LinearLayoutManager linearLayoutManagerWednesday = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerViewWednesday.setLayoutManager(linearLayoutManagerWednesday);
        recyclerViewWednesday.addItemDecoration(new DividerItemDecoration(this,0));

        AdapterSchedule adatperTodayWednesday = new AdapterSchedule();
        recyclerViewWednesday.setAdapter(adatperTodayWednesday);

        ScheduleViewModel todayViewModelWednesday = ViewModelProviders.of(this).get(ScheduleViewModel.class);
        todayViewModelWednesday.getNodeLiveData(2, weeknum).observe(this, new Observer<List<Schedule>>() {
            @Override
            public void onChanged(List<Schedule> notes) {
                adatperTodayWednesday.setItems(notes);
                if(adatperTodayWednesday.getItemCount() == 0) {
                    nosubj_date3.setVisibility(View.VISIBLE);
                    recyclerViewWednesday.setVisibility(View.GONE);
                }
                else {
                    nosubj_date3.setVisibility(View.GONE);
                    recyclerViewWednesday.setVisibility(View.VISIBLE);
                }
            }
        });

        recyclerViewThursday = findViewById(R.id.list_thursday);
        LinearLayoutManager linearLayoutManagerThursday = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerViewThursday.setLayoutManager(linearLayoutManagerThursday);
        recyclerViewThursday.addItemDecoration(new DividerItemDecoration(this,0));

        AdapterSchedule adatperTodayThursday = new AdapterSchedule();
        recyclerViewThursday.setAdapter(adatperTodayThursday);

        ScheduleViewModel todayViewModelThursday = ViewModelProviders.of(this).get(ScheduleViewModel.class);
        todayViewModelThursday.getNodeLiveData(3, weeknum).observe(this, new Observer<List<Schedule>>() {
            @Override
            public void onChanged(List<Schedule> notes) {
                adatperTodayThursday.setItems(notes);
                if(adatperTodayThursday.getItemCount() == 0) {
                    nosubj_date4.setVisibility(View.VISIBLE);
                    recyclerViewThursday.setVisibility(View.GONE);
                }
                else {
                    nosubj_date4.setVisibility(View.GONE);
                    recyclerViewThursday.setVisibility(View.VISIBLE);
                }
            }
        });

        recyclerViewFriday = findViewById(R.id.list_friday);
        LinearLayoutManager linearLayoutManagerFriday = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerViewFriday.setLayoutManager(linearLayoutManagerFriday);
        recyclerViewFriday.addItemDecoration(new DividerItemDecoration(this,0));

        AdapterSchedule adatperTodayFriday = new AdapterSchedule();
        recyclerViewFriday.setAdapter(adatperTodayFriday);

        ScheduleViewModel todayViewModelFriday = ViewModelProviders.of(this).get(ScheduleViewModel.class);
        todayViewModel.getNodeLiveData(4, weeknum).observe(this, new Observer<List<Schedule>>() {
            @Override
            public void onChanged(List<Schedule> notes) {
                adatperTodayFriday.setItems(notes);
                if(adatperTodayFriday.getItemCount() == 0) {
                    nosubj_date5.setVisibility(View.VISIBLE);
                    recyclerViewFriday.setVisibility(View.GONE);
                }
                else {
                    nosubj_date5.setVisibility(View.GONE);
                    recyclerViewFriday.setVisibility(View.VISIBLE);
                }
            }
        });

        recyclerViewSaturday = findViewById(R.id.list_saturday);
        LinearLayoutManager linearLayoutManagerSaturday = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerViewSaturday.setLayoutManager(linearLayoutManagerSaturday);
        recyclerViewSaturday.addItemDecoration(new DividerItemDecoration(this,0));

        AdapterSchedule adatperTodaySaturday = new AdapterSchedule();
        recyclerViewSaturday.setAdapter(adatperTodaySaturday);

        ScheduleViewModel todayViewModelSaturday = ViewModelProviders.of(this).get(ScheduleViewModel.class);
        todayViewModel.getNodeLiveData(5, weeknum).observe(this, new Observer<List<Schedule>>() {
            @Override
            public void onChanged(List<Schedule> notes) {
                adatperTodaySaturday.setItems(notes);
                if(adatperTodaySaturday.getItemCount() == 0) {
                    nosubj_date6.setVisibility(View.VISIBLE);
                    recyclerViewSaturday.setVisibility(View.GONE);
                }
                else {
                    nosubj_date6.setVisibility(View.GONE);
                    recyclerViewSaturday.setVisibility(View.VISIBLE);
                }
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.weekbutton, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            /*case android.R.id.home:
                finish();
                break;*/
            case R.id.add_note_toolbar:
                AddSchedule.start(this,null, weeknum);
                break;
            case R.id.downloadSchedule:
                Intent intent3 = new Intent(this, SelectGroup.class);
                startActivity(intent3);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.change_week1:
                finish();
                Intent intent1 = new Intent(ScheduleFragment.this, ScheduleFragment.class);
                intent1.putExtra(EXTRA_WEEK, 1);
                startActivity(intent1);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.change_week2:
                finish();
                Intent intent2 = new Intent(ScheduleFragment.this, ScheduleFragment.class);
                intent2.putExtra(EXTRA_WEEK, 2);
                startActivity(intent2);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.schedule:
                    finish();
                    Intent intent1 = new Intent(ScheduleFragment.this, ScheduleFragment.class);
                    startActivity(intent1);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    return true;
                case R.id.tasks:
                    finish();
                    Intent intent2 = new Intent(ScheduleFragment.this, TodayFragment.class);
                    startActivity(intent2);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    return true;
                case R.id.calendar:
                    finish();
                    Intent intent3 = new Intent(ScheduleFragment.this, DateFragment.class);
                    startActivity(intent3);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    return true;
            }

            return true;
        }
    };
}