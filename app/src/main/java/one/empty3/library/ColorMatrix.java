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
package one.empty3.library;

public class ColorMatrix<E> extends Representable {

    /*__
     *
     */
    private static final long serialVersionUID = 8229059930778672554L;
    private String id;
    private Point3D[] nodes;
    private My2DarrayColor colors;
    private E[] elements;
    private Point3D[] interpolate;
    private int dimx;
    private int dimy;

    public ColorMatrix(int dimx, int dimy) {
        super();
        this.dimx = dimx;
        this.dimy = dimy;
    }

    public My2DarrayColor getColors() {
        return colors;
    }

    public void setColors(My2DarrayColor colors) {
        this.colors = colors;
    }

    public int getDimx() {
        return dimx;
    }

    public void setDimx(int dimx) {
        this.dimx = dimx;
    }

    public int getDimy() {
        return dimy;
    }

    public void setDimy(int dimy) {
        this.dimy = dimy;
    }

    public E[] getElements() {
        return elements;
    }

    public void setElements(E[] elements) {
        this.elements = elements;
    }

    public Point3D[] getInterpolate() {
        return interpolate;
    }

    public void setInterpolate(Point3D[] interpolate) {
        this.interpolate = interpolate;
    }

    public Point3D[] getNodes() {
        return nodes;
    }

    public void setNodes(Point3D[] nodes) {
        this.nodes = nodes;
    }

}
