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
package one.empty3.library.core.tribase;

import one.empty3.library.*;

import java.util.ArrayList;

public class PointWire extends Representable implements IFct1D3D {

    private String id;

    private ArrayList<BezierCubique> beziers;

    public PointWire(ArrayList<Point3D> base) {
        beziers = new ArrayList<BezierCubique>();

        base.add(0, base.get(0));
        base.add(base.get(base.size() - 1));

        for (int i = 0; i < base.size() - 1; i++) {

            BezierCubique bc = new BezierCubique();

            bc.add(base.get(i).plus(base.get(i + 1)).mult(0.5));
            bc.add(base.get(i + 1));
            bc.add(base.get(i + 1));
            bc.add(base.get(i + 1).plus(base.get(i + 2)).mult(0.1));

            beziers.add(bc);

        }
    }

    @Override
    public int iteres() {
        return 1000;
    }

    public double maxValue() {
        return beziers.size();
    }

    public Point3D normale(double t) {
        return tangente(t + 0.00001).plus(tangente(t).mult(-1d));
    }

    public Representable place(MODObjet aThis) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Barycentre position() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public boolean supporteTexture() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Point3D tangente(double t) {
        return beziers
                .get((int) (0.0001 + t))
                .calculerPoint3D((0.0000001 + t) - (int) (0.0001 + t))
                .plus(beziers.get((int) t).calculerPoint3D(t - (int) t)
                        .mult(-1d));
    }

    public TextureCol texture() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Point3D value(double t) {
        return beziers.get((int) t).calculerPoint3D(t - (int) t);
    }

}
