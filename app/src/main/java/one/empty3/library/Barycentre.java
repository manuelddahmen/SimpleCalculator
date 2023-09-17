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

public class Barycentre {

    public Point3D position = Point3D.O0;
    public Matrix33 rotation = Matrix33.I;
    public double agrandissement = 1.0;

    public Barycentre() {
    }

    public Point3D calculer(Point3D p0) {
        Point3D res = p0;

        if (p0 == null) {
            System.err.println("Erreur de positionnement p0==null");
        } else {
            if (agrandissement != 1d) {
                res = p0.moins(position).mult(agrandissement).plus(position);
            }
            if (rotation != Matrix33.I) {
                res = rotation.mult(res.moins(position)).plus(position);
            }
            if (position != null) {
                res = res.plus(position);
            }
            return res;
        }
        return res == null ? p0 : res;
    }

    public String id() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setId(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "position (\t\t@ " + position.toString()
                + "\t\t* " + rotation.toString()
                + "\t\t*" + agrandissement
                + " \t)\n";
    }
}
