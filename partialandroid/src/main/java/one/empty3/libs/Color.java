package one.empty3.libs;

import one.empty3.libs.commons.IColorMp;

public class Color extends android.graphics.Color implements IColorMp {
    private android.graphics.Color colorObject;

    public Color(int rgb) {
        this.colorObject = android.graphics.Color.valueOf(rgb);
    }

    public Color(android.graphics.Color color) {
        this.colorObject = color;
    }

    // Add a default constructor if needed:
    public Color() {
        this.colorObject = android.graphics.Color.valueOf(android.graphics.Color.BLACK); // or another default
    }

    public static Color newCol(double r, double g, double b) {
        return new Color((((int) (r * 255) & 0xff) << 16) | (((int) (g * 255) & 0xff) << 8) | (((int) (b * 255) & 0xff)));
    }

    public static Color newCol(float r, float g, float b) {
        return new Color((((int) (r * 255) & 0xff) << 16) | (((int) (g * 255) & 0xff) << 8) | (((int) (b * 255) & 0xff)));
    }

    public static Color newCol(float r, float g, float b, float a) {
        return new Color(android.graphics.Color.argb((int) (a * 255), (int) (r * 255), (int) (g * 255), (int) (b * 255)));
    }

    public static float[] getColorComponents(Color color) {
        return new float[]{color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 1f}; // Or handle alpha
    }

    public float[] getColorComponents() {
        return getColorComponents(this);
        }

    @Override
    public IColorMp getColorObject() {
        return new Color(android.graphics.Color.valueOf(colorObject.toArgb()));  // Creates a copy
    }


    public static float[] getColorComponents(android.graphics.Color color) {
        return new float[]{color.red(), color.green(), color.blue(), 1f};
    }


    public int getColor() {
        return ((android.graphics.Color) colorObject).toArgb();
    }

    public void setColor(int i) {
        setRGB(i);
    }

    public void setColor(android.graphics.Color color) {
        this.colorObject = color;
    }

    public void setRGB(int rgb) {
        this.colorObject = android.graphics.Color.valueOf(rgb);
    }

    public void setRGB(int r, int g, int b) {
        this.colorObject = android.graphics.Color.valueOf(
                android.graphics.Color.rgb( r, g, b));
    }

    @Override
    public int getRgb() {
        if(colorObject == null) {
            int red = (int) (red() * 255f);
            int green = (int) (green() * 255);
            int blue = (int) (blue() * 255);
            int color = ((red << 16) | (green << 8) | blue);;
            return color|0xFF000000;
        }
        int red = (int) (colorObject.red()*255f);
        int green = (int) (colorObject.green()*255);
        int blue = (int) (colorObject.blue()*255);
        int color = ((red << 16) | (green << 8) | blue);;
        return color|0xFF000000;
    }

    public int getRGB() {
        return getRgb()|0xFF000000;
    }

    public void setRgb(int rgb) {
        if(colorObject!=null) {
            this.colorObject = android.graphics.Color.valueOf(rgb);
        }
    }
    public int getRed() {
        if(colorObject == null)
            return (int) (super.red()*255);
        return (int) (colorObject.red()*255);
    }

    public int getGreen() {

        if(colorObject == null)
            return (int) (super.green()*255);
        return (int) (colorObject.green() * 255f);
    }

    public int getBlue() {
        if(colorObject == null)
            return (int) (super.blue()*255);

        return (int) (colorObject.blue() * 255f);
    }

    public int getAlpha() {
        if(colorObject == null)
            return (int) (super.getComponents()[3]*255);

        return (int) (colorObject.alpha() * 255f);
    }

    public float red() {
        if(colorObject == null)
            return (int) (super.red());
        return (int) (colorObject.red());
    }

    public float green() {

        if(colorObject == null)
            return (int) (super.green());
        return (int) (colorObject.green());
    }

    public float blue() {
        if(colorObject == null)
            return (int) (super.blue());

        return (int) (colorObject.blue());
    }
}
