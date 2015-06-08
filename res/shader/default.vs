#version 330 core

layout ( location = 0 ) in vec4 vertex;

uniform mat3 pr_matrix;
uniform mat3 vi_matrix;
uniform mat3 md_matrix;

out DATA
{
	vec2 tc;
} vs_out;

void main()
{
	vec3 pos = pr_matrix * vi_matrix * md_matrix * vec3(vertex.xy, 1.0);
	gl_Position = vec4(pos.x, pos.y, 0.0, pos.z)
	vs_out.tc = vertex.zw;
}