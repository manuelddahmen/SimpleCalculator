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

import one.empty3.library.core.nurbs.ParametricCurve;
import one.empty3.library.core.nurbs.ParametricSurface;

import java.awt.*;
import java.util.List;

/*__
 * *
 * <p>
 * Classe pour les éléments à trois coordonnées de type Double
 * Points, Vecteur 3D et calcul
 *
 * @author Manuel Dahmen
 */
public class Point3D_0 extends Representable {
    private Double tempz;
    private Double tempy;
    private Double tempx;

    public static void start() {
    }

    public static void end() {
    }

    public Point3D_0() {
        super();
    }

    /*__
     * *
     * axe X vector
     */
    public static final Point3D_0 X = new Point3D_0(1d, 0d, 0d);
    /*__
     * *
     * axe Y vector
     */
    public static final Point3D_0 Y = new Point3D_0(0d, 1d, 0d);
    /*__
     * *
     * axe Z vector
     */
    public static final Point3D_0 Z = new Point3D_0(0d, 0d, 1d);
    /*__
     * *
     * O0 origin
     */
    public static final Point3D_0 O0 = new Point3D_0(0d, 0d, 0d);
    /*__
     * *
     * Point "Infinite" limite pour Z-Buffer
     */
    public static final Point3D_0 INFINI = new Point3D_0(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
    /*__
     * *
     * Coordonnées (coordArr,y,z) du point
     */
    final StructureMatrix<Double> coordArr = new StructureMatrix<>(1, Double.class);

    {
        coordArr.setElem(0d, 0);
        coordArr.setElem(0d, 1);
        coordArr.setElem(0d, 2);

    }
    /*__
     * *
     * Pour le tracé de surface normale au point
     */
    protected Point3D_0 normale;

    /*__
     * *
     * id
     */
    /*__
     * *
     *
     * @param x0 coordArr-coordonnée
     * @param y0 y-coordonnée
     * @param z0 z-coordonnée
     */
    public Point3D_0(Double x0, Double y0, Double z0, ITexture t) {
        this(x0, y0, z0);
        texture(t);
    }

    /*__
     * *
     * Initialise à partir d'un vecteur
     *
     * @param x0 coordonnées (>3)
     */
    public Point3D_0(double[] x0) {
        int i = 0;
        if(x0.length<3)
            throw new UnsupportedOperationException("x0.length<3");
        for (double d : x0) {
            coordArr.setElem(d, i);
            i++;
        }
    }

    /*__
         * *
         * Initialise à partir d'un vecteur
         *
         * @param x0 coordonnées (>3)
         */
    public Point3D_0(Double... x0) {
        int i = 0;
        if(x0.length<3)
            throw new UnsupportedOperationException("x0.length<3");
        for (Double d : x0) {
            coordArr.setElem(d, i);
            i++;
        }
    }

    public Point3D_0(Double[] x0, ITexture t) {
        int i = 0;
        if(x0.length<3)
            throw new UnsupportedOperationException("x0.length<3");
        for (Double d : x0) {
            coordArr.setElem(d, i);
            i++;
        }
        texture(t);
    }

    /*__
     *
     *
     * @param p0 point à copier
     */
    public Point3D_0(int n) {
        super();
        for (int i = 0; i < n; i++)
            coordArr.setElem(0.0, i);
    }

    /*__
         *
         *
         * @param p0 point à copier
         */
    public Point3D_0(Point3D_0 p0) {
        super();
        for (int i = 0; i < 3; i++)
            coordArr.setElem(p0.get(i), i);
        texture(p0.texture);
    }

    public Point3D_0(StructureMatrix<Double> coordArr) {
        this(coordArr.getElem(0), coordArr.getElem(1), coordArr.getElem(2));
    }

    public static Point3D_0 n(Double a, Double b, Double c) {
        return new Point3D_0(a, b, c);
    }


    public static Point3D_0 n(Point3D_0 p) {
        return new Point3D_0(p);
    }

    /*__
     * *
     * Distance cartésienne entre 2 points
     *
     * @param p1 Point1
     * @param p2 Point2
     * @return
     */
    public static Double distance(Point3D_0 p1, Point3D_0 p2) {
        double d = 0.0;
        for (int i = 0; i < p1.getCoordArr().getData1d().size(); i++)
            d += (p1.get(i) - p2.get(i)) * (p1.get(i) - p2.get(i));
        return Math.sqrt(d);
    }
    /*
    public static Double distance(Point3D_0 p1, Point3D_0 p2, Fct1D_1D f) {
        double d = 0.0;
        for (int i = 0; i < p1.getCoordArr().getData1d().size(); i++)
            d += (f.result(p1.get(i) - p2.get(i))) * f.result((p1.get(i) - p2.get(i)));
        return Math.sqrt(d);
    }
*/
    public static Point3D_0 random(Double d) {
        return new Point3D_0(Math.random() - 0.5, Math.random() - 0.5, Math.random() - 0.5).mult(d * 2);
    }

    public static Point3D_0 random(Double d, int n) {

        return new Point3D_0(Math.random() - 0.5, Math.random() - 0.5, Math.random() - 0.5).mult(d * 2);
    }

    public static Point3D_0 r(Double d) {
        return random(d);
    }

    public static Point3D_0 random2(Double d) {

        return new Point3D_0(((Math.random() - 0.5) * 2 * d), ((Math.random() - 0.5) * 2 * d), ((Math.random() - 0.5) * 2 * d));
    }

    public static double[] toArray1d(Point3D_0 norme1, double [] arr) {
        double[] d = arr;

        if(arr.length<3)
            d = new double[3];

        arr [0] = norme1.coordArr.getElem(0);arr[1]=norme1.coordArr.getElem(1);arr[2]=norme1.coordArr.getElem(2);

        return arr;
    }

    @Override
    public Object clone() {
        return new Point3D_0(coordArr);
    }

    public Double get(int i) {
        // if(i>=0 && i<3 && coordArr.data1d.size()==3)
        return coordArr.getElem(i);
       /* else
            try {
                throw new Throwable("point3D coordArr out of bounds or array dim error\nValues="+coordArr.toString()+"\nSize="+coordArr.data1d.size());
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        return Double.NaN;*/
    }


    public List<Double> getDoubleArray() {
        return coordArr.getData1d();
    }


    public Point3D_0 getNormale() {
        return normale;
    }

    public void setNormale(Point3D_0 normale) {
        this.normale = normale;
    }

    public Double getY() {
        return coordArr.getElem(1);
    }

    public void setY(Double x0) {
        coordArr.setElem(x0, 1);

    }

    public Double getZ() {
        return coordArr.getElem(2);
    }

    public void setZ(Double x0) {
        coordArr.setElem(x0, 2);

    }

    public Double getX() {
        return coordArr.getElem(0);
    }

    public void setX(Double x0) {
        coordArr.setElem(x0, 0);

    }


    public Point3D_0 plus(Point3D_0 p) {
        Point3D_0 p1 = new Point3D_0(this);
        for (int i = 0; i < coordArr.data1d.size(); i++)
            p1.set(i, get(i) + p.get(i));

        return p1;
    }

    public Point3D_0 moins(Point3D_0 p) {
        Point3D_0 p1 = new Point3D_0(this);
        for (int i = 0; i < coordArr.data1d.size(); i++)
            p1.set(i, get(i) - p.get(i));

        return p1;
    }

    /*__
     * *
     * Multiplication
     *
     * @param xFactor facteur
     * @return
     */

    public Point3D_0 mult(Point3D_0 p) {
        Point3D_0 p1 = new Point3D_0(this);
        for (int i = 0; i < coordArr.data1d.size(); i++)
            p1.set(i, get(i) * p.get(i));

        return p1;
    }
    public Point3D_0 multDot(Point3D_0 p1) {
        return mult(p1);
    }
    public Point3D_0 mult(Double d) {

        Point3D_0 p1 = new Point3D_0(this);
        for (int i = 0; i < coordArr.data1d.size(); i++)
            p1.set(i, get(i) * d);

        return p1;
    }

    public Point3D_0 mult(double d) {

        Point3D_0 p1 = new Point3D_0(this);
        for (int i = 0; i < coordArr.data1d.size(); i++)
            p1.set(i, get(i) * d);

        return p1;
    }

    /*
     public Vec  mult(Vec point3D) {
        return Matrix33.YZX.mult(Matrix33.ZXY.mult(Matrix33.XYZ.mult(point3D)));
       }*/
    /*__
     * *
     * norme d'un vecteur (distance du point à l'origine)
     *
     * @return
     */
    public Double norme() {
        double n = 0.0;
        for (int i = 0; i < coordArr.getData1d().size(); i++)
            n += get(i) * get(i);
        return Math.sqrt(n);
    }

    /*__
     * *
     * "direction" (norme1)
     *
     * @return Vecteur normalisé à 1
     */
    public Point3D_0 norme1() {
        if(norme()==0.0)
            return Point3D_0.O0;
        return mult(1 / norme());
    }

    /*__
     * *
     * Ajoute @param i à chaque coordonnée
     *
     * @param i
     * @return
     */
    public Point3D_0 plus(Double d) {
        Point3D_0 p = new Point3D_0(this);
        for (int i = 0; i < coordArr.getData1d().size(); i++)
            p.set(i, get(i) + d);
        return p;
    }


    /*__
     * *
     * Produit scalaire
     *
     * @param p2
     * @return
     */
    public Double prodScalaire(Point3D_0 p2) {
        double s = 0.0;
        if (p2 != null) {
            for (int i = 0; i < coordArr.getData1d().size(); i++)
                s += coordArr.getElem(i) * p2.get(i);
        } else
            throw new NullPointerException("Exception prodScalre p2==null");
        return s;
    }

    /*__/*__
     * *
     * Produit scalaire
     *
     * @param p2
     * @return
     */
    public Double dot(Point3D_0 p2) {
        return this.prodScalaire(p2);
    }

    /* *
    * produit vectoriel
    *
    * @param p1
    * @return
    */
    public Point3D_0 prodVect(Point3D_0 p1) {
        return new Point3D_0(p1.getY() * getZ() + -p1.getZ() * getY(), p1.getZ()
                * getX() - p1.getX() * getZ(), p1.getX() * getY() - p1.getY()
                * getX());
    }

    public void set(int i, Double d) {
        coordArr.setElem(d, i);

    }

    public String toLongString() {
        //Color c = texture.toString();
        return "p ( \n\t(" + coordArr.getElem(0) + " , " + coordArr.getElem(1) + " , " + coordArr.getElem(2) + " )\n\t("
                + texture.toString()
                + ")\n)\n";
    }

    @Override
    public String toString() {
        String s = "\n\tp3( " + (Double) (coordArr.getElem(0)) + " , " + (Double) (coordArr.getElem(1)) + " , " + (Double) (coordArr.getElem(2)) + " ) ";
        return s;
    }

    @Override
    public boolean ISdrawStructureDrawFastIMPLEMENTED(ZBuffer z) {
        return super.ISdrawStructureDrawFastIMPLEMENTED(z); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void drawStructureDrawFast(ZBuffer z) {

       // z.testDeep(this, new Color(CFAST.getColorAt(0.5, 0.5)));

    }

    public Point2D get2D() {
        return new Point2D(coordArr.getElem(0), coordArr.getElem(1));
    }

    public void normalize() {
        Double n = norme();
        for (int i = 0; i < coordArr.getData1d().size(); i++)
            set(i, get(i) / n);
    }

    public Point2D to2DwoZ() {
        return get2D();
    }

    public Double NormeCarree() {
        return coordArr.getElem(0) * coordArr.getElem(0) + coordArr.getElem(1) * coordArr.getElem(1) + coordArr.getElem(2) * coordArr.getElem(2);
    }

    @Override
    public Representable intersects(Representable r2) {
        if (r2 instanceof Point3D) {
            Point3D_0 p2 = (Point3D_0) (r2);
            return ((coordArr.getElem(0) == p2.get(0)) && (coordArr.getElem(1) == p2.get(1)) && (coordArr.getElem(2) == p2.get(2))) ? this : null;
        } else if (r2 instanceof LineSegment) {
            LineSegment sd = (LineSegment) r2;

        } else if (r2 instanceof TRI) {
            TRI tri = (TRI) r2;

        } else if (r2 instanceof ParametricSurface) {
        } else if (r2 instanceof ParametricCurve) {
        }
        throw new UnsupportedOperationException("Pas implémenté encore");
    }


    public Point3D_0 changeTo(Point3D_0 dst) {
        for (int i = 0; i < 3; i++)
            this.coordArr.setElem(dst.coordArr.getElem(i), i);

        texture(dst.texture());
        return this;
    }

    public static Point3D_0 n(double x, double y, double z) {
        return new Point3D_0(x, y, z);
    }

    public double getLength() {
        return norme();
    }

    public void textureIndex(Double tempx, Double tempy, Double tempz) {
        this.tempx = tempx;
        this.tempy = tempy;
        this.tempz = tempz;
    }


    public class P extends Point3D_0 {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point3D)) return false;

        Point3D_0 point3D = (Point3D_0) o;

        if (point3D.coordArr.data1d.size() !=
                this.coordArr.data1d.size())
            return false;
        for (int i = 0; i < 3; i++) {
            if (!(coordArr.getElem(i) - (point3D.get(i)) < 1E-10))
                return false;
            if(coordArr.getElem(i).equals(Double.NaN)|| coordArr.getElem(i).equals(Double.NaN)) {
                return false;
            }
        }
        return true;
    }

