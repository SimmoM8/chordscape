package com.benjaminsimmons.chordscape.graphics;


import org.lwjgl.opengl.GL20;

public class Shader {

    private int program;

    public void init() {
        String vertexShaderSrc = """
                #version 330 core
                layout (location = 0) in vec2 position;
                layout (location = 1) in vec3 color;
                
                out vec3 vertexColor;

                void main() {
                    gl_Position = vec4(position, 0.0, 1.0);
                    vertexColor = color;
                }
                """;

        String fragmentShaderSrc = """
                #version 330 core
                
                in vec3 vertexColor;
                
                out vec4 FragColor;

                void main() {
                    FragColor = vec4(vertexColor, 1.0);
                }
                """;

        int vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(vertexShader, vertexShaderSrc);
        GL20.glCompileShader(vertexShader);

        int fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(fragmentShader, fragmentShaderSrc);
        GL20.glCompileShader(fragmentShader);

        program = GL20.glCreateProgram();
        GL20.glAttachShader(program, vertexShader);
        GL20.glAttachShader(program, fragmentShader);
        GL20.glLinkProgram(program);

        GL20.glDeleteShader(vertexShader);
        GL20.glDeleteShader(fragmentShader);
    }

    public void bind() {
        GL20.glUseProgram(program);
    }

    public void cleanup() {
        GL20.glDeleteProgram(program);
    }
}