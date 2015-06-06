precision highp float;
uniform float time;
uniform vec2 resolution;
varying vec3 fPosition;
varying vec3 fNormal;
varying vec3 worldNormal;

float interp2(in float x, in float a){
  if(x >= 1.0){
    return 1.0;
  } else if(x <= 0.0) {
    return 0.0;
  }
  return ((1.0 / a) * pow(x, a) - (1.0 / (a + 1.0)) * pow(x, a + 1.0)) / (1.0 / a - 1.0 / (a + 1.0));
}

float interp1(in float x, in float a){
  if(x >= 1.0){
    return 1.0;
  } else if(x <= 0.0) {
    return 0.0;
  }
  return sign(x - 0.5) * pow((2.0 * abs(6.0 * (.5 * pow(x, 2.0) - (1.0 / 3.0) * pow(x, 3.0)) - 0.5)), a) + 1.0;
}

vec3 lightDirection = vec3(0.0,0.0,1.0);

float rimAmount = 1.0;
float rimPower = 5.0;
vec3 rimColour = vec3(1.0, 1.0, 1.0);

vec3 amb = vec3(.1,.1,.2);

vec3 diffuse = vec3(1.0,1.0,1.0);

float specularAmount = 1.0;
float specularPower = 1000.0;

void old.main()
{
  vec3 normals = normalize(fNormal);
  
  vec3 ambient = amb;
  
  vec3 rim = rimColour * pow((1.0 - dot(fNormal, lightDirection)), rimPower) * rimAmount;
  
  vec3 specularCol = vec3(1.0,1.0,1.0) * specularAmount * pow(dot(normals, normalize(lightDirection)), specularPower);
  diffuse = (diffuse * dot(normals, normalize(lightDirection)) + ambient) / (length(diffuse + ambient));
  gl_FragColor = vec4(diffuse + specularCol + rim, 1.0);
}
