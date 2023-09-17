/*
 * Copyright (c) 2023. Manuel Daniel Dahmen
 *
 *
 *    Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package one.empty3.library;

import one.empty3.library.elements.PPMFileInputStream;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ECBufferedImage extends BufferedImage {
    /*__
     *
     */
    private static final long serialVersionUID = 2739941470855574089L;
    private int pixelCountMax = 5;
    private int squarepixelCountMax = 25;

    public ECBufferedImage(BufferedImage read) {
        this(read.getWidth(), read.getHeight(), read.getType());
        setData(read.getData());
    }

    public ECBufferedImage(int width, int height, int imageType) {
        super(width, height, imageType);
    }

    public ECBufferedImage(PPMFileInputStream ppmFileInputStream) {

        super(0, 0, 0);

    }

    public static ECBufferedImage ppm(byte[] bytes, String ppm) {
        return null;
    }

    public static ECBufferedImage getFromFile(File url) throws IOException {
        return new ECBufferedImage(ImageIO.read(url));
    }

    public static ECBufferedImage getFromPackage(Class c, String resource) throws IOException {
        return new ECBufferedImage(ImageIO.read(c.getResourceAsStream(resource)));
    }

    public static ECBufferedImage getFromURL(URL url) {
        ECBufferedImage ecbi = null;
        try {
            Object o = url.getContent(new Class[]{BufferedImage.class});

            if (o instanceof BufferedImage) {
                BufferedImage bi = (BufferedImage) o;
                ecbi = new ECBufferedImage(bi);
                ecbi.setData(bi.getData());

            }
        } catch (IOException ex) {
            Logger.getLogger(ECBufferedImage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ecbi;
    }

    @Override
    public String toString() {
        String s = "P3\n";
        s += "# image in emptycanvas' mood file\n";
        s += "# \n";
        s += "" + getWidth() + " " + getHeight() + "\n";
        s += "255\n";
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                int r, g, b;
                java.awt.Color c;
                c = new java.awt.Color(getRGB(i, j));
                r = c.getRed();
                g = c.getGreen();
                b = c.getBlue();
                s += "" + r + " " + g + " " + b + " ";

                if (j * getWidth() + i % 3 == 0) {
                    s += "\n";
                }
            }
        }
        return s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ECBufferedImage that = (ECBufferedImage) o;

        int diffPixels = -pixelCountMax;

        for (int x = 0; x < getWidth(); x++)
            for (int y = 0; y < getHeight(); y++) {
                int thatRGB = that.getRGB(x, y);
                int thisRGB = this.getRGB(x, y);
                int[] thatRgba = new int[4];
                int[] thisRgba = new int[4];
                getRGBA(thatRGB, thatRgba);
                getRGBA(thisRGB, thisRgba);

                for (int i = 0; i < 4; i++) {
                    pixelCountMax += thatRgba[i] - thisRgba[i];
                }
            }

        if (pixelCountMax != that.pixelCountMax) return false;
        return squarepixelCountMax == that.squarepixelCountMax;

    }

    private void getRGBA(int rgba, int[] componentsRGBA) {
        int a = (rgba & 0xFF000000) >> 24;
        int r = (rgba & 0xFF000000) >> 16;
        int g = (rgba & 0xFF000000) >> 8;
        int b = (rgba & 0xFF000000) >> 0;

        componentsRGBA[0] = a;
        componentsRGBA[1] = r;
        componentsRGBA[2] = g;
        componentsRGBA[3] = b;


    }

    @Override
    public int hashCode() {
        int result = pixelCountMax;
        result = 31 * result + squarepixelCountMax;
        return result;
    }
}
