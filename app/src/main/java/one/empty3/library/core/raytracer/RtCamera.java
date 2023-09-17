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

public class RtCamera extends RtNode {
    protected double mViewplaneDist;                                                // Distance du viexplane par rapport � la position de la cam.
    protected double mViewplaneWidth, mViewplaneHeight;                            // Largeur/Hauteur du viewplane.
    protected Point3D mCamPos, mVecDir, mUpVec, mRightVec, mViewPlaneUpLeft;

    public RtCamera(Point3D vCamPos, Point3D directionVec, Point3D vRightVec, Point3D vUpVector, int type)

    {
        super(type, "CAMERA");
        mCamPos = vCamPos;
        mVecDir = directionVec.norme1();
        mUpVec = vUpVector.norme1();
        mRightVec = vRightVec.norme1();
        mViewplaneDist = 1.0f;
        mViewplaneHeight = 1.0f;
        mViewplaneWidth = 1.0f;
        mViewPlaneUpLeft = mCamPos.plus(mVecDir.mult(mViewplaneDist))
                .plus(mRightVec.mult(-mViewplaneWidth)).plus(mUpVec.mult(-mViewplaneHeight));

    }

    Point3D calcDirVec(double x, double y, int xRes, int yRes) {
        double xIndent, yIndent;
        double posX = x;
        double posY = y;
        xIndent = 2.0 * mViewplaneWidth / xRes;
        yIndent = 2.0 * mViewplaneHeight / yRes;

        return mViewPlaneUpLeft.plus(mRightVec.mult(xIndent * posX).plus(mUpVec.mult(yIndent * posY))).moins(getPosition()).norme1();
    }


    @Override
    public boolean intersectsNode(RtRay ray, RtIntersectInfo intersectInfo) {
        return false;
    }

    public Point3D getPosition() {
        return mCamPos;
    }
}




