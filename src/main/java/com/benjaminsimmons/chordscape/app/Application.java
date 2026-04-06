package com.benjaminsimmons.chordscape.app;

import com.benjaminsimmons.chordscape.graphics.*;
import org.lwjgl.glfw.GLFW;

public class Application {

    private final Window window;
    private final Renderer renderer;
    private final ShaderProgram shaderProgram;
    private Transform transform;

    private RenderCircle renderCircle;

    private Mesh testMesh;

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

        float[] triangleVertices = {
                // x, y,     r, g, b
                0.0f,  0.5f, 1.0f, 0.0f, 0.0f,
                -0.5f, -0.5f, 0.0f, 1.0f, 0.0f,
                0.5f, -0.5f, 0.0f, 0.0f, 1.0f
        };
        transform = new Transform(0.0f, 0.0f);
        testMesh = new Mesh(triangleVertices, org.lwjgl.opengl.GL11.GL_TRIANGLES);
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

            // Update application state (e.g., move objects, handle input)
            transform.x += 0.5f * deltaTime;

            render();

            // Present the rendered frame
            window.swapBuffers();
        }
    }

    private void render() {
        renderer.clear(0.08f, 0.12f, 0.18f, 1.0f);
        renderer.draw(testMesh, shaderProgram, transform);
    }

    private void cleanup() {
        testMesh.cleanup();
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