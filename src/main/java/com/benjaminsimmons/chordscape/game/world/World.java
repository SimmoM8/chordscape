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
import com.benjaminsimmons.chordscape.game.music.PitchProfile;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class World {
    private final List<GameObject> objects = new ArrayList<>();

    private final WorldGrid grid;
    private final WorldGridMesher mesher;
    private final Mesh gridMesh;
    private Mesh coloredCellsMesh;

    private final List<SubRegion> subRegions = new ArrayList<>();
    private final List<Region> regions = new ArrayList<>();
    private final Mesh subRegionOutlineMesh;
    private final Mesh regionOutlineMesh;

    private final Transform worldTransform = new Transform(0.0f, 0.0f);
    private boolean gridVisualDirty = true;

    private static final int INFLUENCE_RADIUS_IN_CELLS = 1;
    private static final float MIN_TOTAL_INFLUENCE_REQUIRED = 1.0f;
    private static final float MIN_STRONGEST_PITCH_WEIGHT = 0.1f;

    public World() {
        this.grid = new WorldGrid(40, 40, 1.0f);
        this.mesher = new WorldGridMesher();

        seedTestCells();

        buildSubRegions();
        buildRegions();
        refreshPitchProfiles();

        growWorldOnce();

        this.subRegionOutlineMesh = buildSubRegionOutlineMesh();
        this.regionOutlineMesh = buildRegionOutlineMesh();

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

        refreshPitchProfiles();
        growWorldOnce();

        if (gridVisualDirty) {
            rebuildColoredCellsMesh();
        }
    }

    public void render(Renderer renderer, ShaderProgram shaderProgram, Camera camera) {
        if (coloredCellsMesh != null) {
            renderer.draw(coloredCellsMesh, shaderProgram, worldTransform, camera);
        }

        renderer.draw(gridMesh, shaderProgram, worldTransform, camera);
        renderer.draw(subRegionOutlineMesh, shaderProgram, worldTransform, camera);
        renderer.draw(regionOutlineMesh, shaderProgram, worldTransform, camera);

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
        if (subRegionOutlineMesh != null) {
            subRegionOutlineMesh.cleanup();
        }
        if (regionOutlineMesh != null) {
            regionOutlineMesh.cleanup();
        }
    }

    public WorldGrid getGrid() {
        return grid;
    }

    public List<SubRegion> getSubRegions() {
        return subRegions;
    }

    public List<Region> getRegions() {
        return regions;
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

    public Cell getCellContainingPlayer(Player player) {
        return grid.findCellAtWorldCoordinate(player.getTransform().x, player.getTransform().y);
    }

    public List<Cell> getCellsAroundPlayer(Player player, int radius) {
        Cell centerCell = getCellContainingPlayer(player);
        return grid.getCellsAround(centerCell, radius);
    }

    public LocalMusicContext getLocalMusicContext(Player player, int radius) {
        Cell centerCell = getCellContainingPlayer(player);
        List<Cell> nearbyCells = grid.getCellsAround(centerCell, radius);
        return new LocalMusicContext(centerCell, nearbyCells);
    }

    private void buildSubRegions() {
        subRegions.clear();

        int subRegionSizeInCells = SubRegion.SIZE_IN_CELLS;
        int startSubRegionX = Math.floorDiv(grid.getMinCellX(), subRegionSizeInCells) * subRegionSizeInCells;
        int startSubRegionY = Math.floorDiv(grid.getMinCellY(), subRegionSizeInCells) * subRegionSizeInCells;

        for (int subRegionY = startSubRegionY; subRegionY <= grid.getMaxCellY(); subRegionY += subRegionSizeInCells) {
            for (int subRegionX = startSubRegionX; subRegionX <= grid.getMaxCellX(); subRegionX += subRegionSizeInCells) {
                subRegions.add(new SubRegion(subRegionX, subRegionY, grid));
            }
        }
    }

    public void buildRegions() {
        regions.clear();

        int regionSizeInCells = Region.SIZE_IN_SUB_REGIONS * SubRegion.SIZE_IN_CELLS;
        int startRegionX = Math.floorDiv(grid.getMinCellX(), regionSizeInCells) * regionSizeInCells;
        int startRegionY = Math.floorDiv(grid.getMinCellY(), regionSizeInCells) * regionSizeInCells;

        for (int regionY = startRegionY; regionY <= grid.getMaxCellY(); regionY += regionSizeInCells) {
            for (int regionX = startRegionX; regionX <= grid.getMaxCellX(); regionX += regionSizeInCells) {
                Region region = new Region(regionX, regionY);

                regions.add(region);

                for (SubRegion subRegion : subRegions) {
                    boolean insideX = subRegion.getPosX() >= regionX && subRegion.getPosX() < regionX + regionSizeInCells;
                    boolean insideY = subRegion.getPosY() >= regionY && subRegion.getPosY() < regionY + regionSizeInCells;

                    if (insideX && insideY) {
                        region.addSubRegion(subRegion);
                    }
                }
            }
        }
    }

    private void refreshPitchProfiles() {
        for (SubRegion subRegion : subRegions) {
            subRegion.refreshPitchProfile();
        }

        for (Region region : regions) {
            region.refreshPitchProfile();
        }
    }

    private void seedTestCells() {
        grid.getCell(0, 0).setAuthoredNote(new Note(0));
        grid.getCell(1, 0).setAuthoredNote(new Note(1));
        grid.getCell(0, 1).setAuthoredNote(new Note(2));
        grid.getCell(-1, -1).setAuthoredNote(new Note(3));
        grid.getCell(2, 2).setAuthoredNote(new Note(0));
    }

    private Mesh buildSubRegionOutlineMesh() {
        List<Float> vertices = new ArrayList<>();
        float size = SubRegion.SIZE_IN_CELLS;

        // cyan
        float r = 0.0f;
        float g = 1.0f;
        float b = 1.0f;

        for (SubRegion subRegion : subRegions) {
            float left = subRegion.getPosX();
            float right = left + size;
            float bottom = subRegion.getPosY();
            float top = bottom + size;

            addLine(vertices, left, bottom, right, bottom, r, g, b);
            addLine(vertices, right, bottom, right, top, r, g, b);
            addLine(vertices, right, top, left, top, r, g, b);
            addLine(vertices, left, top, left, bottom, r, g, b);
        }

        return new Mesh(toFloatArray(vertices), GL11.GL_LINES);
    }

    private Mesh buildRegionOutlineMesh() {
        List<Float> vertices = new ArrayList<>();
        float size = Region.SIZE_IN_SUB_REGIONS * SubRegion.SIZE_IN_CELLS;

        // magenta
        float r = 1.0f;
        float g = 0.0f;
        float b = 1.0f;

        for (Region region : regions) {
            float left = region.getPosX();
            float right = left + size;
            float bottom = region.getPosY();
            float top = bottom + size;

            addLine(vertices, left, bottom, right, bottom, r, g, b);
            addLine(vertices, right, bottom, right, top, r, g, b);
            addLine(vertices, right, top, left, top, r, g, b);
            addLine(vertices, left, top, left, bottom, r, g, b);
        }

        return new Mesh(toFloatArray(vertices), GL11.GL_LINES);
    }

    private void addLine(List<Float> vertices,
                         float x1, float y1,
                         float x2, float y2,
                         float r, float g, float b) {
        vertices.add(x1);
        vertices.add(y1);
        vertices.add(r);
        vertices.add(g);
        vertices.add(b);

        vertices.add(x2);
        vertices.add(y2);
        vertices.add(r);
        vertices.add(g);
        vertices.add(b);
    }

    private float[] toFloatArray(List<Float> vertices) {
        float[] result = new float[vertices.size()];
        for (int i = 0; i < vertices.size(); i++) {
            result[i] = vertices.get(i);
        }
        return result;
    }

    private record ProposedCellNote(Cell cell, Note note) {}

    public void growWorldOnce() {
        List<ProposedCellNote> proposedNotes = new ArrayList<>();

        for (Cell targetCell : grid.getAllCells()) {
            if (!targetCell.isEmpty()) {
                continue;
            }

            PitchProfile influenceProfile = new PitchProfile();
            float totalInfluence = 0.0f;

            for (Cell sourceCell : grid.getCellsAround(targetCell, INFLUENCE_RADIUS_IN_CELLS)) {
                if (sourceCell == null || sourceCell == targetCell || !sourceCell.hasNote()) {
                    continue;
                }

                float dx = sourceCell.getCellX() - targetCell.getCellX();
                float dy = sourceCell.getCellY() - targetCell.getCellY();
                float distance = (float) Math.sqrt(dx * dx + dy * dy);

                if (distance == 0.0f) {
                    continue;
                }

                float distanceWeight = 1.0f / (1.0f + distance);
                float outgoingStrength = sourceCell.getInfluence();
                float influence = distanceWeight * outgoingStrength;

                influenceProfile.addPitch(sourceCell.getNote().getPitch(), influence);
                totalInfluence += influence;
            }

            if (totalInfluence == 0.0f) {
                continue;
            }

            influenceProfile.normalise();

            int strongestPitch = influenceProfile.getStrongestPitch();
            float strongestPitchWeight = influenceProfile.getWeight(strongestPitch);

            if (totalInfluence >= MIN_TOTAL_INFLUENCE_REQUIRED
                    && strongestPitchWeight >= MIN_STRONGEST_PITCH_WEIGHT) {
                proposedNotes.add(new ProposedCellNote(targetCell, new Note(strongestPitch)));
            }
        }

        for (ProposedCellNote proposed : proposedNotes) {
            proposed.cell().setGeneratedNote(proposed.note());
        }

        if (!proposedNotes.isEmpty()) {
            refreshPitchProfiles();
            markGridVisualDirty();
        }
    }
}
