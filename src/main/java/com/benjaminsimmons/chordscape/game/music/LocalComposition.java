package com.benjaminsimmons.chordscape.game.music;

import java.util.ArrayList;
import java.util.List;

public class LocalComposition {
    private final int anchorCellX;
    private final int anchorCellY;
    private final int widthInCells;
    private final int heightInCells;
    private final List<CompositionEvent> events = new ArrayList<>();

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

    public List<CompositionEvent> getEvents() {
        return events;
    }

    public void addEvent(CompositionEvent event) {
        events.add(event);
    }

    @Override
    public String toString() {
        return "LocalComposition{" +
                "anchor=(" + anchorCellX + ", " + anchorCellY + ")" +
                ", widthInCells=" + widthInCells +
                ", heightInCells=" + heightInCells +
                ", eventCount=" + events.size() +
                '}';
    }
}