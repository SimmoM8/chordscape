package com.benjaminsimmons.chordscape.engine.math;

public class Transform {
    public float x;
    public float y;
    //public float rotation;
    public float scaleX = 1.0f;
    public float scaleY = 1.0f;

    public Transform(float x, float y, float scaleX, float scaleY) {
        this.x = x;
        this.y = y;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    public Transform(float x, float y) {
        this(x, y, 1.0f, 1.0f);
    }
}
