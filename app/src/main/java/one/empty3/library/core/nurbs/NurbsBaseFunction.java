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
 * Created by manue on 11-03-19.
 */
public class NurbsBaseFunction extends ParametricCurve
{
    private final Point3D [] P;
    private final int degree;
    private final double knot[];
    private final int length;
    private final double[] weight;

    public NurbsBaseFunction(double [] t, int degree, Point3D [] controls,double [] weigth)
    {
        this.P = controls;
        this.weight = weigth;
        knot = t;
        this.degree = degree;
        this.length = t.length;
    }

    public Point3D calculerPoint3D(double t)
    {
        Point3D sum = Point3D.O0;
        double div = 0;
        for(int i=0; i<P.length-degree-1; i++)
        {
            sum = sum.plus(P[i].mult(weight[i]*Ndi(degree, i, t)));
            div += weight[i]*Ndi(degree, i, t);
        }

        return sum.mult(1/div);
    }

    @Override
    public Point3D calculerVitesse3D(double t) {
        return null;
    }


    public double Ndi(int d, int j, double t)
    {
        if(d==0)
        {
            if(t>= knot[j]&&t<knot[j+1])
            {
                return 1.0;
            }
                else
            {
                return 0.0;
            }

        }
        else
            return test0div0((t-knot[j])/(knot[j+d]-knot[j])*Ndi(d-1, j, t))
            +test0div0((knot[j+d-1]-t)/(knot[j+d+1]-knot[j-1])*Ndi(d-1, j+1, t));
    }

    private double test0div0(double v) {
        if(Double.isInfinite(v) || Double.isNaN(v))
            return 0;
        else
            return v ;
    }
    public String toString()
    {
        String rep = "NurbsCurve (\n\thomogeneousSpace\n\t(";
        for (int i = 0; i < knot.length; i++) {
            rep += "\n\t\t" + knot[i]+ " \n";
        }
        rep += "\n\tControlsPoints\n\t" + "\n\t(";
        for (int i = 0; i < P.length; i++) {
            rep += "\n\t\t" + P[i] + " \n";
        }
        rep += "\n\tControlsWeights\n\t" + "\n\t(";
        for (int i = 0; i < weight.length; i++) {
            rep += "\n\t\t" + weight[i] + " \n";
        }
        rep += "\n\t)\n\t" + texture().toString() + "\n\n)";
        return rep;
    }


}
