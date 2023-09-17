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

import java.awt.*;

/*__
 * @author Atelier
 */
public class LumierePointSimple extends LumierePoint {

    public static final Lumiere PARDEFAUT = new LumierePointSimple(Color.WHITE,
            Point3D.O0, 2.0);
    float[] f = new float[3];
    private Color couleur;
    private Point3D point;
    private double intensite = 1.0;
    private float[] comp = new float[3];

    public LumierePointSimple(Color c, Point3D pl, double intensite) {
        this.couleur = c;
        this.point = pl;
        this.intensite = intensite;
        couleur.getColorComponents(comp);
    }

    @Override
    public int getCouleur(int base, Point3D p, Point3D n) {
        if (p != null && n != null) {
            return mult(
                    (float) (Math.abs(n.norme1().prodScalaire(
                            p.moins(point).norme1())) * intensite), base);
        } else {
            return base;
        }
    }

    public int getCouleur(Point3D p) {
        return getCouleur(p.texture.getColorAt(0.5,0.5)
                , p, p.getNormale());
    }

    private int mult(float d, int base1) {
        new Color(base1).getColorComponents(f);
        for (int i = 0; i < 3; i++) {
            f[i] = (float) (f[i] * comp[i] * intensite);
            if (f[i] > 1f) {
                f[i] = 1f;
            }
            if (f[i] < 0f) {
                f[i] = 0f;
            }
        }
        return new Color(f[0], f[1], f[2]).getRGB();
    }

}
