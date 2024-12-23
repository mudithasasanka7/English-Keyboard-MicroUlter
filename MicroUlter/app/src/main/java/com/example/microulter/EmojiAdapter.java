package com.example.microulter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EmojiAdapter extends RecyclerView.Adapter<EmojiAdapter.EmojiViewHolder> {
    private final List<String> emojiList;
    private final OnEmojiClickListener emojiClickListener;

    public EmojiAdapter(List<String> emojiList, OnEmojiClickListener emojiClickListener) {
        this.emojiList = emojiList;
        this.emojiClickListener = emojiClickListener;
    }

    @NonNull
    @Override
    public EmojiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_emoji, parent, false);
        return new EmojiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmojiViewHolder holder, int position) {
        String emoji = emojiList.get(position);
        holder.emojiText.setText(emoji);
        holder.itemView.setOnClickListener(v -> emojiClickListener.onEmojiClick(emoji));
    }

    @Override
    public int getItemCount() {
        return emojiList.size();
    }

    public static class EmojiViewHolder extends RecyclerView.ViewHolder {
        public TextView emojiText;

        public EmojiViewHolder(@NonNull View itemView) {
            super(itemView);
            emojiText = itemView.findViewById(R.id.emojiText);
        }
    }

    public interface OnEmojiClickListener {
        void onEmojiClick(String emoji);
    }
}