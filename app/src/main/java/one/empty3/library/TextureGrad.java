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

import java.awt.image.BufferedImage;

public class TextureGrad extends TextureMov {
    private BufferedImage currentImg;
    private BufferedImage nextImg;
/*
    public TextureGrad(String s) {
        super(s);
    }*/

    public int getColorAt(double u, double v) {
/*
        currentImg = images.get(0);
        nextImg = images.get(1);

        //TODO
        int[] col1 = getRGB(currentImg, u, v);
        int[] colN = getRGB(nextImg, u, v);
        int[] col = new int[]{0, 0, 0, 0};
        col[0] = (int) ((col1[0] - colN[0]) / 2. + 256.);

        col[1] = (int) ((col1[1] - colN[1]) / 2. + 256.);

        col[2] = (int) ((col1[2] - colN[2]) / 2. + 256.);

        col[3] = (int) ((col1[3] - colN[3]) / 2. + 256.);

        return col[0] << 24 + col[1] << 16 + col[2] << 8 + col[2];*/
        return 0;
    }

    public int[] getRGB(BufferedImage image, double u, double v) {
        int x = (int) u * image.getWidth();
        int y = (int) v * image.getHeight();
        if (x >= 0 && x < image.getWidth() && y >= 0 && y < image.getHeight()) {
            int rgb = image.getRGB(x, y);
            int a = rgb >> 24 & 0xFF;
            int r = rgb >> 16 & 0xFF;
            int g = rgb >> 8 & 0xFF;
            int b = rgb >> 0 & 0xFF;
            return new int[]{a, r, g, b};

        } else
            return new int[]{0, 0xff, 0xff, 0xff};


    }
}
