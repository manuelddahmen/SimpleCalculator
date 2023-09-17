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
public final class LumierePonctuellePeriodique extends Lumiere {

    private Color couleurLumiere = Color.RED;
    private Point3D position;
    private double k = 1;
    private double r0 = 11;

    public LumierePonctuellePeriodique(Point3D pos, Color couleurLumiere) {
        this.position = pos;
        this.couleurLumiere = couleurLumiere;
    }

    @Override
    public int getCouleur(int base, Point3D p, Point3D n) {
        double x = (n.norme1().prodScalaire(position.moins(p).norme1()) + 1) / 2;
        double r = x;
        Color couleurObjet = new Color(base);
        return new Color((float) ((couleurObjet.getRed() / 256.0) * r + (couleurLumiere.getRed() / 256.0) * (1 - r)), (float) ((couleurObjet.getGreen() / 256.0) * r + (couleurLumiere.getGreen() / 256.0) * (1 - r)), (float) ((couleurObjet.getBlue()
                / 256.0) * r + (couleurLumiere.getBlue() / 256.0) * (1 - r))).getRGB();
    }

    public void r0(int r0) {
        this.r0 = r0;
    }

}
