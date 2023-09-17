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

/*__
 * *
 * <p>
 * PACKAGE be.ibiiztera.md.pmatrix.pushmatrix OWNER DAHMEN MANUEL
 */

import java.awt.*;
import java.util.ArrayList;

public class LumiereScene {/*
     public class Lumiere
     {
     private double ratio = 1.0;
     private Color baseCouleur = Color.WHITE;
     private Point3D vecteur = new Point3D(0,0,1);
     public void ratio(double r)
     {
     ratio = r;
     }
     public double ratio()
     {
     return ratio;
     }
     public void vecteur(Point3D v)
     {
     vecteur=v;
     }
     public Point3D vecteur()
     {
     return vecteur;
     }
     public void couleur(Color c)
     {
     baseCouleur = c;
     }
     public Color couleur()
     {
     return baseCouleur;
     }
     public double facteurAngulaire(double a)
     {
     return Math.exp(-a*a);
     }
     }

     private ArrayList<Lumiere> lumieres = new ArrayList<Lumiere>();
     public Color calculer(Point3D n, Color co)
     {
     double ratio = 0.0;
     double a = 0.0;
     double [] c  = new double[] {co.getRed(), co.getGreen(), co.getBlue()};
     for(int i=0; i<lumieres.size(); i++)
     ratio += lumieres.get(i).ratio();
     for(int i=0; i<lumieres.size(); i++)
     {
     Lumiere l  = lumieres.get(i);
     a = Math.acos(l.vecteur().prodScalaire(n));
     for(int comp = 0; comp<3; comp++)
     {
     double compVal = 0;
     switch(comp)
     {
     case 0:
     compVal = l.couleur().getRed() - c[comp];
     break;
     case 1:
     compVal = l.couleur().getGreen() - c[comp];
     break;
     case 2:
     compVal = l.couleur().getBlue() - c[comp];
     break;
     }
     c[comp] += 
     compVal
     * l.ratio()/ratio
     * l.facteurAngulaire(a);
     }
     }
     for(int comp = 0; comp<3;comp++)
     {
     if(c[comp]>255)
     c[comp] = 255;
     if(c[comp]<0)
     c[comp] = 0;

     }
     return new Color((int)c[0], (int)c[1], (int)c[2]);
     }
     */


    public ITexture calculer(ArrayList<Lumiere> ls, Point3D p) {
        Color synthese = new Color(p.texture().getColorAt(0.5, 0.5));

        /* Calcul */
        if (ls.size() > 0) {

            float[] ratio = new float[]{1};

            Color[] colors = new Color[]{synthese};

            float[] f = synthese(ratio, colors);

            synthese = new Color(f[0], f[1], f[2]);
        }

        return new TextureCol(synthese);
    }

    private float[] synthese(float[] ratio, Color[] colors) {

        float[] c = new float[]{0, 0, 0};
        float[] incr = new float[]{0, 0, 0};

        for (int i = 0; i < ratio.length; i++) {
            colors[i].getColorComponents(incr);
            for (int j = 0; j < 3; j++) {
                c[j] += incr[j] / ratio.length;
            }
        }

        return c;
    }
}
