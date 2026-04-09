package com.benjaminsimmons.chordscape.engine.audio;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class AudioEngine {

    private long device;
    private long context;
    private boolean initialized = false;

    public void init() {
        device = alcOpenDevice((ByteBuffer) null);
        if (device == NULL) {
            throw new IllegalStateException("Failed to open OpenAL device.");
        }

        ALCCapabilities alcCapabilities = ALC.createCapabilities(device);

        context = alcCreateContext(device, (IntBuffer) null);
        if (context == NULL) {
            alcCloseDevice(device);
            throw new IllegalStateException("Failed to create OpenAL context.");
        }

        if (!alcMakeContextCurrent(context)) {
            alcDestroyContext(context);
            alcCloseDevice(device);
            throw new IllegalStateException("Failed to make OpenAL context current.");
        }

        AL.createCapabilities(alcCapabilities);
        initialized = true;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void cleanup() {
        if (!initialized) {
            return;
        }

        alcMakeContextCurrent(NULL);
        alcDestroyContext(context);
        alcCloseDevice(device);

        initialized = false;
    }
}