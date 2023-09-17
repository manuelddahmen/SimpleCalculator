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

/*__
 * Created by manuel on 02-12-16.
 */
public class RtParameterCamera extends RtTargetCamera {
    public static final int ORIENTATION_HORIZONTALE = 1;
    public static final int ORIENTATION_VERTICALE = 2;
    private double angleH, angleV;
    private int orientation;

    public RtParameterCamera(Point3D camPos, Point3D lookAtPoint, Point3D upVector) {
        super(camPos, lookAtPoint, upVector);
    }

    public double getAngleH() {
        return angleH;
    }

    public void setAngleH(double angleH) {
        this.angleH = angleH;
    }

    public double getAngleV() {
        return angleV;
    }

    public void setAngleV(double angleV) {
        this.angleV = angleV;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }
}
