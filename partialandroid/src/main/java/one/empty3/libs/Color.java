package one.empty3.libs;

import android.os.Build;

import androidx.annotation.RequiresApi;

import one.empty3.libs.commons.IColorMp;

public class Color extends android.graphics.Color implements IColorMp {
    private android.graphics.Color colorObject;


    public Color(android.graphics.Color color) {
        this.colorObject = color;
    }

    public Color(int rgb) {
        this.colorObject = android.graphics.Color.valueOf(rgb);
    }

    public static float[] getColorComponents(Color color) {
        return new float[]{color.red(), color.green(), color.blue(), color.alpha()};
    }

    @Override
    public IColorMp getColorObject() {
        return new Color( colorObject);
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
        this.colorObject = android.graphics.Color.valueOf(r, g, b);
    }

    public void setRgb(int rgb) {
        this.colorObject = android.graphics.Color.valueOf(rgb);
    }
    public int getRGB() {
        return colorObject.toArgb();
    }
    public int getRed() {
        return (int)(colorObject.red()/256);
    }
    public int getGreen() {
        return (int)(colorObject.green()/256);
    }
    public int getBlue() {
        return (int) (colorObject.blue() / 256);
    }
    public int getAlpha() {
        return (int) (colorObject.alpha() / 256);
    }
}
