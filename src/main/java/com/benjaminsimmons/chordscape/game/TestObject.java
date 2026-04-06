package com.benjaminsimmons.chordscape.game;

import com.benjaminsimmons.chordscape.graphics.Mesh;
import com.benjaminsimmons.chordscape.graphics.Transform;

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
