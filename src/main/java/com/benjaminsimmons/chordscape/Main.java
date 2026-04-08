package com.benjaminsimmons.chordscape;

import com.benjaminsimmons.chordscape.app.Application;

public class Main {
    public static void main(String[] args) {
        try {
            new Application().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}