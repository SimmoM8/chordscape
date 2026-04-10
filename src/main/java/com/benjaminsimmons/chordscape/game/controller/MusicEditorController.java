package com.benjaminsimmons.chordscape.game.controller;

import com.benjaminsimmons.chordscape.engine.input.Input;
import com.benjaminsimmons.chordscape.game.editor.MusicEditor;
import org.lwjgl.glfw.GLFW;

public class MusicEditorController {

    private final Input input;
    private final MusicEditor musicEditor;

    private boolean toggleWasDown = false;

    public MusicEditorController(Input input, MusicEditor musicEditor) {
        this.input = input;
        this.musicEditor = musicEditor;
    }

    public void handleInput() {
        boolean toggleDown = input.isKeyDown(GLFW.GLFW_KEY_TAB);

        if (toggleDown && !toggleWasDown) {
            if (musicEditor.isOpen()) {
                musicEditor.close();
            } else {
                // opening should probably be handled from Application
                // when current LocalComposition is available
            }
        }

        toggleWasDown = toggleDown;
    }
}