package com.benjaminsimmons.chordscape.game.world;

import com.benjaminsimmons.chordscape.game.music.PitchProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SubRegion {
    public static final int SIZE_IN_CELLS = 4;

    private final int posX;
    private final int posY;
    private final List<Cell> cells = new ArrayList<>();
    private PitchProfile pitchProfile;

    public SubRegion(int posX, int posY, WorldGrid grid) {
        if (posX % SIZE_IN_CELLS != 0 || posY % SIZE_IN_CELLS != 0) {
            throw new IllegalArgumentException(
                    "Invalid SubRegion coordinates (" + posX + ", " + posY + "). Position must be a multiple of " + SIZE_IN_CELLS
            );
        }

        this.posX = posX;
        this.posY = posY;

        for (int x = posX; x < posX + SIZE_IN_CELLS; x++) {
            for (int y = posY; y < posY + SIZE_IN_CELLS; y++) {
                Cell cell = grid.getCell(x, y);
                if (cell != null) {
                    cells.add(cell);
                }
            }
        }

        this.pitchProfile = calculatePitchProfile();
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public PitchProfile getPitchProfile() {
        return pitchProfile;
    }

    private PitchProfile calculatePitchProfile() {
        PitchProfile profile = new PitchProfile();

        for (Cell cell : this.cells) {
            if (cell.hasNote()) {
                profile.addPitch(cell.getNote().getPitch(), 1.0f);
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
        return "SubRegion (" + posX + ", " + posY + "):\n" +
                "Cells: " +
                cells.stream()
                        .map(Cell::toString)
                        .collect(Collectors.joining("\n")) +
                "\nPitchProfile: " + pitchProfile;
    }
}