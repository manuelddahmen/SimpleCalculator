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

package one.empty3.library.core.aonb;

import one.empty3.library.Point3D;
import one.empty3.library.Representable;
import one.empty3.library.ZBuffer;
import one.empty3.library.core.nurbs.ParametricCurve;
import one.empty3.library.core.nurbs.ParametricSurface;

public class AonB extends ParametricSurface {
    /*__
     *
     */
    private Representable a = null;
    /*__
     *
     */
    private Representable b = null;

    /*__
     * For curve or surface a : [1-2][min-max] entry parameter
     * @param parameterARange
     */
    private double parameterARange[][];
    /*__
     * For curve or surface b : [1-2][min-max] (entry parameter, for bounds checking)
     * @param parameterBRange
     */
    private double parameterBRange[][];
    private AonB up;
    private AonB down;


    /*__
     *
     * @param a Parametric Curve or Surface
     * @param b Parametric Curve or Surface
     */
    public AonB(Representable a, Representable b)
    {
        this.a = a;
        this.b = b;
    }

    public Representable getA() {
        return a;
    }

    public void setA(Representable a) {
        this.a = a;
    }

    public Representable getB() {
        return b;
    }

    public void setB(Representable b) {
        this.b = b;
    }

    public double[][] getParameterARange() {
        return parameterARange;
    }

    public void setParameterARange(double[][] parameterARange) {
        this.parameterARange = parameterARange;
    }

    public double[][] getParameterBRange() {
        return parameterBRange;
    }

    public void setParameterBRange(double[][] parameterBRange) {
        this.parameterBRange = parameterBRange;
    }

    /*__
     *
     * @return
     */
    public boolean check() {
        if (a instanceof ParametricCurve && b instanceof ParametricCurve) {
            return true;
        }
        if (a instanceof ParametricCurve && b instanceof ParametricSurface) {
            return true;
        }
        if (a instanceof ParametricSurface && b instanceof ParametricSurface) {
            return true;
        }
        return false;
    }

    /*__
     *
     * @param z
     */
    public void draw(ZBuffer z) {

    }

    @Override
    public Point3D calculerPoint3D(double u, double v) {

        if (check()) {
            Point3D p = Point3D.O0;
            if (a instanceof ParametricCurve && b instanceof ParametricCurve) {
                p =  ((ParametricCurve)b).calculerPoint3D(
                        ((ParametricCurve)a).calculerPoint3D(u).getX()
                );
                p.texture(a.texture());
            }
            if (a instanceof ParametricCurve && b instanceof ParametricSurface) {
                p = ((ParametricSurface)b).calculerPoint3D(
                        ((ParametricCurve)a).calculerPoint3D(u).get2D().getX(),
                        ((ParametricCurve)a).calculerPoint3D(u).get2D().getY()
                );
                p.texture(a.texture());
            }
            if (a instanceof ParametricSurface && b instanceof ParametricSurface) {
                p = ((ParametricSurface)b).calculerPoint3D(
                        ((ParametricSurface)a).calculerPoint3D(u,v).get2D().getX(),
                        ((ParametricSurface)a).calculerPoint3D(u,v).get2D().getY()
                );
                p.texture(a.texture());//TODO
            }
            if(down!=null)
            {
                p = down.calculerPoint3D(
                        ((ParametricSurface)a).calculerPoint3D(u,v).get2D().getX(),
                        ((ParametricSurface)a).calculerPoint3D(u,v).get2D().getY()
                );
            }
            return p;
        }
        return null;

    }


    /*__
     * TODO
     * @param down
     */
    public void addDown(AonB down)
    {
        if((down.getA() instanceof ParametricSurface && b instanceof ParametricSurface)||
                down.getB() instanceof ParametricCurve && b instanceof ParametricCurve)
        {
            this.down = down;
        }

    }
}
