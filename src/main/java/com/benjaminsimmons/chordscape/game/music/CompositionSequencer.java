package com.benjaminsimmons.chordscape.game.music;

import com.benjaminsimmons.chordscape.engine.audio.TonePlayer;

public class CompositionSequencer {

    private static final float SECONDS_PER_TIME_SLOT = 0.1f;

    private final TonePlayer tonePlayer;
    private final PitchToFrequencyMapper pitchToFrequencyMapper;

    private LocalComposition composition;
    private float timer = 0.0f;
    private int currentTimeSlot = 0;
    private int startOffset = 0;

    public CompositionSequencer(TonePlayer tonePlayer) {
        this.tonePlayer = tonePlayer;
        this.pitchToFrequencyMapper = new PitchToFrequencyMapper();
    }

    public void setComposition(LocalComposition newComposition) {
        if (newComposition == null || newComposition.getLoopLengthInTimeSlots() <= 0) {
            this.composition = newComposition;
            this.currentTimeSlot = 0;
            this.timer = 0.0f;
            return;
        }

        if (this.composition == null || this.composition.getLoopLengthInTimeSlots() <= 0) {
            this.composition = newComposition;
            this.currentTimeSlot = 0;
            return;
        }

        int oldLength = this.composition.getLoopLengthInTimeSlots();
        int newLength = newComposition.getLoopLengthInTimeSlots();

        float slotProgress = (currentTimeSlot + (timer / SECONDS_PER_TIME_SLOT)) / oldLength;
        slotProgress = slotProgress - (float) Math.floor(slotProgress);

        this.composition = newComposition;
        this.currentTimeSlot = Math.min((int) (slotProgress * newLength), Math.max(newLength - 1, 0));
    }

    public void update(float deltaTime) {
        if (composition == null || composition.getEvents().isEmpty() || composition.getLoopLengthInTimeSlots() <= 0) {
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
        int actualSlot = (startOffset + currentTimeSlot) % composition.getLoopLengthInTimeSlots();

        for (CompositionEvent event : composition.getEvents()) {
            if (event.getTimeSlot() == actualSlot) {
                float frequency = pitchToFrequencyMapper.getFrequency(event.getPitch());
                tonePlayer.playFrequency(frequency);
            }
        }
    }

    private void advanceTimeSlot() {
        currentTimeSlot++;

        if (currentTimeSlot >= composition.getLoopLengthInTimeSlots()) {
            currentTimeSlot = 0;
        }
    }

    public void setStartOffset(int startOffset) {
        if (composition == null || composition.getLoopLengthInTimeSlots() <= 0) {
            this.startOffset = 0;
            return;
        }

        int loopLength = composition.getLoopLengthInTimeSlots();
        this.startOffset = ((startOffset % loopLength) + loopLength) % loopLength;
    }
}