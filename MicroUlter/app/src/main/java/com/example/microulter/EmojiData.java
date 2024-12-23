package com.example.microulter;
import java.util.Arrays;
import java.util.List;
public class EmojiData {
    public static List<String> getEmojiList() {
        return Arrays.asList(
                "😊", "😂", "❤️", "😍", "🤔", // Add all 450 emojis
                "😎", "🎉", "🌟", "🔥", "🌈",
                "💡", "📚", "🍎", "☕", "🎵"
                // Continue until all 450 emojis are added
        );
    }
}

