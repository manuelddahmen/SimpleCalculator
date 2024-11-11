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

package one.empty3.featureAndroid.tryocr;

import one.empty3.ImageIO;
import one.empty3.matrix.PixM;
import one.empty3.io.ProcessFile;
import one.empty3.library.Lumiere;
import one.empty3.libs.Color;

import java.io.File;

public class SelectColor extends ProcessFile {
    private Color color = new Color(Lumiere.getIntFromInts(255, 255, 255));

    @Override
    public boolean process(File in, File out) {
        PixM pixM = new PixM(ImageIO.read(in));

        PixM pixM2 = new PixM(pixM.getColumns(), pixM.getLines());

        float[] colorCompF = new float[4];

        colorCompF = Color.getColorComponents(color);

        int[] col = new int[4];

        for (int c = 0; c < 4; c++) {
            col[c] = (int) (colorCompF[c] * 255f);
        }

        for (int i = 0; i < pixM.getColumns(); i++)
            for (int j = 0; j < pixM.getColumns(); j++) {
                for (int c = 0; c < 4; c++) {
                    pixM2.set(i, j, pixM.get(i, j) == (col[c] / 255.) ? 1 : 0);
                }
            }
        ImageIO.write(pixM2.getImage(), "jpg", out, shouldOverwrite);
        return true;
    }
}
