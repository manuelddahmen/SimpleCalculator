/*
 * Copyright (c) 2024.
 *
 *
 *  Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

package one.empty3.feature20220726;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

import javaAnd.awt.Color;
import javaAnd.awt.image.BufferedImage;
import javaAnd.awt.image.imageio.ImageIO;
import one.empty3.io.ProcessFile;
import one.empty3.library.Lumiere;

public class Classification extends ProcessFile {
    Random random = new Random();
    private BufferedImage imageOut;
    private int SIZE = 5;
    private double ratio = 0.3;
    private double threshold = 0.3;


    @Override
    public boolean process(File in, final File out) {
        File tempFile = null;
        try {
            tempFile = File.createTempFile("effectClassification-", ".jpg");
            Lines7luckyLinesOutline lines7luckyLinesOutline = new Lines7luckyLinesOutline();
            lines7luckyLinesOutline.process(in, tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //!!! Border effect
        in = tempFile;
        if (in == null) {
            try {
                throw new FileNotFoundException("Temporary file in $tempFile");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (!in.getName().endsWith(".jpg"))
            return false;
        PixM selectPointColorMassAglo = null;
        BufferedImage read = null;
        read = new BufferedImage(Objects.requireNonNull(ImageIO.read(in)).bitmap);
        selectPointColorMassAglo = PixM.getPixM(read, maxRes);
        imageOut = ImageIO.read(in);
        SelectPointColorMassAglo selectPointColorMassAglo1 = new SelectPointColorMassAglo(read);
        int color = Color.WHITE;
        for (int i = 0; i < imageOut.getWidth(); i += 1)
            for (int j = 0; j < imageOut.getHeight(); j += 1) {
                selectPointColorMassAglo1.setTmpColor(Color.random());
                double v = selectPointColorMassAglo1.averageCountMeanOf(i, j, SIZE, SIZE, threshold);
                if (v > ratio) {
                    imageOut.setRGB(i, j, color);/*selectPointColorMassAglo.getChosenColor().getRGB()*/
                } else {
                    double[] doubles = Lumiere.getDoubles(read.getRGB(i, j));
                    /*for(int c=0; c<3; c++)
                        doubles[c] = doubles[c]/3;
*/
                    imageOut.setRGB(i, j, Lumiere.getInt(doubles));
                }
            }

        try {
            ImageIO.write(imageOut, "jpg", out, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }
}
