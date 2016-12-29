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
	/** Used in brighter and darker algorithms */
	private static final double FACTOR = 0.7;
	/** The current ColorSpace */
	private static java.awt.color.ColorSpace cs;
	/** Used to get the current KnifeColor<br><br>
	 *	0 - 	Integer representation<br>
	 *	1 - 	Float representation<br><br>
	 *	Defaults to black (<code>KnifeColor(0, 0, 0)</code>)
	 */
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
		fvalue[3] = KnifeColor.getAlpha();
		a = KnifeColor.getAlphai();
		fvalue[2] = KnifeColor.getBlue();
		b = KnifeColor.getBluei();
		fvalue[1] = KnifeColor.getGreen();
		g = KnifeColor.getGreeni();
		fvalue[0] = KnifeColor.getRed();
		r = KnifeColor.getRedi();
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
	/** Returns the RGB value representing the KnifeColor in the default sRGB ColorModel */
	public int getRGB()
	{
		return value;
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
	/** Creates a KnifeColor in the specified ColorSpace with the color components 
		specified in the float array and the specified alpha. The number 
		of components is determined by the type of the ColorSpace. For example, 
		RGB requires 3 components, but CMYK requires 4 components.
	*/
	public KnifeColor(java.awt.color.ColorSpace cspace, float components[], float alpha)
	{
		boolean rangeError = false;
		String badComponentString = "";
		int n = cspace.getNumComponents();
		fvalue = new float[n];
		for(int i=0;i<n;i++)
		{
			if(components[i] < 0.0 || components[i] > 1.0)
			{
				rangeError = true;
				badComponentString = badComponentString + "Component" + i + " ";
			} else
			{
				fvalue[i] = components[i];
			}
		}
		if (alpha < 0.0 || alpha > 1.0)
		{
			rangeError = true;
			badComponentString = badComponentString + "Alpha";
		}else
		{
			fvalue[3] = alpha;
		}
		if(rangeError)
		{
			throw new java.lang.IllegalArgumentException(
				"Color parameter outside of expected range: " +
				badComponentString);
		}
		frgbvalue = cspace.toRGB(fvalue);
		cs = cspace;
		value = ((((int)(fvalue[3]*255)) & 0xFF) << 24) |
			((((int)(fvalue[2]*255)) & 0xFF) << 16) |
			((((int)(fvalue[1]*255)) & 0xFF) << 8) |
			((((int)(fvalue[0]*255)) & 0xFF) << 0);
	}
	/** Creates a new KnifeColor that is a brighter version of this KnifeColor */
	public KnifeColor brighter()
	{
		int r = getRedi();
		int g = getGreeni();
		int b = getBluei();

		/* From 2D group:
		 * 1. black.brighter() should return gray
		 * 2. applying brighter to blue will always return blue, brighter
		 * 3. non pure color (non zero rgb) will eventually return white
		 */
		int i = (int)(1.0/(1.0-FACTOR));
		if ( r == 0 && g == 0 && b == 0)
		{
			return new KnifeColor(i, i, i);
		}
		if( r > 0 && r < i) r = i;
		if( g > 0 && g < i) g = i;
		if( b > 0 && b < i) b = i;

		return new KnifeColor(Math.min((int)(r/FACTOR), 255),
				      Math.min((int)(g/FACTOR), 255),
				      Math.min((int)(b/FACTOR), 255));
	}
	/** Creates a new KnifeColor that is a darker version of this KnifeColor */
	public KnifeColor darker()
	{
		return new KnifeColor(Math.max((int)(getRed() * FACTOR), 0),
							  Math.max((int)(getGreen() * FACTOR), 0),
							  Math.max((int)(getBlue() * FACTOR), 0),
							  getAlpha());
	}
	/** Computes the hash code for this <code>KnifeColor</code>
	 * @return	a hash code value for this object.
	 */
	public int hashcode()
	{
		return value;
	}
	/** Determines whether or not another object is equal to this <code>KnifeColor</code>
	 * @param	obj	the object to test for equality with this <code>KnifeColor</code>
	 * @return	<code>true</code> if the objects are the same; <code>false</code> otherwise
	 */
	public boolean equals(Object obj)
	{
		return obj instanceof KnifeColor && ((KnifeColor)obj).getRGB() == this.getRGB();
	}
	/** Returns a string representation of this <code>KnifeColor</code>. Intended for 
	 *  debugging purposes only.
	 *  @return	a string representation of this <code>KnifeColor</code>
	 */
	public String toString()
	{
		return getClass().getName() + "[r="+getRed()+",g="+getGreen()+",b="+getBlue()+",a="+getAlpha()+"]";
	}
	/** Converts a color in the HSB colormodel to a color in the RGB colormodel
	 * 
	 */
	public static int HSBtoRGB(float hue, float saturation, float brightness)
	{
		int r = 0, g = 0, b = 0;
		if(saturation == 0)
			r = g = b = (int)(brightness * 255.0f + 0.5f);
		else
		{
			float h = (hue - (float)Math.floor(hue)) * 6.0f;
			float f = h - (float)java.lang.Math.floor(h);
			float p = brightness * (1.0f - saturation);
			float q = brightness * (1.0f - saturation * f);
			float t = brightness * (1.0f - (saturation * (1.0f - f)));
			switch((int)h)
			{
			case 0:
				r = (int)(brightness * 255.0f + 0.5f);
				g = (int)(t * 255.0f + 0.5f);
				b = (int)(p * 255.0f + 0.5f);
				break;
			case 1:
				r = (int)(q * 255.0f + 0.5f);
				g = (int)(brightness * 255.0f + 0.5f);
				b = (int)(p * 255.0f + 0.5f);
				break;
			case 2:
				r = (int)(p * 255.0f + 0.5f);
				g = (int)(brightness * 255.0f + 0.5f);
				b = (int)(t * 255.0f + 0.5f);
				break;
			case 3:
				r = (int)(p * 255.0f + 0.5f);
				g = (int)(q * 255.0f + 0.5f);
				b = (int)(brightness * 255.0f + 0.5f);
				break;
			case 4:
				r = (int)(t * 255.0f + 0.5f);
				g = (int)(p * 255.0f + 0.5f);
				b = (int)(brightness * 255.0f + 0.5f);
				break;
			case 5:
				r = (int)(brightness * 255.0f + 0.5f);
				g = (int)(p * 255.0f + 0.5f);
				b = (int)(q * 255.0f + 0.5f);
				break;
			}
		}
		return 0xFF000000 | (r << 16) | (g << 8) | (b << 0);
	}
	/** Converts a RGB color to a HSB color */
	public static float[] RGBtoHSB(int r, int g, int b, float[] hsbvals)
	{
		float hue, saturation, brightness;
		if (hsbvals == null)
		{
			hsbvals = new float[3];
		}
		int cmax = (r > g) ? r : g;
		if(b > cmax) cmax = b;
		int cmin = (r < g) ? r : g;
		if(b < cmin) cmin = b;
		
		brightness = ((float) cmax) / 255.0f;
		if(cmax != 0)
			saturation = ((float) (cmax - cmin)) / ((float) cmax);
		else
			saturation = 0;
		if(saturation == 0)
			hue = 0;
		else
		{
			float redc = ((float) (cmax - r)) / ((float) (cmax - cmin));
			float greenc = ((float) (cmax - g)) / ((float) (cmax - cmin));
			float bluec = ((float) (cmax - b)) / ((float) (cmax - cmin));
			if(r == cmax)
				hue = bluec - greenc;
			else if (g == cmax)
				hue = 2.0f + redc - bluec;
			else
				hue = 4.0f + greenc - redc;
			hue = hue / 6.0f;
			if(hue < 0)
				hue = hue + 1.0f;
		}
		hsbvals[0] = hue;
		hsbvals[1] = saturation;
		hsbvals[2] = brightness;
		return hsbvals;
	}
	/** Returns the current ColorSpace of this KnifeColor */
	public java.awt.color.ColorSpace getColorSpace()
	{
		if (cs == null)
			cs = java.awt.color.ColorSpace.getInstance(java.awt.color.ColorSpace.CS_sRGB);
		return cs;
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
