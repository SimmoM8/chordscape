package com.benjaminsimmons.chordscape.game.world;

import com.benjaminsimmons.chordscape.game.music.Note;

public class Cell {
    private final int gridX;
    private final int gridY;

    private Note note;

    public Cell(int gridX, int gridY) {
        this.gridX = gridX;
        this.gridY = gridY;
    }

    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public boolean hasNote() {
        return note != null;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public void clearNote() {
        this.note = null;
    }
}
