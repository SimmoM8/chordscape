package com.benjaminsimmons.chordscape.game.world;

import com.benjaminsimmons.chordscape.engine.graphics.Mesh;
import com.benjaminsimmons.chordscape.engine.math.Transform;
import com.benjaminsimmons.chordscape.engine.view.Camera;
import com.benjaminsimmons.chordscape.engine.graphics.Renderer;
import com.benjaminsimmons.chordscape.engine.graphics.ShaderProgram;
import com.benjaminsimmons.chordscape.game.entity.GameObject;

import java.util.ArrayList;
import java.util.List;

public class World {
    private final List<GameObject> objects = new ArrayList<>();

    private final WorldGrid grid;
    private final Mesh gridMesh;
    private final Transform gridTransform;

    public World() {
        this.grid = new WorldGrid(200, 200, 0.1f);
        this.gridMesh = grid.createMesh();
        this.gridTransform = new Transform(0.0f, 0.0f);
    }

    public void addObject(GameObject object) {
        objects.add(object);
    }

    public void update(float deltaTime) {
        for (GameObject object : objects) {
            object.update(deltaTime);
        }
    }

    public void render(Renderer renderer, ShaderProgram shaderProgram, Camera camera) {
        renderer.draw(gridMesh, shaderProgram, gridTransform, camera);
        for (GameObject object : objects) {
            renderer.draw(object.getMesh(), shaderProgram, object.getTransform(), camera);
        }
    }

    public void cleanup() {
        gridMesh.cleanup();
    }
}
