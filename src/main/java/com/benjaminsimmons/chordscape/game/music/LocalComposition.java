package com.benjaminsimmons.chordscape.game.music;

import java.util.ArrayList;
import java.util.List;

public class LocalComposition {
    private final int anchorCellX;
    private final int anchorCellY;
    private final int widthInCells;
    private final int heightInCells;

    private final List<CompositionSlot> slots = new ArrayList<>();

    private int playerStartSlot = 0;
    private int loopLengthInTimeSlots = 0;

    public LocalComposition(int anchorCellX, int anchorCellY, int widthInCells, int heightInCells) {
        this.anchorCellX = anchorCellX;
        this.anchorCellY = anchorCellY;
        this.widthInCells = widthInCells;
        this.heightInCells = heightInCells;
    }

    public int getAnchorCellX() {
        return anchorCellX;
    }

    public int getAnchorCellY() {
        return anchorCellY;
    }

    public int getWidthInCells() {
        return widthInCells;
    }

    public int getHeightInCells() {
        return heightInCells;
    }

    public List<CompositionSlot> getSlots() {
        return slots;
    }

    public void addSlot(CompositionSlot slot) {
        slots.add(slot);
    }

    public CompositionSlot getSlotAt(int timeSlot) {
        for (CompositionSlot slot : slots) {
            if (slot.getTimeSlot() == timeSlot) {
                return slot;
            }
        }
        return null;
    }

    public int getPlayerStartSlot() {
        return playerStartSlot;
    }

    public void setPlayerStartSlot(int playerStartSlot) {
        this.playerStartSlot = playerStartSlot;
    }

    public int getLoopLengthInTimeSlots() {
        return loopLengthInTimeSlots;
    }

    public void setLoopLengthInTimeSlots(int loopLengthInTimeSlots) {
        this.loopLengthInTimeSlots = loopLengthInTimeSlots;
    }

    @Override
    public String toString() {
        return "LocalComposition{" +
                "anchor=(" + anchorCellX + ", " + anchorCellY + ")" +
                ", widthInCells=" + widthInCells +
                ", heightInCells=" + heightInCells +
                ", slotCount=" + slots.size() +
                ", loopLengthInTimeSlots=" + loopLengthInTimeSlots +
                '}';
    }
}