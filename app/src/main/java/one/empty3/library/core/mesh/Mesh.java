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
package one.empty3.library.core.mesh;

import one.empty3.library.*;
import one.empty3.library.core.nurbs.*;
import one.empty3.library.core.tribase.TRIEllipsoide;

/*__
 * @author Manuel Dahmen _manuel.dahmen@gmx.com_
 */
public class Mesh extends ParametricSurface {

    public static final int MESH_SHAPE_CUBE       = 0;
    public static final int MESH_SHAPE_ELLIPSOID  = 1;
    public static final int MESH_SHAPE_PLANE      = 2;
    public static final int MESH_SHAPE_BEZIER     = 4;
    StructureMatrix<Integer> meshType = new StructureMatrix(0, Integer.class);
    public static final int MESH_ADD_TYPE_SUBDIVID      = 8;
    public static final int MESH_ADD_TYPE_EXPAND        = 16;
    public static final int MESH_POINT_ADD_STYLE_HEIGHT      = 32;
    public static final int MESH_POINT_ADD_STYlE_SMOOTHED    = 64;
    public static final int MESH_POINT_ADD_STYlE_ANGULAR     = 128;
    private StructureMatrix<SurfaceParametriquePolynomialeBezier> baseBezier = new StructureMatrix<>(0, SurfaceParametriquePolynomialeBezier.class);
    private StructureMatrix<Sphere> baseSphere = new StructureMatrix<>(0, Sphere.class);
    private StructureMatrix<Cube> baseCube = new StructureMatrix<>(0, Cube.class);
    private StructureMatrix<TRIEllipsoide> baseEllipse = new StructureMatrix<>(0, TRIEllipsoide.class);

    private StructureMatrix<Point3D> pAdd = new StructureMatrix<>(1, Point3D.class);

    @Override
    public Point3D calculerPoint3D(double u, double v) {
        throw new UnsupportedOperationException("Calculer point du polygone");
    }

    public Mesh() {}

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("meshType/Mesh type", meshType);
        getDeclaredDataStructure().put("baseSphere/Base of type 'Sphere'", baseSphere);
        getDeclaredDataStructure().put("baseSphere/Base of type 'Cube'", baseCube);
        getDeclaredDataStructure().put("baseSphere/Base of type 'Ellipse'", baseEllipse);
        getDeclaredDataStructure().put("baseSphere/Base of type 'Bezier Surface'", baseBezier);
        getDeclaredDataStructure().put("add Points At Location", pAdd);
        // Suggérer des points intermédiares -> confirmer ou proposer autre constellation
    }
}
