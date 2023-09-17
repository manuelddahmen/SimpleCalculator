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

package one.empty3.library.core.nurbs;

import one.empty3.library.*;

/*__
 * Created by manue on 02-02-19.
 */
public class PcOnPs extends ParametricCurve {
    ParametricSurface ps;
    ParametricCurve pc;

    public PcOnPs(ParametricSurface ps, ParametricCurve pc) {
        this.ps = ps;
        this.pc = pc;
        this.texture(pc.texture());
    }


    @Override
    public Point3D calculerPoint3D(double t) {
        Point3D p1 = pc.calculerPoint3D(t);
        return ps.calculerPoint3D(p1.get(0), p1.get(1));
    }

    @Override
    public Point3D calculerVitesse3D(double t) {
        return null;
    }

}
