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

package one.empty3.library.elements;

import one.empty3.library.ECBufferedImage;
import one.empty3.library.Scene;
import one.empty3.library.core.raytracer.RtScene;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class JPanelPPMPreview extends JPanel

{
    private Scene scene;
    private RtScene rtScene;
    private int typeScene;
    private StringBuilder ppmBuilder;
    private File recordFolder;
    private String filePrefix;
    private String timestampFormat;
    private String fileExtension;
    private String fileInfoPostfix;
    private Image previewImage;

    public JPanelPPMPreview(Scene scene) {

    }

    public void paint(Graphics graphics) {
        if (previewImage != null) {
            graphics.drawImage(previewImage, 0, 0, null, null);
        }
    }

    private void buildPreviewImage() {
        Graphics graphics = this.getGraphics();
        super.paint(graphics); // TODO CHECK

        PPMFileInputStream ppmFileInputStream = new PPMFileInputStream(ppmBuilder);

        // Read String
        // Complete with white, transparent or black

        ECBufferedImage ecBufferedImage = new ECBufferedImage(ppmFileInputStream);

    }
}
