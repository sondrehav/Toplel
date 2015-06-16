#version 330 core

layout ( location = 0 ) in vec2 vertex;
layout ( location = 1 ) in vec2 texcoord;

uniform mat4 prvw_matrix;
uniform mat4 md_matrix;
uniform float alpha = 1.0;
uniform float addColor = 0.0;

out vec2 pass_tc;
out float pass_alpha;
out float pass_addColor;

void main()
{
	vec4 pos = vec4(vertex,0.0,1.0);
	pos = prvw_matrix * md_matrix * pos;
	gl_Position = pos;
	pass_tc = texcoord;
	pass_alpha = alpha;
	pass_addColor = addColor;
}