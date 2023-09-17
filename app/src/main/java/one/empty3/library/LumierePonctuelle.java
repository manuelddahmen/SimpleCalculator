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
package one.empty3.library;

import java.awt.*;

/*__
 * @author Manuel Dahmen _manuel.dahmen@gmx.com_
 */
public final class LumierePonctuelle extends Lumiere {

    double minThreshold = 0.0, maxThreshold = 1.0;

    private StructureMatrix<ITexture> couleurLumiere = new StructureMatrix<>(0, ITexture.class);
    private StructureMatrix<Point3D> position = new StructureMatrix<>(0, Point3D.class);
    private double r0 = 100.0;
    private StructureMatrix<Boolean> directional = new StructureMatrix<>(0, Boolean.class);

    public LumierePonctuelle() {
        position.setElem(Point3D.O0);
        this.couleurLumiere.setElem(new ColorTexture(Color.YELLOW));
        directional.setElem(Boolean.FALSE);
    }

    public LumierePonctuelle(Point3D pos, Color couleurLumiere) {
        this.position.setElem(pos);
        this.couleurLumiere.setElem(new ColorTexture(couleurLumiere));
        this.Ls = couleurLumiere;
        directional.setElem(Boolean.FALSE);

    }

    @Override
    public int getCouleur(int base, Point3D p, Point3D n) {
        if (n == null)
            return base;
        //double x = Math.asin(p.moins(position.getElem()).norme1().dot(n.norme1())) / 2 / Math.PI;
        Point3D l = p.moins(position.getElem()).norme1();

        Double dot = l.dot(n.norme1());
        double angle = Math.cos(dot);


        double[] couleurObjet = getDoubles(base);
        double[] res = new double[3];
        double[] Lsa = getRgb(Ls);
        double[] Laa = getRgb(La);
        for (int i = 0; i < 3; i++) {
            double x = (1-angle)*couleurObjet[i]
                    + (angle) * Lsa[i];
            res[i] = Math.exp(-x*x*2);

            if (res[i] < minThreshold) {
                res[i] = minThreshold;
                res[i] = couleurObjet[i];
            }
            if (res[i] > maxThreshold) {
                res[i] = Lsa[i];
            }

        }
        return getInt(res);
    }

    public void intensite(int r0) {
        this.r0 = r0;
    }

    public StructureMatrix<Boolean> getDirectional() {
        return directional;
    }

    public void setDirectional(StructureMatrix<Boolean> directional) {
        this.directional = directional;
    }

    public void setR0(double r0) {
        this.r0 = r0;
    }

    float minmaxc(double c) {
        return (float) Math.max(1.0, Math.min(0.0, c));
    }



    public void declareProperties() {
        getDeclaredDataStructure().put("position/Position de la provenace lumineuse", position);
        getDeclaredDataStructure().put("color/Couleur de la lumière", couleurLumiere);
        getDeclaredDataStructure().put("directinal/isDirectional rayons parallèle et sphèrique", directional);
    }
}
