varying vec2 pass_TextureCoord;

uniform sampler2D tex;

void main(void)
{
	float diffuse = 1.0;
	gl_FragColor = vec4(texture2D(tex,pass_TextureCoord).xyz*diffuse,1.0);
}
