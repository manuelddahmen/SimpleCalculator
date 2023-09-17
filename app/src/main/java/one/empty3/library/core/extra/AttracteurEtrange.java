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

package one.empty3.library.core.extra;

import one.empty3.library.*;

/*__
 * @author manuel
 */
public final class AttracteurEtrange extends Representable implements POConteneur {

    public static int NOMBRE_POINTS = 1000000;
    private final double A;
    private final double B;
    private final double C;
    private final double D;
    private PObjet po;
    private String id;
    private Barycentre position;

    public AttracteurEtrange(double A, double B, double C, double D) {
        this.A = A;
        this.B = B;
        this.C = C;
        this.D = D;

        po = new PObjet();
        Point3D actuel = new Point3D(1d, 2d, 3d);
        Point3D precedent;
        int i = 0;
        while (i < NOMBRE_POINTS) {
            precedent = actuel;
            actuel = formule(precedent);
            po.add(actuel);
            i++;
        }
    }

    public Point3D formule(Point3D precedent) {

        Point3D ref = new Point3D(Math.sin(A * precedent.getY()) - precedent.getZ()
                * Math.cos(B * precedent.getX()), precedent.getZ()
                * (Math.sin(C * precedent.getX()) - Math.cos(D
                * precedent.getY())), Math.sin(precedent.getX()));
        return position == null ? ref : position.calculer(ref);
    }

    @Override
    public Iterable<Point3D> iterable() {
        return po.iterable();
    }


    @Override
    public String toString() {
        return "attracteurEtrange ( " + A + " " + B + " " + " " + C + " " + D
                + ")\n";
    }

}
