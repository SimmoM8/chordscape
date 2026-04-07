package com.benjaminsimmons.chordscape.game.entity;

import com.benjaminsimmons.chordscape.engine.graphics.Mesh;
import com.benjaminsimmons.chordscape.engine.math.Transform;

public abstract class GameObject {
    protected Mesh mesh;
    protected Transform transform;

    public GameObject(Mesh mesh, Transform transform) {
        this.mesh = mesh;
        this.transform = transform;
    }

    public GameObject(Transform transform) {
        this.transform = transform;
    }

    public Transform getTransform() {
        return transform;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public abstract void update(float deltaTime);
}
