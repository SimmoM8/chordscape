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

    public boolean isMouseButtonDown(int button) {
        return GLFW.glfwGetMouseButton(windowHandle, button) == GLFW.GLFW_PRESS;
    }

    public double getMouseX() {
        double[] x = new double[1];
        double[] y = new double[1];
        GLFW.glfwGetCursorPos(windowHandle, x, y);
        return x[0];
    }

    public double getMouseY() {
        double[] x = new double[1];
        double[] y = new double[1];
        GLFW.glfwGetCursorPos(windowHandle, x, y);
        return y[0];
    }
}
