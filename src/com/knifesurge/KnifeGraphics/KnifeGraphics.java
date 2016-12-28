package com.knifesurge.KnifeGraphics;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.nio.ByteBuffer;
import java.text.AttributedCharacterIterator;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public final class KnifeGraphics extends Graphics{

	private static KnifeGraphics knifeuu = null;
	
	protected KnifeGraphics() {}
	
	public void setUpGL(double l, double r, double b, double t, double n, double f)
	{
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(l, r, b, t, n, f);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}
	
	public static KnifeGraphics getKnifeuu()
	{
		if(knifeuu == null)
			knifeuu = new KnifeGraphics();
		return knifeuu;
	}
	
	@Override
	public void dispose()
	{
		knifeuu = null;
	}
	
	@Override
	public boolean drawImage(Image image, int x, int y, ImageObserver imageO)
	{
		BufferedImage image2 = (BufferedImage) image;
		int textureID = loadTexture(image2);
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		GL11.glPushMatrix();
		
		GL11.glTranslatef(x, y, 0);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
		
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(0, 0);
			
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(image2.getHeight(), 0);
			
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(image2.getHeight(), image2.getWidth());
			
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(0, image2.getWidth());
		}
		
		GL11.glEnd();
		
		GL11.glPopMatrix();
		return true;
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
	
	@Override
	public void drawRect(int x, int y, int width, int height)
	{
		GL11.glLoadIdentity();
		GL11.glBegin(GL11.GL_LINE_STRIP);
			GL11.glVertex2i(x, y);	//TOP-LEFT
			GL11.glVertex2i(x, y + height);	//BOTTOM-LEFT
			GL11.glVertex2i(x + width, y + height);	//BOTTOM-RIGHT
			GL11.glVertex2i(x + width, y);	//TOP-RIGHT
			GL11.glVertex2i(x, y);	//TOP-LEFT (to connect the last point back to the first vertex
		GL11.glEnd();
	}
	
	@Override
	public void drawLine(int x1, int y1, int x2, int y2)
	{
		GL11.glLoadIdentity();
		GL11.glBegin(GL11.GL_LINE_STRIP);
			GL11.glVertex2i(x1, y1);
			GL11.glVertex2i(x2, y2);
		GL11.glEnd();
	}
	
	@Override
	public void fillRect(int x, int y, int w, int h)
	{
		GL11.glLoadIdentity();
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glColor3f(KnifeColor.getRed(), KnifeColor.getGreen(), KnifeColor.getBlue());
			//Expects coordinates in a CCW (counter-clockwise) fashion. Start at top left...
			GL11.glVertex2i(x, y);
			//... bottom left ...
			GL11.glVertex2i(x, y+h);
			//... bottom right ...
			GL11.glVertex2i(x+w, y+h);
			//... and top right.
			GL11.glVertex2i(x+w, y);
		GL11.glEnd();
	}
	
	@Override
	public void setColor(Color color)
	{
		throw new RuntimeException("Use KnifeColor version instead.");
	}
	
	public void setColor(float r, float g, float b)
	{
		GL11.glColor3f(r, g, b);
	}

	public void setColor(KnifeColor color)
	{
		GL11.glColor3f(KnifeColor.getRed(), KnifeColor.getGreen(), KnifeColor.getBlue());
	}






	/* Don't care about these ones below */
	
	@Override
	public Graphics create() {return null;}
	
	@Override
	public void clearRect(int arg0, int arg1, int arg2, int arg3){}

	@Override
	public void clipRect(int arg0, int arg1, int arg2, int arg3){}

	@Override
	public void copyArea(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5){}

	

	

	@Override
	public void drawArc(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5){}

	

	@Override
	public boolean drawImage(Image image, int x, int y, Color color, ImageObserver imageO) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean drawImage(Image image, int x, int y, int width, int height, ImageObserver imageO) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean drawImage(Image image, int x, int y, int width, int height, Color color, ImageObserver imageO) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean drawImage(Image image, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2,
			ImageObserver imageO) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean drawImage(Image image, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2,
			Color color, ImageObserver imageO) {
		// TODO Auto-generated method stub
		return false;
	}

	

	@Override
	public void drawOval(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawPolygon(int[] arg0, int[] arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawPolyline(int[] arg0, int[] arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawRoundRect(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawString(String arg0, int arg1, int arg2) {
	}

	@Override
	public void drawString(AttributedCharacterIterator arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fillArc(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fillOval(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fillPolygon(int[] arg0, int[] arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void fillRoundRect(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Shape getClip() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rectangle getClipBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Font getFont() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FontMetrics getFontMetrics(Font arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setClip(Shape arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setClip(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void setFont(Font arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPaintMode() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setXORMode(Color arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void translate(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

}
