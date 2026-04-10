package com.benjaminsimmons.chordscape.game.editor;

import com.benjaminsimmons.chordscape.engine.graphics.Mesh;
import com.benjaminsimmons.chordscape.engine.graphics.UiRenderer;
import com.benjaminsimmons.chordscape.engine.graphics.UiShaderProgram;
import com.benjaminsimmons.chordscape.game.music.CompositionEvent;
import com.benjaminsimmons.chordscape.game.music.CompositionSlot;
import com.benjaminsimmons.chordscape.game.music.LocalComposition;
import com.benjaminsimmons.chordscape.game.music.Note;

public class MusicEditor {

    private boolean open = false;
    private LocalComposition composition;

    private Mesh backgroundMesh;
    private Mesh gridMesh;
    private Mesh noteMesh;

    private float left = -0.95f;
    private float right = 0.95f;
    private float bottom = -0.95f;
    private float top = -0.35f;
    private int pitchRowCount = 12;

    private final PianoRollMesher pianoRollMesher = new PianoRollMesher();

    public void open(LocalComposition composition) {
        this.open = true;
        this.composition = composition;
        rebuildMeshes();
    }

    public void close() {
        this.open = false;
    }

    public boolean isOpen() {
        return open;
    }

    public LocalComposition getComposition() {
        return composition;
    }

    public void setComposition(LocalComposition composition) {
        this.composition = composition;
        if (open) {
            rebuildMeshes();
        }
    }

    public float getLeft() {
        return left;
    }

    public float getRight() {
        return right;
    }

    public float getBottom() {
        return bottom;
    }

    public float getTop() {
        return top;
    }

    public int getPitchRowCount() {
        return pitchRowCount;
    }

    public Mesh getBackgroundMesh() {
        return backgroundMesh;
    }

    public Mesh getGridMesh() {
        return gridMesh;
    }

    public Mesh getNoteMesh() {
        return noteMesh;
    }

    public boolean containsPoint(float x, float y) {
        return x >= left && x <= right && y >= bottom && y <= top;
    }

    public float screenToUiX(double mouseX, int windowWidth) {
        return (float) ((mouseX / windowWidth) * 2.0 - 1.0);
    }

    public float screenToUiY(double mouseY, int windowHeight) {
        return (float) (1.0 - (mouseY / windowHeight) * 2.0);
    }

    public int getTimeSlotAt(float uiX) {
        if (composition == null || composition.getLoopLengthInTimeSlots() <= 0) {
            return -1;
        }

        float panelWidth = right - left;
        float columnWidth = panelWidth / composition.getLoopLengthInTimeSlots();
        int slot = (int) ((uiX - left) / columnWidth);

        if (slot < 0 || slot >= composition.getLoopLengthInTimeSlots()) {
            return -1;
        }

        return slot;
    }

    public int getPitchAt(float uiY) {
        float panelHeight = top - bottom;
        float rowHeight = panelHeight / pitchRowCount;
        int pitch = (int) ((uiY - bottom) / rowHeight);

        if (pitch < 0 || pitch >= pitchRowCount) {
            return -1;
        }

        return pitch;
    }

    public void pencilAt(int timeSlot, int pitch) {
        if (composition == null) {
            return;
        }

        if (timeSlot < 0 || timeSlot >= composition.getLoopLengthInTimeSlots()) {
            return;
        }

        if (pitch < 0 || pitch >= pitchRowCount) {
            return;
        }

        CompositionSlot slot = composition.getSlotAt(timeSlot);
        if (slot == null) {
            return;
        }

        slot.setNote(new Note(pitch));
        rebuildMeshes();
    }

    public void rebuildMeshes() {
        cleanupMeshes();

        if (composition == null) {
            return;
        }

        backgroundMesh = pianoRollMesher.buildBackgroundMesh(this);
        gridMesh = pianoRollMesher.buildGridMesh(this);
        noteMesh = pianoRollMesher.buildNoteMesh(this);
    }

    public void render(UiRenderer uiRenderer, UiShaderProgram uiShaderProgram) {
        if (!open) {
            return;
        }

        if (backgroundMesh != null) {
            uiRenderer.draw(backgroundMesh, uiShaderProgram);
        }
        if (gridMesh != null) {
            uiRenderer.draw(gridMesh, uiShaderProgram);
        }
        if (noteMesh != null) {
            uiRenderer.draw(noteMesh, uiShaderProgram);
        }
    }

    public void cleanup() {
        cleanupMeshes();
    }

    private void cleanupMeshes() {
        if (backgroundMesh != null) {
            backgroundMesh.cleanup();
            backgroundMesh = null;
        }
        if (gridMesh != null) {
            gridMesh.cleanup();
            gridMesh = null;
        }
        if (noteMesh != null) {
            noteMesh.cleanup();
            noteMesh = null;
        }
    }
}