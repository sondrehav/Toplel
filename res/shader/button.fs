#version 330 core

layout (location = 0) out vec4 color;

in DATA
{
	vec2 tc;
	vec3 pass_color;
	float alpha;
} fs_in;

void main()
{
	color = vec4(fs_in.pass_color, fs_in.alpha);
}