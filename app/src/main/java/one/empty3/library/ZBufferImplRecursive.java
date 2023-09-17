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
 * Global license  GNU GPL v2
 * author Manuel Dahmen _manuel.dahmen@gmx.com_
 */
package one.empty3.library;

import one.empty3.library.core.nurbs.*;
import one.empty3.pointset.PCont;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/*__
 * * Classe de rendu graphique
 */
public class ZBufferImplRecursive extends ZBufferImpl {
    public ZBufferImplRecursive() {
        super();
    }

    public ZBufferImplRecursive(int l, int h) {
        super(l, h);
    }


    public ZBufferImplRecursive(Resolution resolution) {
        super(resolution.x(), resolution.y());
    }

    public void copyResourceFiles(File destDirectory) {
    }

    protected long idImg() {
        return idImg;
    }


    public Camera camera() {

        return scene().cameraActive();
    }

    public void camera(Camera c) {

        this.scene().cameraActive(c);
    }

    public synchronized void draw() {
        draw(scene());
    }

    public Point3D rotate(Point3D p0, Representable ref) {
        return p0;
        //return camera().calculerPointDansRepere(super.rotate(p0, ref));
    }

    public synchronized void draw(Collection<Object> collection) {
        collection.forEach(new Consumer() {
            @Override
            public void accept(Object o) {
                if (o instanceof Representable) {
                    draw((Representable) o);
                } else if (o instanceof Collection)
                    draw((Collection) o);
            }
        });
    }

