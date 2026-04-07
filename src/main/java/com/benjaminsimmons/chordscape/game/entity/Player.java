package com.benjaminsimmons.chordscape.game.entity;

import com.benjaminsimmons.chordscape.engine.graphics.Mesh;
import com.benjaminsimmons.chordscape.engine.math.Transform;
import org.lwjgl.opengl.GL11;

public class Player extends GameObject {

    private final float moveSpeed = 1.0f;

    public Player(Transform transform) {
        super(transform);
        this.mesh = createMesh();
    }

    @Override
    public void update(float deltaTime) {

    }

    public void move(float dx, float dy, float deltaTime) {
        transform.x += (float) (dx * (moveSpeed / Math.sqrt(2)) * deltaTime);
        transform.y += (float) (dy * (moveSpeed / Math.sqrt(2)) * deltaTime);
    }

    private Mesh createMesh() {
        float[] playerVertices = {
                // x, y,     r, g, b
                0.0f, 0.5f, 1.0f, 0.0f, 0.0f,
                0.5f, -0.5f, 1.0f, 0.0f, 0.0f,
                -0.5f, -0.5f, 1.0f, 0.0f, 0.0f
        };

        return new Mesh(playerVertices, GL11.GL_TRIANGLES);
    }
}
