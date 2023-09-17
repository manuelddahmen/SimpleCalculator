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

package one.empty3.library.modelerview;

import java.util.HashMap;

public class CubesOrientation {
    public double size = 1.0;
    public enum CubesOrientationConstants { upFrontLeft, upFrontRight, downFrontLeft, downFrontRight,
        upBackLeft, upBackRight, downBackLeft, downBackRight }
    public enum OrientationConstants {up, right, down, left, front, back};
    public HashMap<CubesOrientationConstants, CubeProperty> properties = new HashMap<>();
    public CubesOrientation(double size) {
        this.size = size;
    }
}
