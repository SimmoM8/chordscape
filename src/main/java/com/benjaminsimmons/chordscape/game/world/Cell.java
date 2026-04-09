package com.benjaminsimmons.chordscape.game.world;

import com.benjaminsimmons.chordscape.game.music.Note;

public class Cell {
    private final int gridX;
    private final int gridY;

    private Note note;

    private CellState state = CellState.EMPTY;

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

    public boolean isEmpty() {
        return state == CellState.EMPTY;
    }

    public boolean isGenerated() {
        return state == CellState.GENERATED;
    }

    public boolean isAuthored() {
        return state == CellState.AUTHORED;
    }

    public Note getNote() {
        return note;
    }

    public CellState getState() {
        return state;
    }

    public void setGeneratedNote(Note note) {
        this.note = note;
        this.state = CellState.GENERATED;
    }

    public void setAuthoredNote(Note note) {
        this.note = note;
        this.state = CellState.AUTHORED;
    }

    public void clearNote() {
        this.note = null;
        this.state = CellState.AUTHORED_EMPTY;
    }

    public float getInfluence() {
        return switch (state) {
            case EMPTY -> 0.0f;
            case GENERATED -> 0.7f;
            case AUTHORED -> 1.2f;
            case AUTHORED_EMPTY -> 0.3F;
        };
    }

    public float getInfluenceResistance() {
        return switch (state) {
            case EMPTY -> 0.0f;
            case GENERATED -> 0.35f;
            case AUTHORED -> 1.5f;
            case AUTHORED_EMPTY -> 1.0F;
        };
    }

    @Override
    public String toString() {
        return "Cell (" + gridX + ", " + gridY + ")" + (hasNote() ? " has Note: " + note.getPitch() : " has no Note");
    }
}
