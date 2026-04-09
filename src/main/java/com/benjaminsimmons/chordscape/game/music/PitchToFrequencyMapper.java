package com.benjaminsimmons.chordscape.game.music;

public class PitchToFrequencyMapper {

    private static final int BASE_MIDI_NOTE = 60; // Middle C

    public float getFrequency(int pitch) {
        int midiNote = BASE_MIDI_NOTE + pitch;
        return (float) (440.0 * Math.pow(2.0, (midiNote - 69) / 12.0));
    }
}