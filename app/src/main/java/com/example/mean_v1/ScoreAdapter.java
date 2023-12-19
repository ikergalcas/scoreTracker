package com.example.mean_v1;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(Score score);
    }
    private List<Score> scores;
    private ScoreAdapter.OnItemClickListener listener;

    public void setOnItemClickListener(ScoreAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public ScoreAdapter(List<Score> scores) {
        this.scores = scores;
    }

    @NonNull
    @Override
    public ScoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_list_item, parent, false);
        return new ScoreAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreAdapter.ViewHolder holder, int position) {
        Score score = scores.get(position);
        String index = new String(position + ". ");
        holder.textView1.setText(index);
        holder.textView2.setText(String.valueOf(score.getScore()));
        holder.textView3.setText(String.valueOf(score.getMax_score()));
        holder.textView4.setText(String.valueOf(score.getWeight()));


        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(score);
            }
        });
    }

    @Override
    public int getItemCount() {
        return scores.size();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateScores() {
        //scores.clear();
        //scores.addAll(newScores);
        notifyDataSetChanged();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1, textView2, textView3, textView4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
            textView3 = itemView.findViewById(R.id.textView3);
            textView4 = itemView.findViewById(R.id.textView4);
        }
    }
}


