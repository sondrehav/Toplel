#version 330 core

layout ( location = 0 ) in vec2 vertex;
layout ( location = 1 ) in vec2 texcoord;

uniform mat3 pr_matrix;
uniform mat3 vw_matrix;
uniform mat3 md_matrix;
uniform vec4 uv_region;

uniform float in_alpha;
uniform float tc_mult;
uniform vec3 in_color;

out DATA
{
	vec2 tc;
	vec4 in_color;
} vs_out;

void oldold.main()
{
	vec3 pos = pr_matrix * vw_matrix * md_matrix * vec3(vertex, 1.0);
	gl_Position = vec4(pos.xy, 0.0, 1.0) * in_alpha;
	vs_out.tc = texcoord * (uv_region.zw - uv_region.xy) + uv_region.xy;
	//vs_out.tc = texcoord * tc_mult;
	vs_out.in_color = vec4(in_color, in_alpha);
}