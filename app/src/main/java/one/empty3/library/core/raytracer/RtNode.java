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

public abstract class RtNode {
    public static final int LIGHT = 0x0001000;
    public static final int OMNILIGHT = 0x0001002;
    public static final int CAMERA = 0x0010004;
    public static final int TARGETCAMERA = 0x0010008;

    protected int mNodeType;
    protected String mName;

    // constructeurs et destructeur
    public RtNode() {
        mName = null;
    }

    public RtNode(int nodeType, String name) {
        mNodeType = nodeType;
        mName = name;
    }

    // raytrace related
    public abstract boolean intersectsNode(RtRay ray, RtIntersectInfo intersectInfo);

    // get
    public final int getNodeType() {
        return mNodeType;
    }

    // set
    public void setNodeType(int nodeType) {
        mNodeType = nodeType;
    }

    public final String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public final RtNode getNodeInstance() {
        return this;
    }
}