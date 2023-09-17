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
 * *
 * Global license : * Microsoft Public Licence
 * <p>
 * author Manuel Dahmen _manuel.dahmen@gmx.com_
 * <p>
 * Creation time 17-sept.-2014
 * <p>
 * *
 */
package one.empty3.library.core.nurbs;

import one.empty3.library.Point3D;
import one.empty3.library.StructureMatrix;

/*__
 * @author Manuel Dahmen _manuel.dahmen@gmx.com_
 */
public class SurfaceParametriquePolynomialeBezier extends SurfaceParametriquePolynomiale implements SurfaceElem{


    public double B(int i, int n, double t) {
        return factorielle(n) / factorielle(i) / factorielle(n - i)
                * Math.pow(t, i) * Math.pow(1 - t, n - i);
    }

    @Override
    public Point3D calculerPoint3D(double u, double v) {
        Point3D sum = Point3D.O0;
        for (int i = 0; i <getPower1(); i++) {
            for (int j = 0; j < getPower2(); j++) {
                sum = sum.plus(coefficients.getElem(i,j).mult(B(i, power1.getElem() - 1, u) * B(j, power2.getElem() - 1, v)));
            }
        }
        return sum;
    }


    protected double factorielle(int n) {
        double sum = 1;
        for (int i = 1; i <= n; i++) {
            sum *= i;
        }
        return sum;
    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("coefficients/points de contrôle", coefficients);
        getDeclaredDataStructure().put("power1/puissance par defaut #dim1", power1);
        getDeclaredDataStructure().put("power2/puissance par defaut #dim2", power2);

    }

    public Integer getPower1() {
        return coefficients.getData2d().size();
    }

    public void setPower1(Integer power1) {
        this.power1 = new StructureMatrix<>(power1, Integer.class);
    }

    public Integer getPower2() {
        return coefficients.getData2d().get(0).size();
    }

    public void setPower2(Integer power2) {
        this.power2 = new StructureMatrix<>(power2, Integer.class);
    }

    @Override
    public String toString() {
        String s = "bezier2(";

        s += "controls : "+coefficients.toString();
        s += "power1 : "+power1.toString();
        s += "power2 : "+power2.toString();

        s+=")";
        return s;
    }
}