    public synchronized void draw(Representable r) {
        camera().ratioHorizontalAngle(dimx, dimy);
        if (r == null) {
            Logger.getAnonymousLogger().log(Level.INFO, "r is null return");
            return;
        }
        if (r.texture() != null)
            r.texture().timeNext();

        if (r instanceof Scene) {
            Scene scene = (Scene) r;
            scene.getObjets().getData1d().forEach(representable -> draw(representable));
            return;
        } else if (r instanceof RepresentableConteneur) {
            ((RepresentableConteneur) r).getListRepresentable().forEach(representable -> draw(representable));
            return;
        }

        /* OBJECTS */
        if (r instanceof Point3D) {
            Point3D p = (Point3D) r;
            ime.testDeep(p);
        } else if (r instanceof ThickSurface) {
            // Logger.getAnonymousLogger().log(Level.INFO, "Surface");
            ThickSurface n = (ThickSurface) r;
            // TODO Dessiner les bords

            for (double u = n.getStartU(); u <= n.getEndU(); u += n.getIncrU()) {
                // Logger.getAnonymousLogger().log(Level.INFO, "(u,v) = ("+u+","+")");
                for (double v = n.getStartU(); v <= n.getEndV(); v += n.getIncrV()) {
                    Point3D p1, p2, p3, p4;

                    p1 = n.calculerPoint3D(u, v);
                    p2 = n.calculerPoint3D(u + n.getIncrU(), v);
                    p3 = n.calculerPoint3D(u + n.getIncrU(), v + n.getIncrV());
                    p4 = n.calculerPoint3D(u, v + n.getIncrV());
                    switch (displayType) {
                        case DISPLAY_ALL:
                        case SURFACE_DISPLAY_COL_QUADS:
                        case SURFACE_DISPLAY_TEXT_QUADS:
                            tracerQuad(p1, p2, p3, p4, n.texture(), u, u + n.getIncrU(), v, v + n.getIncrV(), n);
                            break;
                        case SURFACE_DISPLAY_COL_TRI:
                        case SURFACE_DISPLAY_TEXT_TRI:
                            tracerTriangle(
                                    n.calculerPoint3D(u, v),
                                    n.calculerPoint3D(u + n.getIncrU(), v),
                                    n.calculerPoint3D(u + n.getIncrU(),
                                            v + n.getIncrV()),
                                    n.texture(),
                                    u, v, u + n.getIncrU(), v + n.getEndV());
                            tracerTriangle(
                                    n.calculerPoint3D(u + n.getIncrU(),
                                            v + n.getIncrV()),
                                    n.calculerPoint3D(u, v + n.getIncrV()),
                                    n.calculerPoint3D(u, v),
                                    n.texture(),
                                    u + n.getIncrU(), v + n.getEndV(),
                                    u, v + n.getIncrV()
                            );
                            break;
                        case SURFACE_DISPLAY_LINES:
                            tracerLines(p1, p2, p3, p4, n.texture(), u, u + n.getIncrU(), v, v + n.getIncrV(), n);
                            break;
                        case SURFACE_DISPLAY_POINTS:
                            ime.testDeep(p1);
                            ime.testDeep(p2);
                            ime.testDeep(p3);
                            ime.testDeep(p4);
                            break;
                    }
                }
            }
        } else if (r instanceof TRI) {
            //System.out.print("Draw TRI");
            TRI tri = (TRI) r;
            if (displayType == SURFACE_DISPLAY_LINES) {
                for (int i = 0; i < 3; i++)
                    line(rotate(tri.getSommet().getElem(i), r),
                            rotate(tri.getSommet().getElem((i + 1) % 3), r)
                            , tri.texture);
            } else if (displayType == SURFACE_DISPLAY_POINTS) {
                for (int i = 0; i < 3; i++)
                    ime.testDeep(rotate(tri.getSommet().getElem(i), r)
                            , tri.texture);
            } else {
                tracerTriangle(rotate(tri.getSommet().getElem(0), r),
                        rotate(tri.getSommet().getElem(1), r),
                        rotate(tri.getSommet().getElem(2), r)
                        , tri.texture());
                //System.out.print("Triangle");
            }
        } else if (r instanceof ParametricSurface) {
            ParametricSurface n = (ParametricSurface) r;
            // Logger.getAnonymousLogger().log(Level.INFO, "Surface");
            //Logger.getAnonymousLogger().log(Level.INFO, "class" + n.getClass());
            // TODO Dessiner les bords
            for (double u = n.getStartU(); u + n.getIncrU() <= n.getEndU(); u += n.getIncrU()) {
                // Logger.getAnonymousLogger().log(Level.INFO, "(u,v) = ("+u+","+")");
                for (double v = n.getStartV(); v + n.getIncrV() <= n.getEndV(); v += n.getIncrV()) {
                    Point3D p1, p2, p3, p4;
                    double u2 = u + n.getIncrU(), v2 = v + n.getIncrV();
                    if (u > n.getEndU() - n.getIncrU()) {
                        Point3D point3D = new Point3D(u2, v2, 0.0);
                        point3D = n.getTerminalU().data0d.result(point3D);
                        u2 = point3D.get(0);
                        v2 = point3D.get(1);
                    }
                    if (v > n.getEndV() - n.getIncrV()) {
                        Point3D point3D = new Point3D(u2, v2, 0.0);
                        point3D = n.getTerminalU().data0d.result(point3D);
                        u2 = point3D.get(0);
                        v2 = point3D.get(1);
                    }

                    p1 = n.calculerPoint3D(u, v);
                    p2 = n.calculerPoint3D(u2, v);
                    p3 = n.calculerPoint3D(u2, v2);
                    p4 = n.calculerPoint3D(u, v2);
                    if (n instanceof HeightMapSurface) {
                        Point3D n1, n2, n3, n4;
                        HeightMapSurface h = (HeightMapSurface) n;
                        n1 = n.calculerNormale3D(u, v);
                        n2 = n.calculerNormale3D(u2, v);
                        n3 = n.calculerNormale3D(u2, v2);
                        n4 = n.calculerNormale3D(u, v2);
                        p1 = p1.plus(n1.norme1().mult(h.height(u, v)));
                        p2 = p2.plus(n2.norme1().mult(h.height(u2, v)));
                        p3 = p3.plus(n3.norme1().mult(h.height(u2, v2)));
                        p4 = p4.plus(n4.norme1().mult(h.height(u, v2)));
                    }
                    if (displayType == SURFACE_DISPLAY_POINTS || displayType == SURFACE_DISPLAY_POINTS_DEEP) {
                        ime.testDeep(p1, n.texture(), u, v, n);
                        if (displayType == SURFACE_DISPLAY_POINTS_DEEP) {
                            double v1p = maxDistance(camera().coordonneesPoint2D(p1, this), camera().coordonneesPoint2D(p2, this),
                                    camera().coordonneesPoint2D(p3, this), camera().coordonneesPoint2D(p4, this));
                            if (v1p > 1 && v1p < la && v1p < ha) {
                                int i = 0;
                                int j = 0;
                                for (i = 0; i < v1p; i += 1)
                                    for (j = 0; j < v1p; j += 1) {
                                        double u2p = u2 / (1 + v1p) * i;
                                        double v2p = v2 / (1 + v1p) * j;
                                        ime.testDeep(n.calculerPoint3D(u2, v2),
                                                n.texture(), u2, v2, n);
                                    }
                            }
                        } else if (displayType == SURFACE_DISPLAY_POINTS_LARGE) {
                            tracerQuad(p1, p2, p3, p4, n.texture(), u, u2, v, v2, n);
                        }
                    } else if (displayType == SURFACE_DISPLAY_LINES) {
                        tracerLines(p1, p2, p3, p4, n.texture(), u, u2, v, v2, n);
                    } else if (displayType == SURFACE_DISPLAY_COL_TRI ||
                            displayType == SURFACE_DISPLAY_TEXT_TRI) {
                        tracerTriangle(p1, p2, p3, n.texture(), u, v, u2, v2);
                        tracerTriangle(p3, p4, p1, n.texture(), u2, v2, u, v);


                    } else if (displayType == SURFACE_DISPLAY_COL_QUADS) {
                        if (p1 != null && p2 != null && p3 != null && p4 != null) {
                            tracerQuad(p1, p2, p3, p4, n.texture().getColorAt(u, v));
                        }
                    } else {
                        if (p1 != null && p2 != null && p3 != null && p4 != null) {
                            tracerQuad(p1, p2, p3, p4, n.texture(), u, u2, v, v2, n);
                        }
                    }
                }

            }
        } else if (r instanceof TRIGenerable) {
            r = ((TRIGenerable) r).generate();
            draw(r);

        } else if (r instanceof PGenerator) {
            r = ((PGenerator) r).generatePO();
            draw(r);
        } else if (r instanceof TRIConteneur) {
            r = ((TRIConteneur) r).getObj();
            draw(r);
        } else
            // OBJETS
            if (r instanceof TRIObject) {
                TRIObject o = (TRIObject) r;
                Logger.getAnonymousLogger().log(Level.INFO, "Objets triangle n°" + ((TRIObject) r).getTriangles().size());
                for (TRI t : o.getTriangles()) {

                    draw(t);

                }
            } else if (r instanceof Point3DS) {
                Point3D p = ((Point3DS) r).calculerPoint3D(0);
                ime.testDeep(rotate(p, r), r.texture());
            } else if (r instanceof LineSegment) {
                LineSegment s = (LineSegment) r;
                Point3D pO = s.getOrigine();
                Point3D pE = s.getExtremite();
                line(pO, pE, s.texture());
            } else if (r instanceof BezierCubique) {
                BezierCubique b = (BezierCubique) r;
                int nt = largeur() / 10;
                Point3D p0 = b.calculerPoint3D(0.0);
                for (double t = 0; t < 1.0; t += 1.0 / nt) {
                    try {
                        Point3D p1 = b.calculerPoint3D(t);
                        line(rotate(p0, r), rotate(p1, r), b.texture());
                        p0 = p1;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            } else if (r instanceof BezierCubique2D) {
                BezierCubique2D b = (BezierCubique2D) r;
                int i1 = BezierCubique2D.DIM1, i2 = BezierCubique2D.DIM2;
                for (int i = 0; i < i1; i++) {
                    for (int j = 0; j < i2; j++) {
                        double tx = (Math.max(i - 1, 0)) * 1d / i1;
                        double ty = (Math.max(j - 1, 0)) * 1d / i2;
                        draw(new Polygon(new Point3D[]{
                                rotate(b.calculerPoint3D(tx, (j) * 1d / i2), r),
                                rotate(b.calculerPoint3D((i) * 1d / i1, (j) * 1d / i2), r),
                                rotate(b.calculerPoint3D((i) * 1d / i1, ty), r),
                                rotate(b.calculerPoint3D(tx, ty), r)},
                                b.texture()));
                    }
                }
            } else if (r instanceof PCont) {
                PCont b = (PCont) r;
                b.getPoints().forEach(o -> ime.testDeep(rotate((Point3D) o, b)
                        , ((Point3D) o).texture().getColorAt(0, 0)));
            } else if (r instanceof POConteneur) {
                POConteneur c = (POConteneur) r;
                for (Point3D p : c.iterable()) {
                    {
                        ime.testDeep(rotate(p, r), p.texture());
                    }
                }
            } else if (r instanceof TRIConteneur) {
                for (TRI t : ((TRIConteneur) r).iterable()) {
                    {
                        draw(t);
                    }
                }

            } else if (r instanceof ParametricCurve) {
                ParametricCurve n = (ParametricCurve) r;
                double incr = n.getIncrU().getData0d();
                for (double u = n.start(); u <= n.endU(); u += incr) {
                    if (n.isConnected() && displayType != SURFACE_DISPLAY_POINTS) {
                        line(
                                n.calculerPoint3D(u),
                                n.calculerPoint3D(u + incr),
                                n.texture(), u, u + incr, n);
                    } else {
                        ime.testDeep(n.calculerPoint3D(u), n.texture().getColorAt(0.5, 0.5));
                    }
                }

            } else if (r instanceof Polygon) {
                Polygon p = (Polygon) r;
                List<Point3D> points = p.getPoints().getData1d();
                int length = points.size();
                Point3D centre = Point3D.O0;
                for (int i = 0; i < points.size(); i++)
                    centre = centre.plus(points.get(i));
                centre = centre.mult(1.0 / points.size());
                for (int i = 0; i < length; i++) {
                    if (getDisplayType() <= SURFACE_DISPLAY_COL_TRI) {
                        draw(new TRI(points.get(i), points.get((i + 1) % points.size()), centre, p.texture()));
                    } else {
                        line(points.get((i % length)), points.get((i + 1) % length), p.texture);
                    }
                }
            } else if (r instanceof RPv) {
                drawElementVolume(((RPv) r).getRepresentable(), (RPv) r);
            }

    }


    public double echelleEcran() {
        return box.echelleEcran();
    }

    public int getColorAt(Point p) {
        if (ime.getIME().getElementProf((int) p.getX(), (int) p.getY()) <= INFINITY_DEEP) {
            return ime.getIME().getElementCouleur((int) p.getX(), (int) p.getY());
        } else {
            return Color.TRANSLUCENT;
        }
    }

    public int[] getData() {
        return Scolor;
    }

    public ZBuffer getInstance(int x, int y) {
        return new ZBufferImpl(x, y);
    }

    /*__
     * @return hauteur du zbuffer
     */
    public int hauteur() {
        return ha;
    }

    @Override
    public void setDimension(int width, int height) {
        la = width;
        ha = height;
    }

    public ECBufferedImage image() {

        ECBufferedImage bi2 = new ECBufferedImage(la, ha, ECBufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < la; i++) {
            for (int j = 0; j < ha; j++) {
                int elementCouleur = ime.ime.getElementCouleur(i, j);
                bi2.setRGB(i, j, elementCouleur);

            }
        }
        this.bi = bi2;
        return bi2;

    }

    @Override
    public ECBufferedImage imageInvX() {
        ECBufferedImage bi2 = new ECBufferedImage(la, ha, ECBufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < la; i++) {
            for (int j = 0; j < ha; j++) {
                int elementCouleur = ime.ime.getElementCouleur(i, j);
                if (ime.getIME() != null && ime.getIME().getElementPoint(i, j).equals(INFINITY)) {
                    //elementCouleur = Color.TRANSLUCENT;
                }
                bi2.setRGB(la - i - 1, j, elementCouleur);

            }
        }
        this.bi = bi2;
        return bi2;
    }

    //??
    public ECBufferedImage image2() {
        //return image2();

//        BufferedImage bi = new BufferedImage(la, ha, BufferedImage.TYPE_INT_RGB);
//        bi.setRGB(0, 0, la, ha, getData(), 0, la);
//        return new ECBufferedImage(bi);
        return image();

    }

    public boolean isLocked() {
        return locked;
    }

    public void isobox(boolean isBox) {
    }


    /*__
     * @return largeur du zbuffer
     */
    public int largeur() {
        return la;
    }

    @Override
    /*__
     * @param p1 first point
     * @param p2 second point
     * @param t  colour of de la line
     */
    public void line(Point3D p1, Point3D p2, ITexture t) {
        Point x1 = camera().coordonneesPoint2D(p1, this);
        Point x2 = camera().coordonneesPoint2D(p2, this);
        if (x1 == null || x2 == null || (!checkScreen(x1) || !checkScreen(x2))) {
            return;
        }
        Point3D n = p1.moins(p2).norme1();
        double itere = Math.max(Math.abs(x1.getX() - x2.getX()), Math.abs(x1.getY() - x2.getY())) * 4 + 1;
        for (int i = 0; i < itere; i++) {
            Point3D p = p1.plus(p2.moins(p1).mult(i / itere));
            ime.testDeep(p, t.getColorAt(0.5, 0.5));
        }

    }

    public void line(Point3D p1, Point3D p2, ITexture t, double u, double u1, ParametricCurve curve) {
        Point x1 = camera().coordonneesPoint2D(p1, this);
        Point x2 = camera().coordonneesPoint2D(p2, this);
        if (x1 == null && x2 == null || (!checkScreen(x1) || !checkScreen(x2))) {
            return;
        }
        Point3D n = p1.moins(p2).norme1();
        double iterate = Math.max(Math.abs(x1.getX() - x2.getX()), Math.abs(x1.getY() - x2.getY())) * 4 + 1;
        for (int i = 0; i < iterate; i++) {
            Point3D p = p1.plus(p2.moins(p1).mult(i / iterate));
            double u0 = i / iterate;
            if (curve != null)
                p = curve.calculerPoint3D(u0);
            ime.testDeep(p, t, u, 0.0, curve);
        }

    }

    public void line(Point3D p1, Point3D p2, ITexture texture, double u1, double v1, double u2, double v2,
                     ParametricSurface surface) {
        // TODO Check
        Point x1 = camera().coordonneesPoint2D(p1, this);
        Point x2 = camera().coordonneesPoint2D(p2, this);
        if (x1 == null && x2 == null || (!checkScreen(x1) || !checkScreen(x2))) {
            return;
        }
        Point3D n = p2.moins(p1).norme1();
        double itere = Math.sqrt((x1.getX() - x2.getX()) * (x1.getX() - x2.getX())
                + (x1.getY() - x2.getY()) * (x1.getY() - x2.getY())) + 1;
        for (int i = 0; i < itere; i++) {
            Point3D p = p1.plus(n.mult(i / itere));
            double u = u1 + i / itere * (u2 - u1);
            double v = v1 + i / itere * (v2 - v1);
            if (surface != null) {
                //p = surface.calculerPoint3D(u2, v2);
                ime.testDeep(p, texture, u, v, surface);
            } else {
                ime.testDeep(p, texture.getColorAt(u, v));
            }
        }
    }


    public boolean lock() {
        if (locked) {
            return false;
        }
        locked = true;
        return true;
    }

    public Lumiere lumiereActive() {
        return currentScene.lumiereActive();
    }

    public double[][] map() {
        double[][] Map = new double[la][ha];

        for (int i = 0; i < la; i++) {
            for (int j = 0; j < ha; j++) {
                if (ime.getIME().getElementPoint(i, j) != null) {
                    Map[i][j] = ime.getIME().getElementPoint(i, j).getZ();
                } else {
                    Map[i][j] = INFINITY_DEEP;
                }
            }
        }
        return Map;

    }
    @Override
    public void testDeep(Point3D pFinal, ITexture texture, double u, double v, ParametricSurface n) {
        ime.testDeep(pFinal, texture, u, v, n);
    }

    @Override
    public int la() {
        return la;
    }

    @Override
    public int ha() {
        return ha;
    }


    public void itereMaxDist(List<Double> points, ParametricCurve pc, double pStart, double pEnd, ParametricVolume v) {
        Point3D p2start = v.calculerPoint3D(pc.calculerPoint3D(pStart));
        Point3D p2End = v.calculerPoint3D(pc.calculerPoint3D(pEnd));
        double dist = Point2D.dist(
                new Point2D(camera().coordonneesPoint2D(p2start, this)),
                new Point2D(camera().coordonneesPoint2D(p2End, this)));
        if (dist <= 1.0) {
            points.add(pStart);
            points.add(pEnd);
            ;
        } else {
            for (int i = 0; i < 10; i++) {
                itereMaxDist(points, pc, pStart + (pEnd - pStart) * i / 10.0, pStart + (pEnd - pStart) * (i + 1) / 10.0, v);
            }
        }
    }

    public void itereMaxDist(List<Double[]> polygons, ParametricSurface ps,
                             double u0, double u1, double v0, double v1, ParametricVolume v) {
        Point3D p1 = v.calculerPoint3D(ps.calculerPoint3D(u0, v0));
        Point3D p2 = v.calculerPoint3D(ps.calculerPoint3D(u1, v0));
        Point3D p3 = v.calculerPoint3D(ps.calculerPoint3D(u1, v1));
        Point3D p4 = v.calculerPoint3D(ps.calculerPoint3D(u0, v1));
        double dist = maxDistance(camera().coordonneesPoint2D(p1, this), camera().coordonneesPoint2D(p2, this),
                camera().coordonneesPoint2D(p3, this), camera().coordonneesPoint2D(p4, this)
        );
        if (dist <= 1.0) {
            polygons.add(new Double[]{u0, u1, v0, v1});
        } else {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    itereMaxDist(polygons, ps,
                            u0 + (u1 - u0) * i / 10.0, u0 + (u1 - u0) * (i + 1) / 10.0, v0 + (v1 - v0) * j / 10.0, v0 + (v1 - v0) * (j + 1) / 10.0, v);
                }
            }
        }
    }