    public boolean isAnyNaN() {
        for (int i = 0; i < 3; i++) {
            if (coordArr.getElem(i).equals(Double.NaN)) {
                return true;
            }
        }
        return false;
    }
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("coordArr/coordonnées", coordArr);
    }

    /*
        public Double get(int i) {
            return i<coordArr.data1d.size()?coordArr.getElem(i):Double.NaN;
        }*/
    public StructureMatrix<Double> getCoordArr() {
        return coordArr;
    }

    public void setCoordArr(StructureMatrix<Double> coordArr) {
        for(int i=0; i<3; i++)
            this.coordArr.setElem(coordArr.getElem(i), i);
    }

    public Point3D_0 calculerPoint0dT(double t) {
        return this;
    }
    
    /*
    public Point3D_0 statOp(Point3D_0 p, char po, int length){
        switch(po) {
                case '+':
                for(int i=0; i<3; i++)
             coordArr.setElem(i,coordArr.getElem(i)+p.get(i));
                break;
                case '-':
                    for(int i=0; i<3; i++)
              coordArr.setElem( coordArr.getElem(i)-p.get(i));
                break;
                    case '*':
                        
        for(int i=0; i<3; i++)
              coordArr.setElem( i,  coordArr.getElem(i)*p.get(i));
                break;
                    case '/':
                
        for(int i=0; i<3; i++)
              coordArr.setElem(i,
            coordArr.getElem(i)/p.get(i));
                break;
                case '.':
                double sum = 0.0;
                for(int i=0; i<3; i++)
              
                  sum += coordArr.getElem(i)*p.get(i);
                coordArr.setElem(0, sum);
                break;
                }
           
        
        return this;
    }*/

    public Color toColor() {
        return new Color((float)(double)(get(0)), (float)(double)(get(1)), (float)(double)(get(2)));
    }
    public static Point3D_0 fromColor(Color color) {
        float[] colorComponents = color.getColorComponents(new float[3]);
        Point3D_0 point3D = new Point3D_0(3);
        for(int i=0; i< colorComponents.length; i++)
            point3D.set(i, (double)colorComponents[i]);
        return point3D;
    }

    public Double getTempz() {
        return tempz;
    }

    public void setTempz(Double tempz) {
        this.tempz = tempz;
    }

    public Double getTempy() {
        return tempy;
    }

    public void setTempy(Double tempy) {
        this.tempy = tempy;
    }

    public Double getTempx() {
        return tempx;
    }

    public void setTempx(Double tempx) {
        this.tempx = tempx;
    }
}
