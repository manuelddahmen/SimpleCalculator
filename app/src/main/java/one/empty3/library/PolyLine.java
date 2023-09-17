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

import one.empty3.library.core.nurbs.ParametricCurve;

import java.awt.*;
import java.util.List;

/*__
 * Created by manue on 20-06-19.
 */
public class PolyLine extends ParametricCurve {
    StructureMatrix<Point3D> points = new StructureMatrix<>(1, Point3D.class);

    public PolyLine() {
        super();
    }

    public PolyLine(Point3D[] point3DS, Color random) {
        for (Point3D point3D : point3DS) {
            add(point3D);
        }
        texture(new ColorTexture(random));
    }
    public PolyLine(Point3D[] point3DS, ITexture random) {
        for (Point3D point3D : point3DS) {
            points.add(point3D);
        }
        texture(random);
    }

    public List<Point3D> getPoints() {
        return points.getData1d();
    }

    public void setPoints(Point3D[] points) {

        this.points.setAll(points);
    }

    @Override
    public Point3D calculerPoint3D(double t) {
        int size = points.getData1d().size();
        int i = (int) t * size;
        if (i >= size)
            i = size - 1;
        int j = (i + 1) >= size? i : i + 1;
        Point3D p1 = points.getData1d().get(i);
        Point3D p2 = points.getData1d().get(j);
        double d = t - 1.0 * i / size;
        Point3D plus = p1.plus(p2.moins(p1).mult(1 - d));
        return plus;
    }

    public void add(Point3D point3D) {
        if (points == null)
            points = new StructureMatrix<>(1, Point3D.class);
        else {
            points.getData1d().add(point3D);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\r\nPolyLine ( \r\n");
        for (int i = 0; i < points.getData1d().size(); i++)
            sb.append("\t" + points.getData1d().get(i) + "\r\n");
        sb.append(texture);
        sb.append("\r\n)\r\n");
        return sb.toString();
    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("points/Points de la ligne brisée", points);
    }
}
