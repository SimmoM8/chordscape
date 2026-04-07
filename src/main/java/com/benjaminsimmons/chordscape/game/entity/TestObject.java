package com.benjaminsimmons.chordscape.game.entity;

import com.benjaminsimmons.chordscape.engine.graphics.Mesh;
import com.benjaminsimmons.chordscape.engine.math.Transform;

public class TestObject extends GameObject {

    public TestObject(Mesh mesh, Transform transform) {
        super(mesh, transform);
    }

    @Override
    public void update(float deltaTime) {
        getTransform().x += 0.25f * deltaTime;

        if (getTransform().x > 1.2f) {
            getTransform().x = -1.2f;
        }
    }
}
