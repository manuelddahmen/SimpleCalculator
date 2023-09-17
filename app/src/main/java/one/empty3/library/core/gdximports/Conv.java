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
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package one.empty3.library.core.gdximports;

import one.empty3.library.*;
import com.badlogic.gdx.math.Vector3;

/*__
 * @author Manuel Dahmen _manuel.dahmen@gmx.com_
 */
public class Conv {
    public static Vector3 conv(Vector3 out, Point3D in) {
        out.set(new float[]{(float) (double) in.get(0), (float) (double) in.get(1), (float) (double) in.get(2)});
        return out;
    }

    public static Point3D conv(Point3D out, Vector3 in) {
        out.set(0, (double) in.x);
        out.set(1, (double) in.y);
        out.set(2, (double) in.z);
        return out;
    }
}
