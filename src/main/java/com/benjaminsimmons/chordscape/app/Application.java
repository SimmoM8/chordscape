package com.benjaminsimmons.chordscape.app;

import com.benjaminsimmons.chordscape.graphics.Renderer;
import com.benjaminsimmons.chordscape.graphics.ShaderProgram;
import org.lwjgl.glfw.GLFW;

public class Application {

    private final Window window;
    private final Renderer renderer;
    private final ShaderProgram shaderProgram;

    public Application() {
        this.window = new Window(720, 720, "Chordscape");
        this.renderer = new Renderer();
        this.shaderProgram = new ShaderProgram();
    }

    public void run() {
        init();
        loop();
        cleanup();
    }

    private void init() {
        window.init();
        shaderProgram.init();
    }

    private void loop() {
        float lastTime = (float) GLFW.glfwGetTime();
        while (!window.shouldClose()) {
            window.pollEvents();
            float currentTime = (float) GLFW.glfwGetTime();
            float deltaTime = currentTime - lastTime;
            lastTime = currentTime;

            // Process window/input events
            window.pollEvents();

            render();

            // Present the rendered frame
            window.swapBuffers();
        }
    }

    private void render() {
        renderer.clear(0.08f, 0.12f, 0.18f, 1.0f);
    }

    private void cleanup() {
        shaderProgram.cleanup();
        window.cleanup();
    }

    public Window getWindow() {
        return window;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public ShaderProgram getShaderProgram() {
        return shaderProgram;
    }
}