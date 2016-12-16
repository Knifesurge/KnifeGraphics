package com.knifesurge.testing;

import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWJoystickCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import com.knifesurge.KnifeGraphics.KnifeColor;
import com.knifesurge.KnifeGraphics.KnifeGraphics;

public class Test implements Runnable{

	boolean running = false;
	
	Thread thread = null;
	
	private static final int WIDTH = 1080, HEIGHT = WIDTH / 16 * 9;
//	private static final int WIDTH = 400, HEIGHT = WIDTH / 16 * 9;
	
	public synchronized void start()
	{
		if(running) return;
		running = true;
		thread = new Thread(this, "TESTING_THREAD");
		String osName = System.getProperty("os.name");
        if (osName.contains("Mac"))
        	thread.run();
        else
        	thread.start();
		System.out.println("Starting tests...");
	}
	
	public synchronized void stop()
	{
		if(!running) return;
		running = false;
		try { thread.join();} catch (Exception e) {e.printStackTrace();}
		System.out.println("Stopping tests...");
	}
	
	private void init()
	{
		Display.create("Test", WIDTH, HEIGHT, true);
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
		
		while(!Display.shouldClose())
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
			Display.update();
		}
	}
	
	public void tick()
	{
		
	}
	
	public void render()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		
		KnifeGraphics g = KnifeGraphics.getKnifeuu();
		g.setColor(KnifeColor.assignColor(KnifeColor.getColor("darkGray")));
		g.fillRect(0, 0, 100, 100);
		g.setColor(KnifeColor.assignColor(KnifeColor.getColor("yellow")));
		g.drawRect(200, 200, 300, 300);
	}

	public static void main(String[] args)
	{
		new Test().start();
	}
	
}

class Display
{
	private static String 					TITLE;
	private static int 						WIDTH;
	private static int 						HEIGHT;
	private static long 					window;
	private static GLFWKeyCallback 			keyCallback;
	private static GLFWMouseButtonCallback 	mouseCallback;
	private static GLFWJoystickCallback		controllerCallback;
	private static boolean vSync;
	
	public static void create(String title, int width, int height, boolean vsync)
	{
		Display.TITLE = title;
		Display.WIDTH = width;
		Display.HEIGHT = height;
		Display.vSync = vsync;
		//Callback to catch and print errors
		glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
		
		//Initialize GLFW, exit if it fails
		if(!glfwInit())
		{
			System.err.println("GLFW failed to initialize");
			destroy();
			System.exit(1);
		}
		
		glfwDefaultWindowHints();					//Generate default window hints
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);	//Needs to be false so we can move and center the window
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);	//Makes the window resizable

		
		window = glfwCreateWindow(width, height, title, 0, 0);	//Create window with width, height, title, monitor, and share
		if(window == NULL)
			throw new RuntimeException("Failed to create the GLFW window");
		
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());	//Get monitor's desired video mode
		glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);	//Set the poition of the window at xPos, yPos
		glfwMakeContextCurrent(window);	//Get OpenGL context for this window and make it current for the thread that this method was executed on (main thread)
		
		if(isvSync())
			//Enable V-Sync
			glfwSwapInterval(1);
		
		glfwShowWindow(window);			//Display the window (same as glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE))
		GL.createCapabilities();		//Creates GL capabilities instance and find context that GLFW window created and lets the bindings be used
		
		//Set the clear color
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
	}
	
	/**
	 * Destroys and resources GLFW or the Display has allocated
	 */
	public static void destroy()
	{
		glfwTerminate();
		keyCallback.free();
		mouseCallback.free();
		controllerCallback.free();
	}
	/**
	 * Updates the Display to render the output to the screen as well as polls any pending GLFW events
	 */
	public static void update()
	{
		glfwPollEvents();
		glfwSwapBuffers(window);
	}
	
	public static boolean isCloseRequested()
	{
		return glfwWindowShouldClose(window);
	}
	
	public void setClearColor(float r, float g, float b, float a)
	{
		glClearColor(r, g, b, a);
	}
	
	public String getTitle()
	{
		return TITLE;
	}
	
	public int getWidth()
	{
		return WIDTH;
	}
	
	public int getHeight()
	{
		return HEIGHT;
	}
	
	public static boolean isvSync()
	{
		return vSync;
	}
	
	public static boolean shouldClose()
	{
		return glfwWindowShouldClose(window);
	}
	
	public void setvSync(boolean vSync)
	{
		this.vSync = vSync;
	}
}
