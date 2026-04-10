package com.benjaminsimmons.chordscape.engine.graphics;

public class UiRenderer {

    public void draw(Mesh mesh, UiShaderProgram shader) {
        if (mesh == null) {
            return;
        }

        shader.bind();
        mesh.render();
        shader.unbind();
    }
}