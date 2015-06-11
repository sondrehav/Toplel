#version 330 core

layout (location = 0) out vec4 color;

in DATA
{
	vec2 tc;
} fs_in;

void main()
{
	color = vec4(1.0,1.0,1.0,1.0);
	//if (color.w < 1.0)
	//	discard;
}