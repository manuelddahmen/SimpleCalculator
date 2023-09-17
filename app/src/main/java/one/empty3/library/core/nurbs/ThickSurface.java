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
 * Created by manue on 13-02-19.
 */
public abstract class ThickSurface extends ParametricSurface {
    private boolean isThick;
    private double innerWidth = 0.0;
    private double outerWidth = 0.0;
    private boolean isInnerWidth;
    private boolean isOuterWidth;
    private double sign = 1;

    public Point3D computeExt(double u, double v) {
        return
                calculerPoint3D(u + getIncrU(), v)
                        .moins(calculerPoint3D(u-getIncrU(),v))
                        .prodVect(
                                calculerPoint3D(u, v+getIncrV()).
                        moins(calculerPoint3D(u, v-getIncrV())))
                                        .norme1().mult(outerWidth).
                        plus(calculerPoint3D(
                                u, v
                        ));

    }

    public Point3D computeInt(double u, double v) {
        return
                calculerPoint3D(u + getIncrU(), v)
                        .moins(calculerPoint3D(u-getIncrU(),v))
                        .prodVect(
                                calculerPoint3D(u, v+getIncrV()).
                                        moins(calculerPoint3D(u, v-getIncrV())))
                        .norme1().mult(-innerWidth).
                        plus(calculerPoint3D(
                                u, v
                        ));
    }

    public void setOuterWidth(double outerWidth) {
        this.outerWidth = outerWidth;
    }

    public void setInnerWidth(double innerWidth) {
        this.innerWidth = innerWidth;
    }
}
