#version 150
uniform sampler2D DiffuseSampler;
in vec2 texCoord;
out vec4 fragColor;

void main() {
    vec4 color = texture(DiffuseSampler, texCoord);
    color.rgb = 1.0 - color.rgb; // Инвертируем цвета
    fragColor = color;
}
