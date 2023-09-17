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
import one.empty3.library.*;

public class RtIntersectInfo {

    public Point3D mIntersection;    // Position de l'intersection
    public Point3D mNormal;        // Normale au point d'intersection
    public RtNode mNode;            // Node touch�e par l'intersection
    public Representable mRepres;
    public RtMatiere mMaterial;        // Material au point d'intersection

    public RtIntersectInfo() {
        super();
        mIntersection = new Point3D();    // Position de l'intersection
        mNormal = new Point3D();// Normale au point d'intersection
        mNode = new RtDefaultRtNode();            // Node touch�e par l'intersection
        mRepres = null;
        mMaterial = new RtMatiere();        // Material au point d'intersection
    }
}