    public void plotPoint(Color color, Point3D p) {
        if (p != null && color != null) {
            ime.testDeep(p, color.getRGB());
        }

    }

    public void plotPoint(Point3D p) {
        if (p != null && p.texture() != null) {
            ime.dessine(p, p.texture());
        }
    }

    public void plotPoint(Point3D p, Color c) {
        if (p != null && c != null) {
            ime.dessine(p, c);
        }
    }

    public Image rendu() {
        return null;
    }

    public int resX() {
        return largeur();
    }

    public int resY() {
        return hauteur();
    }

    public Scene scene() {
        return currentScene;
    }

    public void scene(Scene s) {
        this.currentScene = s;
        this.texture(s.texture());

    }

    public void setAngles(double angleXRad, double angleYRad) {
        this.angleX = angleXRad;
        this.angleY = angleYRad;
    }

    @Deprecated
    public void setColoration(boolean a) {
        this.colorationActive = a;
    }

    public void next() {
        if (texture() instanceof TextureMov) {
            ((TextureMov) texture()).nextFrame();
        }
        idImg++;
    }

    @Override
    public void testDeep(Point3D p, Color c) {
        ime.testDeep(p, c);
        ime.testDeep(p, c);
    }

    @Override
    public void testDeep(Point3D p, int c) {
        ime.testDeep(p, c);
        ime.testDeep(p, c);

    }

