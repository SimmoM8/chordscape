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

    public int getCellX() {
        return gridX;
    }

    public int getCellY() {
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

    @Override
    public String toString() {
        return "Cell (" + gridX + ", " + gridY + ")" + (hasNote() ? " has Note: " + note.getPitch() : " has no Note");
    }
}
