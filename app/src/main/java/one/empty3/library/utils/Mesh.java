/*
 * Copyright (c) 2022-2023. Manuel Daniel Dahmen
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

package one.empty3.library.utils;

import one.empty3.library.Point3D;
import one.empty3.library.Polygons;
import one.empty3.library.StructureMatrix;
import one.empty3.library.core.nurbs.ParametricSurface;

public class Mesh {
    public static StructureMatrix<Point3D> createMesh(Point3D center, Point3D [] directions, int su, int sv) {
        StructureMatrix<Point3D> point3DStructureMatrix = new StructureMatrix<>(2, Point3D.class);
        for(int i=0; i<su; i++) {
            for(int j=0; j<sv; j++) {
                point3DStructureMatrix.setElem(center.plus(directions[0].mult(i)
                        .plus(directions[1].mult(j))), i, j);
            }
        }
        return point3DStructureMatrix;
    }
    public static StructureMatrix<Point3D> createMesh(Point3D center, ParametricSurface ps, int su, int sv) {
        StructureMatrix<Point3D> point3DStructureMatrix = new StructureMatrix<>(2, Point3D.class);
        for(int i=0; i<su; i++) {
            for(int j=0; j<sv; j++) {
                point3DStructureMatrix.setElem(ps.calculerPoint3D(1.0*i/su, 1.0*j/sv), i, j);
            }
        }
        return point3DStructureMatrix;
    }
    //public static StructureMatrix<Point3D> createCubeMe

    //Cherche un modèle de mesh et éditeur et rendu.
    // extend, expand, contract, move, copy, remove,

}
