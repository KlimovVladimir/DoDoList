package com.example.dodolist.ui.calendar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dodolist.R;
import com.example.dodolist.model.Note;
import com.example.dodolist.ui.AddNote;
import com.example.dodolist.ui.today.AdapterToday;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class DateActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private long selectdate;
    private TextView textData;
    private TextView nonotes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle(getString(R.string.date_select));

        textData = findViewById(R.id.text_data);

        recyclerView = findViewById(R.id.list_date);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, 0));

        final AdapterToday adapter = new AdapterToday();
        recyclerView.setAdapter(adapter);
        nonotes = findViewById(R.id.nonotes_date);

        selectdate = getIntent().getLongExtra("Calendar.EXTRA_Date",0);

        //FloatingActionButton fab = findViewById(R.id.fab1);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNote.startfromCal(DateActivity.this, selectdate);
            }
        });*/

        String TimeText= getDate(selectdate,"dd MMMM yyyy" );
        textData.setText(TimeText);

        DateViewModel mainViewModel = ViewModelProviders.of(this).get(DateViewModel.class);
        mainViewModel.getNodeLiveData(selectdate).observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.setItems(notes);
                if(adapter.getItemCount() == 0)
                    nonotes.setVisibility(View.VISIBLE);
                else
                    nonotes.setVisibility(View.INVISIBLE);
            }
        });
    }
    public static String getDate(long milliSeconds, String dateFormat)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.add_note_toolbar:
                AddNote.startfromCal(DateActivity.this, selectdate);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addbuttontoolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }


}
