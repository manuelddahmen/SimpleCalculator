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

import one.empty3.library.core.raytracer.RtIntersectInfo;
import one.empty3.library.core.raytracer.RtRay;

import java.awt.*;

public class TRI extends Representable {

    private StructureMatrix<Point3D> sommet= new StructureMatrix<>(1, Point3D.class);

    public TRI() {
        super();
        sommet.setElem(Point3D.X,  0);
        sommet.setElem(Point3D.Y,  1);
        sommet.setElem(Point3D.O0, 2);
    }

    public TRI(Point3D coordPoint3D, Point3D coordPoint3D0, Point3D coordPoint3D1) {
        this(coordPoint3D, coordPoint3D0, coordPoint3D1, Color.black);
    }

    public TRI(Point3D point3d, Point3D point3d2, Point3D point3d3,
               Color red) {
        sommet.setElem(point3d,  0);
        sommet.setElem(point3d2, 1);
        sommet.setElem(point3d3, 2);
        this.texture(new TextureCol(red));
    }

    public TRI(Point3D point3d, Point3D point3d2, Point3D point3d3,
               ITexture red) {
        sommet.setElem(point3d,  0);
        sommet.setElem(point3d2, 1);
        sommet.setElem(point3d3, 2);
        this.texture = red;
    }

    public TRI(Point3D[] s, Color c) {
        this(s[0], s[1], s[2], c);
    }

    public TRI(Point3D[] s, ITexture c) {
        this(s[0], s[1], s[2], c);
    }


    public StructureMatrix<Point3D> getSommet() {
        return sommet;
    }

    public void setSommet(Point3D[] sommet) {
        this.sommet.setAll(sommet);
    }

    public Point3D normale() {
        return sommet.getElem(2).moins(sommet.getElem(0)).prodVect(sommet.getElem(1).moins(sommet.getElem(0)))
                .norme1();
    }


    public void setCouleur(Color couleur) {
        this.texture(new TextureCol(couleur));

    }

    @Override
    public String toString() {
        String t = "tri (";
        for (int i = 0; i < 3; i++) {
            t += "\n\t\t(" + sommet.getElem(0).getX() + ", " + sommet.getElem(1).getY() + ", " + sommet.getElem(2).getZ() + "), ";
        }
        return t + "\n\t\t(" + texture.toString() + ")\n\t)\n";
    }

    @Override
    public Intersects.Intersection intersects(RtRay ray, RtIntersectInfo cii) {
        // TODO Implements
        return null;
    }

    public Point3D getCentre() {
        return getSommet().getElem(0).plus(getSommet().getElem(1)).plus(getSommet().getElem(2).mult(1 / 3d));
    }

    public int intersects(TRI tri2) {


        return TRI_Collide.tr_tri_intersect3D(getCentre(), getSommet().getElem(0), getSommet().getElem(1),
                tri2.getCentre(), tri2.getSommet().getElem(0), tri2.getSommet().getElem(1));
    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("sommet/points sommets du triangle",sommet);

    }
}
