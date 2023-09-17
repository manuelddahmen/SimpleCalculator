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
package one.empty3.library.core.extra;

import one.empty3.library.*;

import java.awt.*;
import java.awt.image.BufferedImage;

/*__
 * @author MANUEL DAHMEN
 *         <p>
 *         dev
 *         <p>
 *         27 oct. 2011
 */
public class SimpleSphereAvecTexture extends SimpleSphere {

    private BufferedImage img;
    private Axe axe;
    private double angle;
    private String fichier;

    /*__
     * @param c
     * @param r
     * @param col
     */
    public SimpleSphereAvecTexture(Point3D c, double r, Color col) {
        super(c, r, col);
    }

    public SimpleSphereAvecTexture(Point3D c, double r, Color col,
                                   BufferedImage bufferedImage) {
        super(c, r, col);
        texture(bufferedImage);
    }

    public SimpleSphereAvecTexture(Point3D c, Matrix33 m3d, double angle, double r,
                                   Color col, ECBufferedImage img) {
        super(c, r, col);
        this.axe = axe;
        this.angle = angle;
        texture(img);
    }

    public void fichier(String f) {
        fichier = f;
    }

    @Override
    public TRIObject generate() {
        TRIObject t = new TRIObject();
        po = new PObjet();

        double a = -Math.PI / 2;
        double b;
        double incrLat;
        double incrLong;
        Point3D[] pCur = new Point3D[4];

        incrLat = 2 * Math.PI / numLatQuad;
        while (a < Math.PI / 2) {
            incrLong = 2 * Math.PI * Math.cos(a) / numLongQuad;
            b = 0;
            while (b < 2 * Math.PI && incrLong > 0.0001) {
                //Logger.getAnonymousLogger().log(Level.INFO, "a;b " + a +";"+b);
                pCur[0] = CoordPoint(a, b);
                pCur[1] = CoordPoint(a + incrLat, b);
                pCur[2] = CoordPoint(a, b + incrLong);
                pCur[3] = CoordPoint(a + incrLat, b + incrLong);
                try {
                    Color color = new Color(img.getRGB(
                            (int) ((a + Math.PI) / Math.PI * img.getHeight()),
                            (int) ((b) / 2 / Math.PI * img.getWidth())));
                    t.add(new TRI(pCur[0], pCur[1], pCur[3], color));
                    t.add(new TRI(pCur[0], pCur[2], pCur[3], color));
                } catch (Exception ex) {
                }
                b += incrLong;
            }
            a += incrLat;
        }
        return t;
    }

    public void texture(BufferedImage bufferedImage) {
        this.img = bufferedImage;
    }

    @Override
    public String toString() {
        return "\nsimpleSphereAvecTexture(\n\t" + centre.toString() + "\n\t" + radius + " \n\t\"" + fichier + "\"\n)\n";
    }
}
