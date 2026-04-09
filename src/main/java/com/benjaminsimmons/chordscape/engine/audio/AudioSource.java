package com.benjaminsimmons.chordscape.engine.audio;

import static org.lwjgl.openal.AL10.*;

public class AudioSource {

    private final int sourceId;

    public AudioSource() {
        this.sourceId = alGenSources();
    }

    public void setBuffer(AudioBuffer buffer) {
        alSourcei(sourceId, AL_BUFFER, buffer.getBufferId());
    }

    public void setLooping(boolean looping) {
        alSourcei(sourceId, AL_LOOPING, looping ? AL_TRUE : AL_FALSE);
    }

    public void setGain(float gain) {
        alSourcef(sourceId, AL_GAIN, gain);
    }

    public void setPitch(float pitch) {
        alSourcef(sourceId, AL_PITCH, pitch);
    }

    public void play() {
        alSourcePlay(sourceId);
    }

    public void stop() {
        alSourceStop(sourceId);
    }

    public boolean isPlaying() {
        return alGetSourcei(sourceId, AL_SOURCE_STATE) == AL_PLAYING;
    }

    public void cleanup() {
        stop();
        alDeleteSources(sourceId);
    }
}