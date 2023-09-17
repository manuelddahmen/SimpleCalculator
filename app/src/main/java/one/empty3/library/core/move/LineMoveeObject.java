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

package one.empty3.library.core.move;

import one.empty3.library.LineSegment;
import one.empty3.library.MoveeObject;
import one.empty3.library.Point3D;

/*__
 * Created by manuel on 29-06-17.
 */
public class LineMoveeObject extends LineTrajectory implements MoveeObject {
    LineMoveeObject(LineSegment sd, double speed) {
        super(sd, speed);
        this.setPositionAtTime(a, System.nanoTime());
    }

    @Override
    public void setPositionAtTime(Point3D position, long nanoTime) {
        a = position;
        this.nanoTime = nanoTime;
    }

    @Override
    public Point3D getCurrentPosition() {
        return calculerPoint3D(System.nanoTime() - this.nanoTime);
    }
}
