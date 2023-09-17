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

/*

 Vous êtes libre de :

 */
package one.empty3.library;

public class Perspective {

    public static final int P_CUBIQUE_ISOMETRIQUE = 0;
    public static final int P_CUBIQUE_LINEAIRE = 1;
    public static final int P_CUBIQUE_FONCTION = 2;
    private int type = P_CUBIQUE_ISOMETRIQUE;

    private Perspective(int t) {
        this.type = t;
    }

    public static final Perspective getInstance(int type) {
        return new Perspective(type);
    }

    java.awt.Point coordonneeEcran(Point3D p) {
        if (type == P_CUBIQUE_ISOMETRIQUE) {
            return new java.awt.Point((int) (double) p.getX(), (int) (double) p.getY());
        } else if (type == P_CUBIQUE_LINEAIRE) {
            if (p.getZ() == 0) {
                return new java.awt.Point(0, 0);
            } else {
                return new java.awt.Point((int) (p.getX() / p.getZ()),
                        (int) (p.getY() / p.getZ()));
            }
        } else if (type == P_CUBIQUE_FONCTION) {
            return new java.awt.Point(
                    (int) (p.getX() / f(p.getZ())),
                    (int) (p.getY() / f(p.getZ())));
        }
        return null;
    }

    protected double f(double z) {
        return z;
    }
}
