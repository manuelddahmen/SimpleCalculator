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

public class Lighter implements IBasicLighter {

    Point3D pointLumiere;
    double intensite;
    double rayonEnglobant;

    public Lighter() {
    }

    public Color calculerLumierePoint(Point3D point, Color couleur, Point3D normale) {
        double facteurAngulaire = point.moins(pointLumiere).norme1().prodScalaire(normale.norme1());

        double distance = Point3D.distance(point, pointLumiere);

        double formule = facteurAngulaire * intensite * Math.exp(-distance * distance / rayonEnglobant / rayonEnglobant);

        float[] colorsComp = new float[3];

        couleur.getColorComponents(colorsComp);

        for (int i = 0; i < 3; i++) {
            colorsComp[i] = (float) (colorsComp[i] * formule);
            if (colorsComp[i] > 1f) {
                colorsComp[i] = 1f;
            } else if (colorsComp[i] < 0f) {
                colorsComp[i] = 0f;
            }
        }

        return new Color(colorsComp[0], colorsComp[1], colorsComp[2]);
    }

    public void config(Point3D pointLumiere, double intensite, double rayonEnglobant) {
        this.pointLumiere = pointLumiere;
        this.intensite = intensite;
        this.rayonEnglobant = rayonEnglobant;
    }
}
