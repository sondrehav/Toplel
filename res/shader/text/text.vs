#version 330 core

layout ( location = 0 ) in vec2 vertex;
layout ( location = 1 ) in vec2 texcoord;

uniform mat4 prvw_matrix;
uniform mat4 md_matrix;

uniform vec4 in_color = vec4(1.0,1.0,1.0,1.0);

out vec2 pass_tc;
out vec4 pass_color;

void main()
{
	gl_Position = prvw_matrix * md_matrix * vec4(vertex, 0.0, 1.0);
	pass_tc = texcoord;
	pass_color = in_color;
}