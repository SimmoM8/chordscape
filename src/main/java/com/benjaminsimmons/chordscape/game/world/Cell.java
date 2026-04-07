package com.benjaminsimmons.chordscape.game.world;

public class Cell {
    private final int gridX;
    private final int gridY;

    private boolean hasMusicData;

    private float r;
    private float g;
    private float b;

    public Cell(int gridX, int gridY) {
        this.gridX = gridX;
        this.gridY = gridY;
        this.hasMusicData = false;
        this.r = 0.0f;
        this.g = 0.0f;
        this.b = 0.0f;
    }

    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public boolean hasMusicData() {
        return hasMusicData;
    }

    public void setColor(float r, float g, float b) {
        this.hasMusicData = true;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public void clear() {
        this.hasMusicData = false;
        this.r = 0.0f;
        this.g = 0.0f;
        this.b = 0.0f;
    }

    public float getR() {
        return r;
    }

    public float getG() {
        return g;
    }

    public float getB() {
        return b;
    }
}
