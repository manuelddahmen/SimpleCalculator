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

package one.empty3.library.core.raytracer;

import one.empty3.library.*;

public class RtPointLight extends RtLight {
    Point3D lightVector;
    RtColor lightDiffuseColor;
    RtColor lightSpecularColor;
    RtColor lightColor;

    public RtPointLight(Point3D myLightPos, RtColor myLightDiffuseColor, RtColor myLightSpecularColor, RtColor myLightColor) {
        super(LIGHT, "LIGHT");
        mPosition = myLightPos;
        lightDiffuseColor = myLightDiffuseColor;
        mColor = myLightDiffuseColor;
        lightSpecularColor = myLightSpecularColor;
        lightColor = myLightColor;
    }

    @Override
    public RtColor getLightAt(Point3D normal, Point3D intersectionPoint, RtMatiere material) {
        double angle;
        RtColor finalColor;

        lightVector = intersectionPoint.moins(getPosition());

        Point3D lightVector2 = lightVector.norme1();

        angle = normal.prodScalaire(lightVector2.mult(1 / Math.sqrt(lightVector.norme())));

        //if (angle <= 0)
        //  finalColor = new RtColor(0.0f, 0.0f, 0.0f);

        //else
        finalColor = RtColor.mult(getColor(), (RtColor.mult(material.GetDiffuse(), Math.abs(angle))));

        return RtColor.mult(finalColor, (float) mIntensite);
    }

    @Override
    public boolean intersectsNode(RtRay ray, RtIntersectInfo intersectInfo) {
        return false;
    }
}