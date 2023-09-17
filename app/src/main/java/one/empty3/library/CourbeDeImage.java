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

/*

 Vous êtes libre de :

 */
package one.empty3.library;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Hashtable;
import java.util.Set;

public class CourbeDeImage {

    private BufferedImage image;
    private Hashtable<Point2D, Color> points;

    public CourbeDeImage(BufferedImage image) {
        super();
        this.image = image;
        this.points = new Hashtable<Point2D, Color>();

        anayliserImage();
    }

    public void anayliserImage() {
        for (int i = 0; i < image.getWidth(); i++) {
            int y0 = -1;
            int y1 = -1;
            for (int j = 0; j < image.getHeight(); j++) {
                if (!new Color(image.getRGB(i, j)).equals(Color.white)) {
                    y0 = y1;
                    y1 = j;
                    if (y0 == -1 || (y1 > y0 + 1)) {
                        points.put(new Point2D(i, j), new Color(image.getRGB(i, j)));
                        break;
                    }
                }

            }
        }
    }

    public void classerPoints() {
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(ECBufferedImage image) {
        this.image = image;
    }

    public Hashtable<Point2D, Color> getPoints() {
        return points;
    }

    public void setPoints(Hashtable<Point2D, Color> points) {
        this.points = points;
    }

    public Set<Point2D> getPointsList() {
        return points.keySet();
    }
}
