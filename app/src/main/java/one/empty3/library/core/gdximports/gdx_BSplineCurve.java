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

package one.empty3.library.core.gdximports;

import com.badlogic.gdx.math.BSpline;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import one.empty3.library.Point3D;
import one.empty3.library.core.nurbs.ParametricCurve;

/*__
 * @author Manuel Dahmen _manuel.dahmen@gmx.com_
 */
public class gdx_BSplineCurve extends ParametricCurve {
    int degree;
    boolean continuous;
    private BSpline<Vector3> bspline;
    private Point3D[] controlPoints;

    public gdx_BSplineCurve() {
    }

    public void instantiate(Point3D[] controlPoints, int degree) {
        this.controlPoints = controlPoints;
        Vector3[] arr = new Vector3[controlPoints.length];
        Array<Vector3> knots = new Array<Vector3>();
        int i = 0;
        bspline = new BSpline<Vector3>();
        for (Point3D p : controlPoints) {
            Vector3 v = new Vector3();
            arr[i++] = Conv.conv(v, p);
            knots.add(v);

        }
        bspline.set(arr, degree, true);
        bspline.knots = knots;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public BSpline getBspline() {
        return bspline;
    }


    public Point3D calculerPoint3D(double t) {
        Point3D p = new Point3D();
        Vector3 out = new Vector3();
        return Conv.conv(new Point3D(), bspline.valueAt(out, (float) t));
    }

    @Override
    public Point3D calculerVitesse3D(double t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
