package com.benjaminsimmons.chordscape.engine.audio;

import static org.lwjgl.openal.AL10.*;

public class AudioListener {

    public void setPosition(float x, float y, float z) {
        alListener3f(AL_POSITION, x, y, z);
    }

    public void setGain(float gain) {
        alListenerf(AL_GAIN, gain);
    }
}