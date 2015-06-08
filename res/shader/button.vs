#version 330 core

layout ( location = 0 ) in vec4 vertex;

uniform mat3 projection;
uniform float alpha;
uniform vec3 in_color;

out DATA
{
	vec2 tc;
	float alpha;
	vec3 pass_color;
} vs_out;

void main()
{
	vec3 pos = projection * vec3(vertex.xy, 1.0);
	gl_Position = vec4(pos.x, pos.y, 0.0, pos.z);
	vs_out.tc = vertex.zw;
	vs_out.pass_color = in_color;
	vs_out.alpha = alpha;
}