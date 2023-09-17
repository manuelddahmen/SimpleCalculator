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

public class TRIGeneratorUtil {

    public static TRIObject P32DTriQuad(Point3D[] points, int dimx, int dimy) {
        TRIObject tri = new TRIObject();

        for (int i = 0; i < dimx - 1; i++) {
            for (int j = 0; j < dimy - 1; j++) {

                TRI t1 = new TRI(points[dimx * j + i], points[dimx * (j + 1)
                        + i], points[dimx * (j + 1) + (i + 1)], points[dimx * j
                        + i].texture());
                tri.add(t1);

                TRI t2 = new TRI(points[dimx * j + i], points[dimx * j
                        + (i + 1)], points[dimx * (j + 1) + (i + 1)],
                        points[dimx * j + i].texture());
                tri.add(t2);

            }
        }

        return tri;

    }

    public static TRIObject P32DTriQuad(Point3D[][] controle, int dimx, int dimy) {
        Point3D[] bis = new Point3D[dimx * dimy];
        for (int i = 0; i < dimx; i++) {
            for (int j = 0; j < dimy; j++) {
                bis[j * dimx + i] = controle[i][j];
            }
        }
        return P32DTriQuad(bis, dimx, dimy);
    }

}
