#version 150

uniform sampler2D DiffuseSampler;

in vec2 texCoord;

uniform float InverseAmount;
uniform vec2 InSize;

out vec4 fragColor;

void main() {
    vec4 diffuseColor = texture(DiffuseSampler, texCoord);
    vec4 invertColor = 1.0 - diffuseColor;
    vec4 outColor = mix(diffuseColor, invertColor, InverseAmount);
    fragColor = vec4(outColor.rgb, 1.0);
    gl_FragDepth = gl_FragCoord.z;  // Відновлення глибини
}
