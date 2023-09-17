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

import one.empty3.library.Point3D;

/*__
 * @author Manuel Dahmen _manuel.dahmen@gmx.com_
 */
public class CourbeParametriquePolynomialeBezier extends CourbeParametriquePolynomiale {

    public CourbeParametriquePolynomialeBezier()
    {
        super();
    }
    public CourbeParametriquePolynomialeBezier(Point3D[] coefficients) {
        super(coefficients);
    }

    public double B(int i, int n, double t) {
        return factorielle(n) / factorielle(i) / factorielle(n - i)
                * Math.pow(t, i) * Math.pow(1 - t, n - i);
    }


    @Override
    public Point3D calculerPoint3D(double t) {
        Point3D sum = new Point3D();
        int N = coefficients.getData1d().size();
        for (int i = 0; i < N; i++) {
            sum = sum.plus(coefficients.getElem(i).mult(B(i, N - 1, t)));
        }
        return sum;
    }

    @Override
    public Point3D calculerVitesse3D(double t) {
        return super.calculerVitesse3D(t);
    }

    protected double factorielle(int n) {
        double sum = 1;
        for (int i = 2; i <= n; i++) {
            sum *= i;
        }
        return sum;
    }

    public void declareProperties()
    {
        super.declareProperties();
    }

    @Override
    public String toString() {
        String s = "bezier(";
        for(int i=0; i<coefficients.getData1d().size(); i++)
            s+="\n"+coefficients.getElem(i).toString()+"\n";
        return s+")\n";
    }

}
