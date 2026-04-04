package com.benjaminsimmons.chordscape;

import com.benjaminsimmons.chordscape.graphics.Renderer;
import com.benjaminsimmons.chordscape.graphics.Shader;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.*;
import org.lwjgl.system.MemoryUtil;

public class Main {
    private Renderer renderer;
    private Shader shader;
    private long window;

    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        init();
        loop();
        cleanup();
    }

    private void init() {
        // Step 1: Initialize GLFW
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Failed to initialize GLFW");
        }

        // Step 2: Tell GLFW what kind of window/context we want
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE); // hide window until we show it
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE); // allow user to resize window
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);

        // Step 3: Create the window
        window = GLFW.glfwCreateWindow(720, 720, "Chordscape", MemoryUtil.NULL, MemoryUtil.NULL);
        if (window == MemoryUtil.NULL) {
            throw new RuntimeException("Failed to create GLFW window");
        }

        // Step 4: Make this window's OpenGL context current
        GLFW.glfwMakeContextCurrent(window);

        // Step 5: Create OpenGL capabilities for the current context
        GL.createCapabilities();

        // Step 6: Enable v-sync
        GLFW.glfwSwapInterval(1);

        // Step 7: Show the window
        GLFW.glfwShowWindow(window);

        renderer = new Renderer();
        renderer.init();

        shader = new Shader();
        shader.init();
    }

    private void loop() {
        float lastTime = (float) GLFW.glfwGetTime();
        while (!GLFW.glfwWindowShouldClose(window)) {
            float currentTime = (float) GLFW.glfwGetTime();
            float deltaTime = currentTime - lastTime;
            lastTime = currentTime;

            // Process window/input events
            GLFW.glfwPollEvents();

            update(deltaTime);

            // For now we are not drawing anything fancy yet
            // Later this is where rendering will happen
            render();

            // Present the rendered frame
            GLFW.glfwSwapBuffers(window);
        }
    }

    private void update(float deltaTime) {
        // Update game logic here (e.g., handle input, update object states)
        renderer.updateCircleColors(deltaTime);
    }

    private void render() {
        GL11.glClearColor(0.08f, 0.12f, 0.18f, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        shader.bind();
        renderer.render();
    }

    private void cleanup() {
        renderer.cleanup();
        shader.cleanup();

        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }
}