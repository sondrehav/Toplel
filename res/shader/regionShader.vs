#version 330 core

layout ( location = 0 ) in vec2 vertex;
layout ( location = 1 ) in vec2 texcoord;

uniform mat4 prvw_matrix;
uniform mat4 md_matrix;

uniform vec2 uv_from = vec2(0.0,0.0);
uniform vec2 uv_to = vec2(1.0,1.0);

out vec2 tc;

void main()
{
	gl_Position = prvw_matrix * md_matrix * vec4(vertex, 0.0, 1.0);
	tc = texcoord * (uv_to - uv_from) + uv_from;
}