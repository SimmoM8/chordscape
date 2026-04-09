package com.benjaminsimmons.chordscape.engine.audio;

import org.lwjgl.BufferUtils;

import java.nio.ShortBuffer;

public class ToneGenerator {

    public ShortBuffer generateSineWave(float frequency, float durationSeconds, int sampleRate) {
        int sampleCount = (int) (durationSeconds * sampleRate);
        ShortBuffer buffer = BufferUtils.createShortBuffer(sampleCount);

        for (int i = 0; i < sampleCount; i++) {
            double time = i / (double) sampleRate;
            double sample = Math.sin(2.0 * Math.PI * frequency * time);

            short pcm = (short) (sample * Short.MAX_VALUE);
            buffer.put(pcm);
        }

        buffer.flip();
        return buffer;
    }
}