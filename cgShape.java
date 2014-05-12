/**
 * cgShape.java
 *
 * Class that includes routines for tessellating a number of basic shapes
 *
 * Students are to supply their implementations for the
 * functions in this file using the function "addTriangle()" to do the 
 * tessellation.
 *
 */

import java.awt.*;
import java.nio.*;
import java.awt.event.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import java.io.*;

public class cgShape extends simpleShape
{
    /**
     * constructor
     */
    public cgShape()
    {
    }
    
     
    /**
     * makeDefaultShape - creates a "unit" shape of your choice using
     * your tesselation routines.
     * 
     *
     */
    public void makeDefaultShape ()
    {
        makeCube(5);
    }

    /**
     * makeCube - Create a unit cube, centered at the origin, with a given number
     * of subdivisions in each direction on each face.
     *
     * @param subdivisions - number of equal subdivisons to be made in each
     *        direction along each face
     *
     * Can only use calls to addTriangle()
     * Implemented by Stephen Yingling
     */
    public void makeCube (int subdivisions)
    {
        if( subdivisions < 1 )
            subdivisions = 1;

        //Make all the points at the corners of the cube
        MyPoint frontLowerLeft = new MyPoint(-.5f,-.5f,-.5f),
                frontLowerRight = new MyPoint(.5f,-.5f,-.5f),
                frontUpperRight = new MyPoint(.5f,.5f,-.5f),
                frontUpperLeft = new MyPoint(-.5f,.5f,-.5f),
                backLowerLeft = new MyPoint(-.5f,-.5f,.5f),
                backUpperLeft = new MyPoint(-.5f,.5f,.5f),
                backLowerRight = new MyPoint(.5f,-.5f,.5f),
                backUpperRight = new MyPoint(.5f,.5f,.5f)
                        ;

        //Draw each face

        //Front
        makeQuad(frontLowerLeft,frontLowerRight,frontUpperRight,
                frontUpperLeft, subdivisions,.25f,.5f,false);

        //left
        makeQuad(backLowerLeft,frontLowerLeft,frontUpperLeft,
                backUpperLeft, subdivisions,0,.5f,false);

        //back
        makeQuad(backLowerRight, backLowerLeft, backUpperLeft,
                backUpperRight, subdivisions,.75f,.5f,true);

        //top
        makeQuad(frontUpperLeft, frontUpperRight, backUpperRight,
                backUpperLeft, subdivisions,.25f,.75f,false);

        //bottom
        makeQuad(backLowerLeft, backLowerRight, frontLowerRight,
                frontLowerLeft, subdivisions,.25f,.25f,true);

        //right
        makeQuad(frontLowerRight, backLowerRight, backUpperRight,
                frontUpperRight, subdivisions,.5f,.5f,true);

    }

    /**
     * Makes a quad from the given points with the given subdivisions
     * @param ll - The lower left corner of the quad
     * @param lr - The lower right corner of the quad
     * @param ur - The upper right corner of the quad
     * @param ul - The upper left corner of the quad
     * @param subs - The number of subdivisions for the quad
     * Implemented by: Stephen Yingling
     */
    public void makeQuad(MyPoint ll, MyPoint lr, MyPoint ur, MyPoint ul, int subs,float textureLLx, float textureLLy, boolean flip ){
        float subsize = 1.0f/subs;

        //Subdivide the quad into columns and draw the columns
        for(int i=0; i<subs; i++){
            float curPos = subsize * i;
            MyPoint q = ul.mult(1f-curPos).add(ur.mult(curPos));
            MyPoint r = ll.mult(1f-curPos).add(lr.mult(curPos));

            float iprime = curPos + subsize;
            MyPoint qPrime = ul.mult(1f-iprime).add(ur.mult(iprime));
            MyPoint rprime = ll.mult(1f-iprime).add(lr.mult(iprime));

            makeQuadCol(q,r,qPrime,rprime,subs,textureLLx,textureLLy, flip);
        }
    }

    /**
     * Draws a column of quads
     * @param q - The left upper point of the column
     * @param r - The left lower point of the column
     * @param qp - The right upper point of the column
     * @param rp - The right lower point of the column
     * @param numSubs - The number of rows for this column
     * Implemented by: Stephen Yingling
     */
    public void makeQuadCol(MyPoint q, MyPoint r, MyPoint qp, MyPoint rp, int numSubs, float txllx, float txlly, boolean flip){
        float sublength = 1f/numSubs;

        //Calculate and draw two triangles for each row
        for(int i=0; i < numSubs; i++){
            float f = i * sublength;
            MyPoint p1 = q.mult(1-f).add(r.mult(f)),
                    p2= qp.mult(1-f).add(rp.mult(f)),
                    p3 = q.mult(1-(f+sublength)).add(r.mult(f+sublength)),
                    p4 = qp.mult(1-(f+sublength)).add(rp.mult(f+sublength));

            addTriangle(p3,p4,p2,txllx,txlly,flip);
            addTriangle(p3,p2,p1,txllx,txlly,flip);
        }
    }