    public void testDeep(Point3D p) {
        if (p != null && p.texture() != null) {
            ime.testDeep(p, p.texture().getColorAt(0., 0.));
        }
    }

    public void testPoint(Point3D p, Color c) {
        int cc = c.getRGB();
        cc = scene().lumiereActive().getCouleur(c.getRGB(), p, p.getNormale());
        ime.testDeep(p, cc);
        ime.testDeep(p, cc);
    }

    public void tracerLumineux() {
        throw new UnsupportedOperationException("Not supported yet."); // To
        // change
        // body
        // of
        // generated
        // methods,
        // choose
        // Tools
        // |
        // Templates.
    }

    public double mathUtilPow2(Point p1, Point p2) {
        return Math.sqrt(
                ((p1.getX() - p2.getX()) * (p1.getX() - p2.getX())) +
                        ((p1.getY() - p2.getY()) * (p1.getY() - p2.getY()))
        );
    }

    public void tracerTriangle(Point3D pp1, Point3D pp2, Point3D pp3,
                               ITexture t,
                               double u0, double v0, double u1, double v1) {
        if (camera() == null || pp1 == null || pp2 == null || pp3 == null)
            return;
        Point p1 = camera().coordonneesPoint2D(pp1, this);
        Point p2 = camera().coordonneesPoint2D(pp2, this);
        Point p3 = camera().coordonneesPoint2D(pp3, this);
        if (!checkScreen(p1) || !checkScreen(p2) || !checkScreen(p3)) {
            return;
        }
        Point3D[] uvs = new Point3D[]
                {Point3D.n(u0, v0, 0.0), Point3D.n(u1, v0, 0.0), Point3D.n(u1, v1, 0.0),
                        Point3D.n(u0, v1, 0.0)};
        Point3D n = pp1.moins(pp2).prodVect(pp3.moins(pp2)).norme1();
        int col = t.getColorAt(u0, v0);

        double iteres1 = 1.0 / (1 + mathUtilPow2(p1, p2));
        for (double a = 0; a < 1.0; a += iteres1) {
            Point3D p3a = pp1.plus(pp2.moins(pp1).mult(a));
            Point3D uv3a = uvs[0].plus(uvs[1].moins(uvs[0]).mult(a));
            Point pp = camera().coordonneesPoint2D(p3a, this);
            if (pp != null) {
                double iteres2 = 1.0 / (1 + mathUtilPow2(p3, pp));
                Point3D p3ab;
                for (double b = 0; b <= 1.0/*Math.sqrt(p3a.moins(pp3).norme()*p3a.moins(pp3).norme()
                        -pp2.moins(pp1).norme()*pp2.moins(pp1).norme())>=b*/; b += iteres2) {
                    p3ab = p3a.plus(pp3.moins(p3a).mult(b));
                    Point3D uv3ab = uv3a.plus(uvs[2].moins(uv3a).mult(b));
                    // Corriger la méthode.
                    p3ab.setNormale(n);
                    // Point p22 = coordonneesPoint2D(p);
                    if (displayType <= SURFACE_DISPLAY_TEXT_TRI) {
                        //ime.testDeep(p3ab, t.getColorAt(u0 + a * (u1 - u0), v0 + b * (v1 - v0)));
                        ime.testDeep(p3ab, t.getColorAt(uv3ab.getX(), uv3ab.getY()));
                    } else if (displayType >= SURFACE_DISPLAY_COL_TRI)
                        ime.testDeep(p3ab, col);
                    // LINES, POINTS;
                }
            }
        }
    }

