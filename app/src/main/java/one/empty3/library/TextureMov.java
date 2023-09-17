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

//import com.xuggle.mediatool.IMediaReader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;


public class TextureMov extends ITexture {
    BufferedImage image;
    VideoDecoder defs;
    private File file = null;
    private int transparent = Color.BLACK.getRGB();

    public TextureMov() {
    }

    public TextureMov(String filename) {
        init(filename);
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
        init(file.getAbsolutePath());
    }

    public void init(String filename) {
        this.file = new File(filename);
        defs = VideoDecoderFactory.createInstance(file, this);
        defs.start();
    }


    public int getColorAt(double u, double v) {
        Point2D coord = super.getCoord(u, v);
        u = coord.x;
        v = coord.y;
        if (image == null)
            image = defs.current();
                /*try {
                    throw new EOFVideoException();
                } catch (EOFVideoException e) {
                    e.printStackTrace();
                    System.err.println("Plus de frames");
                }
*/
        if(image==null)
            return transparent;

        int x = (int) (u * image.getWidth());
        int y = (int) (v * image.getHeight());
        if (x >= 0 && x < image.getWidth() && y >= 0 && y < image.getHeight()) {
            int rgb = image.getRGB(x, y);
            int a = rgb >> 24 & 0xFF;
            int r = rgb >> 16 & 0xFF;
            int g = rgb >> 8 & 0xFF;
            int b = rgb >> 0 & 0xFF;
            return rgb & 0x00FFFFFF;

        } else
            return Color.TRANSLUCENT;
    }

    protected void current() {

        if (defs.size() > 0)
            image = defs.current();
    }


    @Override
    public void timeNext() {
        nextFrame();
    }

    @Override
    public void timeNext(long milli) {
        nextFrame();
    }


    public String toString() {
        return " texture ( \"" + file.getAbsolutePath() + "\")";
    }

    public MatrixPropertiesObject copy() throws CopyRepresentableError, IllegalAccessException, InstantiationException {
        return null;
    }


    public boolean nextFrame() {
        image = defs.current();
        return image != null;
    }

    public void setTransparent(Color black) {
        this.transparent = black.getRGB();
    }

    public BufferedImage getImage() {
        return image;
    }
}
