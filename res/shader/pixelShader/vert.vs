#version 330 core

layout ( location = 0 ) in vec4 vertex;

out vec2 fragCoord;

void main()
{
	gl_Position = vec4(vertex.xy, 0.0, 1.0);
	fragCoord = vertex.zw;
}