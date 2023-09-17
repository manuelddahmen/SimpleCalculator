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

import one.empty3.library.Matrix33;
import one.empty3.library.Point3D;
import one.empty3.library.Rotation;
import one.empty3.library.StructureMatrix;
import one.empty3.library.core.nurbs.CourbeParametriquePolynomialeBezier;
import one.empty3.library.core.nurbs.FctXY;
import one.empty3.library.core.nurbs.ParametricCurve;
import one.empty3.library.core.nurbs.ParametricSurface;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Tubulaire3refined extends ParametricSurface implements Precomputable {
    public double TAN_FCT_INCR  = 0.0001;
    public double NORM_FCT_INCR = 0.0001;

    protected StructureMatrix<CourbeParametriquePolynomialeBezier> soulCurve = new StructureMatrix<>(0, CourbeParametriquePolynomialeBezier.class);
    protected StructureMatrix<FctXY> diameterFunction = new StructureMatrix<>(0, FctXY.class);
    protected Point3D lastNorm;
    protected Point3D lastTan = Point3D.Z;
    Point3D[][] vecteurs = new Point3D[3][3];
    private Point3D[][] pointUV = null;
    protected double[] incrUpre;

    {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                vecteurs[i][j] = new Point3D(0., 0., 0.);
                for (int k = 0; k < 3; k++)
                    vecteurs[i][j].set(j, k == i ? 1. : 0.);
            }
    }

    public Tubulaire3refined() {
        super();
        level = 0;
        this.quad_not_computed = QUAD_NOT_COMPUTE_U2|QUAD_NOT_COMPUTE_V2;
        soulCurve.setElem(new CourbeParametriquePolynomialeBezier());
        diameterFunction.setElem(new FctXY());
        declareProperties();
    }

    public Tubulaire3refined(ParametricCurve lineSegment, FctXY rayonMembres) {
        this();
        level = 0;
        this.quad_not_computed = QUAD_NOT_COMPUTE_U2|QUAD_NOT_COMPUTE_V2;
        this.soulCurve.getElem().getCoefficients().setElem(lineSegment.calculerPoint3D(0.0), 0);
        this.soulCurve.getElem().getCoefficients().setElem(lineSegment.calculerPoint3D(1.0), 1);
        this.diameterFunction.setElem(rayonMembres);

    }

    public void precompute() {
        int sizeU = (int) ((getEndU()-getStartU())/getIncrU());
        int sizeV = (int) ((getEndV()-getStartV())/getIncrV());
        pointUV = new Point3D[sizeU][sizeV];
        incrUpre = new double[sizeU];


        for(double u=getStartU(); u<getEndU(); u+=getIncrU()) {
            for(double v=getStartV(); v<getEndV(); v+=getIncrV()) {
                int i = (int) ((u - getStartU()) / getIncrU());
                int j = (int) ((v - getStartV()) / getIncrV());
                if(i<pointUV.length&&j<pointUV[i].length)
                    pointUV[i][j] = calculerPoint3D(u, v);
            }

        }


        for(double u=getStartU(); u<getEndU(); u+=getIncrU()) {
            int i = (int) ((u - getStartU()) / getIncrU());
            if(i<incrUpre.length&&i<pointUV.length-1) {
                int i1 = minDist(pointUV[i][0], pointUV[i + 1]);
                int indexV = i1 >= 0 ? (i1 < incrUpre.length ? i1 : incrUpre.length - 1) : 0;
                if (indexV < incrUpre.length)
                    incrUpre[i] = 1.0 * indexV / sizeV;
            }
        }
    }

    private int minDist(Point3D point3D, Point3D[] point3DS) {
        int indexChoice = -1;
        int index = 0;
        double valMin = Double.MAX_VALUE;
        while(index<point3DS.length) {
            double v0 = 0;
            if((v0 = Point3D.distance(point3DS[index], point3D))<valMin) {
                valMin = v0;
                indexChoice = index;
            }
            index++;
        }
        return indexChoice;
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

//            if(incrUpre==null)
//                precompute();
//
//            int indexU=0;
//            double sumA = 0.0;
//            for(double t1=0; t1<t; t1+=getIncrU()) {
//                indexU++;
//                if(indexU<incrUpre.length)
//                sumA += incrUpre[indexU];
//            }
/*
            Rotation rotation1 = new Rotation();
            rotation1.getCentreRot().setElem(soulCurve.getElem().calculerPoint3D(t));
            rotation1.getRot().setElem(new Matrix33(new double[] {
                    1, 0, 0,
                    0, Math.sin(sumA), Math.cos(sumA),
                    0, Math.cos(sumA), Math.sin(sumA)
            }));
            rotation1.rotation(vecteurs[i][1]);
            rotation1.rotation(vecteurs[i][1]);*/
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
        if(incrUpre==null)
            precompute();
        if (level == 0 && quad_not_computed > 0) {
            super.calculerPoint3D(v, u);
        }
        Point3D[] vectPerp = vectPerp(u, v);

        int indexU = 0;
        for(double t1=getStartU(); t1<v; t1+=getIncrU()) {
            if(indexU<incrUpre.length)
                ;//u += incrUpre[indexU];
            //indexU++;
        }

        //v = v - (int) v ;

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
