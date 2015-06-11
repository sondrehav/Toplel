#version 330 core

layout ( location = 0 ) in vec2 vertex;
layout ( location = 1 ) in vec2 texcoord;

out DATA
{
	vec2 tc;
} vs_out;

void main()
{
	gl_Position = vec4(vertex, 0.0, 1.0);
	vs_out.tc = texcoord;
}