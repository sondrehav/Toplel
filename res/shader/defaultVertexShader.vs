attribute vec3 in_Position;
attribute vec2 in_TextureCoord;

varying vec2 pass_TextureCoord;

void main(void) {
	gl_Position = gl_ProjectionMatrix * gl_ModelViewMatrix * vec4(in_Position, 1.0);
	pass_TextureCoord = in_TextureCoord;
}