    @Override
    public boolean checkScreen(Point p1) {
        if (p1 != null && p1.getX() >= 0d && p1.getY() < la
                && p1.getY() >= 0d && p1.getY() < ha)
            return true;
        return false;

    }

    public void tracerQuad(Point3D pp1, Point3D pp2, Point3D pp3, Point3D pp4, ITexture texture, double u0, double u1,
                           double v0, double v1, ParametricSurface n) {


        Point p1, p2, p3, p4;
        p1 = camera().coordonneesPoint2D(pp1, this);
        p2 = camera().coordonneesPoint2D(pp2, this);
        p3 = camera().coordonneesPoint2D(pp3, this);
        p4 = camera().coordonneesPoint2D(pp4, this);

        int checked = 0;
        if (!checkScreen(p1))
            checked++;
        if (!checkScreen(p2))
            checked++;
        if (!checkScreen(p3))
            checked++;
        if (!checkScreen(p4))
            checked++;
        if (p1 == null || p2 == null || p3 == null || p4 == null || checked > 3)
            return;


        int col = texture.getColorAt(u0, v0);

        TRI triBas = new TRI(pp1, pp2, pp3, texture);
        Point3D normale = triBas.normale();
        double inter = 1 / (maxDistance(p1, p2, p3, p4) + 1) / 3;
        for (double a = 0; a < 1.0; a += inter) {
            Point3D pElevation1 = pp1.plus(pp1.mult(-1d).plus(pp2).mult(a));
            Point3D pElevation2 = pp4.plus(pp4.mult(-1d).plus(pp3).mult(a));
            double inter2 = 1 / (maxDistance(camera().coordonneesPoint2D(pElevation1, this),
                    camera().coordonneesPoint2D(pElevation1, this)) + 1) / 3;
            for (double b = 0; b < 1.0; b += inter2) {
                Point3D pFinal = (pElevation1.plus(pElevation1.mult(-1d).plus(pElevation2).mult(b)));
                pFinal.setNormale(normale);
                pFinal.texture(texture);
                pFinal.setNormale(n.calculerNormale3D(u0 + (u1 - u0) * a, v0 + (v1 - v0) * b));


                if (inter * inter2 >= 4) {
                    double uu1 = u0 + (u1 - u0) * a;
                    double vv1 = v0 + (v1 - v0) * b;
                    double uu2 = u0 + (u1 - u0) * (a+inter);
                    double vv2 = v0 + (v1 - v0) * (b+inter2);
                    Point3D p3d1 = n.calculerPoint3D(uu1, vv1);
                    Point3D p3d2 = n.calculerPoint3D(uu2, vv1);
                    Point3D p3d3 = n.calculerPoint3D(uu2, vv2);
                    Point3D p3d4 = n.calculerPoint3D(uu1, vv2);

                    tracerQuad(p3d1, p3d2, p3d3, p3d4, n.texture(), uu1, uu2, vv1, vv2, n);
                } else {

                    if (n != null) {
                        if (displayType == DISPLAY_ALL) {
                            pFinal = n.calculerPoint3D(u0 + (u1 - u0) * a, v0 + (v1 - v0) * b);
                            ;
                            pFinal.texture(texture);
                        } else {
                            pFinal.setNormale(normale);
                            pFinal.texture(texture);

                        }
                    }
                    if (displayType == SURFACE_DISPLAY_POINTS_LARGE) {
                        double u = u0 + (u1 - u0) * a;
                        double v = v0 + (v1 - v0) * b;
                        ime.testDeep(pFinal, n.texture().getColorAt(u, v));
                    } else if (displayType <= SURFACE_DISPLAY_TEXT_QUADS) {
                        double u = u0 + (u1 - u0) * a;
                        double v = v0 + (v1 - v0) * b;
                        ime.testDeep(pFinal, n.texture(),
                                u, v, n);
                    } else {
                        ime.testDeep(pFinal, col);

                    }
                }
            }
        }

    }

