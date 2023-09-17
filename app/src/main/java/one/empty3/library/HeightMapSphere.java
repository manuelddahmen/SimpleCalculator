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

import atlasgen.TextureOpSphere;

import java.awt.image.BufferedImage;

/*__
 * Created by manue on 22-06-19.
 */
public class HeightMapSphere extends HeightMapSurface {
    private double axis;
    private ITexture heightMap;
    private double radius;

    public HeightMapSphere(Axe axe, double radius, BufferedImage height) {
        super(new Sphere(axe, radius), height);
    }


    @Override
    public Point3D calculerPoint3D(double u, double v) {
        throw new UnsupportedOperationException("calculer point de la sphere + BufferedImage");
    }
}

