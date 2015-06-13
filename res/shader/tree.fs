#version 330 core

layout (location = 0) out vec4 color;

in DATA
{
	vec2 tc;
	float hue;
} fs_in;

uniform sampler2D tex;

void oldold.main()
{
	vec4 out_color = texture2D(tex, fs_in.tc);
	out_color.g *= fs_in.hue;
	color = out_color;
}