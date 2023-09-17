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

public class Tourbillon extends Representable implements
        TRIConteneur {

    private String id;
    private double diametre;
    private double hauteur;
    // private Axe axe;
    private PObjet obj;
    private TRIObject tri;
    private double tours;

    public Tourbillon() {
        this.diametre = 1.0;
        this.hauteur = 1.0;
        // this.axe = new Axe(new Point3D(0, 0, 0), new Point3D(0, 1, 0));
        this.obj = new PObjet();
        this.tours = 4.0;
        Color[] colors = new Color[]{Color.red, Color.green, Color.blue,
                Color.orange, Color.cyan, Color.darkGray, Color.black,
                Color.gray, Color.lightGray, Color.magenta, Color.pink,
                Color.yellow};

        double angle = 0.0;

        int dimx = 100;
        int dimy = colors.length;

        Point3D[] points = new Point3D[dimx * dimy];

        for (int j = 0; j < dimy; j++) {

            Color c = colors[j];

            angle += 2.0 * Math.PI / (dimy - 1);

            for (int i = 0; i < dimx; i++) {

                double h = hauteur * i / dimx;
                double d = h * h * diametre;
                Point3D p = new Point3D(-d
                        * Math.sin(2 * Math.PI * tours * h + angle), -h, d
                        * Math.cos(2 * Math.PI * tours * h + angle));
                p.texture(new TextureCol(c));

                obj.add(p);

                points[dimx * j + i] = p;

            }
        }
        tri = TRIGeneratorUtil.P32DTriQuad(points, dimx, dimy);
    }

    @Override
    public Representable getObj() {
        return tri;
    }

    /*
     * public Iterable<Point3D> iterable() { return obj.getPoints(); }
     */
    @Override
    public Iterable<TRI> iterable() {
        return tri.getTriangles();
    }

    public Representable place(MODObjet aThis) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String toString() {
        return "\ttourbillon(\n\t)\n";
        // return
        // "tourbillon(\n\n  diametre("+diametre+")\n\n  hauteur(\n\n"+hauteur+")\n\n  triobjet  (\n\n"+tri.toString()+"\n\n)\n\n\n)\n";
    }

}
