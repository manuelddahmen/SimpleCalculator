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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package one.empty3.library;

import one.empty3.library.core.nurbs.ParametricSurface;
import one.empty3.library.core.nurbs.SurfaceParametricPolygonalBezier;

/*__
 * @author Se7en
 */
public class WireRepresentation extends RepresentableConteneur {

    private final ParametricSurface surface;
    private Point3D[][] pts;

    public WireRepresentation(ParametricSurface parametricSurface) {

        this.surface = parametricSurface;
        getRP();
    }
    public WireRepresentation(Point3D [][] point3DS) {

        this.surface = new SurfaceParametricPolygonalBezier(point3DS);
        getRP();
    }

    public void getRP() {
        this.clear();
        for (double i = surface.getStartU(); i < surface.getEndU()
                ; i+=surface.getIncrU()) {
            for (double j = surface.getStartV();
                 j < surface.getEndV()
                    ; j+=surface.getIncrV()) {
                    this.add(
                            new LineSegment(
                                    surface.calculerPoint3D(i,j),
                                    surface.calculerPoint3D(i,j +
                                    surface.getIncrV()),
                                    surface.calculerPoint3D(i,j).texture()));
                    this.add(
                            new LineSegment(
                                    surface.calculerPoint3D(i,j),
                                    surface.calculerPoint3D(i+surface.getIncrU(),j + 1),
                                    surface.calculerPoint3D(i,j).texture()));

            }
        }
    }

}
