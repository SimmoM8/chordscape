package com.benjaminsimmons.chordscape.game.editor;

import com.benjaminsimmons.chordscape.engine.graphics.Mesh;
import com.benjaminsimmons.chordscape.engine.graphics.UiRenderer;
import com.benjaminsimmons.chordscape.engine.graphics.UiShaderProgram;
import com.benjaminsimmons.chordscape.game.music.LocalComposition;

public class MusicEditor {

    private boolean open = false;
    private LocalComposition composition;

    private Mesh backgroundMesh;
    private Mesh gridMesh;
    private Mesh noteMesh;

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

    public Mesh getBackgroundMesh() {
        return backgroundMesh;
    }

    public Mesh getGridMesh() {
        return gridMesh;
    }

    public Mesh getNoteMesh() {
        return noteMesh;
    }

    public void rebuildMeshes() {
        cleanupMeshes();

        if (composition == null) {
            return;
        }

        backgroundMesh = pianoRollMesher.buildBackgroundMesh();
        gridMesh = pianoRollMesher.buildGridMesh(composition);
        noteMesh = pianoRollMesher.buildNoteMesh(composition);
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