package com.benjaminsimmons.chordscape.game.entity;

import com.benjaminsimmons.chordscape.engine.graphics.Mesh;
import com.benjaminsimmons.chordscape.engine.math.Transform;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class Player extends GameObject {

    private final float velocity = 3.0f;

    public Player(Transform transform) {
        super(transform);
        this.mesh = createMesh();
    }

    @Override
    public void update(float deltaTime) {
    }

    public void move(float dx, float dy, float deltaTime) {
        float length = (float) Math.sqrt(dx * dx + dy * dy);

        // Normalize the movement vector to prevent faster diagonal movement
        if (length > 0.0f) { dx /= length; dy /= length; }

        transform.x += dx * velocity * deltaTime;
        transform.y += dy * velocity * deltaTime;
    }

    private Mesh createMesh() {
        int segments = 12;

        float innerRadius = 0.85f;
        float outerRadius = 1.0f;

        float redR = 1.0f;
        float redG = 0.0f;
        float redB = 0.0f;

        float blackR = 0.0f;
        float blackG = 0.0f;
        float blackB = 0.0f;

        List<Float> vertices = new ArrayList<>();

        // Red filled center
        for (int i = 0; i < segments; i++) {
            float angleA = (float) (2.0 * Math.PI * i / segments);
            float angleB = (float) (2.0 * Math.PI * (i + 1) / segments);

            float ax = (float) Math.cos(angleA) * innerRadius;
            float ay = (float) Math.sin(angleA) * innerRadius;

            float bx = (float) Math.cos(angleB) * innerRadius;
            float by = (float) Math.sin(angleB) * innerRadius;

            addVertex(vertices, 0.0f, 0.0f, redR, redG, redB);
            addVertex(vertices, ax, ay, redR, redG, redB);
            addVertex(vertices, bx, by, redR, redG, redB);
        }

        // Black outline ring
        for (int i = 0; i < segments; i++) {
            float angleA = (float) (2.0 * Math.PI * i / segments);
            float angleB = (float) (2.0 * Math.PI * (i + 1) / segments);

            float innerAx = (float) Math.cos(angleA) * innerRadius;
            float innerAy = (float) Math.sin(angleA) * innerRadius;
            float innerBx = (float) Math.cos(angleB) * innerRadius;
            float innerBy = (float) Math.sin(angleB) * innerRadius;

            float outerAx = (float) Math.cos(angleA) * outerRadius;
            float outerAy = (float) Math.sin(angleA) * outerRadius;
            float outerBx = (float) Math.cos(angleB) * outerRadius;
            float outerBy = (float) Math.sin(angleB) * outerRadius;

            // Triangle 1
            addVertex(vertices, innerAx, innerAy, blackR, blackG, blackB);
            addVertex(vertices, outerAx, outerAy, blackR, blackG, blackB);
            addVertex(vertices, outerBx, outerBy, blackR, blackG, blackB);

            // Triangle 2
            addVertex(vertices, innerAx, innerAy, blackR, blackG, blackB);
            addVertex(vertices, outerBx, outerBy, blackR, blackG, blackB);
            addVertex(vertices, innerBx, innerBy, blackR, blackG, blackB);
        }

        return new Mesh(toFloatArray(vertices), GL11.GL_TRIANGLES);
    }

    private void addVertex(List<Float> vertices, float x, float y, float r, float g, float b) {
        vertices.add(x);
        vertices.add(y);
        vertices.add(r);
        vertices.add(g);
        vertices.add(b);
    }

    private float[] toFloatArray(List<Float> vertices) {
        float[] result = new float[vertices.size()];
        for (int i = 0; i < vertices.size(); i++) {
            result[i] = vertices.get(i);
        }
        return result;
    }
}
