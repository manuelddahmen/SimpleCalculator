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
import java.awt.*;
import java.util.Random;

/*__
 * @author Manuel
 */
public class CollineModele1 extends Representable implements TRIGenerable {

    Random r = new Random();
    private TRIObject tris = new TRIObject();
    private String id;
    private double deltaInterne = 100;

    public CollineModele1(int numTRIS) {

        Point3D p0 = new Point3D(0d, 0d, 0d);
        Color c0 = new Color(128, 0, 255);

        for (int i = 0; i < numTRIS; i++) {
            Point3D[] p = new Point3D[3];

            p[0] = p0.plus(new Point3D(aleatoireSigne(deltaInterne),
                    aleatoireSigne(deltaInterne), aleatoireSigne(deltaInterne)));
            p[1] = p[0].plus(new Point3D(aleatoireSigne(deltaInterne),
                    aleatoireSigne(deltaInterne), aleatoireSigne(deltaInterne)));
            p[2] = p[1].plus(new Point3D(aleatoireSigne(deltaInterne),
                    aleatoireSigne(deltaInterne), aleatoireSigne(deltaInterne)));

            p0 = p[2];

            TRI t = new TRI(p[0], p[1], p[2], c0);

            tris.add(t);
        }
    }

    private double aleatoireSigne(double maxAmpl) {

        return (r.nextDouble() - 0.5) * maxAmpl;

    }

    @Override
    public TRIObject generate() {
        return tris;
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

    public TextureCol texture() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "colline()\n";
    }

}
