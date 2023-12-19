package com.example.mean_v1;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Subject subject);
    }
    private List<Subject> subjects;
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public SubjectAdapter(List<Subject> subjects) {
        this.subjects = subjects;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Subject subject = subjects.get(position);
        holder.subjectName.setText(subject.getName());
        holder.subjectScore.setText(String.valueOf(subject.getMean_score()));
        holder.subjectMax.setText(String.valueOf(subject.getMax()));
        double total = subject.getMean_score()*10/ subject.getMax();
        holder.subjectTotal.setText(String.valueOf(total));

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(subject);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateSubjects() {
        //subjects.clear();
        //subjects.addAll(newSubjects);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView subjectName, subjectScore, subjectMax, subjectTotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectName = itemView.findViewById(R.id.subjectName);
            subjectScore = itemView.findViewById(R.id.subjectScore);
            subjectMax = itemView.findViewById(R.id.subjectMax);
            subjectTotal = itemView.findViewById(R.id.subjectTotal);
        }
    }
}
