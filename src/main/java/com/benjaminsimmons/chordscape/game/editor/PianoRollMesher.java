package com.benjaminsimmons.chordscape.game.editor;

import com.benjaminsimmons.chordscape.engine.graphics.Mesh;
import com.benjaminsimmons.chordscape.game.music.CompositionEvent;
import com.benjaminsimmons.chordscape.game.music.CompositionSlot;
import com.benjaminsimmons.chordscape.game.music.LocalComposition;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class PianoRollMesher {

    public Mesh buildBackgroundMesh(MusicEditor editor) {
        List<Float> vertices = new ArrayList<>();

        float r = 0.10f;
        float g = 0.10f;
        float b = 0.10f;

        addQuad(vertices,
                editor.getLeft(),
                editor.getBottom(),
                editor.getRight(),
                editor.getTop(),
                r, g, b);

        return new Mesh(toFloatArray(vertices), GL11.GL_TRIANGLES);
    }

    public Mesh buildGridMesh(MusicEditor editor) {
        LocalComposition composition = editor.getComposition();

        if (composition == null || composition.getLoopLengthInTimeSlots() <= 0) {
            return null;
        }

        List<Float> vertices = new ArrayList<>();

        float r = 0.35f;
        float g = 0.35f;
        float b = 0.35f;

        int timeSlotCount = composition.getLoopLengthInTimeSlots();

        float panelWidth = editor.getRight() - editor.getLeft();
        float panelHeight = editor.getTop() - editor.getBottom();

        float columnWidth = panelWidth / timeSlotCount;
        float rowHeight = panelHeight / editor.getPitchRowCount();

        for (int i = 0; i <= timeSlotCount; i++) {
            float x = editor.getLeft() + i * columnWidth;
            addLine(vertices, x, editor.getBottom(), x, editor.getTop(), r, g, b);
        }

        for (int i = 0; i <= editor.getPitchRowCount(); i++) {
            float y = editor.getBottom() + i * rowHeight;
            addLine(vertices, editor.getLeft(), y, editor.getRight(), y, r, g, b);
        }

        return new Mesh(toFloatArray(vertices), GL11.GL_LINES);
    }

    public Mesh buildNoteMesh(MusicEditor editor) {
        LocalComposition composition = editor.getComposition();

        if (composition == null || composition.getLoopLengthInTimeSlots() <= 0) {
            return null;
        }

        List<Float> vertices = new ArrayList<>();

        int timeSlotCount = composition.getLoopLengthInTimeSlots();

        float panelWidth = editor.getRight() - editor.getLeft();
        float panelHeight = editor.getTop() - editor.getBottom();

        float columnWidth = panelWidth / timeSlotCount;
        float rowHeight = panelHeight / editor.getPitchRowCount();

        float insetX = columnWidth * 0.08f;
        float insetY = rowHeight * 0.10f;

        for (CompositionSlot slot : composition.getSlots()) {
            if (!slot.hasNote()) {
                continue;
            }

            int timeSlot = slot.getTimeSlot();
            int pitch = slot.getNote().getPitch();
            int duration = 1;

            if (timeSlot < 0 || timeSlot >= timeSlotCount) {
                continue;
            }

            if (pitch < 0 || pitch >= editor.getPitchRowCount()) {
                continue;
            }

            float left = editor.getLeft() + timeSlot * columnWidth + insetX;
            float right = editor.getLeft() + (timeSlot + duration) * columnWidth - insetX;

            float bottom = editor.getBottom() + pitch * rowHeight + insetY;
            float top = editor.getBottom() + (pitch + 1) * rowHeight - insetY;

            float[] color = getPitchColor(pitch);
            addQuad(vertices, left, bottom, right, top, color[0], color[1], color[2]);
        }

        return new Mesh(toFloatArray(vertices), GL11.GL_TRIANGLES);
    }

    private void addQuad(List<Float> vertices,
                         float left, float bottom,
                         float right, float top,
                         float r, float g, float b) {
        addVertex(vertices, left, top, r, g, b);
        addVertex(vertices, left, bottom, r, g, b);
        addVertex(vertices, right, bottom, r, g, b);

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