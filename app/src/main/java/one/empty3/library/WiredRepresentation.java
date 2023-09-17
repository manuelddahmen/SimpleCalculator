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

/*__
 * @author Se7en
 */
public class WiredRepresentation extends RepresentableConteneur {

    private Point3D[][] pts;

    public WiredRepresentation(Point3D[][] pts) {
        this.pts = pts;
    }

    public RepresentableConteneur getRP() {
        RepresentableConteneur rp = new RepresentableConteneur();

        for (int i = 0; i < pts.length; i++) {
            for (int j = 0; j < pts[0].length; j++) {
                if (j + 1 < pts[0].length) {
                    this.add(
                            new LineSegment(pts[i][j], pts[i][j + 1], pts[i][j].texture()));
                }

                if (i + 1 < pts.length) {
                    this.add(
                            new LineSegment(pts[i][j], pts[i + 1][j], pts[i][j].texture()));
                }

            }
        }
        return rp;
    }

}
