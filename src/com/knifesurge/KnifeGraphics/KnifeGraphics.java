package com.knifesurge.KnifeGraphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;

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
		Graphics2D g = (Graphics2D) image.getGraphics();
		
		return false;
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
		// TODO Auto-generated method stub
		
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
