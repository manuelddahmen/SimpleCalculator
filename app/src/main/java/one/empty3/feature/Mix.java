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

package one.empty3.feature;

import java.io.File;

import javaAnd.awt.image.BufferedImage;
import javaAnd.awt.image.imageio.ImageIO;
import one.empty3.io.ProcessNFiles;

public class Mix extends ProcessNFiles {
    public static final int MAX_PROGRESS = 256;
    private int progress = MAX_PROGRESS;

    public void setProgressColor(int progress) {
        this.progress = progress;
    }

    public int getProgressColor() {
        return progress;
    }

    @Override
    public boolean processFiles(File out, File... ins) {
        super.processFiles(out, ins);

        double ratio = 1.0 * progress / MAX_PROGRESS;

        if (ins.length > 1 && ins[0] != null && isImage(ins[0]) && ins[1] != null && isImage(ins[1])) {
            BufferedImage read1 = ImageIO.read(ins[0]);
            BufferedImage read2 = ImageIO.read(ins[1]);
            PixM pixMin1 = new PixM(read1);
            PixM pixMin2 = new PixM(read2);
            PixM outPixM = new PixM(pixMin1.getColumns(), pixMin1.getLines());

            for (int i = 0; i < outPixM.getColumns(); i++) {
                for (int j = 0; j < outPixM.getLines(); j++) {
                    for (int c = 0; c < 3; c++) {
                        pixMin1.setCompNo(c);
                        pixMin2.setCompNo(c);
                        outPixM.setCompNo(c);

                        outPixM.set(i, j, pixMin1.get(i, j) * (1 - ratio) + pixMin2.get(i, j) *(ratio) );
                    }
                }
            }
            return ImageIO.write(outPixM.getBitmap(), "jpg", out);
        }
        return false;
    }
}
