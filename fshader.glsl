#version 120

varying vec2 texCo;
uniform sampler2D tex;

void main()
{ 
    // replace with proper texture function
    gl_FragColor = texture2D(tex, texCo);
} 