    /**
     * A callthrough to the other addTriangle function
     * @param p1 - The first point of the triangle
     * @param p2 - The second point of the triangle
     * @param p3 - The third point of the triangle
     * @param txllx - The x coordinate of the lower left of the texture square
     * @param txlly - The y coordinate of the lower left of the texture square
     * Implemented by: Stephen Yingling
     */
    public void addTriangle(MyPoint p1, MyPoint p2, MyPoint p3,float txllx, float txlly, boolean flip){

	//front and back
        if(p1.getZ() == p2.getZ() && p2.getZ() == p3.getZ()){
            if(!flip){
                addTriangle(p1.getX(), p1.getY(), p1.getZ(),.25f*(p1.getX()+.5f)+txllx,.25f*(p1.getY()+.5f)+txlly,
                    p2.getX(), p2.getY(), p2.getZ(), .25f*(p2.getX()+.5f)+txllx,.25f*(p2.getY()+.5f)+txlly,
                    p3.getX(), p3.getY(), p3.getZ(), .25f*(p3.getX()+.5f)+txllx, .25f*(p3.getY()+.5f)+txlly);
            }
            else{
                addTriangle(p1.getX(), p1.getY(), p1.getZ(),-.25f*(p1.getX()-.5f)+txllx,.25f*(p1.getY()+.5f)+txlly,
                        p2.getX(), p2.getY(), p2.getZ(), -.25f*(p2.getX()-.5f)+txllx,.25f*(p2.getY()+.5f)+txlly,
                        p3.getX(), p3.getY(), p3.getZ(), -.25f*(p3.getX()-.5f)+txllx, .25f*(p3.getY()+.5f)+txlly);
            }
        }

	//left and right
        if(p1.getX() == p2.getX() && p2.getX() == p3.getX()){

            if(!flip){
                addTriangle(p1.getX(), p1.getY(), p1.getZ(),-.25f*(p1.getZ()-.5f)+(txllx),.25f*(p1.getY()+.5f)+txlly,
                        p2.getX(), p2.getY(), p2.getZ(), -.25f*(p2.getZ()-.5f)+(txllx),.25f*(p2.getY()+.5f)+txlly,
                        p3.getX(), p3.getY(), p3.getZ(), -.25f*(p3.getZ()-.5f)+(txllx), .25f*(p3.getY()+.5f)+txlly);
            }
            else{
                addTriangle(p1.getX(), p1.getY(), p1.getZ(),.25f*(p1.getZ()+.5f)+(txllx),.25f*(p1.getY()+.5f)+txlly,
                        p2.getX(), p2.getY(), p2.getZ(), .25f*(p2.getZ()+.5f)+(txllx),.25f*(p2.getY()+.5f)+txlly,
                        p3.getX(), p3.getY(), p3.getZ(), .25f*(p3.getZ()+.5f)+(txllx), .25f*(p3.getY()+.5f)+txlly);
            }
        }

	//Top and bottom
        if(p1.getY() == p2.getY() && p1.getY() == p3.getY()){

            if(!flip){
                addTriangle(p1.getX(), p1.getY(), p1.getZ(),.25f*(p1.getX()+.5f)+txllx,.25f*(p1.getZ()+.5f)+txlly,
                        p2.getX(), p2.getY(), p2.getZ(), .25f*(p2.getX()+.5f)+txllx,.25f*(p2.getZ()+.5f)+txlly,
                        p3.getX(), p3.getY(), p3.getZ(), .25f*(p3.getX()+.5f)+txllx, .25f*(p3.getZ()+.5f)+txlly);
            }
            else{
                addTriangle(p1.getX(), p1.getY(), p1.getZ(),.25f*(p1.getX()+.5f)+txllx,-.25f*(p1.getZ()-.5f)+txlly,
                        p2.getX(), p2.getY(), p2.getZ(), .25f*(p2.getX()+.5f)+txllx,-.25f*(p2.getZ()-.5f)+txlly,
                        p3.getX(), p3.getY(), p3.getZ(), .25f*(p3.getX()+.5f)+txllx, -.25f*(p3.getZ()-.5f)+txlly);
            }
        }
    }

    /**
     * A class to represent a point in 3D space
     * Created by: Stephen Yingling
     */
    public class MyPoint{
        protected float x;
        protected float y;
        protected float z;

        /**
         * Make a point
         * @param x - The x value of the point
         * @param y - The y value of the point
         * @param z - The z value of the point
         * Implemented by: Stephen Yingling
         */
        public MyPoint(float x, float y, float z){
            this.x = x;
            this.y = y;
            this.z = z;
        }

        /**
         * Adds the value of one point to this point and returns a new point
         * containg the result
         * @param other - The point to add to this one
         * @return A point containing the point addition of this point and the other one
         * Implemented by: Stephen Yingling
         */
        public MyPoint add(MyPoint other){

            return new MyPoint(this.x + other.getX(), this.y + other.getY(), this.z + other.getZ());
        }

        /**
         * Multiply this point by a scalar value
         * @param f - The number to multiply the point by
         * @return A new point with the new values
         *
         * Implemented by: Stephen Yingling
         */
        public MyPoint mult(float  f){
            return new MyPoint(x * f, y*f, z*f);
        }

        /**
         * Determine the midpoint between this point and another
         * @param other The other point
         * @return A new point representing the midpoint
         * Implemented by: Stephen Yingling
         */
        public MyPoint midPoint(MyPoint other){
            float nx = (other.getX()+x)/2f,
                    ny = (other.getY()+y)/2f,
                    nz = (other.getZ()+z)/2f;

            return new MyPoint(nx,ny,nz);
        }

        /**
         * Gets the magnitude of this point
         * @return The magnitude of this point
         * Implemented by: Stephen Yingling
         */
        public float getMagnitude(){
            return (float)Math.sqrt(Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2));
        }

        /**
         *
         * @return The x value
         * Implemented by: Stephen Yingling
         */
        public float getX() {
            return x;
        }

        /**
         *
         * @return The y value
         * Implemented by: Stephen Yingling
         */
        public float getY() {
            return y;
        }


        /**
         *
         * @return The z value
         * Implemented by: Stephen Yingling
         */
        public float getZ() {
            return z;
        }

        /**
         * Sets the z value to the specified value
         * @param z - The new z value
         * Implemented by: Stephen Yingling
         */
        public void setZ(float z) {
            this.z = z;
        }
    }
}
