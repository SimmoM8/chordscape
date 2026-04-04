package com.benjaminsimmons.chordscape.graphics;

import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

public class Renderer {

    private int vao;
    private int vbo;

    private final int segments = 4;
    public float[] vertices = new float[segments * 15]; // 2 for position, 3 for color, times 3 for each point in the triangle fan

    private double colorTimer = 0.0;
    private final double colorSpeed = 0.1;

    public void init() {
        vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);

        vbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_DYNAMIC_DRAW);

        // position attribute
        GL20.glVertexAttribPointer(0, 2, GL15.GL_FLOAT, false, 5 * Float.BYTES, 0);
        GL20.glEnableVertexAttribArray(0);

        // color attribute
        GL20.glVertexAttribPointer(1, 3, GL15.GL_FLOAT, false, 5 * Float.BYTES, 2 * Float.BYTES);
        GL20.glEnableVertexAttribArray(1);
    }

    public void render() {
        GL30.glBindVertexArray(vao);
        GL30.glDrawArrays(GL30.GL_TRIANGLES, 0, segments * 3);
    }

    public void cleanup() {
        GL15.glDeleteBuffers(vbo);
        GL30.glDeleteVertexArrays(vao);
    }

    public void updateCircleColors(double deltaTime) {
        colorTimer += deltaTime;

        if (colorTimer >= colorSpeed) {
            colorTimer = 0.0;
            float[] vertices = generateCircle(0.0f, 0.0f, 2f, segments);
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
            GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, vertices);
        }
    }

    private float getRandomColor() {
        return (float) Math.random();
    }

    private float[] generateRandomColorForCirclePoint(int segments) {
        float[] verticeColors = new float[(segments + 1) * 3]; // 3 for color
        for (int i = 0; i < (segments + 1); i++) {
            verticeColors[i * 3] = getRandomColor(); // r
            verticeColors[i * 3 + 1] = getRandomColor(); // g
            verticeColors[i * 3 + 2] = getRandomColor(); // b
        }
        return verticeColors;
    }

    private float[] generateCircle(float centreX, float centreY, float radius, int segments) {

        float[] points = generateCirclePoints(centreX, centreY, radius, segments);
        float[] verticeColors = generateRandomColorForCirclePoint(segments);

        float[] circleVertices = new float[segments * 15]; // 2 for position, 3 for color, times 3 for each point in the triangle fan
        for (int i = 0; i < segments; i++) {
            circleVertices[i * 15] = centreX;
            circleVertices[i * 15 + 1] = centreY;
            circleVertices[i * 15 + 2] = verticeColors[0]; // r
            circleVertices[i * 15 + 3] = verticeColors[1]; // g
            circleVertices[i * 15 + 4] = verticeColors[2]; // b

            if (i == 0) {
                circleVertices[5] = points[0];
                circleVertices[6] = points[1];
                circleVertices[7] = verticeColors[3]; // r
                circleVertices[8] = verticeColors[4]; // g
                circleVertices[9] = verticeColors[5]; // b

                circleVertices[10] = points[(segments - 1) * 2];
                circleVertices[11] = points[(segments - 1) * 2 + 1];
                circleVertices[12] = verticeColors[(segments) * 3]; // r
                circleVertices[13] = verticeColors[(segments) * 3 + 1]; // g
                circleVertices[14] = verticeColors[(segments) * 3 + 2]; // b
            } else {
                circleVertices[i * 15 + 5] = points[i * 2];
                circleVertices[i * 15 + 6] = points[i * 2 + 1];
                circleVertices[i * 15 + 7] = verticeColors[i * 3 + 3]; // r
                circleVertices[i * 15 + 8] = verticeColors[i * 3 + 4]; // g
                circleVertices[i * 15 + 9] = verticeColors[i * 3 + 5]; // b

                circleVertices[i * 15 + 10] = points[(i - 1) * 2];
                circleVertices[i * 15 + 11] = points[(i - 1) * 2 + 1];
                circleVertices[i * 15 + 12] = verticeColors[i * 3]; // r
                circleVertices[i * 15 + 13] = verticeColors[i * 3 + 1]; // g
                circleVertices[i * 15 + 14] = verticeColors[i * 3 + 2]; // b
            }
        }
        return circleVertices;
    }

    private float[] generateCirclePoints(float centreX, float centreY, float radius, int segments) {
        float[] points = new float[segments * 2]; // 2 for position, 3 for color
        for (int i = 0; i < segments; i++) {
            double angle = (Math.PI / 2) + 2.0 * Math.PI * i / segments;
            points[i * 2] = centreX + radius * (float) Math.cos(angle); // x
            points[i * 2 + 1] = centreY + radius * (float) Math.sin(angle); // y
        }
        return points;
    }
}