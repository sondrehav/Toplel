#version 330 core

layout (location = 0) out vec4 color;

in DATA
{
	vec2 tc;
	float alpha_pass;
} fs_in;

uniform sampler2D tex;

void old.main()
{
	//color = vec4(1.0,1.0,1.0,1.0);
	color = texture(tex, fs_in.tc) * vec4(1.0,1.0,1.0,fs_in.alpha_pass);
	
}