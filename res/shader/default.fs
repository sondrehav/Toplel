#version 330 core

layout (location = 0) out vec4 color;

in vec2 pass_tc;
in float pass_alpha;
in float pass_addColor;

uniform sampler2D tex;

void main()
{

	vec4 color_ = texture(tex, pass_tc);
	if(color_.w<1.0) discard;
	color_ = color_ * vec4(1.0,1.0,1.0, pass_alpha);
	color_ += pass_addColor;
	color = color_;
}