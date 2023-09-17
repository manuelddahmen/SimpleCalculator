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

package one.empty3.library.core.tribase;

import one.empty3.library.*;

public class CheminDroite extends Chemin {

    private LineSegment sd;

    public CheminDroite(LineSegment sd) {
        this.sd = sd;
    }

    @Override
    public double getLength() {
        return sd.getLength();
    }

    public Point3D calculerPoint3D(double t) {
        return sd.getOrigine().plus(
                sd.getExtremite().moins(sd.getOrigine()).mult(t));

    }

    @Override
    public Point3D tangent(int i) {
        return sd.getExtremite().moins(sd.getOrigine()).norme1();

    }

    @Override
    public Point3D calculerVitesse3D(double t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
