#version 120

attribute vec4 vPosition;
attribute vec2 vTexCoord;

varying vec2 texCo;

void main()
{
     //Rotate it 180 degrees
     vec3 halfTurn = vec3(0,0,180);
     vec3 angles = radians (halfTurn);
        vec3 c = cos (angles);
        vec3 s = sin (angles);

        // rotation matrices
        mat4 rx = mat4 (1.0,  0.0,  0.0,  0.0,
                        0.0,  c.x,  s.x,  0.0,
                        0.0, -s.x,  c.x,  0.0,
                        0.0,  0.0,  0.0,  1.0);

        mat4 ry = mat4 (c.y,  0.0, -s.y,  0.0,
                        0.0,  1.0,  0.0,  0.0,
                        s.y,  0.0,  c.y,  0.0,
                        0.0,  0.0,  0.0,  1.0);

        mat4 rz = mat4 (c.z, s.z,  0.0,  0.0,
                        -s.z,  c.z,  0.0,  0.0,
                        0.0,  0.0,  1.0,  0.0,
                        0.0,  0.0,  0.0,  1.0);


        gl_Position = rz * ry * rx * vPosition;

    texCo = vTexCoord;
}
