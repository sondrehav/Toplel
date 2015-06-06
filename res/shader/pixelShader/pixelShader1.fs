#version 330 core

layout (location = 0) out vec4 fragColor;
in vec2 fragCoord;

uniform vec2 iResolution;
uniform float iGlobalTime;
uniform sampler2D tex;

#define SAMPLES 8
#define PI 3.14159
#define RADIUS 0.1
#define STRENGTH 1.0
#define THRESHOLD 1.0

float rand(vec2 co){
    return fract(sin(dot(co ,vec2(12.9898,78.233))) * 43758.5453);
}

vec2 getRandomOffsetVector(vec2 orig, int seed){
	return orig + RADIUS * vec2((rand(fragCoord+seed) - 0.5),(rand(fragCoord+seed+1.0) - 0.5));
}

void main()
{
	vec4 col = texture(tex, fragCoord);
	float val = 0.0;
	for(int i=0;i<SAMPLES;i++){
		val += abs(texture(tex, getRandomOffsetVector(fragCoord, i)).x - col.x);
	}
	val /= SAMPLES;
	val = 1.0 - val;
	fragColor = vec4( val, val, val, 1.0 );
}