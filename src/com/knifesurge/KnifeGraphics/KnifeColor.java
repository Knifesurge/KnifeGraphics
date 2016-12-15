package com.knifesurge.KnifeGraphics;

import java.beans.ConstructorProperties;
/** Wrapper class for the default java.awt.Color class. Creates Colors to display
	onto the screen for viewing purposes. Many cool presets. Lots of fun.
	Tons of constructors. Its all-in-all a great class.
*/
public class KnifeColor
{
	/** A special number to convert int RGB values to float RGB vlaues **/
	private static final float specnum = 1/255f;

	/** Integer representation of the current KnifeColor */
	public static int value;
	/** Float array representation of the current KnifeColor */
	public static float[] fvalue;
	/** Float array representation of the current KnifeColor.
	    Used in the constructors to set fvalue
	 */
	private static float[] frgbvalue;
	/** Used to get the current KnifeColor */
	public static KnifeColor getCurrentColor(int format)
	{
		switch(format)
		{
			case 0:	//int representation
				return new KnifeColor(value);
			case 1:	//float representation
				return new KnifeColor(fvalue[0], fvalue[1], fvalue[2]);
			default:
				return new KnifeColor(0, 0, 0);
		}
	}
	/** Used to make sure the right KnifeColor gets used */
	public static KnifeColor assignColor(KnifeColor color)
	{
		int r, g, b, a;
		fvalue[3] = color.getAlpha();
		a = color.getAlphai();
		fvalue[2] = color.getBlue();
		b = color.getBluei();
		fvalue[1] = color.getGreen();
		g = color.getGreeni();
		fvalue[0] = color.getRed();
		r = color.getRedi();
		System.out.println("r:"+r+", g:"+g+", b:"+b+", a:"+a);
		System.out.println("0:"+fvalue[0]+", 1:"+fvalue[1]+", 2:"+fvalue[2]+", 3:"+fvalue[3]);
		if(checkvalue())
			return new KnifeColor(fvalue[0], fvalue[1], fvalue[2]);
		else
		{
			value = ((a & 0xFF) >> 24) |
				((r & 0xFF) >> 16) |
				((g & 0xFF) >> 8) |
				((b & 0xFF) >> 0);
			testColorValueRange(r,g,b,a);
			return new KnifeColor(value, true);
		}
	}
	/** Checks to see if the float array is null or not
		( Used for validation and whether or not to use the integer
		  representation of the KnifeColor)
	 */
	private static boolean checkvalue()
	{
		if(fvalue.length == 0)
			return false;
		return true;
	}
	/** Get the red component of the current KnifeColor */
	public static float getRed()
	{
		if(checkvalue())
			return fvalue[0];
		return ((value >> 16) & 0xFF)/255;
	}
	/** Get the red component of the current KnifeColor as an int */
	public static int getRedi()
	{
		return ((value >> 16) & 0xFF);
	}
	/** Get the green component of the current KnifeColor */
	public static float getGreen()
	{
		if(checkvalue())
			return fvalue[1];
		return ((value >> 8) & 0xFF)/255;
	}
	/** Get the green component of the current KnifeColor as an int */
	public static int getGreeni()
	{
		return ((value >> 8) & 0xFF);
	}
	/** Get the blue component of the current KnifeColor */
	public static float getBlue()
	{
		if(checkvalue())
			return fvalue[2];
		return ((value >> 0) & 0xFF)/255;
	}
	/** Get the blue component of the current KnifeColor as an int */
	public static int getBluei()
	{
		return ((value >> 0) & 0xFF);
	}
	/** Get the alpha component of the current KnifeColor */
	public static float getAlpha()
	{
		if(checkvalue())
			return fvalue[3];
		return ((value >> 24) & 0xFF)/255;
	}
	/** Get the alpha component of the current KnifeColor as an int */
	public static int getAlphai()
	{
		return ((value >> 24) & 0xFF);
	}
	/** Returns a KnifeColor based on a String that was passed */
	public static KnifeColor getColor(String color)
	{
		switch(color)
		{
			case "WHITE":
			case "white":
				return new KnifeColor(specnum*255, specnum*255, specnum*255);
			case "LIGHT_GRAY":
			case "lightGray":
				return new KnifeColor(specnum*193, specnum*193, specnum*193);
			case "GRAY":
			case "gray":
				return new KnifeColor(specnum*128, specnum*128, specnum*128);
			case "DARK_GRAY":
			case "darkGray":
				return new KnifeColor(specnum*64, specnum*64, specnum*64);
			case "BLACK":
			case "black":
				return new KnifeColor(specnum*0, specnum*0, specnum*0);
			case "RED":
			case "red":
				return new KnifeColor(specnum*255, specnum*0, specnum*0);
			case "PINK":
			case "pink":
				return new KnifeColor(specnum*255, specnum*175, specnum*175);
			case "ORANGE":
			case "orange":
				return new KnifeColor(specnum*255, specnum*200, specnum*0);
			case "YELLOW":
			case "yellow":
				return new KnifeColor(specnum*255, specnum*255, specnum*0);
			case "GREEN":
			case "green":
				return new KnifeColor(specnum*0, specnum*255, specnum*0);
			case "MAGENTA":
			case "magenta":
				return new KnifeColor(specnum*255, specnum*0, specnum*255);
			case "CYAN":
			case "cyan":
				return new KnifeColor(specnum*0, specnum*255, specnum*255);
			case "BLUE":
			case "blue":
				return new KnifeColor(specnum*0, specnum*0, specnum*255);
		}
		return new KnifeColor(specnum*0, specnum*0, specnum*0);
	}
	/**  MAIN CONSTRUCTOR

		One of many constructors. Creates a KnifeColor based on the 
		integer values given
	*/
	@ConstructorProperties({"red", "green", "blue", "alpha"})
	public KnifeColor(int r, int g, int b, int a)
	{
		value = ((a & 0xFF) << 24) |
				((r & 0xFF) << 16) |
				((g & 0xFF) << 8) |
				((b & 0xFF) << 0);
		testColorValueRange(r,g,b,a);
	}
	/** Create a KnifeColor based on the r g b values given. Alpha defaults
		to 255 / 1.0f (full alpha)
	*/
	public KnifeColor(int r, int g, int b)
	{
		this(r, g, b, 255);
	}
	/** Create a KnifeColor based on an integer representation of a KnifeColor
	*/
	public KnifeColor(int rgb)
	{
		value = 0xff000000 | rgb;
	}
	/** Create a KnifeColor based on an integer representation of a KnifeColor
		and a flag on whether or not it has an alpha component
	*/
	public KnifeColor(int rgba, boolean hasalpha) {
        if (hasalpha) {
            value = rgba;
        } else {
            value = 0xff000000 | rgba;
        }
    }
	/** Create a KnifeColor based on float r g b values. Fills the fvalue[]
		appropriately
	*/
	public KnifeColor(float r, float g, float b)
	{
		this( (int) (r*255+0.5), (int) (g*255+0.5), (int) (b*255+0.5));
		testColorValueRange(r,g,b,1.0f);
		frgbvalue = new float[4];
		frgbvalue[0] = r;
		frgbvalue[1] = g;
		frgbvalue[2] = b;
		frgbvalue[3] = 1.0f;
		fvalue = frgbvalue;
	}
	/** Creates a KnifeColor based on float r g b values with an alpha component.
		Fills the fvalue[] appropriately
	*/
	public KnifeColor(float r, float g, float b, float a) {
        this((int)(r*255+0.5), (int)(g*255+0.5), (int)(b*255+0.5), (int)(a*255+0.5));
        frgbvalue = new float[4];
        frgbvalue[0] = r;
        frgbvalue[1] = g;
        frgbvalue[2] = b;
        frgbvalue[3] = a;
        fvalue = frgbvalue;
    }
	
