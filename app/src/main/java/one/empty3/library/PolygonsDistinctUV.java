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

package one.empty3.library;

import java.awt.*;

public class PolygonsDistinctUV extends Polygons {
    private StructureMatrix<Point3D> uvMap;
    private ITexture texture2;

    public PolygonsDistinctUV() {
        coefficients = new StructureMatrix<>(2, Point3D.class);
        uvMap = new StructureMatrix<Point3D>(2, Point3D.class);
    }
    public PolygonsDistinctUV(StructureMatrix<Point3D> coefficients,
                              StructureMatrix<Point3D> uvMap) {
        this.coefficients = coefficients;
        this.uvMap = uvMap;
    }

    public Point3D vectorPointPercent(Point3D a, Point3D b, double pc) {
        return a.plus(b.moins(a).mult(a));
    }

    @Override
    public void texture(ITexture t) {
        texture = new ITexture() {
            @Override
            public MatrixPropertiesObject copy() throws CopyRepresentableError, IllegalAccessException, InstantiationException {
                return null;
            }

            @Override
            public int getColorAt(double u, double v) {
                try {

                    int indexU0 = (int) (u * uvMap.getData2d().size());
                    int indexV0 = (int) (v * uvMap.getData2d().get(0).size());
                    if (indexU0 > uvMap.getData2d().size() - 1) {
                        indexU0 = uvMap.getData2d().size() - 1;
                    }
                    if (indexV0 > uvMap.getData2d().get(0).size() - 1) {
                        indexV0 = uvMap.getData2d().get(0).size() - 1;
                    }
                    int indexU1 = (int) (indexU0 + 1.);
                    int indexV1 = (int) (indexV0 + 1.);
                    if (indexU1 > uvMap.getData2d().size() - 1) {
                        indexU1 = uvMap.getData2d().size() - 1;
                    }
                    if (indexV1 > uvMap.getData2d().get(0).size() - 1) {
                        indexV1 = uvMap.getData2d().get(0).size() - 1;
                    }
                    if(coefficients.inBounds(indexU0, indexV0) && coefficients.inBounds(indexU1, indexV0)
                            &&coefficients.inBounds(indexU1, indexV1) && coefficients.inBounds(indexU0, indexV1)) {
                        Point3D[] uvQuad = new Point3D[]{
                                uvMap.getElem(indexU0, indexV1), uvMap.getElem(indexU1, indexV1),
                                uvMap.getElem(indexU1, indexV0), uvMap.getElem(indexU0, indexV0)
                        };

                        double U = u * (uvMap.getData2d().size()) - indexU0;
                        double V = v * (uvMap.getData2d().get(0).size()) - indexV0;
                        assert U >= 0 && U <= 1 && V >= 0 && V <= 1;
                        Point3D pUv0 = uvQuad[0].plus(uvQuad[1].moins(uvQuad[0]).mult(U));
                        Point3D pUv1 = uvQuad[3].plus(uvQuad[2].moins(uvQuad[3]).mult(U));
                        Point3D pU0v = uvQuad[3].moins(uvQuad[0]).mult(V);
                        Point3D pU1v = uvQuad[2].moins(uvQuad[1]).mult(V);
                        Point3D plus = pUv1.plus(pUv0.moins(pUv1).mult(V));// Discutable
                        return texture2.getColorAt(plus.getX(), plus.getY());
                    }
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
                return Color.BLACK.getRGB();
            }
        };
    }

    public StructureMatrix<Point3D> getUvMap() {
        return uvMap;
    }

    public ITexture getTexture2() {
        return texture2;
    }

    public void setTexture2(ITexture texture2) {
        this.texture2 = texture2;
    }

    public void setUvMap(StructureMatrix<Point3D> uvMap) {
        this.uvMap = uvMap;
    }

    @Override
    public ITexture texture() {
        if(texture==null)
            texture(null);
        return texture;
    }
}
