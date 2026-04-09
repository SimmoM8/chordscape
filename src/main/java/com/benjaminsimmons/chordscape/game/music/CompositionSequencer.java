package com.benjaminsimmons.chordscape.game.music;

import com.benjaminsimmons.chordscape.engine.audio.TonePlayer;

public class CompositionSequencer {

    private static final float SECONDS_PER_TIME_SLOT = 0.25f;

    private final TonePlayer tonePlayer;
    private final PitchToFrequencyMapper pitchToFrequencyMapper;

    private LocalComposition composition;
    private float timer = 0.0f;
    private int currentTimeSlot = 0;

    public CompositionSequencer(TonePlayer tonePlayer) {
        this.tonePlayer = tonePlayer;
        this.pitchToFrequencyMapper = new PitchToFrequencyMapper();
    }

    public void setComposition(LocalComposition composition) {
        this.composition = composition;
        this.timer = 0.0f;
        this.currentTimeSlot = 0;
    }

    public void update(float deltaTime) {
        if (composition == null || composition.getEvents().isEmpty()) {
            return;
        }

        timer += deltaTime;

        while (timer >= SECONDS_PER_TIME_SLOT) {
            timer -= SECONDS_PER_TIME_SLOT;
            playCurrentTimeSlot();
            advanceTimeSlot();
        }
    }

    private void playCurrentTimeSlot() {
        for (CompositionEvent event : composition.getEvents()) {
            if (event.getTimeSlot() == currentTimeSlot) {
                float frequency = pitchToFrequencyMapper.getFrequency(event.getPitch());
                tonePlayer.playFrequency(frequency);
            }
        }
    }

    private void advanceTimeSlot() {
        currentTimeSlot++;

        if (currentTimeSlot >= composition.getWidthInCells()) {
            currentTimeSlot = 0;
        }
    }
}