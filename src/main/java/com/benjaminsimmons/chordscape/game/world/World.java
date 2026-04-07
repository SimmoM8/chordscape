package com.benjaminsimmons.chordscape.game.world;

import com.benjaminsimmons.chordscape.engine.graphics.Mesh;
import com.benjaminsimmons.chordscape.engine.math.Transform;
import com.benjaminsimmons.chordscape.engine.view.Camera;
import com.benjaminsimmons.chordscape.engine.graphics.Renderer;
import com.benjaminsimmons.chordscape.engine.graphics.ShaderProgram;
import com.benjaminsimmons.chordscape.game.entity.GameObject;
import com.benjaminsimmons.chordscape.game.entity.Player;
import com.benjaminsimmons.chordscape.game.music.LocalMusicContext;
import com.benjaminsimmons.chordscape.game.music.Note;

import java.util.ArrayList;
import java.util.List;

public class World {
    private final List<GameObject> objects = new ArrayList<>();

    private final WorldGrid grid;
    private final WorldGridMesher mesher;
    private final Mesh gridMesh;
    private Mesh coloredCellsMesh;

    private final Transform worldTransform = new Transform(0.0f, 0.0f);
    private boolean gridVisualDirty = true;

    public World() {
        this.grid = new WorldGrid(200, 200, 1.0f);
        this.mesher = new WorldGridMesher();

        seedTestCells();

        this.gridMesh = mesher.buildGridLines(grid);
        this.coloredCellsMesh = mesher.buildColoredCells(grid);
        this.gridVisualDirty = false;
    }

    public void addObject(GameObject object) {
        objects.add(object);
    }

    public void update(float deltaTime) {
        for (GameObject object : objects) {
            object.update(deltaTime);
        }

        if (gridVisualDirty) {
            rebuildColoredCellsMesh();
        }
    }

    public void render(Renderer renderer, ShaderProgram shaderProgram, Camera camera) {
        if (coloredCellsMesh != null) {
            renderer.draw(coloredCellsMesh, shaderProgram, worldTransform, camera);
        }

        renderer.draw(gridMesh, shaderProgram, worldTransform, camera);

        for (GameObject object : objects) {
            renderer.draw(object.getMesh(), shaderProgram, object.getTransform(), camera);
        }
    }

    public void cleanup() {
        if (gridMesh != null) {
            gridMesh.cleanup();
        }
        if (coloredCellsMesh != null) {
            coloredCellsMesh.cleanup();
        }
    }

    public WorldGrid getGrid() {
        return grid;
    }

    public void markGridVisualDirty() {
        gridVisualDirty = true;
    }

    private void rebuildColoredCellsMesh() {
        if (coloredCellsMesh != null) {
            coloredCellsMesh.cleanup();
        }
        coloredCellsMesh = mesher.buildColoredCells(grid);
        gridVisualDirty = false;
    }

    public Cell getCellAtPlayer(Player player) {
        return grid.findCellAtWorldCoordinate(player.getTransform().x, player.getTransform().y);
    }

    public List<Cell> getCellsAroundPlayer(Player player, int radius) {
        Cell centerCell = getCellAtPlayer(player);
        return grid.getCellsAround(centerCell, radius);
    }

    public LocalMusicContext getLocalMusicContext(Player player, int radius) {
        Cell centerCell = getCellAtPlayer(player);
        List<Cell> nearbyCells = grid.getCellsAround(centerCell, radius);
        return new LocalMusicContext(centerCell, nearbyCells);
    }

    private void seedTestCells() {
        grid.getCell(0, 0).setNote(new Note(0));
        grid.getCell(1, 0).setNote(new Note(4));
        grid.getCell(0, 1).setNote(new Note(7));
        grid.getCell(-1, -1).setNote(new Note(2));
    }
}
