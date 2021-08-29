package com.example.dodolist.ui.schedule;

import android.app.Activity;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.example.dodolist.App;
import com.example.dodolist.R;
import com.example.dodolist.model.Note;
import com.example.dodolist.model.Schedule;
import com.example.dodolist.ui.AddNote;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AdapterSchedule extends RecyclerView.Adapter<AdapterSchedule.ScheduleViewHolder> {

private SortedList<Schedule> sortedList;


public AdapterSchedule() {
        sortedList = new SortedList<>(Schedule.class, new SortedList.Callback<Schedule>() {
@Override
public int compare(Schedule o1, Schedule o2) {

        return (int) (o1.time - o2.time);
        }

@Override
public void onChanged(int position, int count) {
        notifyItemRangeChanged(position,count);
        }

@Override
public boolean areContentsTheSame(Schedule oldItem, Schedule newItem) {
        return oldItem.equals(newItem);
        }

@Override
public boolean areItemsTheSame(Schedule item1, Schedule item2) {
        return item1.unicid == item2.unicid;
        }

@Override
public void onInserted(int position, int count) {
        notifyItemRangeInserted(position,count);

        }

@Override
public void onRemoved(int position, int count) {
        notifyItemRangeRemoved(position,count);
        }

@Override
public void onMoved(int fromPosition, int toPosition) {
        notifyItemMoved(fromPosition, toPosition);
        }
        });
}

@NonNull
@Override
public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //neponyapto
        return new ScheduleViewHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule_list,parent, false)
        );
        }

@Override
public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        holder.bind(sortedList.get(position));
        }

@Override
public int getItemCount() {
        return sortedList.size();
        }

public void setItems(List<Schedule> schedule){
        sortedList.replaceAll(schedule);
        }

static class ScheduleViewHolder extends RecyclerView.ViewHolder {

    TextView scheduleTimeText;
    TextView scheduleTimeendText;
    TextView scheduleSubjectText;
    TextView scheduleTeacherText;
    TextView scheduleRoomText;
    View delete, change, colorbar;
    Schedule schedule;

    boolean silentUpdate;

    public ScheduleViewHolder(@NonNull View itemView) {
        super(itemView);

        scheduleSubjectText = itemView.findViewById(R.id.schedule_text_subject_item);
        scheduleTimeText = itemView.findViewById(R.id.schedule_text_time_item);
        scheduleTimeendText = itemView.findViewById(R.id.schedule_text_timeend_item);
        scheduleTeacherText = itemView.findViewById(R.id.schedule_text_teacher_item);
        scheduleRoomText = itemView.findViewById(R.id.schedule_text_room_item);
        delete = itemView.findViewById(R.id.schedule_delete);
        //change = itemView.findViewById(R.id.schedule_change);
        colorbar = itemView.findViewById(R.id.colorbarschedule);


        /*change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddSchedule.editstart((Activity) itemView.getContext(), schedule);
            }
        });*/
        scheduleSubjectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddSchedule.editstart((Activity) itemView.getContext(), schedule);
            }
        });
        scheduleTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddSchedule.editstart((Activity) itemView.getContext(), schedule);
            }
        });
        scheduleTimeendText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddSchedule.editstart((Activity) itemView.getContext(), schedule);
            }
        });
        scheduleTeacherText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddSchedule.editstart((Activity) itemView.getContext(), schedule);
            }
        });
        scheduleRoomText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddSchedule.editstart((Activity) itemView.getContext(), schedule);
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.getInstance().getScheduleDao().delete(schedule);
            }
        });
    }

    public void bind(Schedule schedule) {
        this.schedule = schedule;
        scheduleSubjectText.setText(schedule.subject);
        scheduleTeacherText.setText(schedule.teacher);
        scheduleRoomText.setText(schedule.room);
        colorbar.setBackgroundColor(schedule.color);

        long milliSeconds= schedule.time;
        String TimeText= getDate(milliSeconds,"HH:mm" );
        scheduleTimeText.setText(TimeText);

        milliSeconds= schedule.timeend;
        TimeText= getDate(milliSeconds,"HH:mm" );
        scheduleTimeendText.setText(TimeText);


    }
    public static String getDate(long milliSeconds, String dateFormat)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}
}
