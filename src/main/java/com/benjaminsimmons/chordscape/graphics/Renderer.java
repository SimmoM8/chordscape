package com.benjaminsimmons.chordscape.graphics;

import org.lwjgl.opengl.GL11;

public class Renderer {

    public void clear(float r, float g, float b, float a) {
        GL11.glClearColor(r, g, b, a);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
    }

    public void draw(Mesh mesh, ShaderProgram shaderProgram) {
        shaderProgram.bind();
        mesh.render();
        shaderProgram.unbind();
    }
}