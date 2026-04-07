package com.benjaminsimmons.chordscape.engine.view;

import com.benjaminsimmons.chordscape.engine.math.Transform;
import com.benjaminsimmons.chordscape.game.entity.GameObject;
import com.benjaminsimmons.chordscape.game.entity.Player;

public class Camera {

    private float x;
    private float y;

    private Transform followTarget;

    public Camera(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void follow(GameObject target) {
        this.followTarget = target.getTransform();
    }

    public void detach() {
        this.followTarget = null;
    }

    public void update() {
        if (followTarget != null) {
            this.x = followTarget.x;
            this.y = followTarget.y;
        }
    }

    public void move(float dx, float dy) {
        if (followTarget == null) {
            this.x += dx;
            this.y += dy;
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}