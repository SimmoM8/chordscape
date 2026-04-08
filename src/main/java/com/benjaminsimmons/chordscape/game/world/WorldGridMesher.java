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

        float minX = grid.getMinCellX();
        float maxX = grid.getMaxCellX() + 1.0f;

        float minY = grid.getMinCellY();
        float maxY = grid.getMaxCellY() + 1.0f;

        for (int lineX = grid.getMinCellX(); lineX <= grid.getMaxCellX() + 1; lineX++) {
            float x = lineX;

            index = addVertex(vertices, index, x, minY, greyR, greyG, greyB);
            index = addVertex(vertices, index, x, maxY, greyR, greyG, greyB);
        }

        for (int lineY = grid.getMinCellY(); lineY <= grid.getMaxCellY() + 1; lineY++) {
            float y = lineY;

            index = addVertex(vertices, index, minX, y, greyR, greyG, greyB);
            index = addVertex(vertices, index, maxX, y, greyR, greyG, greyB);
        }

        return new Mesh(vertices, GL11.GL_LINES);
    }

    public Mesh buildColoredCells(WorldGrid grid) {
        List<Float> vertices = new ArrayList<>();

        for (int cellY = grid.getMinCellY(); cellY <= grid.getMaxCellY(); cellY++) {
            for (int cellX = grid.getMinCellX(); cellX <= grid.getMaxCellX(); cellX++) {
                Cell cell = grid.getCell(cellX, cellY);

                if (cell == null || !cell.hasNote()) {
                    continue;
                }

                float x = cell.getCellX();
                float y = cell.getCellY();

                float inset = 0.08f;
                float left = x + inset;
                float right = x + 1.0f - inset;
                float bottom = y + inset;
                float top = y + 1.0f - inset;

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