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

 Vous êtes libre de :

 */
package one.empty3.library.core.tribase;

import one.empty3.library.*;

/*__
 * @author DAHMEN Manuel
 *         <p>
 *         dev
 * @date 24-mars-2012
 */
@Deprecated
public class SurfaceBezier extends TRIObjetGenerateurAbstract {

    BezierCubique2D bd = null;

    public SurfaceBezier(BezierCubique2D bd) {
        this.bd = bd;
    }

    @Override
    public Point3D coordPoint3D(int x, int y) {
        return bd.calculerPoint3D(1.0 * x / getMaxX(), 1.0 * y / getMaxY());
    }

}
