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

import java.io.Serializable;

/*__
 * cadre à la scène, avec possibilité d'élargir le cadre ou de ne pas en tenir
 * compte
 *
 * @author DAHMEN Manuel
 *         <p>
 *         dev
 * @date 24-mars-2012
 */
public class SceneCadre implements Serializable {

    /*__
     *
     */
    private static final long serialVersionUID = -2001787693050570304L;
    private Point3D[] points = new Point3D[4];
    private boolean cadre = false;
    private boolean exterieur = false;

    public SceneCadre() {
        cadre = false;
    }

    public SceneCadre(Point3D[] p) {
        this.points = p;
        cadre = true;
    }

    public Point3D get(int i) {
        return points[i];
    }

    public boolean isCadre() {
        return cadre;
    }

    public boolean isExterieur() {
        return exterieur;
    }

    public void setExterieur(boolean b) {
        this.exterieur = b;
    }

    public void set(int i, Point3D p) {
        this.points[i] = p;
    }

}
