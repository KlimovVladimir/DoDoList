package com.example.dodolist.ui.addteach;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.dodolist.model.Subject;
import com.example.dodolist.model.Teacher;
import com.example.dodolist.ui.addsubj.AdapterSubject;
import com.example.dodolist.ui.addsubj.SubjectViewModel;

import java.util.List;


public class AddTeacher extends AppCompatActivity {

    private static EditText editText;
    private Teacher teacher;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_addteacher);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle(getString(R.string.note_add));

        editText = findViewById(R.id.teacher_text_edit);

        recyclerView = findViewById(R.id.list_teacher);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,0));

        AdapterTeacher adapterTeacher = new AdapterTeacher();
        recyclerView.setAdapter(adapterTeacher);

        TeacherViewModel teacherViewModel = ViewModelProviders.of(this).get(TeacherViewModel.class);
        teacherViewModel.getNodeLiveData().observe(this, new Observer<List<Teacher>>() {
            @Override
            public void onChanged(List<Teacher> teachers) {
                adapterTeacher.setItems(teachers);
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
                    teacher = new Teacher();
                    teacher.teacher = editText.getText().toString();
                    App.getInstance().getTeacherDao().insert(teacher);
                    editText.setText("");
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
