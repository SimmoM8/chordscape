package com.benjaminsimmons.chordscape.game.music;

public class CompositionSlot {

    private final int timeSlot;
    private final int sourceCellX;
    private final int sourceCellY;
    private Note note;

    public CompositionSlot(int timeSlot, int sourceCellX, int sourceCellY, Note note) {
        this.timeSlot = timeSlot;
        this.sourceCellX = sourceCellX;
        this.sourceCellY = sourceCellY;
        this.note = note;
    }

    public int getTimeSlot() {
        return timeSlot;
    }

    public int getSourceCellX() {
        return sourceCellX;
    }

    public int getSourceCellY() {
        return sourceCellY;
    }

    public Note getNote() {
        return note;
    }

    public boolean hasNote() {
        return note != null;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public void clearNote() {
        this.note = null;
    }

    @Override
    public String toString() {
        return "CompositionSlot{" +
                "timeSlot=" + timeSlot +
                ", sourceCell=(" + sourceCellX + ", " + sourceCellY + ")" +
                ", note=" + (note != null ? note.getPitch() : "none") +
                '}';
    }
}