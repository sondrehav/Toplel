#version 330 core

layout (location = 0) out vec4 color;

uniform sampler2D tex;

in vec2 pass_tc;
in vec4 pass_color;

void main()
{
	vec4 texColor = texture2D(tex, pass_tc);
	if(texColor.r<1.0){
		discard;
	}
	color = texColor * pass_color;
}