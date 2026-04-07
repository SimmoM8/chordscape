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

    public Mesh createMesh() {
        int verticalLineCount = width + 1;
        int horizontalLineCount = height+ 1;
        int totalLineCount = verticalLineCount + horizontalLineCount;

        float[] vertices = new float[totalLineCount * 2 * 5];
        int index = 0;

        float greyR = 0.35f;
        float greyG = 0.35f;
        float greyB = 0.35f;

        float left = -(width * cellSize) / 2.0f;
        float right = left + width * cellSize;

        float bottom = -(height * cellSize) / 2.0f;
        float top = bottom + height * cellSize;

        for (int col = 0; col <= width; col++) {
            float x = left + col * cellSize;

            vertices[index++] = x;
            vertices[index++] = bottom;
            vertices[index++] = greyR;
            vertices[index++] = greyG;
            vertices[index++] = greyB;

            vertices[index++] = x;
            vertices[index++] = top;
            vertices[index++] = greyR;
            vertices[index++] = greyG;
            vertices[index++] = greyB;
        }

        for (int row = 0; row <= height; row++) {
            float y = bottom + row * cellSize;

            vertices[index++] = left;
            vertices[index++] = y;
            vertices[index++] = greyR;
            vertices[index++] = greyG;
            vertices[index++] = greyB;

            vertices[index++] = right;
            vertices[index++] = y;
            vertices[index++] = greyR;
            vertices[index++] = greyG;
            vertices[index++] = greyB;
        }

        return new Mesh(vertices, GL11.GL_LINES);
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
        if (row < 0 || row >= height || col < 0 || col >= width) {
            return null;
        }
        return cells[col][row];
    }
}
