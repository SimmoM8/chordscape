package com.benjaminsimmons.chordscape.engine.input;

import org.lwjgl.glfw.GLFW;

public class Input {
    private final long windowHandle;

    public Input(long windowHandle) {
        this.windowHandle = windowHandle;
    }

    public boolean isKeyDown(int key) {
        return GLFW.glfwGetKey(windowHandle, key) == GLFW.GLFW_PRESS;
    }
}
