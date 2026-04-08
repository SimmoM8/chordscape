package com.benjaminsimmons.chordscape.game.world;

import com.benjaminsimmons.chordscape.game.music.PitchProfile;

import java.util.ArrayList;
import java.util.List;

public class Region {
    public static final int SIZE_IN_SUB_REGIONS = 4;

    private final int posX;
    private final int posY;
    private final List<SubRegion> subRegions = new ArrayList<>();
    private PitchProfile pitchProfile;

    public Region(int posX, int posY) {
        int regionSizeInCells = SIZE_IN_SUB_REGIONS * SubRegion.SIZE_IN_CELLS;

        // Ensure the region's position aligns with the world grid based on the region world size
        if (posX % regionSizeInCells != 0 || posY % regionSizeInCells != 0) {
            throw new IllegalArgumentException(
                    "Invalid Region coordinates (" + posX + ", " + posY + "). Position must align to region world size of " + regionSizeInCells
            );
        }

        this.posX = posX;
        this.posY = posY;

        this.pitchProfile = calculatePitchProfile();
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public List<SubRegion> getSubRegions() {
        return subRegions;
    }

    public PitchProfile getPitchProfile() {
        return pitchProfile;
    }

    public void addSubRegion(SubRegion subRegion) {
        subRegions.add(subRegion);
    }

    public PitchProfile calculatePitchProfile() {
        PitchProfile profile = new PitchProfile();

        for (SubRegion subRegion : subRegions) {
            PitchProfile subProfile = subRegion.getPitchProfile();

            for (int pitch = 0; pitch < 12; pitch++) {
                profile.addPitch(pitch, subProfile.getWeight(pitch));
            }
        }

        profile.normalise();
        return profile;
    }

    public void refreshPitchProfile() {
        this.pitchProfile = calculatePitchProfile();
    }

    @Override
    public String toString() {
        return "Region (" + posX + ", " + posY + "):\n" +
                "Sub regions: " +
                subRegions.stream()
                        .map(subRegion -> "(" + subRegion.getPosX() + ", " + subRegion.getPosY() + "), ")
                        .reduce("", (a, b) -> a + b ) +
                "\n";
    }
}