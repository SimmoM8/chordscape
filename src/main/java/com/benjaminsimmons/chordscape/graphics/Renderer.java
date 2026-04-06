package com.benjaminsimmons.chordscape.graphics;

import org.lwjgl.opengl.GL11;

public class Renderer {

    public void clear(float r, float g, float b, float a) {
        GL11.glClearColor(r, g, b, a);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
    }

    public void draw(Mesh mesh, ShaderProgram shader, Transform transform, Camera camera) {
        shader.bind();

        shader.setUniform2f("uPosition", transform.x, transform.y);
        shader.setUniform2f("uScale", transform.scaleX, transform.scaleY);
        shader.setUniform2f("uCamera", camera.x, camera.y);

        mesh.render();

        shader.unbind();
    }
}