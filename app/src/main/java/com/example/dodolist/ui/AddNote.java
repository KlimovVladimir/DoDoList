package com.example.dodolist.ui;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dodolist.App;
import com.example.dodolist.R;
import com.example.dodolist.TimeNotification;
import com.example.dodolist.model.Note;
import com.example.dodolist.ui.addsubj.AddSubject;
import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;
import com.jaredrummler.android.colorpicker.ColorShape;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddNote extends AppCompatActivity implements ColorPickerDialogListener {
    private static final String EXTRA_NOTE = "AddNote.EXTRA_NOTE";
    private static final String EXTRA_DATE = "AddNote.EXTRA_DATE";
    private static final String EXTRA_ACT = "AddNote.EXTRA_ACT";
    private final static String FILE_NAME = "notify.txt";

    private Note note;

    private static EditText editText, editSubject; ////STATIC

    private static EditText editTextTime, editTextDate, editTextDateNot;
    private Button buttonTime, buttonDate;
    private Spinner spinner;
    private TextView textNotificaton;
    private Switch switch1;
    private boolean notific = false;
    private TextView colorselect;
    private int colorset;

    private int lastSelectedHour = -1;
    private int lastSelectedMinute = -1;
    private boolean is24HView = true;

    private int lastSelectedYear = -1;
    private int lastSelectedMonth = -1;
    private int lastSelectedDayOfMonth = -1;

    private int lastSelectedYearNot = -1;
    private int lastSelectedMonthNot = -1;
    private int lastSelectedDayOfMonthNot = -1;

    private long defDate = -1;
    private long defTime = -1;

    String[] reminders = {"Нет", "В момент события", "За 5 минут", "За 10 минут", "За 15 минут", "За 30 минут", "За 1 час", "За 2 часа", "За 1 день"};
    //!!!!!!!!!!!!!
    final String LOG_TAG = "myLogs";
    // NotificationManager nm;
    //AlarmManager am;
    //Intent intent1;
    //Intent intent2;
    //PendingIntent pIntent1;
    //PendingIntent pIntent2;

    public static String getEdit() {
        //String texx = editText.getText().toString();
        return editText.getText().toString();
    }

    public static void SubjectGet(String text) {
        editSubject.setText(text);
    }
    //!!!!!!!!!!!!!!!

    public static void start(Activity caller, Note note) {
        Intent intent = new Intent(caller, AddNote.class);
        if (note !=  null) {
            intent.putExtra(EXTRA_NOTE, note);

        }
        caller.startActivity(intent);
        caller.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public static void startfromCal(Activity caller, long date) {
        Intent intent = new Intent(caller, AddNote.class);
        if (date !=  0) {
            intent.putExtra(EXTRA_DATE, date);

        }
        caller.startActivity(intent);
        caller.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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

        setContentView(R.layout.activity_addnote);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        setTitle(getString(R.string.note_add));
        editText = findViewById(R.id.text_edit);
        editSubject = findViewById(R.id.text_edit_subject);

        this.editTextTime = (EditText) this.findViewById(R.id.editText_time);
        //this.buttonTime = (Button) this.findViewById(R.id.button_time);

        this.editTextDate = (EditText) this.findViewById(R.id.editText_date);
        this.editTextDateNot = (EditText) this.findViewById(R.id.editText_dateNot);
        //this.buttonDate = (Button) this.findViewById(R.id.button_date);
        colorselect = findViewById(R.id.colorselect);

        switch1 = findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (isChecked) {
                   notific = true;
                   editTextTime.setVisibility(View.VISIBLE);
                   editTextDateNot.setVisibility(View.VISIBLE);
               }
               else {
                   notific = false;
                   editTextTime.setVisibility(View.GONE);
                   editTextDateNot.setVisibility(View.GONE);
               }
            }
        });

               //nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE); //////!!!!!!!
        //am = (AlarmManager) getSystemService(Context.ALARM_SERVICE); //!1!!!!!!!


        if(getIntent().hasExtra(EXTRA_NOTE)) {
            note = getIntent().getParcelableExtra(EXTRA_NOTE);
            editText.setText(note.text);
            editSubject.setText(note.subject);

            long milliSeconds= note.time;
            String TimeText= getDate(milliSeconds,"HH:mm" );
            editTextTime.setText(TimeText);

            milliSeconds= note.date;
            TimeText= getDate(milliSeconds,"dd.MM.yyyy" );
            editTextDate.setText(TimeText);

            milliSeconds= note.dateNotif;
            TimeText= getDate(milliSeconds,"dd.MM.yyyy" );
            editTextDateNot.setText(TimeText);

            setTitle("Изменить");

            colorset = note.color;
            colorselect.setBackgroundColor(colorset);

            //spinner.setSelection(3);
        }
        else if(getIntent().hasExtra(EXTRA_DATE)){
            note = new Note();
            long milliSeconds= System.currentTimeMillis();
            String TimeText= getDate(milliSeconds,"HH:mm" );
            editTextTime.setText(TimeText);

            Calendar date = Calendar.getInstance();
            date.set(Calendar.HOUR_OF_DAY, Integer.parseInt(getDate(milliSeconds,"H" )));
            date.set(Calendar.MINUTE, Integer.parseInt(getDate(milliSeconds,"m" )));
            date.set(Calendar.SECOND, 0);
            date.set(Calendar.MILLISECOND, 0);
            date.set(Calendar.YEAR,0);
            date.set(Calendar.MONTH,0);
            date.set(Calendar.DAY_OF_MONTH,0);
            defTime = date.getTimeInMillis();

            defDate = getIntent().getLongExtra(EXTRA_DATE,0);
            TimeText= getDate(defDate,"dd.MM.yyyy" );
            editTextDate.setText(TimeText);
            editTextDateNot.setText(TimeText);

            colorset = Color.parseColor("#e0ae17");

        }
        else {
            note = new Note();
            long milliSeconds= System.currentTimeMillis();
            String TimeText= getDate(milliSeconds,"HH:mm" );
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

            TimeText= getDate(milliSeconds,"dd.MM.yyyy" );
            editTextDate.setText(TimeText);
            editTextDateNot.setText(TimeText);

            Calendar date1 = Calendar.getInstance();
            date1.set(Calendar.HOUR_OF_DAY, 0);
            date1.set(Calendar.MINUTE, 0);
            date1.set(Calendar.SECOND, 0);
            date1.set(Calendar.MILLISECOND, 0);
            defDate = date1.getTimeInMillis();

            colorset = Color.parseColor("#e0ae17");
        }

       /* this.buttonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSelectTime();
            }
        });*/

        this.editTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSelectTime();
            }
        });

        this.editSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AddNote.this, AddSubject.class);
                intent1.putExtra(EXTRA_ACT, 1);
                startActivity(intent1);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });




       /* this.buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSelectDate();
            }
        });*/

        this.colorselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createColorPickerDialog(1);
            }
        });

        this.editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSelectDate();
            }
        });

        this.editTextDateNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSelectDateNot();
            }
        });


        //AdapterView<Adapter> parent;
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


    private void buttonSelectDate() {
        final Calendar cl = Calendar.getInstance();
        this.lastSelectedYear = cl.get(Calendar.YEAR);
        this.lastSelectedMonth = cl.get(Calendar.MONTH);
        this.lastSelectedDayOfMonth = cl.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                if (dayOfMonth < 10 && monthOfYear < 10) {
                    editTextDate.setText("0"+ dayOfMonth + "." + "0"+ (monthOfYear + 1) + "." + year);
                }
                else if (dayOfMonth < 10) {
                    editTextDate.setText("0"+ dayOfMonth + "." + (monthOfYear + 1) + "." + year);
                }
                else if (monthOfYear < 10) {
                    editTextDate.setText(dayOfMonth + "." + "0"+ (monthOfYear + 1) + "." + year);
                }
                else {
                    editTextDate.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
                }
                lastSelectedYear = year;
                lastSelectedMonth = monthOfYear;
                lastSelectedDayOfMonth = dayOfMonth;
            }
        };

        DatePickerDialog datePickerDialog = null;
        datePickerDialog = new DatePickerDialog(this,
                dateSetListener, lastSelectedYear, lastSelectedMonth, lastSelectedDayOfMonth);

        datePickerDialog.show();
    }

    private void buttonSelectDateNot() {
        final Calendar cl1 = Calendar.getInstance();
        this.lastSelectedYearNot = cl1.get(Calendar.YEAR);
        this.lastSelectedMonthNot = cl1.get(Calendar.MONTH);
        this.lastSelectedDayOfMonthNot = cl1.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog.OnDateSetListener dateSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                if (dayOfMonth < 10 && monthOfYear < 10) {
                    editTextDateNot.setText("0"+ dayOfMonth + "." + "0"+ (monthOfYear + 1) + "." + year);
                }
                else if (dayOfMonth < 10) {
                    editTextDateNot.setText("0"+ dayOfMonth + "." + (monthOfYear + 1) + "." + year);
                }
                else if (monthOfYear < 10) {
                    editTextDateNot.setText(dayOfMonth + "." + "0"+ (monthOfYear + 1) + "." + year);
                }
                else {
                    editTextDateNot.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
                }
                lastSelectedYearNot = year;
                lastSelectedMonthNot = monthOfYear;
                lastSelectedDayOfMonthNot = dayOfMonth;
            }
        };

        DatePickerDialog datePickerDialog1 = null;
        datePickerDialog1 = new DatePickerDialog(this,
                dateSetListener1, lastSelectedYearNot, lastSelectedMonthNot, lastSelectedDayOfMonthNot);

        datePickerDialog1.show();
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
                if(editText.getText().length() > 0) {
                    note.text = editText.getText().toString();
                    note.subject = editSubject.getText().toString();
                    note.done = false;
                    if(lastSelectedHour != -1 && lastSelectedMinute != -1)
                        note.time = getTimeInMillis (lastSelectedHour,  lastSelectedMinute);
                    else if (defTime != -1)
                        note.time =  defTime;

                    note.color = colorset;

                    Calendar date = Calendar.getInstance();
                    date.set(Calendar.HOUR_OF_DAY, 0);
                    date.set(Calendar.MINUTE, 0);
                    date.set(Calendar.SECOND, 0);
                    date.set(Calendar.MILLISECOND, 0);
                    date.set(Calendar.YEAR,lastSelectedYear);
                    date.set(Calendar.MONTH,lastSelectedMonth);
                    date.set(Calendar.DAY_OF_MONTH,lastSelectedDayOfMonth);
                    if(lastSelectedYear != -1 && lastSelectedMonth != -1 && lastSelectedDayOfMonth != -1)
                        note.date = date.getTimeInMillis();
                    else if (defDate != -1)
                        note.date =  defDate;


                    Calendar date2 = Calendar.getInstance();
                    date2.set(Calendar.HOUR_OF_DAY, lastSelectedHour);
                    date2.set(Calendar.MINUTE, lastSelectedMinute);
                    date2.set(Calendar.SECOND, 0);
                    date2.set(Calendar.MILLISECOND, 0);
                    date2.set(Calendar.YEAR,lastSelectedYearNot);
                    date2.set(Calendar.MONTH,lastSelectedMonthNot);
                    date2.set(Calendar.DAY_OF_MONTH,lastSelectedDayOfMonthNot);
                    if(lastSelectedYearNot != -1 && lastSelectedMonthNot != -1 && lastSelectedDayOfMonthNot != -1 && lastSelectedHour != -1 && lastSelectedMinute != -1)
                        note.dateNotif = date2.getTimeInMillis();
                    else if (defDate != -1)
                        note.dateNotif =  System.currentTimeMillis();
                    //note.time = lastSelectedHour;
                    //note.time = System.currentTimeMillis();
                    if(getIntent().hasExtra(EXTRA_NOTE)) {
                        App.getInstance().getNoteDao().update(note);
                    }
                    else {
                        App.getInstance().getNoteDao().insert(note);
                    }

                    /*NotificationCompat.Builder builder =
                            new NotificationCompat.Builder(this)
                                    .setSmallIcon(R.mipmap.ic_launcher)
                                    .setContentTitle("DoDoList")
                                    .setContentText(editText.getText().toString())
                                    .setWhen(getTimeInMillis (lastSelectedHour,  lastSelectedMinute));

                    Notification notification = builder.build();

                    NotificationManager notificationManager =
                            (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    notificationManager.notify(1, notification);*/

                    //!!!!!!!!!!!!!!!!!!
                    //intent1 = createIntent(note.text, note.text);

                    AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);

                    Intent intent1 = new Intent(this, TimeNotification.class);
                    intent1.setAction(editText.getText().toString());
                    //Uri data1 = Uri.parse(intent1.toUri(Intent.URI_INTENT_SCHEME));
                    //intent1.setData(data1);
                    intent1.putExtra("NOTIFY_ID", (int)System.currentTimeMillis());
                    intent1.putExtra("TEXT", note.text);
                    //intent1.putExtra(editText.getText().toString(), editText.getText().toString());
                    //intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    PendingIntent pIntent1 = PendingIntent.getBroadcast(this, (int)System.currentTimeMillis(), intent1, 0);

                    // intent2 = createIntent("action 2", "extra 2");
                    // pIntent2 = PendingIntent.getBroadcast(this, 0, intent2, 0);
                    /*if(note.date > System.currentTimeMillis())*/ {
                        if (notific == true) {
                            am.set(AlarmManager.RTC, note.dateNotif, pIntent1);
                        }
                        //am.set(AlarmManager.RTC, System.currentTimeMillis() + 4000, pIntent2);
                        //sendNotif(1, pIntent1);
                        //sendNotif(2, pIntent2);
                    }

                    //!!!!!!!!!!!!!!!!!!!

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
