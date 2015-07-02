#version 330 core

layout (location = 0) out vec4 color;

in vec2 tc;
in float lightDistance;
in float lightIntensity;
in float lightSpread;

uniform sampler2D tex;

void main()
{
	vec4 texColor = texture2D(tex, tc);
	float diffuse = lightIntensity * lightSpread / (lightDistance + lightSpread);
	texColor.xyz *= diffuse;
	color = texColor;
}