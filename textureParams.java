/**
 *
 * textureParams.java
 *
 * Simple class for setting up the textures for the textures
 * Assignment.
 *
 * Students are to complete this class.
 *
 */

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.IntBuffer;
import javax.imageio.ImageIO;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.fixedfunc.*;
import com.jogamp.opengl.util.texture.*;

public class textureParams
{
    
	/**
	 * constructor
	 */
	public textureParams()
	{
        
	}
    
    /**
     * This functions loads texture data to the GPU.
     *
     * You will need to write this function, and maintain all of the values needed
     * to be sent to the various shaders.
     *
     * @param filename - The name of the texture file.
     * Implemented by: Stephen Yingling
     */
    public void loadTexture (String filename)
    {
        Texture tex_id;

        try{
            InputStream stream = new FileInputStream(filename);
            tex_id = TextureIO.newTexture(stream,false,"jpg");
        }catch(IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    
    /**
     * This functions sets up the parameters
     * for texture use.
     *
     * You will need to write this function, and maintain all of the values needed
     * to be sent to the various shaders.
     *
     * @param program - The ID of an OpenGL (GLSL) program to which parameter values
     *    are to be sent
     *
     * @param gl2 - GL2 object on which all OpenGL calls are to be made
     *
     * It did not seem necessary to imp[lement this to get the program to work on the Ubuntu Systems
     */
    public void setUpTextures (int program, GL2 gl2)
    {
    }
}
