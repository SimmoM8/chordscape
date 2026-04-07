package com.benjaminsimmons.chordscape.game.music;

public class NoteColorMapper {

    public float[] getColor(Note note) {
        return switch (note.getPitch()) {
            case 0 -> new float[]{1.0f, 0.0f, 0.0f};   // C
            case 1 -> new float[]{1.0f, 0.5f, 0.0f};   // C#
            case 2 -> new float[]{1.0f, 1.0f, 0.0f};   // D
            case 3 -> new float[]{0.5f, 1.0f, 0.0f};   // D#
            case 4 -> new float[]{0.0f, 1.0f, 0.0f};   // E
            case 5 -> new float[]{0.0f, 1.0f, 0.5f};   // F
            case 6 -> new float[]{0.0f, 1.0f, 1.0f};   // F#
            case 7 -> new float[]{0.0f, 0.5f, 1.0f};   // G
            case 8 -> new float[]{0.3f, 0.3f, 1.0f};   // G#
            case 9 -> new float[]{0.7f, 0.3f, 1.0f};   // A
            case 10 -> new float[]{1.0f, 0.3f, 0.8f};  // A#
            case 11 -> new float[]{0.8f, 0.8f, 0.8f};  // B
            default -> throw new IllegalArgumentException("Invalid pitch: " + note.getPitch());
        };
    }
}