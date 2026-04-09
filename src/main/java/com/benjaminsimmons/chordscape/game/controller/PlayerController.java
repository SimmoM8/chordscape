package com.benjaminsimmons.chordscape.game.controller;

import com.benjaminsimmons.chordscape.engine.input.Input;
import com.benjaminsimmons.chordscape.game.entity.Player;
import com.benjaminsimmons.chordscape.game.world.World;
import org.lwjgl.glfw.GLFW;

public class PlayerController {
    private final Input input;
    private final Player player;
    private final WorldEditController worldEditor;
    private int selectedPitch = 0;

    private boolean nextPitchWasDown = false;
    private boolean previousPitchWasDown = false;
    private boolean placeWasDown = false;
    private boolean clearWasDown = false;

    public PlayerController(Input input, Player player, World world) {
        this.input = input;
        this.player = player;
        this.worldEditor = new WorldEditController(world, player);
    }

    public void handleInput(float deltaTime) {
        handleMovementInput(deltaTime);
        handleSelectionInput();
        handleEditInput();
    }

    public void handleMovementInput(float deltaTime) {
         float dx = 0;
         float dy = 0;

         if (input.isKeyDown(GLFW.GLFW_KEY_UP)) dy++;
         if (input.isKeyDown(GLFW.GLFW_KEY_DOWN)) dy--;
         if (input.isKeyDown(GLFW.GLFW_KEY_RIGHT)) dx++;
         if (input.isKeyDown(GLFW.GLFW_KEY_LEFT)) dx--;

         player.move(dx, dy, deltaTime);
    }

    public void handleSelectionInput() {
        boolean nextPitchDown = input.isKeyDown(GLFW.GLFW_KEY_E);
        boolean previousPitchDown = input.isKeyDown(GLFW.GLFW_KEY_Q);

        if (nextPitchDown && !nextPitchWasDown) {
            selectedPitch = (selectedPitch + 1) % 12;
        }

        if (previousPitchDown && !previousPitchWasDown) {
            selectedPitch = (selectedPitch + 11) % 12;
        }

        if (input.isKeyDown(GLFW.GLFW_KEY_1)) selectedPitch = 0;
        if (input.isKeyDown(GLFW.GLFW_KEY_2)) selectedPitch = 1;
        if (input.isKeyDown(GLFW.GLFW_KEY_3)) selectedPitch = 2;
        if (input.isKeyDown(GLFW.GLFW_KEY_4)) selectedPitch = 3;
        if (input.isKeyDown(GLFW.GLFW_KEY_5)) selectedPitch = 4;
        if (input.isKeyDown(GLFW.GLFW_KEY_6)) selectedPitch = 5;
        if (input.isKeyDown(GLFW.GLFW_KEY_7)) selectedPitch = 6;
        if (input.isKeyDown(GLFW.GLFW_KEY_8)) selectedPitch = 7;
        if (input.isKeyDown(GLFW.GLFW_KEY_9)) selectedPitch = 8;
        if (input.isKeyDown(GLFW.GLFW_KEY_0)) selectedPitch = 9;
        if (input.isKeyDown(GLFW.GLFW_KEY_MINUS)) selectedPitch = 10;
        if (input.isKeyDown(GLFW.GLFW_KEY_EQUAL)) selectedPitch = 11;

        nextPitchWasDown = nextPitchDown;
        previousPitchWasDown = previousPitchDown;
    }

    public void handleEditInput() {
         if (input.isKeyDown(GLFW.GLFW_KEY_SPACE)) worldEditor.editCellAtPlayer(selectedPitch);
         if (input.isKeyDown(GLFW.GLFW_KEY_X)) worldEditor.clearCellAtPlayer();
    }
}
