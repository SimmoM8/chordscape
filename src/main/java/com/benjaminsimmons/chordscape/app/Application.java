package com.benjaminsimmons.chordscape.app;

import com.benjaminsimmons.chordscape.engine.graphics.*;
import com.benjaminsimmons.chordscape.engine.input.Input;
import com.benjaminsimmons.chordscape.engine.math.Transform;
import com.benjaminsimmons.chordscape.engine.view.Camera;
import com.benjaminsimmons.chordscape.game.controller.PlayerController;
import com.benjaminsimmons.chordscape.game.entity.Player;
import com.benjaminsimmons.chordscape.game.music.CompositionEvent;
import com.benjaminsimmons.chordscape.game.music.LocalComposition;
import com.benjaminsimmons.chordscape.game.music.LocalMusicContext;
import com.benjaminsimmons.chordscape.game.music.LocalWorldSampler;
import com.benjaminsimmons.chordscape.game.world.Cell;
import com.benjaminsimmons.chordscape.game.world.Region;
import com.benjaminsimmons.chordscape.game.world.SubRegion;
import com.benjaminsimmons.chordscape.game.world.World;
import org.lwjgl.glfw.GLFW;

public class Application {

    private final Window window;
    private final Renderer renderer;
    private final ShaderProgram shaderProgram;

    private World world;
    private Camera camera;
    private Player player;
    private PlayerController playerController;

    private Cell playerLastCell;
    private SubRegion playerLastSubRegion;

    public Application() {
        this.window = new Window(720, 720, "Chordscape");
        this.renderer = new Renderer();
        this.shaderProgram = new ShaderProgram();
    }

    public void run() {
        init();
        loop();
        cleanup();
    }

    private void init() {
        window.init();
        shaderProgram.init();

        world = new World();

        for (SubRegion subRegion : world.getSubRegions()) {
            if (!subRegion.getPitchProfile().isEmpty()) {
                System.out.println(subRegion);
            }
        }

        for (Region region : world.getRegions()) {
            if (!region.getPitchProfile().isEmpty()) {
                System.out.println(region);
            }
        }

        camera = new Camera(0.0f, 0.0f, 0.1f);
        Input input = new Input(window.getHandle());

        player = new Player(new Transform(0.0f, 0.0f, 0.5f, 0.5f));
        playerController = new PlayerController(input, player, world);
        camera.follow(player);

        world.addObject(player);
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

        Cell currentCell = world.getCellContainingPlayer(player);
        SubRegion currentSubRegion = world.getSubRegionContainingPlayer(player);

        if (currentCell != playerLastCell) {
            playerLastCell = currentCell;

            if (currentCell != null) {
                System.out.println("Entered cell: ("
                        + currentCell.getCellX() + ", "
                        + currentCell.getCellY() + ")");
                System.out.println("Cell is in SubRegion: " + world.getSubRegions().stream()
                        .filter(subRegion -> subRegion.getCells().contains(currentCell))
                        .findFirst()
                        .map(subRegion -> "(" + subRegion.getPosX() + ", " + subRegion.getPosY() + ")")
                        .orElse("none"));

                if (currentCell.hasNote()) {
                    System.out.println("Center note: " + currentCell.getNote().getPitch());
                } else {
                    System.out.println("Center note: none");
                }

                LocalMusicContext context = world.getLocalMusicContext(player, 1);
                System.out.println("Nearby note count: " + context.getNearbyNotes().size());
                System.out.println("-----");
            }
        }

        if (currentSubRegion != playerLastSubRegion) {
            playerLastSubRegion = currentSubRegion;

            if (currentSubRegion != null) {
                System.out.println("Entered SubRegion: ("
                        + currentSubRegion.getPosX() + ", "
                        + currentSubRegion.getPosY() + ")");

                LocalWorldSampler sampler = new LocalWorldSampler();
                LocalComposition composition = sampler.build(world, player);

                if (composition != null) {
                    System.out.println("LocalComposition:");
                    System.out.println(composition);
                    for (CompositionEvent event : composition.getEvents()) {
                        System.out.println(event);
                    }
                }
            } else {
                System.out.println("Entered SubRegion: none");
            }
        }

    }

    private void render() {
        renderer.clear(0.08f, 0.12f, 0.18f, 1.0f);
        world.render(renderer, shaderProgram, camera);
    }

    private void cleanup() {
        player.getMesh().cleanup();
        world.cleanup();
        shaderProgram.cleanup();
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