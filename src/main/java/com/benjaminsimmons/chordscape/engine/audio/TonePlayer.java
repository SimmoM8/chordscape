package com.benjaminsimmons.chordscape.engine.audio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TonePlayer {

    private static final int SAMPLE_RATE = 44100;
    private static final float NOTE_DURATION_SECONDS = 0.20f;
    private static final float NOTE_GAIN = 0.15f;

    private final ToneGenerator toneGenerator = new ToneGenerator();
    private final List<PlayingTone> activeTones = new ArrayList<>();

    public void playFrequency(float frequency) {
        AudioBuffer buffer = new AudioBuffer(
                toneGenerator.generateSineWave(frequency, NOTE_DURATION_SECONDS, SAMPLE_RATE),
                SAMPLE_RATE,
                false
        );

        AudioSource source = new AudioSource();
        source.setBuffer(buffer);
        source.setLooping(false);
        source.setGain(NOTE_GAIN);
        source.play();

        activeTones.add(new PlayingTone(buffer, source));
    }

    public void update() {
        Iterator<PlayingTone> iterator = activeTones.iterator();

        while (iterator.hasNext()) {
            PlayingTone playingTone = iterator.next();

            if (!playingTone.source.isPlaying()) {
                playingTone.source.cleanup();
                playingTone.buffer.cleanup();
                iterator.remove();
            }
        }
    }

    public void cleanup() {
        for (PlayingTone playingTone : activeTones) {
            playingTone.source.cleanup();
            playingTone.buffer.cleanup();
        }
        activeTones.clear();
    }

    private record PlayingTone(AudioBuffer buffer, AudioSource source) {}
}