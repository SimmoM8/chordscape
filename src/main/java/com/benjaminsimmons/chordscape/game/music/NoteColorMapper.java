package com.benjaminsimmons.chordscape.game.music;

public class NoteColorMapper {

    public float[] getColor(Note note) {
        return switch (note.getPitch()) {
            case 0 -> new float[]{1.0f, 0.0f, 0.0f}; // C - Red
            case 1 -> new float[]{1.0f, 0.5f, 0.0f}; // C#/Db - Orange
            case 2 -> new float[]{1.0f, 1.0f, 0.0f}; // D - Yellow
            case 3 -> new float[]{0.5f, 1.0f, 0.0f}; // D#/Eb - Light Green
            case 4 -> new float[]{0.0f, 1.0f, 0.0f}; // E - Green
            case 5 -> new float[]{0.0f, 1.0f, 0.5f}; // F - Teal
            case 6 -> new float[]{0.0f, 1.0f, 1.0f}; // F#/Gb - Cyan
            case 7 -> new float[]{0.5f, 1.0f, 1.0f}; // G - Light Cyan
            case 8 -> new float[]{1.0f, 1.0f, 1.0f}; // G#/Ab - White
            case 9 -> new float[]{1.0f, 1.0f, 0.5f}; // A - Light Yellow
            case 10 -> new float[]{1.0f, 1.5f, 0.5f}; // A#/Bb - Light Orange
            case 11 -> new float[]{1.5f, 1.5f, 1.5f}; // B - Light Gray
            default -> throw new IllegalArgumentException("Invalid pitch: " + note.getPitch());
        };
    }
}
