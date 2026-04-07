package com.benjaminsimmons.chordscape.game.world;

import com.benjaminsimmons.chordscape.engine.graphics.Mesh;
import com.benjaminsimmons.chordscape.game.music.NoteColorMapper;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class WorldGridMesher {
    private final NoteColorMapper noteColorMapper = new NoteColorMapper();

    public Mesh buildGridLines(WorldGrid grid) {
        int verticalLineCount = grid.getWidth() + 1;
        int horizontalLineCount = grid.getHeight() + 1;
        int totalLineCount = verticalLineCount + horizontalLineCount;

        float[] vertices = new float[totalLineCount * 2 * 5];
        int index = 0;

        float greyR = 0.35f;
        float greyG = 0.35f;
        float greyB = 0.35f;

        float cellSize = grid.getCellSize();
        float left = -(grid.getWidth() * cellSize) / 2.0f;
        float right = left + grid.getWidth() * cellSize;
        float bottom = -(grid.getHeight() * cellSize) / 2.0f;
        float top = bottom + grid.getHeight() * cellSize;

        for (int col = 0; col <= grid.getWidth(); col++) {
            float x = left + col * cellSize;
            index = addVertex(vertices, index, x, bottom, greyR, greyG, greyB);
            index = addVertex(vertices, index, x, top, greyR, greyG, greyB);
        }

        for (int row = 0; row <= grid.getHeight(); row++) {
            float y = bottom + row * cellSize;
            index = addVertex(vertices, index, left, y, greyR, greyG, greyB);
            index = addVertex(vertices, index, right, y, greyR, greyG, greyB);
        }

        return new Mesh(vertices, GL11.GL_LINES);
    }

    public Mesh buildColoredCells(WorldGrid grid) {
        List<Float> vertices = new ArrayList<>();
        float cellSize = grid.getCellSize();

        for (int row = 0; row < grid.getHeight(); row++) {
            for (int col = 0; col < grid.getWidth(); col++) {
                Cell cell = grid.getCell(row, col);

                if (cell == null || !cell.hasNote()) {
                    continue;
                }

                float x = cell.getGridX() * cellSize;
                float y = cell.getGridY() * cellSize;

                float inset = cellSize * 0.08f;
                float left = x + inset;
                float right = x + cellSize - inset;
                float bottom = y + inset;
                float top = y + cellSize - inset;

                float[] color = noteColorMapper.getColor(cell.getNote());
                float r = color[0];
                float g = color[1];
                float b = color[2];

                addVertex(vertices, left, top, r, g, b);
                addVertex(vertices, left, bottom, r, g, b);
                addVertex(vertices, right, bottom, r, g, b);

                addVertex(vertices, left, top, r, g, b);
                addVertex(vertices, right, bottom, r, g, b);
                addVertex(vertices, right, top, r, g, b);
            }
        }

        return new Mesh(toFloatArray(vertices), GL11.GL_TRIANGLES);
    }

    private int addVertex(float[] vertices, int index, float x, float y, float r, float g, float b) {
        vertices[index++] = x;
        vertices[index++] = y;
        vertices[index++] = r;
        vertices[index++] = g;
        vertices[index++] = b;
        return index;
    }

    private void addVertex(List<Float> vertices, float x, float y, float r, float g, float b) {
        vertices.add(x);
        vertices.add(y);
        vertices.add(r);
        vertices.add(g);
        vertices.add(b);
    }

    private float[] toFloatArray(List<Float> list) {
        float[] result = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }
}