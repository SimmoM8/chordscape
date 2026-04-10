package com.benjaminsimmons.chordscape.app;

import com.benjaminsimmons.chordscape.engine.audio.*;
import com.benjaminsimmons.chordscape.engine.graphics.*;
import com.benjaminsimmons.chordscape.engine.input.Input;
import com.benjaminsimmons.chordscape.engine.math.Transform;
import com.benjaminsimmons.chordscape.engine.view.Camera;
import com.benjaminsimmons.chordscape.game.controller.PlayerController;
import com.benjaminsimmons.chordscape.game.entity.Player;
import com.benjaminsimmons.chordscape.game.music.*;
import com.benjaminsimmons.chordscape.game.world.Cell;
import com.benjaminsimmons.chordscape.game.world.Region;
import com.benjaminsimmons.chordscape.game.world.SubRegion;
import com.benjaminsimmons.chordscape.game.world.World;
import org.lwjgl.glfw.GLFW;

public class Application {

    private final Window window;
    private final Renderer renderer;
    private final ShaderProgram shaderProgram;

    private AudioEngine audioEngine;
    private TonePlayer tonePlayer;

    private World world;
    private Camera camera;
    private Player player;
    private PlayerController playerController;

    private Cell playerLastCell;
    private SubRegion playerLastSubRegion;

    private LocalWorldSampler sampler;
    private LocalComposition currentComposition;
    private LocalCompositionPrinter localCompositionPrinter;
    private LocalCompositionDebugMesher localCompositionDebugMesher;
    private Mesh sampledRegionOutlineMesh;
    private CompositionSequencer compositionSequencer;

    private int lastKnownCellRevision = -1;

    public Application() {
        this.window = new Window(720, 720, "Chordscape");
        this.renderer = new Renderer();
        this.shaderProgram = new ShaderProgram();
        this.audioEngine = new AudioEngine();
    }

    public void run() {
        init();
        loop();
        cleanup();
    }

    private void init() {
        window.init();
        shaderProgram.init();
        audioEngine.init();
        tonePlayer = new TonePlayer();
        world = new World();
        camera = new Camera(0.0f, 0.0f, 0.1f);
        sampler = new LocalWorldSampler();
        compositionSequencer = new CompositionSequencer(tonePlayer);
        localCompositionPrinter = new LocalCompositionPrinter();
        localCompositionDebugMesher = new LocalCompositionDebugMesher();
        Input input = new Input(window.getHandle());
        player = new Player(new Transform(0.0f, 0.0f, 0.5f, 0.5f));
        playerController = new PlayerController(input, player, world);

        camera.follow(player);
        world.addObject(player);

        currentComposition = sampler.build(world, player);
        compositionSequencer.setComposition(currentComposition);
        if (currentComposition != null) {
            sampledRegionOutlineMesh = localCompositionDebugMesher.buildSampleOutline(currentComposition);
        }
    }

    private void loop() {
        float lastTime = (float) GLFW.glfwGetTime();

        while (!window.shouldClose()) {
            float currentTime = (float) GLFW.glfwGetTime();
            float deltaTime = currentTime - lastTime;
            lastTime = currentTime;

            // Process window/input events
            window.pollEvents();

            // Update application state (e.g., move objects, handle input)
            update(deltaTime);

            render();

            // Present the rendered frame
            window.swapBuffers();
        }
    }

    private void update(float deltaTime) {
        playerController.handleInput(deltaTime);
        world.update(deltaTime);
        camera.update();

        SubRegion currentSubRegion = world.getSubRegionContainingPlayer(player);
        int currentCellRevision = world.getCellRevision();

        boolean subRegionChanged = currentSubRegion != playerLastSubRegion;
        boolean cellsChanged = currentCellRevision != lastKnownCellRevision;

        if (subRegionChanged || cellsChanged) {
            playerLastSubRegion = currentSubRegion;
            lastKnownCellRevision = currentCellRevision;

            currentComposition = sampler.build(world, player);
            compositionSequencer.setComposition(currentComposition);

            if (sampledRegionOutlineMesh != null) {
                sampledRegionOutlineMesh.cleanup();
                sampledRegionOutlineMesh = null;
            }

            if (currentComposition != null) {
                sampledRegionOutlineMesh = localCompositionDebugMesher.buildSampleOutline(currentComposition);
                localCompositionPrinter.print(currentComposition);
                compositionSequencer.setStartOffset(currentComposition.getPlayerStartSlot());
            }

        }

        compositionSequencer.update(deltaTime);
        tonePlayer.update();

    }

    private void render() {
        renderer.clear(0.08f, 0.12f, 0.18f, 1.0f);
        world.render(renderer, shaderProgram, camera);
        if (sampledRegionOutlineMesh != null) {
            renderer.draw(sampledRegionOutlineMesh, shaderProgram, new Transform(0.0f, 0.0f), camera);
        }
    }

    private void cleanup() {
        player.getMesh().cleanup();
        world.cleanup();
        shaderProgram.cleanup();
        if (tonePlayer != null) {
            tonePlayer.cleanup();
        }
        if (audioEngine != null) {
            audioEngine.cleanup();
        }
        if (sampledRegionOutlineMesh != null) {
            sampledRegionOutlineMesh.cleanup();
        }
        window.cleanup();
    }

    public Window getWindow() {
        return window;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public ShaderProgram getShaderProgram() {
        return shaderProgram;
    }
}