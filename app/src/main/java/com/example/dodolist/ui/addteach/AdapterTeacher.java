package com.example.dodolist.ui.addteach;

import android.app.Activity;
import android.graphics.Color;
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
import com.example.dodolist.model.Subject;
import com.example.dodolist.model.Teacher;
import com.example.dodolist.ui.AddNote;
import com.example.dodolist.ui.schedule.AddSchedule;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


public class AdapterTeacher extends RecyclerView.Adapter<AdapterTeacher.TeacherViewHolder> {

    private SortedList<Teacher> sortedList;


    public AdapterTeacher() {
        sortedList = new SortedList<>(Teacher.class, new SortedList.Callback<Teacher>() {
            @Override
            public int compare(Teacher o1, Teacher o2) {
                return (int) (o1.unicid - o2.unicid);
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position,count);
            }

            @Override
            public boolean areContentsTheSame(Teacher oldItem, Teacher newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(Teacher item1, Teacher item2) {
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
    public TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TeacherViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_teacher,parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewHolder holder, int position) {
        holder.bind(sortedList.get(position));
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    public void setItems(List<Teacher> notes){
        sortedList.replaceAll(notes);
    }

    static class TeacherViewHolder extends RecyclerView.ViewHolder {

        TextView TeacherText;
        View delete;
        Teacher teacher;

        boolean silentUpdate;

        public TeacherViewHolder(@NonNull View itemView) {
            super(itemView);

            TeacherText = itemView.findViewById(R.id.teacher_item);
            delete = itemView.findViewById(R.id.teacher_delete);

            TeacherText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddSchedule.TeacherGet(TeacherText.getText().toString());
                    ((Activity)v.getContext()).finish();
                    ((Activity)v.getContext()).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    App.getInstance().getTeacherDao().delete(teacher);
                }
            });
        }

        public void bind(Teacher teacher) {
            this.teacher = teacher;
            TeacherText.setText(teacher.teacher);
        }

    }
}
