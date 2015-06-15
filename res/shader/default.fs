#version 330 core

layout (location = 0) out vec4 color;

in DATA
{
	vec2 tc;
	float alpha;
} fs_in;

uniform sampler2D tex;

void main()
{
	vec4 color_ = texture(tex, fs_in.tc);
	if(color_.w < 1.0){
		discard;
	}
	color = color_ * vec4(1.0,1.0,1.0, fs_in.alpha);
}