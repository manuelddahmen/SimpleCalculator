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

package one.empty3.library;

import one.empty3.library.core.nurbs.ParametricSurface;
import one.empty3.library.core.nurbs.ParametricVolume;
import one.empty3.library.core.nurbs.RPv;

import java.awt.*;
import java.io.File;
/*__
 * Created by manue on 15-06-19.
 */
public class ZBufferGL implements ZBuffer {
    private int idImg;

    @Override
    public Camera camera() {
        return null;
    }

    @Override
    public void couleurDeFond(ITexture couleurFond) {

    }

    @Override
    public void draw() {

    }

    @Override
    public void draw(Representable r) {

    }

    @Override
    public int getColorAt(Point p) {
        return 0;
    }

    @Override
    public ZBuffer getInstance(int x, int y) {
        return null;
    }

    @Override
    public ECBufferedImage image() {
        return null;
    }

    @Override
    public ECBufferedImage imageInvX() {
        return null;
    }

    @Override
    public boolean isLocked() {
        return false;
    }

    @Override
    public void isobox(boolean isBox) {

    }


    @Override
    public void line(Point3D p1, Point3D p2, ITexture t) {

    }

    @Override
    public boolean lock() {
        return false;
    }

    @Override
    public void plotPoint(Point3D p, Color c) {

    }

    @Override
    public int resX() {
        return 0;
    }

    @Override
    public int resY() {
        return 0;
    }
    
    @Override
    public boolean checkScreen(Point p1) {
        return false;
    }
    @Override
    public Scene scene() {
        return null;
    }

    @Override
    public void scene(Scene s) {

    }

    @Override
    public void next() {

    }

    @Override
    public void testDeep(Point3D point3D) {

    }

    @Override
    public void testDeep(Point3D p, Color c) {

    }

    @Override
    public void testDeep(Point3D p, int c) {

    }

    @Override
    public void tracerLumineux() {

    }

    @Override
    public boolean unlock() {
        return false;
    }

    @Override
    public void zoom(float z) {

    }

    @Override
    public ITexture backgroundTexture() {
        return null;
    }

    @Override
    public void backgroundTexture(ITexture iTexture) {

    }

    @Override
    public int largeur() {
        return 0;
    }

    @Override
    public int hauteur() {
        return 0;
    }

    @Override
    public void setDimension(int width, int height) {

    }
    public void copyResourceFiles(File destDirectory){}
 
    @Override
    public Point3D clickAt(double x, double y) {
        return null;
    }


    @Override
    public int idz() {
        return idImg;
    }

    @Override
    public void drawElementVolume(Representable representable, ParametricVolume volume) {

        throw new UnsupportedOperationException("Not implemented");//return null;
    }

    @Override
    public int getDisplayType() {
        return 0;
    }

    @Override
    public double maxDistance(Point... p1) {
        return 0;
    }

    @Override
    public void testDeep(Point3D pFinal, ITexture texture, double u, double v, ParametricSurface n) {

    }

    @Override
    public int la() {
        return resX();
    }

    @Override
    public int ha() {
        return resY();
    }


    public void idzpp() {
        idImg++;
    }
}
