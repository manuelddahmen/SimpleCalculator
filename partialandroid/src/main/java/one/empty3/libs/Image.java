package one.empty3.libs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;

import org.jetbrains.annotations.NotNull;
import one.empty3.libs.commons.IImageMp;

import java.io.*;

public class Image extends BitmapDrawable implements IImageMp {


    public Image(@NotNull Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (bitmap != null) {
                bitmap = bitmap.copy(Bitmap.Config.RGB_565, true);
                setBitmap(bitmap);
            }
        }
    }

    public Image(int width, int height) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            setBitmap(Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565));
        }
    }

    public Image(int width, int height, int nothing) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            setBitmap(Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565));
        }
    }

    public static Image getFromInputStream(InputStream stream) {
        Bitmap bitmap = BitmapFactory.decodeStream(stream);
        bitmap = bitmap.copy(Bitmap.Config.RGB_565, true);
        return bitmap != null ? new Image(bitmap) : null;
    }

    @Override
    public boolean toOutputStream(OutputStream stream) {
        return false;
    }

    public static Image getFromFile(File file) {
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        return bitmap != null ? new Image(bitmap) : null;
    }

    @Override
    public boolean saveToFile(String path) {
        return saveFile(new File(path));
    }

    public boolean saveFile(File path) {
        return saveFile(path, Bitmap.CompressFormat.JPEG, 100); // Default JPEG, quality 100
    }

    public boolean saveFile(File path, Bitmap.CompressFormat format, int quality) {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            getBitmap().compress(format, quality, fos);
            return true;
        } catch (IOException e) {
            throw new RuntimeException("Error saving image: " + e.getMessage(), e);
        }
    }

    @Override
    public int getWidth() {
        return getBitmap().getWidth(); // Or handle null Bitmap
    }

    @Override
    public int getHeight() {
        return getBitmap().getHeight(); // Or handle null Bitmap
    }

    public int getRgb(int x, int y) {
        return getBitmap().getPixel(x, y); // Or handle out-of-bounds
    }

    @Override
    public void setImageToMatrix(int[][] imagetoMatrix) {

    }

    @Override
    public int[][] getMatrix() {
        return new int[0][];
    }

    public void setRgb(int x, int y, int rgb) {
        if (x >= 0 && y >= 0 && x < getWidth() && y < getHeight()) {
            getBitmap().setPixel(x, y, rgb & 0x00FFFFFF); // Or handle out-of-bounds
        }
    }

    // Optional: Add Color-based methods
    public Color getColor(int x, int y) {
        return new Color(getBitmap().getPixel(x, y)); // Or handle out-of-bounds
    }

    public void setColor(int x, int y, Color color) {
        setRgb(x, y, color.getRgb());
    }

    @NotNull
    public final Bitmap getImage() {
        return getBitmap();
    }
}