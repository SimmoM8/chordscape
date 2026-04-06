package com.benjaminsimmons.chordscape.graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Mesh {

    private final int vaoId;
    private final int vboId;
    private final int vertexCount;
    private final int drawMode;

    public Mesh(float[] vertices, int vertexCount, int drawMode) {
        this.vertexCount = vertexCount;
        this.drawMode = drawMode;

        vaoId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoId);

        vboId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_STATIC_DRAW);

        int stride = 5 * Float.BYTES;

        // location 0 -> position (x, y)
        GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, stride, 0);
        GL20.glEnableVertexAttribArray(0);

        // location 1 -> color (r, g, b)
        GL20.glVertexAttribPointer(1, 3, GL11.GL_FLOAT, false, stride, 2L * Float.BYTES);
        GL20.glEnableVertexAttribArray(1);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
    }

    public void render() {
        GL30.glBindVertexArray(vaoId);
        GL11.glDrawArrays(drawMode, 0, vertexCount);
        GL30.glBindVertexArray(0);
    }

    public void cleanup() {
        GL15.glDeleteBuffers(vboId);
        GL30.glDeleteVertexArrays(vaoId);
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public int getDrawMode() {
        return drawMode;
    }
}