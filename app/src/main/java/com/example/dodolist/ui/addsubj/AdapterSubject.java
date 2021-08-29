package com.example.dodolist.ui.addsubj;

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
import com.example.dodolist.ui.AddNote;
import com.example.dodolist.ui.schedule.AddSchedule;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


public class AdapterSubject extends RecyclerView.Adapter<AdapterSubject.SubjectViewHolder> {

    private SortedList<Subject> sortedList;


    public AdapterSubject() {
        sortedList = new SortedList<>(Subject.class, new SortedList.Callback<Subject>() {
            @Override
            public int compare(Subject o1, Subject o2) {
                return (int) (o1.unicid - o2.unicid);
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position,count);
            }

            @Override
            public boolean areContentsTheSame(Subject oldItem, Subject newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(Subject item1, Subject item2) {
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
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //neponyapto
        return new SubjectViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subject,parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        holder.bind(sortedList.get(position));
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    public void setItems(List<Subject> notes){
        sortedList.replaceAll(notes);
    }

    static class SubjectViewHolder extends RecyclerView.ViewHolder {

        TextView SubjectText;
        View delete;
        Subject subject;

        boolean silentUpdate;

        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);

            SubjectText = itemView.findViewById(R.id.subject_item);
            delete = itemView.findViewById(R.id.subject_delete);

            SubjectText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ( ((AddSubject)v.getContext()).getAct() == 1 )
                        AddNote.SubjectGet(SubjectText.getText().toString());
                    else if( ((AddSubject)v.getContext()).getAct() == 2 )
                        AddSchedule.SubjectGet(SubjectText.getText().toString());
                    ((Activity)v.getContext()).finish();
                    ((Activity)v.getContext()).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    App.getInstance().getSubjectDao().delete(subject);
                }
            });
        }

        public void bind(Subject subject) {
            this.subject = subject;
            SubjectText.setText(subject.subject);
        }

    }
}
