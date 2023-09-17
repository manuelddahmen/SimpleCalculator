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

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TourDeRevolution extends Representable {

    private String id;
    private CourbeDeImage courbe;
    private TRIObject o;
    private PObjet op;

    public TourDeRevolution(File image, Axe axe) {
        try {
            this.courbe = new CourbeDeImage(ImageIO.read(image));
            courbe.anayliserImage();

        } catch (Exception ex) {}

    }

    public static void main(String[] argss) {
        try {
            System.out.print(new File(".").getCanonicalPath());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        TourDeRevolution tr = new TourDeRevolution(new File("tour.png"), new Axe(Point3D.Y.mult(-1.), Point3D.Y.mult(1.)));
        tr.generateB();
        PObjet o = tr.getPO();
        ZBuffer z = new ZBufferImpl(500, 500);
        Scene s = new Scene();
        s.add(tr);
        z.scene(s);
        z.draw();
        try {
            ImageIO.write(z.image(), "png",
                    ImageIO.createImageOutputStream(new File("result2TR.png")));
        } catch (Exception ex) {}

    }

    // @Override
    public void generateB() {

        Color[] colors = new Color[256];
        for (int i = 0; i < 255; i++) {
            double a = 1.0 * i / 255 * 2 * Math.PI;
            colors[i] = new Color(0.0f/*1*((float)Math.sin(a)+1)/2*/, 1 * (float) (Math.sin(a) + 1) / 2, 1 * (float) (Math.cos(a) + 1) / 2);
        }
        o = new TRIObject();
        op = new PObjet();

        int max = 1000;
        @SuppressWarnings("unchecked")
        ArrayList<Point3D>[] points = new ArrayList[courbe.getPoints().size()];
        for (int i = 0; i < courbe.getPoints().size(); i++) {
            points[i] = new ArrayList<Point3D>();
        }
        Enumeration<Point2D> en = courbe.getPoints().keys();
        while (en.hasMoreElements()) {
            Point2D p = en.nextElement();
            double diamx = p.getX();
            double diamy = p.getY();

            Logger.getAnonymousLogger().log(Level.INFO, ""+courbe.getPoints().size());
            int i = 0;
            for (i = 0; i < max; i++) {
                double a = 2 * Math.PI * i / max;

                Point3D p2d = new Point3D(diamx * Math.cos(a), diamy, -diamx * Math.sin(a));

                p2d.texture(new TextureCol(colors[(int) ((Math.cos(a) + 1) / 2 * 255)]));

                op.add(p2d);

                //points[j].add(p2d);
            }

        }

         for (int i = 0; i < max; i++)
             for (int j = 0; j < points[0].size(); j++) {
         if (i > 0 && j > 0) {
         o.add(new TRI(points[j].get(i), points[j-1].get(i), points[j-1].get(i-1), Color.red));
         o.add(new TRI(points[j].get(i), points[j].get(i-1), points[j-1].get(i-1), Color.red));
         }
         }

    }

    public PObjet getPO() {
        return op;
    }

    public TRIObject getTRI() {
        return o;

    }


}
