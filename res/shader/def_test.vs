#version 330 core

layout ( location = 0 ) in vec2 vertex;
layout ( location = 1 ) in vec2 texcoord;

out DATA
{
	vec2 tc;
	float alpha;
} vs_out;

uniform mat4 prvw_matrix;
uniform mat4 md_matrix;
uniform float alpha = 1.0;

void main()
{
	gl_Position = prvw_matrix * md_matrix * vec4(vertex, 0.0, 1.0);
	vs_out.tc = texcoord;
	vs_out.alpha = alpha;
}