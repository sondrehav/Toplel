#version 330 core

layout ( location = 0 ) in vec4 vertex;

uniform mat3 projection;
uniform vec2 uv_from;
uniform vec2 uv_to;
uniform float offset_x;
uniform float offset_y;

out DATA
{
	vec2 tc;
} vs_out;

void main()
{
	vec2 off = vec2(offset_x, offset_y);
	vec3 pos = projection * vec3(vertex.xy + off, 1.0);
	gl_Position = vec4(pos.xy, 0.0, 1.0);
	vs_out.tc = vertex.zw * uv_to + uv_from;
}