    protected void tracerQuad(Point3D pp1, Point3D pp2, Point3D pp3, Point3D pp4, int colorAt) {


        Point p1, p2, p3, p4;
        p1 = camera().coordonneesPoint2D(pp1, this);
        p2 = camera().coordonneesPoint2D(pp2, this);
        p3 = camera().coordonneesPoint2D(pp3, this);
        p4 = camera().coordonneesPoint2D(pp4, this);

        int checked = 0;
        if (!checkScreen(p1))
            checked++;
        if (!checkScreen(p2))
            checked++;
        if (!checkScreen(p3))
            checked++;
        if (!checkScreen(p4))
            checked++;
        if (p1 == null || p2 == null || p3 == null || p4 == null || checked > 3)
            return;


        int col = colorAt;

        TRI triBas = new TRI(pp1, pp2, pp3, texture);
        Point3D normale = triBas.normale();
        double inter = 1 / (maxDistance(p1, p2, p3, p4) + 1) / 3;
        for (double a = 0; a < 1.0; a += inter) {
            Point3D pElevation1 = pp1.plus(pp1.mult(-1d).plus(pp2).mult(a));
            Point3D pElevation2 = pp4.plus(pp4.mult(-1d).plus(pp3).mult(a));

            double inter2 = 1 / (maxDistance(camera().coordonneesPoint2D(pElevation1, this),
                    camera().coordonneesPoint2D(pElevation1, this)) + 1) / 3;
            for (double b = 0; b < 1.0; b += inter2) {
                Point3D pFinal = (pElevation1.plus(pElevation1.mult(-1d).plus(pElevation2).mult(b)));
                ime.testDeep(pFinal, col);
            }
        }
    }


    public void tracerTriangle(Point3D pp1, Point3D pp2, Point3D pp3, ITexture c) {
        Point p1, p2, p3;
        p1 = camera().coordonneesPoint2D(pp1, this);
        p2 = camera().coordonneesPoint2D(pp2, this);
        p3 = camera().coordonneesPoint2D(pp3, this);
        if (p1 == null || p2 == null || p3 == null) {
            return;
        }

        Point3D n = (pp3.moins(pp1)).prodVect(pp2.moins(pp1)).norme1();

        int checked = 0;
        if (!checkScreen(p1))
            checked++;
        if (!checkScreen(p2))
            checked++;
        if (!checkScreen(p3))
            checked++;
        if (checked > 0)
            return;
        double iteres1 = 1.0 / (maxDistance(p1, p2, p3) + 1) / 3;
        for (double a = 0; a < 1.0; a += iteres1) {
            Point3D p11 = pp1.plus(pp1.mult(-1d).plus(pp2).mult(a));
            double iteres2 = 1.0 / maxDistance(p1, p2, p3) / 3;
            for (double b = 0; b < 1.0; b += iteres2) {
                Point3D p21 = p11.plus(p11.mult(-1d).plus(pp3).mult(b));
                p21.setNormale(n);
                p21.texture(c);
                ime.testDeep(p21);
            }
        }
    }

    public boolean unlock() {
        if (!locked) {
            return false;
        }
        locked = false;
        return true;
    }

    public void zoom(float z) {
        if (z > 0) {
            zoom = z;
        }
    }

    @Override
    public ITexture backgroundTexture() {
        return texture();
    }

    public void couleurDeFond(ITexture couleurFond) {
    }

    public void backgroundTexture(ITexture texture) {
        if (texture != null) {
            texture(texture);
            applyTex();
        }
    }

    public void applyTex() {
        if (texture instanceof TextureMov) {
            for (int i = 0; i < la; i++) {
                for (int j = 0; j < ha; j++) {
                    ime.ime.setElementCouleur(i, j, texture().getColorAt(1.0 * i / la, 1.0 * j / ha));
                }
            }
        }
    }

    public void dessine(Point3D p, ITexture texture) {
        ime.dessine(p, new Color(texture.getColorAt(0.5, 0.5)));
    }

    public Point3D clickAt(double x, double y) {
        return clickAt((int) (x * largeur()), (int) y * hauteur());
    }

    /*__
     *
     * @param x Coordonnees de l'image ds ZBuffer
     * @param y Coordonnees de l'image ds ZBuffer
     * @return Point3D avec texture. Si vide Point3D.INFINI
     */
    public Point3D clickAt(int x, int y) {
        Point3D p = ime.getIME().getElementPoint(x, y);
        p.texture(new TextureCol(ime.getIME().getElementCouleur(x, y)));
        return p;
    }

    /*__
     *
     * @param x Coordonnees de l'image ds ZBuffer
     * @param y Coordonnees de l'image ds ZBuffer
     * @return Point3D avec texture. Si vide Point3D.INFINI
     */
    public Representable representableAt(int x, int y) {
        Representable p = ime.getIME().getElementRepresentable(x, y);
        return p;
    }

    /*__
     *
     * @param p point 3D à inverser dans le repère de la caméra
     * @param camera Caméra null="this.camera()"
     * @return point3d inversion
     *
     * P = M(P-E)
     * MT p' = P-E
     * P = MT p' + E
     */
    public Point3D invert(Point3D p, Camera camera, double returnedDist) {
        p = new Point3D(p);
        p.setX(p.getX() * returnedDist);
        p.setY(p.getY() * returnedDist);
        //p.setX(p.getX()-0.5);
        //p.setY(-p.getY()-0.5);
        p.setZ(returnedDist);
        return camera.getMatrice().tild()
                .mult(p).plus(camera.getEye());
    }

