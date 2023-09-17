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

public class RtPlane extends RtObject {
    private Point3D mPointPlane;
    private Point3D mNormal;


    public RtPlane(Point3D pointplane, Point3D normal) {
        super();
        mNormal = normal;
        mNormal = mNormal.norme1();

        mPointPlane = pointplane;
    }

    public RtPlane(Point3D point1, Point3D point2, Point3D point3) {
    }

    // [RtNode inherited]
    public boolean intersectsNode(RtRay ray, RtIntersectInfo intersectInfo) {
        double t;
        double dv;

        dv = (double) mNormal.prodScalaire(ray.mVDir);

        if (dv == 0)
            return false;

        t = mNormal.prodScalaire(mPointPlane.moins(ray.mVStart)) / dv;

        if (t < 0)
            return false;

        if (intersectInfo != null) {
            intersectInfo.mIntersection = ray.mVStart.plus(ray.mVDir.mult(t));

            if (dv < 0)
                intersectInfo.mNormal = mNormal.mult(-1d);

            else
                intersectInfo.mNormal = mNormal.mult(-1d);

            intersectInfo.mNode = getNode();
            intersectInfo.mMaterial = getMaterial();
        }

        return true;
    }


}
