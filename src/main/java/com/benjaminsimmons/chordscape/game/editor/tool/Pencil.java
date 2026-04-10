package com.benjaminsimmons.chordscape.game.editor.tool;

import com.benjaminsimmons.chordscape.engine.input.Input;
import com.benjaminsimmons.chordscape.game.editor.MusicEditor;
import org.lwjgl.glfw.GLFW;

public class Pencil implements MusicEditorTool {

    private boolean leftMouseWasDown = false;

    @Override
    public boolean handleInput(Input input, MusicEditor editor, int windowWidth, int windowHeight) {
        if (!editor.isOpen()) {
            leftMouseWasDown = false;
            return false;
        }

        boolean leftMouseDown = input.isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT);
        boolean changed = false;

        if (leftMouseDown && !leftMouseWasDown) {
            float uiX = editor.screenToUiX(input.getMouseX(), windowWidth);
            float uiY = editor.screenToUiY(input.getMouseY(), windowHeight);

            if (editor.containsPoint(uiX, uiY)) {
                int timeSlot = editor.getTimeSlotAt(uiX);
                int pitch = editor.getPitchAt(uiY);

                if (timeSlot >= 0 && pitch >= 0) {
                    editor.pencilAt(timeSlot, pitch);
                    changed = true;
                }
            }
        }

        leftMouseWasDown = leftMouseDown;
        return changed;
    }
}