package com.example.dodolist.ui.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dodolist.MainActivity;
import com.example.dodolist.R;
import com.example.dodolist.model.Note;
import com.example.dodolist.ui.AddNote;
import com.example.dodolist.ui.schedule.ScheduleFragment;
import com.example.dodolist.ui.today.AdapterToday;
import com.example.dodolist.ui.today.TodayFragment;
import com.example.dodolist.ui.today.TodayViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;
import java.util.List;

public class DateFragment extends AppCompatActivity {

    CalendarView calendarView;
    private AppBarConfiguration mAppBarConfiguration;
    private RecyclerView recyclerView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_date);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);
        setTitle(getString(R.string.menu_gallery));
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        MenuItem item = bottomNav.getMenu().findItem(R.id.calendar);
        item.setChecked(true);



        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year,
                                            int month, int dayOfMonth) {
                int mYear = year;
                int mMonth = month;
                int mDay = dayOfMonth;
                String selectedDate = new StringBuilder().append(mMonth + 1)
                        .append("-").append(mDay).append("-").append(mYear)
                        .append(" ").toString();


                Calendar date = Calendar.getInstance();
                date.set(Calendar.HOUR_OF_DAY, 0);
                date.set(Calendar.MINUTE, 0);
                date.set(Calendar.SECOND, 0);
                date.set(Calendar.MILLISECOND, 0);
                date.set(Calendar.YEAR,year);
                date.set(Calendar.MONTH,month);
                date.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                recyclerView = findViewById(R.id.list_date1);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DateFragment.this, RecyclerView.VERTICAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setItemAnimator(null);
                //recyclerView.addItemDecoration(new DividerItemDecoration(DateFragment.this, 0));

                final AdapterToday adapter = new AdapterToday();
                recyclerView.setAdapter(adapter);

                DateViewModel mainViewModel = ViewModelProviders.of(DateFragment.this).get(DateViewModel.class);
                mainViewModel.getNodeLiveData(date.getTimeInMillis()).observe(DateFragment.this, new Observer<List<Note>>() {
                    @Override
                    public void onChanged(List<Note> notes) {
                        adapter.setItems(notes);
                        /*if(adapter.getItemCount() == 0)
                            nonotes.setVisibility(View.VISIBLE);
                        else
                            nonotes.setVisibility(View.INVISIBLE);*/
                    }
                });

                /*Intent intent1 = new Intent(DateFragment.this, DateActivity.class);
                intent1.putExtra("Calendar.EXTRA_Date", date.getTimeInMillis());
                startActivity(intent1);*/
                //Toast.makeText(getApplicationContext(), selectedDate, Toast.LENGTH_LONG).show();
            }
        });


        //GalleryViewModel galleryViewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);

        /*final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addbuttontoolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            /*case android.R.id.home:
                finish();
                break;*/
            case R.id.add_note_toolbar:
                AddNote.start(this,null);
        }
        return super.onOptionsItemSelected(item);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.schedule:
                    finish();
                    Intent intent1 = new Intent(DateFragment.this, ScheduleFragment.class);
                    startActivity(intent1);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    return true;
                case R.id.tasks:
                    finish();
                    Intent intent2 = new Intent(DateFragment.this, TodayFragment.class);
                    startActivity(intent2);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    return true;
                case R.id.calendar:
                    finish();
                    Intent intent3 = new Intent(DateFragment.this, DateFragment.class);
                    startActivity(intent3);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    return true;
            }

            return true;
        }
    };


}

