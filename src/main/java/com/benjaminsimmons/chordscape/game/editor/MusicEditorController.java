package com.benjaminsimmons.chordscape.game.editor;

import com.benjaminsimmons.chordscape.engine.input.Input;
import com.benjaminsimmons.chordscape.game.editor.tool.MusicEditorTool;
import com.benjaminsimmons.chordscape.game.editor.tool.Pencil;
import com.benjaminsimmons.chordscape.game.music.LocalComposition;
import com.benjaminsimmons.chordscape.game.world.World;

public class MusicEditorController {

    private final World world;
    private final Input input;
    private final MusicEditor musicEditor;
    private MusicEditorTool currentTool;

    public MusicEditorController(Input input, MusicEditor musicEditor, World world) {
        this.input = input;
        this.musicEditor = musicEditor;
        this.world = world;
        this.currentTool = new Pencil();
    }

    public void handleInput(int windowWidth, int windowHeight) {
        if (!musicEditor.isOpen()) {
            return;
        }

        boolean changed = currentTool.handleInput(input, musicEditor, windowWidth, windowHeight);

        if (changed && musicEditor.getComposition() != null) {
            world.applyLocalComposition(musicEditor.getComposition());
        }
    }

    public void setCurrentTool(MusicEditorTool currentTool) {
        this.currentTool = currentTool;
    }
}