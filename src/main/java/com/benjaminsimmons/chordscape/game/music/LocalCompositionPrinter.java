package com.benjaminsimmons.chordscape.game.music;

public class LocalCompositionPrinter {

    public void print(LocalComposition composition) {
        if (composition == null) {
            System.out.println("LocalComposition is null.");
            return;
        }

        int width = composition.getWidthInCells();
        int height = composition.getHeightInCells();

        String[][] grid = new String[height][width];

        for (int lane = 0; lane < height; lane++) {
            for (int timeSlot = 0; timeSlot < width; timeSlot++) {
                grid[lane][timeSlot] = ".";
            }
        }

        for (CompositionEvent event : composition.getEvents()) {
            int timeSlot = event.getTimeSlot();
            int lane = event.getLane();

            if (timeSlot < 0 || timeSlot >= width || lane < 0 || lane >= height) {
                continue;
            }

            grid[lane][timeSlot] = Integer.toString(event.getPitch());
        }

        System.out.println("LocalComposition:");
        System.out.println("anchor=(" + composition.getAnchorCellX() + ", " + composition.getAnchorCellY() + ")");
        System.out.println("size=" + width + "x" + height);
        System.out.println("eventCount=" + composition.getEvents().size());
        System.out.println();

        for (int lane = height - 1; lane >= 0; lane--) {
            System.out.printf("lane %2d | ", lane);

            for (int timeSlot = 0; timeSlot < width; timeSlot++) {
                System.out.print(grid[lane][timeSlot] + " ");
            }

            System.out.println();
        }

        System.out.print("         ");
        for (int i = 0; i < width * 2 + 1; i++) {
            System.out.print("-");
        }
        System.out.println();

        System.out.print("time     ");
        for (int timeSlot = 0; timeSlot < width; timeSlot++) {
            System.out.print(timeSlot + " ");
        }
        System.out.println();
    }
}