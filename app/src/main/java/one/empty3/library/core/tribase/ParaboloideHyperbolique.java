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

/*__
 * Global license :
 * <p>
 * Microsoft Public Licence
 * <p>
 * author Manuel Dahmen _manuel.dahmen@gmx.com_
 ***/


package one.empty3.library.core.tribase;

import one.empty3.library.Point3D;
import one.empty3.library.core.nurbs.ParametricSurface;

/*__
 * @author Manuel Dahmen _manuel.dahmen@gmx.com_
 */
public class ParaboloideHyperbolique extends ParametricSurface {
    private double a;
    private double b;
    private double h;

    {
        setStartU(-1.0);
        setStartV(-1.0);
        setEndU (1.0);
        setEndV (1.0);
    }

    public ParaboloideHyperbolique(double a, double b, double h) {
        this.a = a;
        this.b = b;
        this.h = h;
    }

    @Override
    public Point3D calculerPoint3D(double u, double v) {
        return new Point3D(a / 2 * (u + v), b / 2 * (u - v), h * u * v);
    }


}
