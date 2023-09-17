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
 * Global license : * Microsoft Public Licence
 * <p>
 * author Manuel Dahmen _manuel.dahmen@gmx.com_
 * <p>
 * *
 */
package one.empty3.library.core.move;

import one.empty3.library.*;

import java.util.ArrayList;
import java.util.Collection;

/*__
 * @author Manuel Dahmen _manuel.dahmen@gmx.com_
 */
public abstract class SimpleTrajectory implements Trajectory {

    public long nanoTime;
    private ArrayList<Point3D> liste = new ArrayList<Point3D>();

    public boolean hasMorePoints() {
        return !liste.isEmpty();
    }

    public Point3D getNextPointAndRemove() {
        Point3D p = liste.get(0);
        liste.remove(p);
        return p;
    }

    public Point3D[] getIntermediatePointsUntilNext() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void addPoints(Collection<Point3D> points) {
        liste.addAll(points);
    }

    public void addPoints(Point3D[] points) {
        for (int i = 0; i < points.length; i++) {
            liste.add(points[i]);
        }
    }

    public abstract Point3D calculerPoint3D(double timeEllapsedNano);
}
