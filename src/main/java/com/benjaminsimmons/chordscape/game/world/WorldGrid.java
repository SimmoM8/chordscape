package com.benjaminsimmons.chordscape.game.world;

import java.util.ArrayList;
import java.util.List;

public class WorldGrid {
    private final int width;
    private final int height;
    private final float cellSize;
    private final Cell[][] cells;

    private final int minCellX;
    private final int maxCellX;
    private final int minCellY;
    private final int maxCellY;

    public WorldGrid(int width, int height, float cellSize) {
        this.width = width;
        this.height = height;
        this.cellSize = cellSize;
        this.cells = new Cell[width][height];

        this.minCellX = -width / 2;
        this.maxCellX = minCellX + width - 1;
        this.minCellY = -height / 2;
        this.maxCellY = minCellY + height - 1;

        for (int cellY = minCellY; cellY <= maxCellY; cellY++) {
            for (int cellX = minCellX; cellX <= maxCellX; cellX++) {
                int arrayCol = toArrayCol(cellX);
                int arrayRow = toArrayRow(cellY);
                cells[arrayCol][arrayRow] = new Cell(cellX, cellY);
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getCellSize() {
        return cellSize;
    }

    public int getMinCellX() {
        return minCellX;
    }

    public int getMaxCellX() {
        return maxCellX;
    }

    public int getMinCellY() {
        return minCellY;
    }

    public int getMaxCellY() {
        return maxCellY;
    }

    public boolean hasCell(int cellX, int cellY) {
        return cellX >= minCellX && cellX <= maxCellX
                && cellY >= minCellY && cellY <= maxCellY;
    }

    public Cell getCell(int cellX, int cellY) {
        if (!hasCell(cellX, cellY)) {
            return null;
        }

        return cells[toArrayCol(cellX)][toArrayRow(cellY)];
    }

    public Cell findCellAtWorldCoordinate(float worldX, float worldY) {
        int cellX = (int) Math.floor(worldX / cellSize);
        int cellY = (int) Math.floor(worldY / cellSize);

        return getCell(cellX, cellY);
    }

    public List<Cell> getCellsAround(int centerCellX, int centerCellY, int radius) {
        List<Cell> result = new ArrayList<>();

        for (int cellY = centerCellY - radius; cellY <= centerCellY + radius; cellY++) {
            for (int cellX = centerCellX - radius; cellX <= centerCellX + radius; cellX++) {
                Cell cell = getCell(cellX, cellY);
                if (cell != null) {
                    result.add(cell);
                }
            }
        }

        return result;
    }

    public List<Cell> getCellsAround(Cell centerCell, int radius) {
        if (centerCell == null) {
            return new ArrayList<>();
        }

        return getCellsAround(centerCell.getGridX(), centerCell.getGridY(), radius);
    }

    private int toArrayCol(int cellX) {
        return cellX - minCellX;
    }

    private int toArrayRow(int cellY) {
        return cellY - minCellY;
    }
}
