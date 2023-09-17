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

/*__
 * *
 * Global license : * Microsoft Public Licence
 * <p>
 * author Manuel Dahmen _manuel.dahmen@gmx.com_
 * <p>
 * *
 */
package one.empty3.library.core.nurbs;

import one.empty3.library.Camera;
import one.empty3.library.Point3D;
import one.empty3.library.TextureCol;
import one.empty3.library.core.testing.TestObjetSub;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/*__
 * @author Manuel Dahmen _manuel.dahmen@gmx.com_
 */
public class TestNurbs1 extends TestObjetSub {

    public static void main(String[] args) {
        TestNurbs1 n = new TestNurbs1();
        n.loop(false);
        n.setGenerate(GENERATE_MODEL | GENERATE_IMAGE);
        new Thread(n).start();
    }

    @Override
    public void testScene() throws Exception {
        NurbsSurface n = new NurbsSurface();
        n.setMaillage(new Point3D[][]{
                {
                        new Point3D(0d, 1d, 2d),
                        new Point3D(2d, 3d, 0d),
                        new Point3D(4d, 4d, -2d)},
                {
                        new Point3D(3d, 2d, 5d),
                        new Point3D(8d, 4d, 4d),
                        new Point3D(5d, 4d, 4d)},
                {
                        new Point3D(1d, 2d, 1d),
                        new Point3D(4d, 7d, 4d),
                        new Point3D(5d, 7d, 5d)}
        }, new double[][]{
                {1, 1, 1},
                {1, 1, 1},
                {1, 1, 1}
        });

        n.setDegreU(5);
        n.setDegreV(5);

        n.setReseauFonction(new double[][]{
                {0, 0, 0, 0.5, 1, 1, 1},
                {0, 0, 0, 0.5, 1, 1, 1}
        });

        n.texture(new TextureCol(Color.WHITE));

        n.setStartU(0.0);
        n.setStartV(0.0);
        n.setEndU(1.0);
        n.setEndV(1.0);
        n.setIncrU(0.01);
        n.setIncrV(0.01);

        n.creerNurbs();

        scene().add(n);
        Logger.getAnonymousLogger().log(Level.INFO,""+ n);
        //Axes axes = new Axes();

        //scene().add(axes);
        scene().cameraActive(new Camera(Point3D.Z.mult(-10d), Point3D.O0));
    }

    @Override
    public void finit() {
    }

    @Override
    public void ginit() {
    }
}
