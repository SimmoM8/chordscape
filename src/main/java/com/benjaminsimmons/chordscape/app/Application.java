package com.benjaminsimmons.chordscape.app;

import com.benjaminsimmons.chordscape.game.GameObject;
import com.benjaminsimmons.chordscape.game.TestObject;
import com.benjaminsimmons.chordscape.game.World;
import com.benjaminsimmons.chordscape.graphics.*;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class Application {

    private final Window window;
    private final Renderer renderer;
    private final ShaderProgram shaderProgram;

    private World world;
    private Camera camera;
    private Mesh triangleMesh;

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

        world = new World();
        camera = new Camera(0.0f, -1.0f);

        float[] triangleVertices = {
                // x, y,     r, g, b
                0.0f,  0.5f, 1.0f, 0.0f, 0.0f,
                -0.5f, -0.5f, 0.0f, 1.0f, 0.0f,
                0.5f, -0.5f, 0.0f, 0.0f, 1.0f
        };

        triangleMesh = new Mesh(triangleVertices, GL11.GL_TRIANGLES);

        world.addObject(new TestObject(
                triangleMesh,
                new Transform(-0.5f, 0.0f, 0.25f, 0.25f)
                )
        );

        world.addObject(
                new TestObject(
                        triangleMesh,
                        new Transform(0.0f, 0.0f, 0.8f, 0.8f)
                )
        );
    }

    private void loop() {
        float lastTime = (float) GLFW.glfwGetTime();

        while (!window.shouldClose()) {
            float currentTime = (float) GLFW.glfwGetTime();
            float deltaTime = currentTime - lastTime;
            lastTime = currentTime;

            // Process window/input events
            window.pollEvents();

            // Update application state (e.g., move objects, handle input)
            update(deltaTime);

            render();

            // Present the rendered frame
            window.swapBuffers();
        }
    }

    private void update(float deltaTime) {
        camera.y += 0.1f * deltaTime;
        world.update(deltaTime);
    }

    private void render() {
        renderer.clear(0.08f, 0.12f, 0.18f, 1.0f);
        world.render(renderer, shaderProgram, camera);
    }

    private void cleanup() {
        triangleMesh.cleanup();
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