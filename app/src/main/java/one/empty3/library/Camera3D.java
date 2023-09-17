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

/*__
 * @author Atelier
 */
public class Camera3D extends Camera {

    /*__
     *
     */
    private static final long serialVersionUID = 1907724549670145492L;
    protected Camera cGauche;
    protected Camera cDroite;
    double d = 1;
    private double angle3D = Math.PI / 360 * 20;
    private boolean enable = true;
    private Point3D ccg;
    private Point3D ccd;
    private double dist3D;

    public Camera3D(Point3D camera, Point3D lookAt, double dist3D) {
        enable3D();
        d = camera.moins(lookAt).norme();

        configure(dist3D);

        cGauche = new Camera(camera, lookAt);
        cDroite = new Camera(camera, lookAt);

    }

    public double angle3D() {
        return angle3D;
    }

    public void angle3D(double angle3D) {
        this.angle3D = angle3D;
    }
    public void calculerMatrice() {
        /*
         Point3D offsetGauche = cGauche.camera.prodVect(Point3D.Y).norme1()
         .mult(d * Math.atan(angle3D));
         Point3D offsetDroite = cDroite.camera.prodVect(Point3D.Y).norme1()
         .mult(-d * Math.atan(angle3D));
         */
        cGauche.eye.setElem(cGauche.eye.getElem()
                .plus(Point3D.X.mult(-dist3D / 2)));
        cDroite.eye.setElem(cDroite.eye.getElem()
                .plus(Point3D.X.mult(dist3D / 2)));

        calculerNouveauPoint();

        cGauche.calculerMatrice(null);
        cDroite.calculerMatrice(null);

    }

    protected void calculerNouveauPoint() {
    }

    public Point3D calculerPointDansRepereDROIT(Point3D p) {
        Point3D p2 = cDroite.calculerPointDansRepere(p);
        p2.texture(p.texture());
        return p2;
    }

    public Point3D calculerPointDansRepereGAUCHE(Point3D p) {
        Point3D p2 = cGauche.calculerPointDansRepere(p);
        p2.texture(p.texture());
        return p2;
    }

    public void configure(double dist3D) {
        this.dist3D = dist3D;
    }

    public boolean enable3D() {
        return enable;
    }

    public void enable3D(boolean d3) {
        this.enable = d3;
    }

    public Matrix33 oeilDroite() {
        return null;
    }

    public Matrix33 oeilGauche() {
        return null;
    }


}