	/**
     * Checks the color integer components supplied for validity.
     * Throws an {@link IllegalArgumentException} if the value is out of
     * range.
     * @param r the Red component
     * @param g the Green component
     * @param b the Blue component
     **/
    private static void testColorValueRange(int r, int g, int b, int a) {
        boolean rangeError = false;
        String badComponentString = "";

        if ( a < 0 || a > 255) {
            rangeError = true;
            badComponentString = badComponentString + " Alpha";
        }
        if ( r < 0 || r > 255) {
            rangeError = true;
            badComponentString = badComponentString + " Red";
        }
        if ( g < 0 || g > 255) {
            rangeError = true;
            badComponentString = badComponentString + " Green";
        }
        if ( b < 0 || b > 255) {
            rangeError = true;
            badComponentString = badComponentString + " Blue";
        }
        if ( rangeError == true ) {
        throw new IllegalArgumentException("Color parameter outside of expected range:"
                                           + badComponentString);
        }
    }
	
    /**
     * Checks the color <code>float</code> components supplied for
     * validity.
     * Throws an <code>IllegalArgumentException</code> if the value is out
     * of range.
     * @param r the Red component
     * @param g the Green component
     * @param b the Blue component
     **/
    private static void testColorValueRange(float r, float g, float b, float a) {
        boolean rangeError = false;
        String badComponentString = "";
        if ( a < 0.0 || a > 1.0) {
            rangeError = true;
            badComponentString = badComponentString + " Alpha";
        }
        if ( r < 0.0 || r > 1.0) {
            rangeError = true;
            badComponentString = badComponentString + " Red";
        }
        if ( g < 0.0 || g > 1.0) {
            rangeError = true;
            badComponentString = badComponentString + " Green";
        }
        if ( b < 0.0 || b > 1.0) {
            rangeError = true;
            badComponentString = badComponentString + " Blue";
        }
        if ( rangeError == true ) {
        throw new IllegalArgumentException("Color parameter outside of expected range:"
                                           + badComponentString);
        }
    }
}
