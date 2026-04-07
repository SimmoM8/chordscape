package com.benjaminsimmons.chordscape.game.music;

public class Note {

    private final int pitch;

    public Note(int pitch) {
        if (pitch < 0 || pitch > 11) {
            throw new IllegalArgumentException("Pitch must be between 0 and 11 inclusive.");
        }
        this.pitch = pitch;
    }

    public int getPitch() {
        return pitch;
    }
}
