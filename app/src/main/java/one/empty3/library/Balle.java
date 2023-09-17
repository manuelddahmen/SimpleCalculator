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

package one.empty3.library;

import one.empty3.library.core.nurbs.CameraInPath;
import one.empty3.library.core.nurbs.Fct2D_1D;

/*__
 * Created by manue on 22-06-19.
 */

public class Balle extends HeightMapSphere

{
    private int formula;
    public static final int formula1 = 0;
    public static final int formula2 = 1;
    public static final int formula3 = 2;
    public static final int formulaCustom = 3;
    private int nbrPoints = 50;
    private double minX = 0, maxY = 1;
    public double d = 1;
    Point3D[] s = new Point3D[100];
    private double u = 1;
    private double D = 1;
    private Point3D[] v = new Point3D[100];
    private CameraInPath camera;
    private double radius = 20;
    private Fct2D_1D fctCustom = null;

    public Balle(Axe axe, double radius) {

        super(axe, radius, null);

        s = new Point3D[nbrPoints];
        v = new Point3D[nbrPoints];
        for (int i = 0; i < nbrPoints; i++) {
            s[i] = new Point3D(Math.random(), Math.random(), 0d);
            v[i] = Point3D.random(D / 10);
        }
    }

    public Fct2D_1D getFctCustom() {
        return fctCustom;
    }

    public void setFctCustom(Fct2D_1D fctCustom) {
        this.fctCustom = fctCustom;
    }

    @Override
    public Point3D height(double u, double v) {
        double h0 = 20;
        double h = 0;
        for (int i = 0; i < nbrPoints; i++) {
            Point3D p = s[i];
            double du = (p.getX() - u);
            double dv = (p.getY() - v);
            switch (formula) {
                case formula1:
                    h += h0 * du * dv;
                    break;
                case formula2:
                    h += h0 * (1 / (du * dv + 0.01));
                    break;
                case formula3:
                    h += h0 * (1 / (du * du + dv * dv + 0.001));
                    break;
                case formulaCustom:
                    if (fctCustom != null)
                        h += fctCustom.result(u, v);
            }
        }
        return surface.data0d.calculerPoint3D(u,v).mult(h);
    }


    public void bounce(int i) {
        s[i] = s[i].plus(v[i]);


        if (s[i].getX() > D && v[i].getX() > 0) {
            v[i].setX(-v[i].getX());
        }
        if (s[i].getX() < -D && v[i].getX() < 0) {
            v[i].setX(-v[i].getX());
        }
        if (s[i].getY() > D && v[i].getY() > 0) {
            v[i].setY(-v[i].getY());
        }
        if (s[i].getY() < -D && v[i].getY() < 0) {
            v[i].setY(-v[i].getY());
        }
        if (s[i].getZ() > D && v[i].getZ() > 0) {
            v[i].setZ(-v[i].getZ());
        }
        if (s[i].getZ() < -D && v[i].getZ() < 0) {
            v[i].setZ(-v[i].getZ());
        }
    }

    public void bounce() {
        for (int i = 0; i < s.length; i++) {
            bounce(i);
        }
    }

    public int getFormula() {
        return formula;
    }

    public void setFormula(int formula) {
        this.formula = formula;
    }
}
