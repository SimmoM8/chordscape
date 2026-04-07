package com.benjaminsimmons.chordscape.game.world;

import com.benjaminsimmons.chordscape.engine.graphics.Mesh;
import org.lwjgl.opengl.GL11;

public class WorldGrid {
    private final int width;
    private final int height;
    private final float cellSize;
    private final Cell[][] cells;

    public WorldGrid(int width, int height, float cellSize) {
        this.width = width;
        this.height = height;
        this.cellSize = cellSize;
        this.cells = new Cell[width][height];

        int startX = -width / 2;
        int startY = -height / 2;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                cells[col][row] = new Cell(startX + col, startY + row);
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

    public Cell getCell(int row, int col) {
        if (isOutBounds(row, col)) {
            return null;
        }
        return cells[col][row];
    }

    public boolean isOutBounds(int row, int col) {
        return row < 0 || row >= height || col < 0 || col >= width;
    }

    public Cell getCellAtWorldPosition(float worldX, float worldY) {
        int col = (int) Math.floor(worldX / cellSize + width / 2.0f);
        int row = (int) Math.floor(worldY / cellSize + height / 2.0f);

        if (isOutBounds(row, col)) {
            return null;
        }

        return cells[col][row];
    }
}