    public int getDisplayType() {
        return displayType;
    }

    public void setDisplayType(int displayType) {
        this.displayType = displayType;
    }

    public int idz() {
        return idImg;
    }

    public void drawElementVolume(Representable representable, ParametricVolume volume) {
        if (representable instanceof ParametricSurface) {
            ParametricSurface ps = (ParametricSurface) representable;
            List<Double[]> doubles = new ArrayList<>();
            itereMaxDist(doubles, ps, 0., 1., 0., 1., volume);


            // Tracer les points
            doubles.forEach(new Consumer<Double[]>() {
                @Override
                public void accept(Double[] doubles) {
                    tracerQuad(
                            ps.calculerPoint3D(doubles[0], doubles[2]),
                            ps.calculerPoint3D(doubles[1], doubles[2]),
                            ps.calculerPoint3D(doubles[1], doubles[3]),
                            ps.calculerPoint3D(doubles[0], doubles[3]),
                            ps.texture(),
                            doubles[0], doubles[1], doubles[2], doubles[3], ps
                    );
                }
            });

        } else if (representable instanceof ParametricCurve) {
            ParametricCurve pc = (ParametricCurve) representable;
            List<Double> doubles = new ArrayList<>();
            itereMaxDist(doubles, pc, 0., 1., volume);


            double start = doubles.get(0);
            double end = doubles.get(1);
            for (int i = 0; i < doubles.size(); i++) {
                line(pc.calculerPoint3D(start), pc.calculerPoint3D(end), pc.texture(), start, end, pc);
                start = end;
                end += doubles.get(i + 1);
            }


        } else if (representable instanceof RepresentableConteneur) {
            ((RepresentableConteneur) representable).getListRepresentable().forEach(new Consumer<Representable>() {
                @Override
                public void accept(Representable representable) {
                    drawElementVolume(representable, volume);
                }
            });
        } else if (representable instanceof Point3D) {
            draw(volume.calculerPoint3D((Point3D) representable));
        } else if (representable instanceof TRI) {
            TRI t = (TRI) representable;
            tracerTriangle(t.getSommet().getElem(0), t.getSommet().getElem(1), t.getSommet().getElem(2), t.texture());
        } else if (representable instanceof Polygon) {
            Polygon t = (Polygon) representable;
            for (int i = 0; i < t.getPoints().getData1d().size(); i++)
                tracerTriangle(t.getPoints().getElem(0), t.getPoints().getElem((i + 1) % t.getPoints().getData1d().size()),
                        t.getIsocentre(), t.texture());
        }

    }

    public void idzpp() {
        idImg++;
        ime = new ImageMap(la, ha);
/*        for(int i=0;i<la; i++)
            for(int j=0;j<ha; j++) {
                Scolor[j * la + i] = texture().getColorAt(1. * i / la, 1. * j / ha);
                ime.ime.setElementPoint(i, j, Point3D.INFINI);
                ime.ime.setDeep(i, j, INFINITY_DEEP);
                ime.ime.setElementID(i, j, idImg());
                ime.ime.setElementCouleur(i, j, Scolor[j*la+i]);
                ime.ime.setElementProf(i, j, INFINITY_DEEP);
                ime = new ImageMap(la, ha);
            }
  */
    }

    public boolean isFORCE_POSITIVE_NORMALS() {
        return FORCE_POSITIVE_NORMALS;
    }

    public void setFORCE_POSITIVE_NORMALS(boolean FORCE_POSITIVE_NORMALS) {
        this.FORCE_POSITIVE_NORMALS = FORCE_POSITIVE_NORMALS;
    }

    public void ratioVerticalAngle() {
        this.angleY = 1.0 * dimy / dimx * angleX;
    }
//
//    public void drawElementVolume(RPv rPv) {
//        Representable representable = rPv.getRepresentable();
//        if (representable instanceof ParametricVolume) {
//            for (double u = 0.0; u <= 1.0; u += ParametricVolume.incrU()) {
//                for (double v = 0.0; v <= 1.0; v += ParametricVolume.incrV()) {
//                    for (double w = 0.0; w <= 1.0; w += ParametricVolume.incrW()) {
//                        Point3D point3D = ((ParametricVolume) representable).calculerPoint3D(P.n(u, v, w));
//                        if (point3D == null) return;
//                        testDeep(point3D);
//                    }
//                }
//            }
//        } else if (representable instanceof ParametricSurface) {
//            ParametricSurface s = (ParametricSurface) representable;
//            for (double u = 0.0; u <= 1.0; u += ParametricSurface.getGlobals().getIncrU()) {
//                for (double v = 0.0; v <= 1.0; v += ParametricSurface.getGlobals().getIncrV()) {
//                    Point3D point3D = ((ParametricSurface) representable).calculerPoint3D(u, v);
//                    if (point3D == null) return;
//                    /*tracerQuad(s.calculerPoint3D(u, v), s.calculerPoint3D(u+s.getIncrU(), v),
//                            s.calculerPoint3D(u+s.getIncrU(), v+s.getIncrV()), s.calculerPoint3D(u, v+s.getIncrV()),
//                              s.texture(), u, u+s.getIncrU(), v, v+s.getIncrV(), s);
//                            );
//                    */
//                    testDeep(point3D);
//                }
//            }
//
//        } else if (representable instanceof ParametricCurve) {
//            for (double u = 0.0; u <= 1.0; u += ParametricCurve.getGlobals().getIncrU()) {
//                Point3D point3D = ((ParametricCurve) representable).calculerPoint3D(u);
//                if (point3D == null) return;
//                testDeep(point3D);
//            }
//        }
//    }

    public class Box2D {

        protected double minx = 1000000;
        protected double miny = 1000000;
        protected double minz = 1000000;
        protected double maxx = -1000000;
        protected double maxy = -1000000;
        protected double maxz = -1000000;
        protected boolean notests = false;

