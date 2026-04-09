package com.benjaminsimmons.chordscape.engine.audio;

import java.nio.ShortBuffer;

import static org.lwjgl.openal.AL10.*;

public class AudioBuffer {

    private final int bufferId;

    public AudioBuffer(ShortBuffer pcmData, int sampleRate, boolean stereo) {
        this.bufferId = alGenBuffers();

        int format = stereo ? AL_FORMAT_STEREO16 : AL_FORMAT_MONO16;
        alBufferData(bufferId, format, pcmData, sampleRate);
    }

    public int getBufferId() {
        return bufferId;
    }

    public void cleanup() {
        alDeleteBuffers(bufferId);
    }
}