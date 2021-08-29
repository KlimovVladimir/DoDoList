package com.example.dodolist.ui.today;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.example.dodolist.MainActivity;
import com.example.dodolist.R;
import com.example.dodolist.model.Note;
import com.example.dodolist.ui.AddNote;
import com.example.dodolist.ui.calendar.DateFragment;
import com.example.dodolist.ui.schedule.ScheduleFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TodayFragment extends AppCompatActivity {

    private TodayViewModel todayViewModel;
    private RecyclerView recyclerView;
    private TextView textCurrentDate;
    private TextView progress_done, progress_all;
    private TextView nonotes;
    private static ProgressBar progressBar;

    public static void inc_progress() {
        progressBar.incrementProgressBy(1);
    }

    public static void dec_progress() {
        progressBar.incrementProgressBy(-1);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_today);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);
        setTitle(getString(R.string.menu_today));
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        MenuItem item = bottomNav.getMenu().findItem(R.id.tasks);
        item.setChecked(true);

        progressBar = findViewById(R.id.progressBar);
        progress_done = findViewById(R.id.progress_done);
        progress_all = findViewById(R.id.progress_all);

        todayViewModel =
                new ViewModelProvider(this).get(TodayViewModel.class);

        // recyclerView = (RecyclerView)getActivity().findViewById(R.id.list_today); //BADDDDD!!!!!!!!!!!!!
        recyclerView = findViewById(R.id.list_today);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,0));
        recyclerView.setItemAnimator(null);

        AdapterToday adatperToday = new AdapterToday();
        recyclerView.setAdapter(adatperToday);
        nonotes = findViewById(R.id.nonotes);

        TodayViewModel todayViewModel = ViewModelProviders.of(this).get(TodayViewModel.class);
        todayViewModel.getNodeLiveData().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adatperToday.setItems(notes);
                progressBar.setMax(adatperToday.getItemCount());
                progressBar.setProgress(adatperToday.getItemDone());
                progress_all.setText(Integer.toString(adatperToday.getItemCount()));
                progress_done.setText(Integer.toString(adatperToday.getItemDone()));
                if(adatperToday.getItemCount() == 0)
                    nonotes.setVisibility(View.VISIBLE);
                else
                    nonotes.setVisibility(View.INVISIBLE);
            }
        });

        //final TextView textView = root.findViewById(R.id.text_today);
       /* todayViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {

            @Override
            public void onChanged(@Nullable String s) {

                textView.setText(s);
            }


        });*/
        textCurrentDate = findViewById(R.id.text_current_date);

        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        String dateText = dateFormat.format(currentDate);
        textCurrentDate.setText(dateText);
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
                    Intent intent1 = new Intent(TodayFragment.this, ScheduleFragment.class);
                    startActivity(intent1);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    return true;
                case R.id.tasks:
                    finish();
                    Intent intent2 = new Intent(TodayFragment.this, TodayFragment.class);
                    startActivity(intent2);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    return true;
                case R.id.calendar:
                    finish();
                    Intent intent3 = new Intent(TodayFragment.this, DateFragment.class);
                    startActivity(intent3);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    return true;
            }

            return true;
        }
    };
}

