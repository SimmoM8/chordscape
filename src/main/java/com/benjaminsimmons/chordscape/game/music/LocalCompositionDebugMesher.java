package com.benjaminsimmons.chordscape.game.music;

import com.benjaminsimmons.chordscape.engine.graphics.Mesh;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class LocalCompositionDebugMesher {

    public Mesh buildSampleOutline(LocalComposition composition) {
        if (composition == null) {
            return null;
        }

        float left = composition.getAnchorCellX();
        float right = left + composition.getWidthInCells();
        float bottom = composition.getAnchorCellY();
        float top = bottom + composition.getHeightInCells();

        float r = 1.0f;
        float g = 1.0f;
        float b = 1.0f;

        List<Float> vertices = new ArrayList<>();

        addLine(vertices, left, bottom, right, bottom, r, g, b);
        addLine(vertices, right, bottom, right, top, r, g, b);
        addLine(vertices, right, top, left, top, r, g, b);
        addLine(vertices, left, top, left, bottom, r, g, b);

        return new Mesh(toFloatArray(vertices), GL11.GL_LINES);
    }

    private void addLine(List<Float> vertices,
                         float x1, float y1,
                         float x2, float y2,
                         float r, float g, float b) {
        vertices.add(x1);
        vertices.add(y1);
        vertices.add(r);
        vertices.add(g);
        vertices.add(b);

        vertices.add(x2);
        vertices.add(y2);
        vertices.add(r);
        vertices.add(g);
        vertices.add(b);
    }

    private float[] toFloatArray(List<Float> vertices) {
        float[] result = new float[vertices.size()];
        for (int i = 0; i < vertices.size(); i++) {
            result[i] = vertices.get(i);
        }
        return result;
    }
}