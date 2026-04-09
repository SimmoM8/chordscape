package com.benjaminsimmons.chordscape.game.controller;

import com.benjaminsimmons.chordscape.game.entity.Player;
import com.benjaminsimmons.chordscape.game.music.Note;
import com.benjaminsimmons.chordscape.game.world.Cell;
import com.benjaminsimmons.chordscape.game.world.World;

public class WorldEditController {
    private final World world;
    private final Player player;

    public WorldEditController(World world, Player player) {
        this.world = world;
        this.player = player;
    }

    public void editCellAtPlayer(int pitch) {
        Cell cell = world.getCellContainingPlayer(player);
        if (cell != null) {
            cell.setAuthoredNote(new Note(pitch));
            world.markGridVisualDirty();
        }
    }

    public void clearCellAtPlayer() {
        Cell cell = world.getCellContainingPlayer(player);
        if (cell != null) {
            cell.clearNote();
            world.markGridVisualDirty();
        }
    }
}
