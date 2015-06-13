#version 330 core

layout ( location = 0 ) in vec2 vertex;
layout ( location = 1 ) in vec2 texcoord;

uniform mat3 pr_matrix;
uniform mat3 md_matrix;
uniform float in_alpha;

out DATA
{
	vec2 tc;
	float alpha;
} vs_out;

void oldold.main()
{
	vec3 pos = pr_matrix * md_matrix * vec3(vertex, 1.0);
	gl_Position = vec4(pos.xy, 0.0, 1.0);
	vs_out.tc = texcoord;
	vs_out.alpha = in_alpha;
}