package com.knifesurge.testing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.lwjgl.opengl.GL11;

import com.knifesurge.KnifeGraphics.KnifeDisplay;
import com.knifesurge.KnifeGraphics.KnifeGraphics;

public class Test implements Runnable{

	boolean running = false;
	
	Thread thread = null;
	
	private static int WIDTH, HEIGHT;
	
	private static String osName = null;
	
	
//	private static final int WIDTH = 400, HEIGHT = WIDTH / 16 * 9;
	
	public synchronized void start()
	{
		if(running) return;
		running = true;
		thread = new Thread(this, "TESTING_THREAD");
        if (getOSName().contains("Mac"))
        	thread.run();
        else if (getOSName().contains("Windows"))
        {
        	WIDTH = 1080;
        	HEIGHT = WIDTH / 16 * 9;
        	thread.start();
        }
        else
        {
        	WIDTH = 400;
        	HEIGHT = WIDTH / 16 * 9;
        	thread.start();
        }
		System.out.println("Starting tests...");
	}
	
	public synchronized void stop()
	{
		if(!running) return;
		running = false;
		System.out.println("Stopping tests...");
		try { thread.join();} catch (Exception e) {e.printStackTrace();}
	}
	
	private static String getOSName()
	{
		if(osName == null)
			osName = System.getProperty("os.name");
		return osName;
	}
	
	private void init()
	{
		KnifeDisplay.create("Test", WIDTH, HEIGHT, true);
		KnifeGraphics.getKnifeuu().setUpGL(0, WIDTH, HEIGHT, 0, -1, 1);
	}
	
	public void run()
	{
		long lastTime = System.nanoTime();
		double nsPerTick = 1.0E9D/60D;
		
		int ticks = 0;
		int frames = 0;
		
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		
		init();
		
		while(!KnifeDisplay.shouldClose())
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;
			while(delta >= 1)
			{
				ticks++;
				tick();
				delta -= 1;
				shouldRender = true;
			}
			
			try { 
				Thread.sleep(2);
			} catch (Exception e) {e.printStackTrace();}
			
			if(shouldRender)
			{
				frames++;
				render();
			}
			
			if(System.currentTimeMillis() - lastTimer >= 1000)
			{
				lastTimer += 1000;
				System.out.println(ticks + " ticks, " + frames + " frames");
				frames = 0;
				ticks = 0;
			}
			KnifeDisplay.update();
		}
	}
	
	public void tick()
	{
		
	}
	
	public void render()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		
		KnifeGraphics g = KnifeGraphics.getKnifeuu();
		
		BufferedImage image = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g2 = (Graphics2D) image.createGraphics();
		
		g2.setColor(new Color(1.0f ,1.0f, 1.0f, 0.5f));
		g2.fillRect(0, 0, 1080, 720);
		g2.setStroke(new BasicStroke(3));
		g2.setColor(Color.red);
		g2.drawRect(0, 0, 127, 127);
		g2.setStroke(new BasicStroke(1));
		g2.fillRect(10, 10, 10, 10);
		g2.setColor(Color.blue);
		g2.drawString("Test image", 10, 64);
		g.drawImage(image, 100, 100, null);
		
	}

	public static void main(String[] args)
	{
		new Test().start();
	}
	
}
