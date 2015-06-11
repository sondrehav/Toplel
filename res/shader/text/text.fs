#version 330 core

layout (location = 0) out vec4 color;

uniform sampler2D tex;

in DATA
{
	vec2 tc;
	vec4 in_color;
} fs_in;

void main()
{
	color = vec4(fs_in.in_color.xyz, texture2D(tex, fs_in.tc).x * fs_in.in_color.w);
	//color = vec4(1.0,1.0,1.0,1.0);
}