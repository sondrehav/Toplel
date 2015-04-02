#version 330 core

layout ( location = 0 ) in vec4 vertex;

uniform mat4 pr_matrix;
uniform mat4 vw_matrix;
uniform mat4 ml_matrix;

out DATA
{
	vec2 tc;
} vs_out;

void main()
{
	gl_Position = pr_matrix * ml_matrix * vec4(vertex.xy, 0.0, 1.0);
	vs_out.tc = vertex.zw;
}