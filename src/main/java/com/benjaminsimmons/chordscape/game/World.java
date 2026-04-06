package com.benjaminsimmons.chordscape.game;

import com.benjaminsimmons.chordscape.graphics.Renderer;
import com.benjaminsimmons.chordscape.graphics.ShaderProgram;

import java.util.ArrayList;
import java.util.List;

public class World {
    private final List<GameObject> objects = new ArrayList<>();

    public void addObject(GameObject object) {
        objects.add(object);
    }

    public void update(float deltaTime) {
        for (GameObject object : objects) {
            object.update(deltaTime);
        }
    }

    public void render(Renderer renderer, ShaderProgram shaderProgram) {
        for (GameObject object : objects) {
            renderer.draw(object.getMesh(), shaderProgram, object.getTransform());
        }
    }
}
