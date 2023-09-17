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

import one.empty3.library.core.nurbs.ParametricSurface;

/*__
 * Created by Win on 28-08-18.
 */
public class Citron extends ParametricSurface {
    private Circle circle;

    public Citron(Axe axis, Point3D center, double radius) {

        circle = new Circle(axis, radius);
    }

    public Point3D calculerPoint3D(double u, double v) {
        Circle c = circle;
        return c.getCenter().plus(
                c.getVectX().mult(circle.getRadius() *
                        Math.cos(2.0 * Math.PI * u) * Math.cos(2.0 * Math.PI * v)).plus(
                        c.getVectY().mult(circle.getRadius() *
                                Math.sin(2.0 * Math.PI * u) * Math.cos(2.0 * Math.PI * v))
                                .
                                        plus(c.getVectZ().mult((v - 0.5) / 2 * Math.sin(2 * Math.PI * v)))
                ).mult(c.radius.getElem()));
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }
}
