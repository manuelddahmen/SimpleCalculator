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

import java.util.ArrayList;
import java.util.List;

public class RtScene {

    private int mNumNodes;
    private List<RtNode> mNodes = new ArrayList<RtNode>();
    private List<Representable> mNodesR = new ArrayList<Representable>();
    private List<RtCamera> mCameras = new ArrayList<RtCamera>();
    private List<RtLight> mLights = new ArrayList<RtLight>();
    private List<RtMatiere> mMaterials = new ArrayList<RtMatiere>();
    private RtCamera mActiveCamera;


    // constructeurs et destructeurs
    public RtScene() {
        mNumNodes = 0;
        mActiveCamera = null;


    }

    // get
    public int getNumNodes() {
        return mNodes.size();
    }

    public RtNode getNode(int i) {
        assert (i < mNodes.size());
        return mNodes.get(i);
    }

    public RtLight getLight(int i) {
        assert (i < mLights.size());
        return mLights.get(i);
    }

    public int getNumLights() {
        return mLights.size();
    }

    public RtCamera getActiveCamera() {
        return mActiveCamera;
    }

    // set
    public void setActiveCamera(int i) {
        assert (i < mCameras.size());
        mActiveCamera = mCameras.get(i);
    }

    public void setActiveCamera(RtCamera cam) {
        assert (cam != null);
        mActiveCamera = cam;
    }

    public RtMatiere getMaterial(int i) {
        assert (i < mMaterials.size());
        return mMaterials.get(i);
    }

    // methodes
    public boolean addObject(RtObject object) {
        assert (object != null);
        addNode(object);
        return true;
    }

    public boolean addCamera(RtCamera camera) {
        assert (camera != null);
        mCameras.add(camera);
        addNode(camera);
        setActiveCamera(camera);
        return true;
    }

    public boolean addNode(RtNode node) {
        assert (node != null);
        mNodes.add(node);
        mNumNodes++;
        return true;
    }

    public boolean addLight(RtLight light) {
        assert (light != null);
        mLights.add(light);
        addNode(light);
        return true;
    }

    public boolean addMaterial(RtMatiere material) {
        assert (material != null);
        mMaterials.add(material);
        return true;
    }

    public void addObject(Representable myRep) {
        mNodesR.add(myRep);
    }

    public List<Representable> getRepresentables() {
        return mNodesR;
    }
}
