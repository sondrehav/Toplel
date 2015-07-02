#version 330 core

layout ( location = 0 ) in vec2 vertex;
layout ( location = 1 ) in vec2 texcoord;

uniform mat4 prvw_matrix;
uniform mat4 md_matrix;

uniform vec2 light;
uniform vec2 lightAttrib;

out vec2 tc;
out float lightDistance;
out float lightIntensity;
out float lightSpread;

void main()
{
	lightIntensity = lightAttrib.x;
	lightSpread = lightAttrib.y;
	lightDistance = pow(length(vertex - light), 2.0);
	gl_Position = prvw_matrix * md_matrix * vec4(vertex, 0.0, 1.0);
	tc = texcoord;
}