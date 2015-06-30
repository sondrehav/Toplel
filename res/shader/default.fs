#version 330 core

layout (location = 0) out vec4 color;

in vec2 pass_tc;

uniform sampler2D tex;

void main()
{
	vec4 outColor = texture(tex, pass_tc);
	if(outColor.w<1.0) discard;
	color = outColor;
}