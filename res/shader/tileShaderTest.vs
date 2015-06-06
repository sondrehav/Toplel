#version 330 core

layout ( location = 0 ) in vec4 vertex;

uniform mat4 pr_matrix;
uniform mat4 vi_matrix;
uniform mat4 md_matrix;

uniform float size_mult;

out DATA
{
	vec2 tc;
	vec2 a_tc;
	float threshold;
} vs_out;

void old.main()
{
	gl_Position = pr_matrix * vi_matrix * md_matrix * vec4(vertex.xy, 0.0, 1.0);
	vs_out.tc = vertex.zw;
	vs_out.a_tc = size_mult * vertex.zw;
	threshold = 0.5;
}