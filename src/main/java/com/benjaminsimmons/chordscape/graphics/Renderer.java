package com.benjaminsimmons.chordscape.graphics;

import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

public class Renderer {

    private int vao;
    private int vbo;

    public void init() {
        float[] vertices = {
                // x, y
                -0.5f,  0.5f,
                -0.5f, -0.5f,
                0.5f, -0.5f,

                -0.5f,  0.5f,
                0.5f, -0.5f,
                0.5f,  0.5f
        };

        vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);

        vbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_STATIC_DRAW);

        GL20.glVertexAttribPointer(0, 2, GL15.GL_FLOAT, false, 0, 0);
        GL20.glEnableVertexAttribArray(0);
    }

    public void render() {
        GL30.glBindVertexArray(vao);
        GL30.glDrawArrays(GL30.GL_TRIANGLES, 0, 6);
    }

    public void cleanup() {
        GL15.glDeleteBuffers(vbo);
        GL30.glDeleteVertexArrays(vao);
    }
}