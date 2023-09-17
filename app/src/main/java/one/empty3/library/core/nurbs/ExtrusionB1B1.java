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

package one.empty3.library.core.nurbs;

import one.empty3.library.Point3D;
import one.empty3.library.StructureMatrix;

/*__
 * Created by manue on 29-12-19.
 */
public class ExtrusionB1B1 extends ExtrusionCurveCurve {
    {
        base = new StructureMatrix<>(0, CourbeParametriquePolynomialeBezier.class);
        path = new StructureMatrix<>(0, CourbeParametriquePolynomialeBezier.class);
    }
    private StructureMatrix<Point3D> path0ref = new StructureMatrix<>();

    public ExtrusionB1B1() {
        base.setElem(new CourbeParametriquePolynomialeBezier());
        path.setElem(new CourbeParametriquePolynomialeBezier());
    }


    @Override
    public Point3D calculerPoint3D(double u, double v) {
        return super.calculerPoint3D(u, v);
    }
    public void declareProperties()
    {
        super.declareProperties();
    }

    @Override
    public String toString() {
        String s = "extrudeB1b1 (\n\t(\n\tbase :"
                + base.toString();
        s += "\n\n" + base.toString() + "\n\tpath : " + path+"\n)\ntexture : "+ texture().toString() + "\n)\n";
        return s;
    }
}
