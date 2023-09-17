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
import one.empty3.library.StructureMatrix;

/*__
 * @author Manuel Dahmen _manuel.dahmen@gmx.com_
 */
public class CourbeParametriquePolynomiale extends ParametricCurve {

    protected final StructureMatrix<Point3D> coefficients=  new StructureMatrix<>(1, Point3D.class);
    protected final StructureMatrix<Integer> power = new StructureMatrix<>(0, Integer.class);

    public CourbeParametriquePolynomiale(Point3D[] coefficients) {
        super();
        this.coefficients.setAll(coefficients);
        this.power.setElem(coefficients.length);
    }

    public CourbeParametriquePolynomiale() {
        super();
        power.setElem( 2);
    }
    public void declareProperties() {
        super.declareProperties();
        power.setElem(coefficients.getData1d().size());
        getDeclaredDataStructure().put(("coefficients/points de contrôle"), coefficients);
        getDeclaredDataStructure().put(("power/puissance du polynone"), power);
    }

    @Override
    public Point3D calculerPoint3D(double t) {
        Point3D sum = Point3D.O0;
        for (int i = 0; i < coefficients.getData1d().size(); i++) {
            sum = sum.plus(coefficients.getElem(i).mult(Math.pow(t, i)));
        }
        return sum;
    }

    @Override
    public Point3D calculerVitesse3D(double t) {
        Point3D sum = Point3D.O0;
        for (int i = 0; i < coefficients.getData1d().size() - 1; i++) {
            sum = sum.plus(coefficients.getElem(i).mult(Math.pow(t, i - 1) * i));
        }
        return sum;
    }

    public StructureMatrix<Point3D> getCoefficients() {
        return coefficients;
    }

    public void setCoefficients(StructureMatrix<Point3D> coefficients) {
        this.coefficients.setAll(coefficients.getData1d().toArray(new Point3D[0]));
    }

    public Integer getPower() {
        return power.getElem();
    }
    public void setPower(Integer pow) {
        power.setElem(pow);
    }
}
