package com.benjaminsimmons.chordscape.game.music;

public class CompositionEvent {
    private final int sourceCellX;
    private final int sourceCellY;
    private int pitch;
    private int timeSlot;
    private int duration;
    private int lane;

    public CompositionEvent(int sourceCellX, int sourceCellY, int pitch, int timeSlot, int duration, int lane) {
        this.sourceCellX = sourceCellX;
        this.sourceCellY = sourceCellY;
        this.pitch = pitch;
        this.timeSlot = timeSlot;
        this.duration = duration;
        this.lane = lane;
    }

    public int getSourceCellX() {
        return sourceCellX;
    }

    public int getSourceCellY() {
        return sourceCellY;
    }

    public int getPitch() {
        return pitch;
    }

    public void setPitch(int pitch) {
        this.pitch = pitch;
    }

    public int getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(int timeSlot) {
        this.timeSlot = timeSlot;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getLane() {
        return lane;
    }

    public void setLane(int lane) {
        this.lane = lane;
    }

    @Override
    public String toString() {
        return "CompositionEvent{" +
                "sourceCell=(" + sourceCellX + ", " + sourceCellY + ")" +
                ", pitch=" + pitch +
                ", timeSlot=" + timeSlot +
                ", duration=" + duration +
                ", lane=" + lane +
                '}';
    }
}