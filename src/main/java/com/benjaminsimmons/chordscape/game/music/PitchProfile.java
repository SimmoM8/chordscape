package com.benjaminsimmons.chordscape.game.music;

import java.util.Arrays;

public class PitchProfile {
    public final float[] weights = new float[12];

    public void addPitch(int pitch, float amount) {
        if (pitch < 0 || pitch > 11) {
            throw new IllegalArgumentException("Pitch must be between 0 and 11 inclusive.");
        }
        weights[pitch] += amount;
    }

    public float getWeight(int pitch) {
        if (pitch < 0 || pitch > 11) {
            throw new IllegalArgumentException("Pitch must be between 0 and 11 inclusive.");
        }
        return weights[pitch];
    }

    public float[] getWeights() {
        return weights.clone();
    }

    public void normalise() {
        float totalWeight = 0.0f;

        for (float weight : weights) {
            totalWeight += weight;
        }

        if (totalWeight == 0.0f) {
            return;
        }

        for (int i = 0; i < weights.length; i++) {
            weights[i] /= totalWeight;
        }
    }

    public int getStrongestPitch() {
        int strongestPitch = 0;
        float strongestWeight = weights[0];

        for (int i = 1; i < weights.length; i++) {
            if (weights[i] > strongestWeight) {
                strongestWeight = weights[i];
                strongestPitch = i;
            }
        }

        return strongestPitch;
    }

    public boolean isEmpty() {
        for (float weight : weights) {
            if (weight > 0.0f) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "PitchProfile: " +
                "weights=" + Arrays.toString(weights);
    }
}