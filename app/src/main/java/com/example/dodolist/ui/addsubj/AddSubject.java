package com.example.dodolist.ui.addsubj;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dodolist.App;
import com.example.dodolist.R;
import com.example.dodolist.model.Note;
import com.example.dodolist.model.Subject;
import com.example.dodolist.ui.AddNote;
import com.example.dodolist.ui.calendar.DateFragment;
import com.example.dodolist.ui.calendar.DateViewModel;
import com.example.dodolist.ui.today.AdapterToday;
import com.example.dodolist.ui.today.TodayViewModel;

import java.util.List;


public class AddSubject extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Subject subject;
    private static EditText editText;
    private static final String EXTRA_ACT = "AddNote.EXTRA_ACT";
    private int act;
    public int getAct() {
        return act;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_addsubject);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle(getString(R.string.note_add));

        act = getIntent().getIntExtra(EXTRA_ACT,0);

        editText = findViewById(R.id.subject_text_edit);

        recyclerView = findViewById(R.id.list_subject);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,0));

        AdapterSubject adapterSubject = new AdapterSubject();
        recyclerView.setAdapter(adapterSubject);

        SubjectViewModel subjectViewModel = ViewModelProviders.of(this).get(SubjectViewModel.class);
        subjectViewModel.getNodeLiveData().observe(this, new Observer<List<Subject>>() {
            @Override
            public void onChanged(List<Subject> subjects) {
                adapterSubject.setItems(subjects);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addbuttontoolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.add_note_toolbar:
                if(editText.getText().length() > 0) {
                    subject = new Subject();
                    subject.subject = editText.getText().toString();
                    App.getInstance().getSubjectDao().insert(subject);
                    editText.setText("");
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
