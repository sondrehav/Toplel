#version 330 core

layout (location = 0) out vec4 color;

in DATA
{
	vec2 tc;
	vec2 a_tc;
	float threshold;
} fs_in;

uniform sampler2D tex;
uniform sampler2D alpha;

void old.main()
{
	//color = vec4(1.0,1.0,1.0,1.0);
	
	float alpha_at_point = texture(alpha, fs_in.a_tc).x;
	
	if(alpha_at_point < threshold){
		return;
	}
	
	color = texture(tex, fs_in.tc) * vec4(1.0,1.0,1.0,1.0);
	
}