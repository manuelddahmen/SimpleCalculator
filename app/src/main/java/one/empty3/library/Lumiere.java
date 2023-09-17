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
import java.awt.Color;
/*__
 * @author Atelier
 */
public abstract class Lumiere  extends Representable{
  // ambient specular diffuse shinyness
   protected Color La=Color.BLACK,Ls=Color.WHITE, Ld=Color.WHITE;
   
   protected double S=0.5;

    public static Color getColor(int colorAt) {
        int [] c = new int[3];
        int res = 0x00000000;
        for(int i=0 ;i<3;i++) {
            c[i] = ((int)(float)(colorAt*0xff))<<((2-i)*8);
            if(c[i]<0)
                c[i] = 0;
            if(c[i]>255)
                c[i] = 255;
            res += c[i];
        }
        return new Color(c[0], c[1], c[2]);
    }

    public abstract int getCouleur(int base, Point3D p, Point3D n);
    public int getLa() {return La.getRGB();}
    public int getLs() {return Ls.getRGB();}
    public int getLd() {return Ld.getRGB();}

    public static double [] getRgb(Color c) {
       return new double[] {(c.getRed()/255f),
          (c.getGreen()/255f),
          (c.getBlue()/255f)};
    }
  
  public static int  getInt(double [] d) {
       int res = 0xFF000000;
    for(int i=0 ;i<3;i++) {
        res += ((int)(float)(d[i]*0xff))<<((2-i)*8);
    }
    return res;//|0xFF000000;
  }

   public static double[] getDoubles(int c) {
        double [] res = new double[4];
       for(int i=0 ;i<3;i++) {
        res[i] = (((c&(0xff<<((2-i)*8)) )>>((2-i)*8)))/255.;
       }
       if(res.length>3)
           res[3] = 0.0;
       return res;
    }
    public static Color getColorD(double[] d) {
        return new Color((float)(d[0]), (float)(d[1]),(float)(d[2]));
    }
}
