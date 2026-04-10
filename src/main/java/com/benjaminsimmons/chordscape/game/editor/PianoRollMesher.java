package com.benjaminsimmons.chordscape.game.editor;

import com.benjaminsimmons.chordscape.engine.graphics.Mesh;
import com.benjaminsimmons.chordscape.game.music.CompositionEvent;
import com.benjaminsimmons.chordscape.game.music.LocalComposition;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class PianoRollMesher {

    private static final float PANEL_LEFT = -0.95f;
    private static final float PANEL_RIGHT = 0.95f;
    private static final float PANEL_BOTTOM = -0.95f;
    private static final float PANEL_TOP = -0.35f;

    private static final int PITCH_ROW_COUNT = 12;

    public Mesh buildBackgroundMesh() {
        List<Float> vertices = new ArrayList<>();

        float r = 0.10f;
        float g = 0.10f;
        float b = 0.10f;

        addQuad(vertices, PANEL_LEFT, PANEL_BOTTOM, PANEL_RIGHT, PANEL_TOP, r, g, b);

        return new Mesh(toFloatArray(vertices), GL11.GL_TRIANGLES);
    }

    public Mesh buildGridMesh(LocalComposition composition) {
        if (composition == null || composition.getLoopLengthInTimeSlots() <= 0) {
            return null;
        }

        List<Float> vertices = new ArrayList<>();

        float r = 0.35f;
        float g = 0.35f;
        float b = 0.35f;

        int timeSlotCount = composition.getLoopLengthInTimeSlots();

        float panelWidth = PANEL_RIGHT - PANEL_LEFT;
        float panelHeight = PANEL_TOP - PANEL_BOTTOM;

        float columnWidth = panelWidth / timeSlotCount;
        float rowHeight = panelHeight / PITCH_ROW_COUNT;

        // Vertical grid lines
        for (int i = 0; i <= timeSlotCount; i++) {
            float x = PANEL_LEFT + i * columnWidth;
            addLine(vertices, x, PANEL_BOTTOM, x, PANEL_TOP, r, g, b);
        }

        // Horizontal grid lines
        for (int i = 0; i <= PITCH_ROW_COUNT; i++) {
            float y = PANEL_BOTTOM + i * rowHeight;
            addLine(vertices, PANEL_LEFT, y, PANEL_RIGHT, y, r, g, b);
        }

        return new Mesh(toFloatArray(vertices), GL11.GL_LINES);
    }

    public Mesh buildNoteMesh(LocalComposition composition) {
        if (composition == null || composition.getLoopLengthInTimeSlots() <= 0) {
            return null;
        }

        List<Float> vertices = new ArrayList<>();

        int timeSlotCount = composition.getLoopLengthInTimeSlots();

        float panelWidth = PANEL_RIGHT - PANEL_LEFT;
        float panelHeight = PANEL_TOP - PANEL_BOTTOM;

        float columnWidth = panelWidth / timeSlotCount;
        float rowHeight = panelHeight / PITCH_ROW_COUNT;

        float insetX = columnWidth * 0.08f;
        float insetY = rowHeight * 0.10f;

        for (CompositionEvent event : composition.getEvents()) {
            int timeSlot = event.getTimeSlot();
            int pitch = event.getPitch();
            int duration = event.getDuration();

            if (timeSlot < 0 || timeSlot >= timeSlotCount) {
                continue;
            }

            if (pitch < 0 || pitch >= PITCH_ROW_COUNT) {
                continue;
            }

            float left = PANEL_LEFT + timeSlot * columnWidth + insetX;
            float right = PANEL_LEFT + (timeSlot + duration) * columnWidth - insetX;

            float bottom = PANEL_BOTTOM + pitch * rowHeight + insetY;
            float top = PANEL_BOTTOM + (pitch + 1) * rowHeight - insetY;

            float[] color = getPitchColor(pitch);
            addQuad(vertices, left, bottom, right, top, color[0], color[1], color[2]);
        }

        return new Mesh(toFloatArray(vertices), GL11.GL_TRIANGLES);
    }

    private void addQuad(List<Float> vertices,
                         float left, float bottom,
                         float right, float top,
                         float r, float g, float b) {
        // Triangle 1
        addVertex(vertices, left, top, r, g, b);
        addVertex(vertices, left, bottom, r, g, b);
        addVertex(vertices, right, bottom, r, g, b);

        // Triangle 2
        addVertex(vertices, left, top, r, g, b);
        addVertex(vertices, right, bottom, r, g, b);
        addVertex(vertices, right, top, r, g, b);
    }

    private void addLine(List<Float> vertices,
                         float x1, float y1,
                         float x2, float y2,
                         float r, float g, float b) {
        addVertex(vertices, x1, y1, r, g, b);
        addVertex(vertices, x2, y2, r, g, b);
    }

    private void addVertex(List<Float> vertices, float x, float y, float r, float g, float b) {
        vertices.add(x);
        vertices.add(y);
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

    private float[] getPitchColor(int pitch) {
        return switch (pitch) {
            case 0 -> new float[]{1.0f, 0.0f, 0.0f};
            case 1 -> new float[]{1.0f, 0.5f, 0.0f};
            case 2 -> new float[]{1.0f, 1.0f, 0.0f};
            case 3 -> new float[]{0.5f, 1.0f, 0.0f};
            case 4 -> new float[]{0.0f, 1.0f, 0.0f};
            case 5 -> new float[]{0.0f, 1.0f, 0.5f};
            case 6 -> new float[]{0.0f, 1.0f, 1.0f};
            case 7 -> new float[]{0.0f, 0.5f, 1.0f};
            case 8 -> new float[]{0.3f, 0.3f, 1.0f};
            case 9 -> new float[]{0.7f, 0.3f, 1.0f};
            case 10 -> new float[]{1.0f, 0.3f, 0.8f};
            case 11 -> new float[]{0.8f, 0.8f, 0.8f};
            default -> new float[]{1.0f, 1.0f, 1.0f};
        };
    }
}