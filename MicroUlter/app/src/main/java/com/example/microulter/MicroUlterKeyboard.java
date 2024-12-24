package com.example.microulter;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.View;
import android.view.inputmethod.InputConnection;

public class MicroUlterKeyboard extends InputMethodService implements KeyboardView.OnKeyboardActionListener {
    private KeyboardView keyboardView;
    private Keyboard defaultKeyboard;
    private Keyboard emojiKeyboard;
    private boolean isShifted = false; // Shift state
    private boolean isEmojiKeyboard = false; // Emoji state

    @Override
    public View onCreateInputView() {
        keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard_view, null);

        // Initialize keyboards
        defaultKeyboard = new Keyboard(this, R.xml.keyboard_layout);
        emojiKeyboard = new Keyboard(this, R.xml.keyboard_emoji_layout);

        // Set the default keyboard initially
        keyboardView.setKeyboard(defaultKeyboard);
        keyboardView.setOnKeyboardActionListener(this);

        return keyboardView;
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection inputConnection = getCurrentInputConnection();
        if (inputConnection != null) {
            switch (primaryCode) {
                // Inside onKey method
                case -1: // Shift key
                    isShifted = !isShifted; // Toggle shift state
                    keyboardView.setShifted(isShifted); // Update keyboard keys

                    // Change Shift key background based on shift state
                    int shiftKeyIndex = findShiftKeyIndex(); // Get Shift key index
                    if (shiftKeyIndex != -1) {
                        Keyboard.Key shiftKey = keyboardView.getKeyboard().getKeys().get(shiftKeyIndex);
                        if (isShifted) {
                            shiftKey.icon = getResources().getDrawable(R.drawable.shift_key_active, null);
                        } else {
                            shiftKey.icon = getResources().getDrawable(R.drawable.shift_key_inactive, null);
                        }
                        keyboardView.invalidateKey(shiftKeyIndex); // Refresh Shift key only
                    }
                    break;


                case -5: // Backspace key
                    CharSequence currentText = inputConnection.getTextBeforeCursor(2, 0);
                    if (currentText != null && currentText.length() > 0) {
                        // Check if the last character is a part of a multi-byte emoji
                        if (Character.isHighSurrogate(currentText.charAt(0))) {
                            // Delete two characters (for surrogate pairs)
                            inputConnection.deleteSurroundingText(2, 0);
                        } else {
                            // Delete one character
                            inputConnection.deleteSurroundingText(1, 0);
                        }
                    }
                    break;

                case 10: // Enter key
                    inputConnection.commitText("\n", 1);
                    break;

                case 1001: // Switch to Emoji keyboard
                    isEmojiKeyboard = true;
                    keyboardView.setKeyboard(emojiKeyboard);
                    break;

                case 1002: // Switch to Default keyboard
                    isEmojiKeyboard = false;
                    keyboardView.setKeyboard(defaultKeyboard);
                    break;

                default:
                    // Handle regular characters and emojis
                    if (primaryCode >= 128512 && primaryCode <= 128591) {
                        // Emoji range in Unicode
                        String emoji = new String(Character.toChars(primaryCode));
                        inputConnection.commitText(emoji, 1);
                    } else {
                        // Handle regular characters
                        if (isShifted && Character.isLowerCase((char) primaryCode)) {
                            primaryCode = Character.toUpperCase((char) primaryCode);
                        }
                        char code = (char) primaryCode;
                        inputConnection.commitText(String.valueOf(code), 1);
                    }
            }
        }
    }

    private int findShiftKeyIndex() {
        for (int i = 0; i < keyboardView.getKeyboard().getKeys().size(); i++) {
            Keyboard.Key key = keyboardView.getKeyboard().getKeys().get(i);
            if (key.codes[0] == -1) { // Shift key code
                return i;
            }
        }
        return -1; // Not found
    }


    @Override public void onPress(int primaryCode) {}
    @Override public void onRelease(int primaryCode) {}
    @Override public void onText(CharSequence text) {}
    @Override public void swipeLeft() {}
    @Override public void swipeRight() {}
    @Override public void swipeDown() {}
    @Override public void swipeUp() {}
}
