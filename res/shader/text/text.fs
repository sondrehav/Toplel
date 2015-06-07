#version 330 core

layout (location = 0) out vec4 color;

uniform vec3 in_color;
uniform float alpha;
uniform sampler2D tex;

in DATA
{
	vec2 tc;
} fs_in;

void main()
{
	vec2 uv = fs_in.tc;
	color = vec4(in_color,texture(tex, uv).x*alpha);
}