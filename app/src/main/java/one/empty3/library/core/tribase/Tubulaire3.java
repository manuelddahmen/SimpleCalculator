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
 * Global license : * GNU GPL v3
 * <p>
 * author Manuel Dahmen _manuel.dahmen@gmx.com_
 * <p>
 * Creation time 2015-03-25
 * <p>
 * *
 */
package one.empty3.library.core.tribase;

import one.empty3.library.Point3D;
import one.empty3.library.StructureMatrix;
import one.empty3.library.core.nurbs.CourbeParametriquePolynomialeBezier;
import one.empty3.library.core.nurbs.FctXY;
import one.empty3.library.core.nurbs.ParametricCurve;
import one.empty3.library.core.nurbs.ParametricSurface;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Tubulaire3 extends ParametricSurface {
    public double TAN_FCT_INCR = 0.000001;
    public double NORM_FCT_INCR = 0.000001;

    protected StructureMatrix<CourbeParametriquePolynomialeBezier> soulCurve = new StructureMatrix<>(0, CourbeParametriquePolynomialeBezier.class);
    protected StructureMatrix<FctXY> diameterFunction = new StructureMatrix<>(0, FctXY.class);
    protected Point3D lastNorm;
    protected Point3D lastTan = Point3D.Z;
    Point3D[][] vecteurs = new Point3D[3][3];

    {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                vecteurs[i][j] = new Point3D(0., 0., 0.);
                for (int k = 0; k < 3; k++)
                    vecteurs[i][j].set(j, k == i ? 1. : 0.);
            }
    }

    public Tubulaire3() {
        super();
        soulCurve.setElem(new CourbeParametriquePolynomialeBezier());
        diameterFunction.setElem(new FctXY());
        declareProperties();
        this.quad_not_computed = 0;//QUAD_NOT_COMPUTE_U2|QUAD_NOT_COMPUTE_V2;
    }

    public Tubulaire3(ParametricCurve lineSegment, double rayonMembres) {
        this();
        this.soulCurve.getElem().getCoefficients().setElem(lineSegment.calculerPoint3D(0.0), 0);
        this.soulCurve.getElem().getCoefficients().setElem(lineSegment.calculerPoint3D(1.0), 1);
        FctXY fctXY = new FctXY();
        fctXY.setFormulaX("" + rayonMembres);
        this.diameterFunction.setElem(fctXY);
        this.quad_not_computed = QUAD_NOT_COMPUTE_U2|QUAD_NOT_COMPUTE_V2;
    }

    public Point3D calculerNormale(double t) {
        return calculerTangente(t + NORM_FCT_INCR).moins(calculerTangente(t)).mult(1.0 / NORM_FCT_INCR);
    }

    public Point3D calculerTangente(double t) {
        return soulCurve.getElem().calculerPoint3D(t + TAN_FCT_INCR).moins(
                soulCurve.getElem().calculerPoint3D(t)).mult(1.0 / TAN_FCT_INCR);
    }

    public void nbrAnneaux(int n) {
        setIncrU(1.0 / n);
    }

    public void nbrRotations(int r) {
        setIncrV(1.0 / r);
    }

    @Override
    public String toString() {
        String s = "tubulaire3 (\n\t("
                + soulCurve.getElem().toString();
        s += "\n\n)\n\t" + diameterFunction.toString() + "\n\t" + texture().toString() + "\n)\n";
        return s;
    }

    private Point3D calculerTangenteUpart(double u, double v) {
        return soulCurve.data0d.calculerTangente(u);
    }

    private Object calculerTangenteVpart(double u, double v) {
        return calculerTangenteUpart(u, v).prodVect(calculerTangenteUpart(u + TAN_FCT_INCR, v)).norme1();//?????
    }

    public Point3D[] vectPerp(double t, double v) {
        int j = -1;
        double min = Double.POSITIVE_INFINITY;
        double minI = Double.POSITIVE_INFINITY; // TODO
        for (int i = 0; i < 3; i++) {
            Point3D tangente = calculerTangente(t);
            if (tangente.equals(Point3D.O0) || tangente.isAnyNaN()) {
                //TODO
                tangente = lastTan == null ? Point3D.X : lastTan;
            } else {
                lastTan = tangente;
            }


            Point3D[] refs = new Point3D[3];

            refs[0] = new Point3D(0d, 0d, 1d);
            refs[1] = new Point3D(1d, 0d, 0d);
            refs[2] = new Point3D(0d, 1d, 0d);

            tangente = tangente.norme1();

            Point3D px;
            Point3D normal;

            //normal = lastNorm;
            Point3D tangente1 = tangente;
            Point3D tangente2 = tangente.prodVect(refs[i]);
            normal = tangente1.prodVect(tangente2);
            if (normal != null) {
                if (Math.abs(normal.prodScalaire(tangente)) >= 0.00001) {
                    normal = calculerNormale(t);
                    if (normal.equals(Point3D.O0) || normal.isAnyNaN() || normal.norme() < 0.8) {
                        normal = tangente.prodVect(refs[i]);//TODO .prodVect(refs[i])).norme1();
                    }
                }
            } else {
                normal = lastNorm;
            }
            if (!normal.equals(Point3D.O0) && !normal.isAnyNaN() && !(normal.norme() < 0.8)) {
                lastNorm = normal;
            }
            normal = normal.norme1();
            px = tangente.prodVect(normal);//TODO .prodVect(refs[i])).norme1();

            Point3D py = tangente.prodVect(px).norme1();


            vecteurs[i][0] = tangente.norme1();
            vecteurs[i][1] = px.norme1();
            vecteurs[i][2] = py.norme1();

            minI = (px.prodVect(py).norme() - 1.0) * (px.prodVect(py).norme() - 1.0);

            if (minI < min) {
                min = minI;
                j = i;
            }
        }
        if (j == -1) {
            Logger.getAnonymousLogger().log(Level.INFO, "Error j==-1");
            j = 0;
        }

        return vecteurs[j];
    }

    @Override
    public Point3D calculerPoint3D(double v, double u) {
        if (level == 0 && quad_not_computed > 0) {
            super.calculerPoint3D(v, u);
        }
        Point3D[] vectPerp = vectPerp(u, v);
      /*  if(v==0) {
            vectPerp1 = vectPerp(u, v);
            vectPerp = vectPerp(u, v);
        } else {
            vectPerp1 = vectPerp(u, v);
            Matrix33 matrix33 = new Matrix33(vectPerp);
            double angle = Math.acos(vectPerp[0].prodScalaire(vectPerp1[0])/(vectPerp[0].norme()*vectPerp1[0].norme()));
            Matrix33 mult = matrix33.mult(new Matrix33(new Point3D[]{Point3D.X, Point3D.Y, vectPerp[0]}));

            vectPerp = mult.getColVectors();
        }
        */
        return soulCurve.getElem().calculerPoint3D(u).plus(
                vectPerp[1].mult(diameterFunction.getElem().result(u) * Math.cos(2 * Math.PI * v))).plus(
                vectPerp[2].mult(diameterFunction.getElem().result(u) * Math.sin(2 * Math.PI * v)));
    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        soulCurve.getElem().declareProperties();
        diameterFunction.getElem().declareProperties();
        getDeclaredDataStructure().put("soulCurve/ame de la courbe", soulCurve);
        getDeclaredDataStructure().put("diameterFunction/ fonction de la longueur du diamètre", diameterFunction);

    }

    public StructureMatrix<CourbeParametriquePolynomialeBezier> getSoulCurve() {
        return soulCurve;
    }


    public StructureMatrix<FctXY> getDiameterFunction() {
        return diameterFunction;
    }


}