        public Box2D() {
            SceneCadre cadre = currentScene.getCadre();
            if (cadre.isCadre()) {
                for (int i = 0; i < 4; i++) {
                    if (cadre.get(i) != null) {
                        test(cadre.get(i));
                    }
                }
                /*
                 * if (!cadre.isExterieur()) { notests = true; }
                 */
            }

            if (!notests) {
                Iterator<Representable> it = currentScene.iterator();
                while (it.hasNext()) {
                    Representable r = it.next();
                    // GENERATORS
                    if (r instanceof TRIGenerable) {
                        r = ((TRIGenerable) r).generate();
                    } else if (r instanceof PGenerator) {
                        r = ((PGenerator) r).generatePO();
                    } else if (r instanceof TRIConteneur) {
                        r = ((TRIConteneur) r).getObj();
                    }
                    // OBJETS
                    if (r instanceof TRIObject) {
                        TRIObject o = (TRIObject) r;
                        Iterator<TRI> ts = o.iterator();
                        while (ts.hasNext()) {
                            TRI t = ts.next();
                            for (Point3D p : t.getSommet().getData1d()) {
                                test(p);
                            }
                        }
                    } else if (r instanceof Point3D) {
                        Point3D p = (Point3D) r;
                        test(p);
                    } else if (r instanceof LineSegment) {
                        LineSegment p = (LineSegment) r;
                        test(p.getOrigine());
                        test(p.getExtremite());
                    } else if (r instanceof TRI) {
                        TRI t = (TRI) r;
                        test(t.getSommet().getElem(0));
                        test(t.getSommet().getElem(1));
                        test(t.getSommet().getElem(2));
                    } /*else if (r instanceof BSpline) {
                        BSpline b = (BSpline) r;
                        Iterator<Point3D> ts = b.iterator();
                        while (ts.hasNext()) {
                            Point3D p = ts.next();
                            test(p);
                        }
                    }(*/ else if (r instanceof BezierCubique) {
                        BezierCubique b = (BezierCubique) r;
                        Iterator<Point3D> ts = b.iterator();
                        while (ts.hasNext()) {
                            Point3D p = ts.next();
                            test(p);
                        }
                    } else if (r instanceof BezierCubique2D) {
                        BezierCubique2D b = (BezierCubique2D) r;
                        for (int i = 0; i < 4; i++) {
                            for (int j = 0; j < 4; j++) {
                                Point3D p = b.getControle(i, j);
                                test(p);
                            }
                        }
                    } else if (r instanceof POConteneur) {
                        for (Point3D p : ((POConteneur) r).iterable()) {
                            test(p);
                        }

                    } else if (r instanceof PObjet) {
                        for (Point3D p : ((PObjet) r).iterable()) {
                            test(p);
                        }

                    } else if (r instanceof RepresentableConteneur) {
                        throw new UnsupportedOperationException("Conteneur non supporté");
                    }
                }
            }
            // Adapter en fonction du ratio largeur/hauteur
            double ratioEcran = 1.0 * la / ha;
            double ratioBox = (maxx - minx) / (maxy - miny);
            double minx2 = minx, miny2 = miny, maxx2 = maxx, maxy2 = maxy;
            if (ratioEcran > ratioBox) {
                // Ajouter des points en coordArr
                minx2 = minx - (1 / ratioBox * ratioEcran / 2) * (maxx - minx);
                maxx2 = maxx + (1 / ratioBox * ratioEcran / 2) * (maxx - minx);
            } else if (ratioEcran < ratioBox) {
                // Ajouter des points en y
                miny2 = miny - (ratioBox / ratioEcran / 2) * (maxy - miny);
                maxy2 = maxy + (ratioBox / ratioEcran / 2) * (maxy - miny);

            }
            minx = minx2;
            miny = miny2;
            maxx = maxx2;
            maxy = maxy2;

            double ajuste = zoom - 1.0;
            minx2 = minx - ajuste * (maxx - minx);
            miny2 = miny - ajuste * (maxy - miny);
            maxx2 = maxx + ajuste * (maxx - minx);
            maxy2 = maxy + ajuste * (maxy - miny);
            minx = minx2;
            miny = miny2;
            maxx = maxx2;
            maxy = maxy2;

        }

        public boolean checkPoint(Point3D p) {
            return p.getX() > minx & p.getX() < maxx & p.getY() > miny & p.getY() < maxy;
        }

        public double echelleEcran() {
            return (box.getMaxx() - box.getMinx()) / la;
        }

        public double getMaxx() {
            return maxx;
        }

        public void setMaxx(double maxx) {
            this.maxx = maxx;
        }

        public double getMaxy() {
            return maxy;
        }

        public void setMaxy(double maxy) {
            this.maxy = maxy;
        }

        public double getMaxz() {
            return maxz;
        }

        public void setMaxz(double maxz) {
            this.maxz = maxz;
        }

        public double getMinx() {
            return minx;
        }

        public void setMinx(double minx) {
            this.minx = minx;
        }

        public double getMiny() {
            return miny;
        }

        public void setMiny(double miny) {
            this.miny = miny;
        }

        public double getMinz() {
            return minz;
        }

        public void setMinz(double minz) {
            this.minz = minz;
        }

        public Rectangle rectangle() {
            return new Rectangle((int) minx, (int) miny, (int) maxx, (int) maxy);
        }

        protected void test(Point3D p) {
            if (p.getX() < minx) {
                minx = p.getX();
            }
            if (p.getY() < miny) {
                miny = p.getY();
            }
            if (p.getZ() < minz) {
                minz = p.getZ();
            }
            if (p.getX() > maxx) {
                maxx = p.getX();
            }
            if (p.getY() > maxy) {
                maxy = p.getY();
            }
            if (p.getZ() > maxz) {
                maxz = p.getZ();
            }

        }
    }

    public class Box2DPerspective {

        public float d = -10.0f;
        public float w = 10.0f;
        public float h = w * la / ha;

        /*__
         * @param scene
         */
        public Box2DPerspective(Scene scene) {
        }
    }


}
