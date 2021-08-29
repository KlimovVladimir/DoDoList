package com.example.dodolist.ui.schedule;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dodolist.App;
import com.example.dodolist.R;
import com.example.dodolist.model.Schedule;
import com.example.dodolist.ui.addsubj.AddSubject;
import com.example.dodolist.ui.addteach.AddTeacher;
import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;
import com.jaredrummler.android.colorpicker.ColorShape;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddSchedule extends AppCompatActivity implements ColorPickerDialogListener {
    private static final String EXTRA_SCHEDULE = "AddSchedule.EXTRA_SCHEDULE";
    private static final String EXTRA_WEEK = "AddSchedule.EXTRA_WEEK";
    private static final String EXTRA_DATE = "AddNote.EXTRA_DATE";
    private static final String EXTRA_ACT = "AddNote.EXTRA_ACT";

    private Schedule schedule;

    private static EditText editSubject, editTeacher, editRoom; 

    private static EditText editTextTime, editTextTimeend;

    private Spinner spinner;


    private int lastSelectedHour = -1;
    private int lastSelectedMinute = -1;

    private int lastSelectedHourend = -1;
    private int lastSelectedMinuteend = -1;
    private boolean is24HView = true;

    private TextView colorselect;
    private int colorset;

    private long defTime = -1;

    private int week;

    String[] reminders = {"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"};
    private int GetPosition = 0;

    public static void SubjectGet(String text) {
        editSubject.setText(text);
    }
    public static void TeacherGet(String text) {
        editTeacher.setText(text);
    }

    public static void start(Activity caller, Schedule schedule, int week) {
        Intent intent = new Intent(caller, com.example.dodolist.ui.schedule.AddSchedule.class);
        if (schedule !=  null) {
            intent.putExtra(EXTRA_SCHEDULE, schedule);

        }
        else {
            intent.putExtra(EXTRA_WEEK, week);
        }
        caller.startActivity(intent);
        caller.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public static void editstart(Activity caller, Schedule schedule) {
        Intent intent = new Intent(caller, com.example.dodolist.ui.schedule.AddSchedule.class);
        if (schedule !=  null) {
            intent.putExtra(EXTRA_SCHEDULE, schedule);
        }
        caller.startActivity(intent);
        caller.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    public static void startfromCal(Activity caller, long date) {
        Intent intent = new Intent(caller, com.example.dodolist.ui.AddNote.class);
        if (date !=  0) {
            intent.putExtra(EXTRA_DATE, date);

        }
        caller.startActivity(intent);
    }

    private void createColorPickerDialog(int id) {
        ColorPickerDialog.newBuilder()
                .setColor(Color.RED)
                .setDialogType(ColorPickerDialog.TYPE_PRESETS)
                .setAllowCustom(true)
                .setAllowPresets(true)
                .setColorShape(ColorShape.CIRCLE)
                .setDialogId(id)
                .show(this);
    }

    @Override
    public void onColorSelected(int dialogId, int color) {
        switch (dialogId) {
            case 1:
                colorselect.setBackgroundColor(color);
                colorset = color;
                break;
        }
    }
    @Override
    public void onDialogDismissed(int dialogId) {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_addschedule);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        setTitle(getString(R.string.note_add));
        editSubject = findViewById(R.id.text_edit_subject_schedule);
        editTeacher = findViewById(R.id.text_edit_teacher_schedule);
        editRoom = findViewById(R.id.text_edit_room_schedule);

        editTextTime = findViewById(R.id.text_edit_time_schedule_start);
        editTextTimeend = findViewById(R.id.text_edit_time_schedule_end);

        this.spinner = (Spinner) this.findViewById(R.id.spinner);
        colorselect = findViewById(R.id.colorselectschedule);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, reminders);
        adapter.setDropDownViewResource(R.layout.spinner_item);

        spinner.setAdapter(adapter);


        if(getIntent().hasExtra(EXTRA_SCHEDULE)) {
            schedule = getIntent().getParcelableExtra(EXTRA_SCHEDULE);
            editSubject.setText(schedule.subject);
            editTeacher.setText(schedule.teacher);
            editRoom.setText(schedule.room);

            week = schedule.week;

            long milliSeconds= schedule.time;
            String TimeText= getDate(milliSeconds,"HH:mm" );
            editTextTime.setText(TimeText);

            milliSeconds= schedule.timeend;
            TimeText= getDate(milliSeconds,"HH:mm" );
            editTextTimeend.setText(TimeText);

            colorset = schedule.color;
            colorselect.setBackgroundColor(colorset);

            setTitle("Изменить");

            spinner.setSelection(schedule.day);

        }
        else {
            schedule = new Schedule();
            long milliSeconds= System.currentTimeMillis();
            String TimeText= getDate(milliSeconds,"HH:mm" );
            spinner.setSelection(0);
            //editTextTime.setText(TimeText);

            Calendar date = Calendar.getInstance();
            date.set(Calendar.HOUR_OF_DAY, Integer.parseInt(getDate(milliSeconds,"H" )));
            date.set(Calendar.MINUTE, Integer.parseInt(getDate(milliSeconds,"m" )));
            date.set(Calendar.SECOND, 0);
            date.set(Calendar.MILLISECOND, 0);
            date.set(Calendar.YEAR,0);
            date.set(Calendar.MONTH,0);
            date.set(Calendar.DAY_OF_MONTH,0);
            defTime = date.getTimeInMillis();

            week = getIntent().getIntExtra(EXTRA_WEEK, 1);

            colorset = Color.parseColor("#e0ae17");

        }

       /* this.buttonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSelectTime();
            }
        });*/

        this.colorselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createColorPickerDialog(1);
            }
        });

        this.editTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSelectTime();
            }
        });

        this.editTextTimeend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSelectTimeend();
            }
        });

        this.editSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AddSchedule.this, AddSubject.class);
                intent1.putExtra(EXTRA_ACT, 2);
                startActivity(intent1);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        this.editTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AddSchedule.this, AddTeacher.class);
                startActivity(intent1);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                GetPosition = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
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

    private void buttonSelectTime()  {
        if(this.lastSelectedHour == -1)  {
            final Calendar c = Calendar.getInstance();
            this.lastSelectedHour = c.get(Calendar.HOUR_OF_DAY);
            this.lastSelectedMinute = c.get(Calendar.MINUTE);
        }

        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (minute<10){
                    editTextTime.setText(hourOfDay + ":" + "0"+ minute );
                } else {
                    editTextTime.setText(hourOfDay + ":" + minute );
                }
                lastSelectedHour = hourOfDay;
                lastSelectedMinute = minute;
            }
        };
        TimePickerDialog timePickerDialog = null;
        timePickerDialog = new TimePickerDialog(this,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                timeSetListener, lastSelectedHour, lastSelectedMinute, is24HView);
        timePickerDialog.show();

    }

    private void buttonSelectTimeend()  {
        if(this.lastSelectedHourend == -1)  {
            final Calendar c = Calendar.getInstance();
            this.lastSelectedHourend = c.get(Calendar.HOUR_OF_DAY);
            this.lastSelectedMinuteend = c.get(Calendar.MINUTE);
        }

        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (minute<10){
                    editTextTimeend.setText(hourOfDay + ":" + "0"+ minute );
                } else {
                    editTextTimeend.setText(hourOfDay + ":" + minute );
                }
                lastSelectedHourend = hourOfDay;
                lastSelectedMinuteend = minute;
            }
        };
        TimePickerDialog timePickerDialog = null;
        timePickerDialog = new TimePickerDialog(this,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                timeSetListener, lastSelectedHourend, lastSelectedMinuteend, is24HView);
        timePickerDialog.show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.savebutton, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.action_save:
                if(editSubject.getText().length() > 0) {
                    schedule.subject = editSubject.getText().toString();
                    schedule.teacher = editTeacher.getText().toString();
                    schedule.room = editRoom.getText().toString();

                    if(lastSelectedHour != -1 && lastSelectedMinute != -1)
                        schedule.time = getTimeInMillis (lastSelectedHour,  lastSelectedMinute);
                    else if (defTime != -1)
                        schedule.time =  defTime;

                    if(lastSelectedHourend != -1 && lastSelectedMinuteend != -1)
                        schedule.timeend = getTimeInMillis (lastSelectedHourend,  lastSelectedMinuteend);
                    else if (defTime != -1)
                        schedule.timeend =  defTime;

                    schedule.day = GetPosition;
                    schedule.week = week;

                    schedule.color = colorset;

                    if(getIntent().hasExtra(EXTRA_SCHEDULE)) {
                        App.getInstance().getScheduleDao().update(schedule);
                    }
                    else {
                        App.getInstance().getScheduleDao().insert(schedule);
                    }

                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
        }
        return super.onOptionsItemSelected(item);
    }
    public static long getTimeInMillis (int lastSelectedHour, int lastSelectedMinute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, lastSelectedHour); //+-12
        calendar.set(12, lastSelectedMinute);
        return calendar.getTimeInMillis();
    }

    public static long getDateInMillis (int lastSelectedYear, int lastSelectedMonth, int lastSelectedDayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1, lastSelectedYear);
        calendar.set(2, lastSelectedMonth);
        calendar.set(5, lastSelectedDayOfMonth);   //vozmojno 4
        return calendar.getTimeInMillis();
    }

}
