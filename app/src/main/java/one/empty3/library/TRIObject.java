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

import java.util.Iterator;
import java.util.List;

public class TRIObject extends Representable {

    private StructureMatrix<TRI> triangles = new StructureMatrix<>(1, TRI.class);
    private Barycentre position;

    public TRIObject() {

    }

    /*__
     * @param t
     */
    public TRIObject(TRI t) {
        triangles.add(1, t);
    }

    public boolean add(TRI arg0) {
        triangles.add(1, arg0);
        return true;
    }

    public void clear() {
        triangles.getData1d().clear();
    }

    public List<TRI> getTriangles() {
        return (List<TRI>) triangles.getData1d();
    }

    public Iterator<TRI> iterator() {
        return triangles.getData1d().iterator();
    }

    public Representable place(MODObjet aThis) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Barycentre position() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public Point3D rotation(Point3D p0, double a, double b) {
        Point3D p1 = new Point3D();
        p1.setX(p0.getX() * Math.cos(a) * Math.cos(b) + p0.getY() * Math.cos(a)
                * Math.sin(b) + p0.getZ() * Math.sin(a));
        p1.setY(p0.getX() * Math.cos(a) * Math.sin(b) + p0.getY() * Math.cos(a)
                * Math.cos(b) + p0.getZ() * Math.sin(a));
        p1.setZ(p0.getX() * Math.sin(a) * Math.cos(b) + p0.getY() * Math.sin(a)
                * Math.sin(b) + p0.getZ() * Math.cos((b)));

        if (position != null) {
            p1 = position.calculer(p1);
        }

        return p1;
    }

    public String toLongString() {
        String s = "tris(\n";
        Iterator<TRI> tris = iterator();
        while (tris.hasNext()) {
            s += "\n\ttri" + tris.next().toString() + "\n";
        }
        return s + ")\n";
    }

    @Override
    public String toString() {
        String s = "tris(\n";
        Iterator<TRI> tris = iterator();
        while (tris.hasNext()) {
            s += "\n\t" + tris.next().toString() + "\n";
        }
        return s + ")\n";
    }

}
