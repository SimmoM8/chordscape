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

        if (anchorSubRegion == null) {
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

        sampledSubRegions.stream()
                .sorted(Comparator
                        .comparingInt(SubRegion::getPosX)
                        .thenComparingInt(SubRegion::getPosY))
                .forEach(subRegion -> {
                    List<Cell> orderedCells = subRegion.getCells().stream()
                            .sorted(Comparator
                                    .comparingInt(Cell::getCellX)
                                    .thenComparingInt(Cell::getCellY))
                            .toList();

                    sampledCells.addAll(orderedCells);
                });

        for (Cell cell : sampledCells) {
            if (!cell.hasNote()) {
                continue;
            }

            int relativeX = cell.getCellX() - anchorCellX;
            int relativeY = cell.getCellY() - anchorCellY;

            if (relativeX < 0 || relativeX >= SAMPLE_WIDTH_IN_CELLS
                    || relativeY < 0 || relativeY >= SAMPLE_HEIGHT_IN_CELLS) {
                continue;
            }

            CompositionEvent event = new CompositionEvent(
                    cell.getCellX(),
                    cell.getCellY(),
                    cell.getNote().getPitch(),
                    relativeX,
                    1,
                    relativeY
            );

            composition.addEvent(event);
        }

        return composition;
    }
}