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

import java.awt.*;

public class Tetraedre extends Representable implements TRIGenerable {

    private String id;
    private Point3D[] points;
    private TRIObject obj;
    private Color color;

    public Tetraedre(Point3D[] points) {
        super();
        this.points = points;
        obj = new TRIObject();
    }

    /*__
     * @param ps
     * @param c
     */
    public Tetraedre(Point3D[] ps, Color c) {
        super();
        this.points = ps;
        this.color = c;
        obj = new TRIObject();
    }

    @Override
    public TRIObject generate() {
        int i, j, k;
        obj = new TRIObject();
        i = 0;
        j = 1;
        k = 2;
        obj.add(new TRI(points[i], points[j], points[k], color));
        i = 0;
        j = 1;
        k = 3;
        obj.add(new TRI(points[i], points[j], points[k], color));
        i = 0;
        j = 2;
        k = 3;
        obj.add(new TRI(points[i], points[j], points[k], color));
        i = 1;
        j = 2;
        k = 3;
        obj.add(new TRI(points[i], points[j], points[k], color));
        return obj;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public TRIObject getObj() {
        return obj;
    }

    public void setObj(TRIObject obj) {
        this.obj = obj;
    }

    public Point3D[] getPoints() {
        return points;
    }

    public void setPoints(Point3D[] points) {
        this.points = points;
    }

    public String toString() {
        return "tetraedre(\n\n" + points[0] + " " + points[1] + " " + points[2] + " " + points[3]
                + "\n\n)\n";
    }

}
