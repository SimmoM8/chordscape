package com.benjaminsimmons.chordscape.game.music;

import com.benjaminsimmons.chordscape.game.entity.Player;
import com.benjaminsimmons.chordscape.game.world.Cell;
import com.benjaminsimmons.chordscape.game.world.SubRegion;
import com.benjaminsimmons.chordscape.game.world.World;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LocalWorldSampler {

    private static final int SUB_REGION_SAMPLE_RADIUS = 1;
    private static final int SAMPLE_WIDTH_IN_SUB_REGIONS = 3;
    private static final int SAMPLE_HEIGHT_IN_SUB_REGIONS = 3;
    private static final int SAMPLE_WIDTH_IN_CELLS = SAMPLE_WIDTH_IN_SUB_REGIONS * SubRegion.SIZE_IN_CELLS;
    private static final int SAMPLE_HEIGHT_IN_CELLS = SAMPLE_HEIGHT_IN_SUB_REGIONS * SubRegion.SIZE_IN_CELLS;

    public LocalComposition build(World world, Player player) {
        SubRegion anchorSubRegion = world.getSubRegionContainingPlayer(player);
        Cell playerCell = world.getCellContainingPlayer(player);

        if (anchorSubRegion == null || playerCell == null) {
            return null;
        }

        List<SubRegion> sampledSubRegions = world.getSubRegionsAround(anchorSubRegion, SUB_REGION_SAMPLE_RADIUS);

        int anchorCellX = anchorSubRegion.getPosX() - SubRegion.SIZE_IN_CELLS;
        int anchorCellY = anchorSubRegion.getPosY() - SubRegion.SIZE_IN_CELLS;

        LocalComposition composition = new LocalComposition(
                anchorCellX,
                anchorCellY,
                SAMPLE_WIDTH_IN_CELLS,
                SAMPLE_HEIGHT_IN_CELLS
        );

        List<Cell> sampledCells = new ArrayList<>();

        for (SubRegion subRegion : sampledSubRegions) {
            for (Cell cell : subRegion.getCells()) {
                int relativeX = cell.getCellX() - anchorCellX;
                int relativeY = cell.getCellY() - anchorCellY;

                if (relativeX < 0 || relativeX >= SAMPLE_WIDTH_IN_CELLS
                        || relativeY < 0 || relativeY >= SAMPLE_HEIGHT_IN_CELLS) {
                    continue;
                }

                sampledCells.add(cell);
            }
        }

        double centerX = anchorCellX + (SAMPLE_WIDTH_IN_CELLS - 1) / 2.0;
        double centerY = anchorCellY + (SAMPLE_HEIGHT_IN_CELLS - 1) / 2.0;

        sampledCells.sort(
                Comparator
                        .comparingDouble((Cell cell) -> squaredDistanceFromPoint(cell, centerX, centerY))
                        .thenComparingDouble(cell -> angleFromPoint(cell, centerX, centerY))
                        .thenComparingInt(Cell::getCellX)
                        .thenComparingInt(Cell::getCellY)
        );

        for (int slotIndex = 0; slotIndex < sampledCells.size(); slotIndex++) {
            Cell cell = sampledCells.get(slotIndex);

            if (cell == playerCell) {
                composition.setPlayerStartSlot(slotIndex);
            }

            composition.addSlot(
                    new CompositionSlot(
                            slotIndex,
                            cell.getCellX(),
                            cell.getCellY(),
                            cell.hasNote() ? cell.getNote() : null
                    )
            );
        }

        composition.setLoopLengthInTimeSlots(sampledCells.size());
        return composition;
    }

    private double squaredDistanceFromPoint(Cell cell, double x, double y) {
        double dx = cell.getCellX() - x;
        double dy = cell.getCellY() - y;
        return dx * dx + dy * dy;
    }

    private double angleFromPoint(Cell cell, double x, double y) {
        double dx = cell.getCellX() - x;
        double dy = cell.getCellY() - y;
        return Math.atan2(dy, dx);
    }
}