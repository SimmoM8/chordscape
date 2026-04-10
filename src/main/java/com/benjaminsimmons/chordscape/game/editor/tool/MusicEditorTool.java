package com.benjaminsimmons.chordscape.game.editor.tool;

import com.benjaminsimmons.chordscape.engine.input.Input;
import com.benjaminsimmons.chordscape.game.editor.MusicEditor;

public interface MusicEditorTool {
    boolean handleInput(Input input, MusicEditor editor, int windowWidth, int windowHeight);
}