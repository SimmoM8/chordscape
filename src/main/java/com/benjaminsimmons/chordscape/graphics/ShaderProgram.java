package com.benjaminsimmons.chordscape.graphics;

import org.lwjgl.opengl.GL20;

public class ShaderProgram {

    private int programId;

    public void init() {
        String vertexShaderSrc = """
                #version 330 core
                
                layout (location = 0) in vec2 position;
                layout (location = 1) in vec3 color;
                
                out vec3 vertexColor;
                
                uniform vec2 uPosition;
                uniform vec2 uScale;
                
                void main() {
                    vec2 scaled = position * uScale;
                    vec2 worldPos = scaled + uPosition;
                
                    gl_Position = vec4(worldPos, 0.0, 1.0);
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

        int vertexShaderId = compileShader(GL20.GL_VERTEX_SHADER, vertexShaderSrc);
        int fragmentShaderId = compileShader(GL20.GL_FRAGMENT_SHADER, fragmentShaderSrc);

        programId = GL20.glCreateProgram();
        GL20.glAttachShader(programId, vertexShaderId);
        GL20.glAttachShader(programId, fragmentShaderId);
        GL20.glLinkProgram(programId);

        if (GL20.glGetProgrami(programId, GL20.GL_LINK_STATUS) == GL20.GL_FALSE) {
            String infoLog = GL20.glGetProgramInfoLog(programId);
            throw new RuntimeException("Shader program linking failed:\n" + infoLog);
        }

        GL20.glDeleteShader(vertexShaderId);
        GL20.glDeleteShader(fragmentShaderId);
    }

    public void setUniform2f(String name, float x, float y) {
        int location = GL20.glGetUniformLocation(programId, name);
        GL20.glUniform2f(location, x, y);
    }

    private int compileShader(int type, String source) {
        int shaderId = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderId, source);
        GL20.glCompileShader(shaderId);

        if (GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == GL20.GL_FALSE) {
            String infoLog = GL20.glGetShaderInfoLog(shaderId);
            throw new RuntimeException("Shader compilation failed:\n" + infoLog);
        }

        return shaderId;
    }

    public void bind() {
        GL20.glUseProgram(programId);
    }

    public void unbind() {
        GL20.glUseProgram(0);
    }

    public void cleanup() {
        GL20.glDeleteProgram(programId);
    }
}