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
        world.placeAuthoredNoteAtPlayer(player, pitch);
    }

    public void clearCellAtPlayer() {
        world.clearCellAtPlayer(player);
    }
}
