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

public class Position extends Representable {

    public Point3D position = Point3D.O0;
    public Matrix33 rotation = Matrix33.I;
    public double agrandissement = 1.0;

    public Position() {
    }

    public Point3D calculer(Point3D p0) {
        if (p0 == null) {
            System.err.println("Erreur de positionnement p0==null");
        }
        Point3D res = p0;
        if (position != null) {
            res = res.plus(position);
        }
        if (rotation != null) {
            res = rotation.mult(res);
        }
        if (agrandissement != 1d && position != null) {
            res = position.plus(res.moins(position).mult(agrandissement));
        }
        return res;
    }

    @Override
    public String toString() {
        return "position (\t\t@ " + position.toString()
                + "\t\t* " + rotation.toString()
                + "\t\t*" + agrandissement
                + " \t)\n";
    }
}
