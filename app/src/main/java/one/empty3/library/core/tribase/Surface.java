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

import one.empty3.library.Point3D;
import one.empty3.library.core.nurbs.ParametricCurve;

public abstract class Surface extends ParametricCurve {

    protected int max = 100;

    /*__
     * Point d'index i sur Max
     *
     * @param i index
     * @return Point3D point du chemin discret C
     */
    public Point3D getPoint(int i) {
        Point3D p = calculerPoint3D(1.0 * i / getMax());
        return new Point3D(p.get(0), p.get(1), p.get(2));
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
