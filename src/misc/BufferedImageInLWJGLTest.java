package misc;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

public class BufferedImageInLWJGLTest {

	private static final int WIDTH = 800, HEIGHT = 600;
	
	public BufferedImageInLWJGLTest()
	{
		//Window.create("Buffered Image in LWJGL", 1080, 720, true, new Game());
		
		//Set the Matrix Mode
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();	//Get a clean version of the matrix above
		glOrtho(0, WIDTH, HEIGHT, 0, -1, 1);	//2D Projection Matrix
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		
		//Create a test BufferedImage
		BufferedImage image = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
		//Create a Graphics2D object from the BufferedImage. This object is attached to the image so whenever you draw something, it will draw in the image
		Graphics2D g = (Graphics2D) image.createGraphics();
		
		//Set Color to a new color
		g.setColor(new Color(1.0f, 1.0f, 1.0f, 0.5f));
		//Draw a rectangle filled with the above color (a slight grey)
		g.fillRect(0, 0, 1080, 720);
		
		//Set thickness of line to 3
		g.setStroke(new BasicStroke(3));
		g.setColor(Color.red);
		g.drawRect(0, 0, 127, 127);
		//Reset the thickness back to 1
		g.setStroke(new BasicStroke(1));
		//Draw a 10x10 square
		g.fillRect(10, 10, 10, 10);
		
		g.setColor(Color.blue);
		//Draw some text to the screen
		g.drawString("Test image", 10, 64);
		
		//Get the textureID so we can bind the image to a drawing buffer for OpenGL
		int textureID = loadTexture(image);
		
		System.out.println(textureID);
		
		glEnable(GL_TEXTURE_2D);	//Enable texturing
		
		//Essentially a game loop but you would put this stuff in render()
		//while(!Window.isCloseRequested())
		while(true)
		{
			//Clear the Color Buffer
			glClear(GL_COLOR_BUFFER_BIT);
			
			//Enable blending so the background shows through the image
			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			
			//Set the matrix up for drawing
			glPushMatrix();
			//Move our position to (100, 100)
			glTranslatef(100, 100, 0);
			//Bind the textureID of the image to a 2D Texture for OpenGL
			glBindTexture(GL_TEXTURE_2D, textureID);
			//Start defining vertices for the Quad
			glBegin(GL_QUADS);
			{
				glTexCoord2f(0, 0);	//Texture coordinate
	            glVertex2f(0, 0);	//Screen coordinate (based on 2D Projection matrix)
	            
	            glTexCoord2f(1, 0);
	            glVertex2f(128, 0);
	            
	            glTexCoord2f(1, 1);
	            glVertex2f(128, 128);
	            
	            glTexCoord2f(0, 1);
	            glVertex2f(0, 128);
			}
			//Stop defining vertices for the Quad
			glEnd();
			//Get that matrix out of here!
			glPopMatrix();
			
			//Update the window (MUST BE CALLED!!!!!!!!!!)
			//Window.update();
		}
		//Destroy the Window once program is done
		//Window.destroy();
		//Exit with no errors
		//System.exit(0);
	}
	
	private static final int BYTES_PER_PIXEL = 4;
	private int loadTexture(BufferedImage image)
	{

	      int[] pixels = new int[image.getWidth() * image.getHeight()];
	      image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

	      ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * BYTES_PER_PIXEL); //4 for RGBA, 3 for RGB
	        
	      for(int y = 0; y < image.getHeight(); y++){
	    	  for(int x = 0; x < image.getWidth(); x++){
	    		  int pixel = pixels[y * image.getWidth() + x];
	    		  buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component (shift current bit 16 bits to the right and masks all bits except for the last 8 (the ones we want))
	    		  buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
	    		  buffer.put((byte) (pixel & 0xFF));               // Blue component
	    		  buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
	    	  }
	      }
	
	      buffer.flip(); //FOR THE LOVE OF GOD DO NOT FORGET THIS
	
	      // You now have a ByteBuffer filled with the color data of each pixel.
	      // Now just create a texture ID and bind it. Then you can load it using 
	      // whatever OpenGL method you want, for example:
	
	      int textureID = glGenTextures(); //Generate texture ID
	      glBindTexture(GL_TEXTURE_2D, textureID); //Bind texture ID
	        
	      //Setup wrap mode
	      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
	      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
	
	      //Setup texture scaling filtering
	      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	        
	      //Send texel data to OpenGL
	      glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
	      
	      //Return the texture ID so we can bind it later again
	      return textureID;
	}
	
	public static void main(String[] args)
	{
		new BufferedImageInLWJGLTest();
	}
}
