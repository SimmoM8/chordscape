package com.benjaminsimmons.chordscape.game.music;

import com.benjaminsimmons.chordscape.game.world.Cell;

import java.util.ArrayList;
import java.util.List;

public class LocalMusicContext {
    private final Cell centerCell;
    private final List<Cell> nearbyCells;

    public LocalMusicContext(Cell centerCell, List<Cell> nearbyCells) {
        this.centerCell = centerCell;
        this.nearbyCells = nearbyCells;
    }

    public Cell getCenterCell() {
        return centerCell;
    }

    public List<Cell> getNearbyCells() {
        return nearbyCells;
    }

    public List<Note> getNearbyNotes() {
        List<Note> notes = new ArrayList<>();

        for (Cell cell : nearbyCells) {
            if (cell.hasNote()) {
                notes.add(cell.getNote());
            }
        }

        return notes;
    }

    public Note getCenterNote() {
        if (centerCell == null || !centerCell.hasNote()) {
            return null;
        }
        return centerCell.getNote();
    }
}