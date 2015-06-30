#version 330 core

layout ( location = 0 ) in vec2 vertex;
layout ( location = 1 ) in vec2 texcoord;

uniform mat4 prvw_matrix;
uniform mat4 md_matrix;

out vec2 pass_tc;

void main()
{
	vec4 pos = vec4(vertex,0.0,1.0);
	gl_Position = prvw_matrix * md_matrix * pos;
	pass_tc = texcoord;
}