/*
 * Copyright (c) 2024. Manuel Daniel Dahmen
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
 * 2013-2020 Manuel Dahmen
 */
package one.empty3.library;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javaAnd.awt.Color;
import one.empty3.library.core.TemporalComputedObject3D;
import one.empty3.library.core.lighting.Colors;
import one.empty3.library.core.raytracer.RtIntersectInfo;
import one.empty3.library.core.raytracer.RtMatiere;
import one.empty3.library.core.raytracer.RtRay;
import one.empty3.library.core.testing.Path;
public class Representable /*extends RepresentableT*/ implements Serializable, Comparable, XmlRepresentable, MatrixPropertiesObject, TemporalComputedObject3D
,Serialisable{

    protected StructureMatrix<Point3D> vectors;

    public static final int DISPLAY_ALL = 0;
    public static final int SURFACE_DISPLAY_TEXT_QUADS = 1;
    public static final int SURFACE_DISPLAY_TEXT_TRI = 2;
    public static final int SURFACE_DISPLAY_COL_QUADS = 3;
    public static final int SURFACE_DISPLAY_COL_TRI = 4;
    public static final int SURFACE_DISPLAY_LINES = 5;
    public static final int SURFACE_DISPLAY_POINTS = 6;
    public static final ITexture DEFAULT_TEXTURE = new TextureCol(Color.WHITE);
    private static final String[] displayTypes = {"All", "Textured Quad", "SURFACE_DISPLAY_TEXT_TRI", "SURFACE_DISPLAY_COL_QUADS", "SURFACE_DISPLAY_COL_TRI", "SURFACE_DISPLAY_LINES", "SURFACE_DISPLAY_POINTS"};
    public static Point3D SCALE1;
    protected static ArrayList<Painter> classPainters = new ArrayList<Painter>();
    protected static HashMap<String, StructureMatrix> defaultHashMapData = new HashMap<String, StructureMatrix>();
    public static final int PATH_ELEM_STRUCTURE_MATRIX = 1;
    public static int PATH_ELEM_DOUBLE_VALUES = 2;
    public static int PATH_ELEM_REPRESENTABLE = 4;
    public StructureMatrix<Rotation> rotation = new StructureMatrix<>(0, Rotation.class);
    protected double NFAST = 100;
    protected RtMatiere materiau;
    protected ITexture CFAST = DEFAULT_TEXTURE;
    // protected Barycentre bc = new Barycentre();
    protected Representable parent;
    protected Scene scene;
    protected ITexture texture = DEFAULT_TEXTURE;
    protected Render render; //= Render.getInstance(0, -1);
    protected StructureMatrix<T> T; // = new StructureMatrix<T>(0, one.empty3.library.T.class);
    private int displayType = SURFACE_DISPLAY_TEXT_QUADS;
    private String id;
    private Painter painter = null;
    private int RENDERING_DEFAULT = 0;
    private Map<String, StructureMatrix> declaredDataStructure;// = Collections.synchronizedMap(new HashMap());
    private Map<String, StructureMatrix> declaredLists;//= new HashMap<>();
//    protected Paint paint = new Paint();

    public Representable() {
        if (!(this instanceof Matrix33 || this instanceof Point3D || this instanceof Camera)) {
            rotation.setElem(new Rotation());
            //scale = new Point3D(1d, 1d, 1d);
            //texture = new TextureCol(Colors.random());
            vectors = new StructureMatrix<>(1, Point3D.class);
            vectors.setElem(Point3D.X, 0);
            vectors.setElem(Point3D.Y, 1);
            vectors.setElem(Point3D.Z, 2);
            vectors.setElem(Point3D.O0, 3);

        }
    }

    public Point3D getOrientedPoint(Point3D a) {
        Point3D oriented = vectors.getElem(3)
                .plus(vectors.getElem(0).mult(a.get(0)))
                .plus(vectors.getElem(1).mult(a.get(1)))
                .plus(vectors.getElem(2).mult(a.get(2)));
        oriented.texture(a.texture());
        return oriented;
    }
    public static void setPaintingActForClass(ZBuffer z, Scene s, PaintingAct pa) {
        Painter p = null;
        classPainters().add(new Painter(z, s, Representable.class));
        p.addAction(pa);
    }

    private static ArrayList<Painter> classPainters() {
        return classPainters;
    }

    public static String[] getDisplayTypes() {
        return displayTypes;
    }

    public StructureMatrix<Rotation> getRotation() {
        return rotation;
    }

    public void setRotation(StructureMatrix<Rotation> rotation) {
        this.rotation = rotation;
    }

    public Point3D rotate(Point3D p0, Representable ref) {
        if (p0 == null) {
            return Point3D.O0;
        } else if (ref != null && ref.getRotation() != null && ref.getRotation().getElem() != null)
            return ref.getRotation().getElem().rotation(p0);
        else
            return p0;
    }

    public String id() {
        return id;
    }
/*
    public void scene(Scene scene) {
        this.scene = scene;

    }
*/

    public void id(String id) {
        this.id = id;
    }

    public void informer(Representable parent) {
        this.parent = parent;
    }

    public void replace(String moo) {
        throw new UnsupportedOperationException("Operation non supportee");
    }

    public boolean supporteTexture() {
        return false;
    }

    public ITexture texture() {
        return this.texture;
    }

    public void texture(ITexture tc) {
        this.texture = tc;
    }


    public Point3D refPoint(Point3D x) {
        if(!(this instanceof Point3D) && !(this instanceof Matrix33))
            return getOrientedPoint(x);
        else
            return x;
    }

    public void setAxes(Point3D o, Point3D vx, Point3D vy, Point3D vz) {
        setOrig(o);
        setVectX(vx);
        setVectY(vy);
        setVectZ(vz);
    }
    /*__
     * DOn't call ZBuffer dessiine methods here: it would loop.
     *
     * @param z ZBuffer use plot or dessine(P) or tracerTriangle(TRI, Itexture)
     */
    public void drawStructureDrawFast(ZBuffer z) {
        throw new UnsupportedOperationException("No genral method for drawing objects");
    }

    public boolean ISdrawStructureDrawFastIMPLEMENTED(ZBuffer z) {
        return false;
    }

    /*__
     * When correctly initialized, PaintingAct action method is called while
     * the shape is rendered.
     *
     * @param z  the actual ZBuffer in which the action should occurs
     * @param s  the scene in which the actions can access to other objects properties.
     *           Optional parameter
     * @param pa The "painting act" (term referring to history of arts).
     */
    public void setPaintingAct(ZBuffer z, Scene s, PaintingAct pa) {
        this.painter = new Painter(z, s, this);
        pa.setObjet(this);
        pa.setScene(s);
        pa.setZBuffer(z);
        painter.addAction(pa);
    }

    public Painter getPainter() {
        return painter;
    }

    public void setPainter(Painter painter) {
        this.painter = painter;
    }

    public void paint() {
        if (getPainter() != null) {
            getPainter().getPaintingAct().paint();
        }
    }

    public Intersects.Intersection intersects(RtRay ray, RtIntersectInfo cii) {
        // TODO Implements
        return null;
    }

    public Representable intersects(Representable r2) {
        throw new UnsupportedOperationException("Pas implémenté  en cours" +
                "");
    }

    public void become(Representable r) {
        if (this.getClass().equals(r.getClass())) {
            set(r);
        }
    }

    private void set(Representable r) {
    }

    /*__
     *
     * @param o
     * @return ???
     */
    @Override
    public int compareTo(Object o) {
        if (!(o instanceof Representable))
            return 0;
        else
            return 1;

    }

    public void draw(ZBufferImpl zBuffer) {
    }

    @Override
    public StructureMatrix getDeclaredProperty(String name) {
        declareProperties();
        for (String s : getDeclaredDataStructure().keySet()) {
            if(s.startsWith(name)) {
                return getDeclaredDataStructure().get(s);
            }
        }
        return null;
    }

    public Map<String, StructureMatrix> getDeclaredDataStructure() {
        if ((!(this instanceof Point3D)) && (declaredDataStructure == null))
            declaredDataStructure = Collections.synchronizedMap(new HashMap());
        else declaredDataStructure = defaultHashMapData;

        return declaredDataStructure;
    }

    public Map<String, StructureMatrix> getDeclaredLists() {
        return declaredLists;
    }

    public ITexture getTexture() {
        return texture;
    }

    public void setTexture(ITexture texture) {
        this.texture = texture;
    }

    public Class getPropertyType(String propertyName) throws NoSuchMethodException {
        Method propertyGetter = null;
        propertyGetter = this.getClass().getMethod("get" + ("" + propertyName.charAt(0)).toUpperCase() + (propertyName.length() > 1 ? propertyName.substring(1) : ""));
        return propertyGetter.getReturnType();
    }

    public void setProperty(String propertyName, Object value) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method propertySetter = null;

        propertySetter = this.getClass().getMethod("set" + ("" + propertyName.charAt(0)).toUpperCase() + (propertyName.substring(1)), value==null?null:value.getClass());
        propertySetter.invoke(this, value);
        Logger.getAnonymousLogger().log(Level.INFO, "RType : " + this.getClass().getName() + " Property: " + propertyName + " New Value set " + getProperty(propertyName));
    }

    public Object getProperty(String propertyName) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method propertySetter = null;
        propertySetter = this.getClass().getMethod("get" + ("" + propertyName.charAt(0)).toUpperCase() + propertyName.substring(1));
        return propertySetter.invoke(this);
    }

    public String toString() {
        return "Representable()";
    }

    public void declareProperties() {
        getDeclaredDataStructure().clear();
        if (getRotation() != null
                && getRotation().getElem() != null && !(this instanceof Point3D)) {
            getDeclaredDataStructure().put("rotation/Rotation", rotation);
        }
        getDeclaredDataStructure().put("vectors/vectors", vectors);
    }

    public Map<String, StructureMatrix> declarations() {
        declareProperties();
        Map<String, StructureMatrix> dec = Collections.synchronizedMap(new HashMap<>());
        getDeclaredDataStructure().forEach((s, structureMatrix) -> dec.put(s, structureMatrix));
        return dec;
    }

    public ITexture getCFAST() {
        return CFAST;
    }

    public void setCFAST(ITexture CFAST) {
        this.CFAST = CFAST;
    }

    public void xmlRepresentation(String filesPath, StringBuilder stringBuilder, Double o) {
        stringBuilder.append("<Double class=\"" + o.getClass() + "\">" + o + "</Double>");
    }

    public void xmlRepresentation(String filesPath, StringBuilder stringBuilder, Boolean o) {
        stringBuilder.append("<Boolean class=\"" + o.getClass() + "\">" + o + "</Boolean>");
    }

    public void xmlRepresentation(String filesPath, StringBuilder stringBuilder, Integer o) {
        stringBuilder.append("<Integer class=\"" + o.getClass() + "\">" + o + "</Integer>");
    }

    public void xmlRepresentation(String filesPath, StringBuilder stringBuilder, String o) {
        stringBuilder.append("<String class=\"" + o.getClass() + "\"> <![CDATA[ " + o + " ]]> </String>");
    }

    public void xmlRepresentation(String filesPath, StringBuilder stringBuilder, File o) {
        stringBuilder.append("<String filename=\"" + o.getName() + "\"class=\"" + o.getClass() + "\"> <![CDATA[ " + o.getName() + " ]]> </String>");
        try {
            FileInputStream fileInputStream = new FileInputStream(o);
            File copy = new File(filesPath + "/" + o.getName());
            copy.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(copy);
            int read = -1;
            byte[] bytes = new byte[4096];
            while (read != 0) {

                read = fileInputStream.read(bytes);
                fileOutputStream.write(bytes, 0, read);
            }

        } catch (Exception ex) {}



    }

    public void xmlRepresentation(String filesPath, MatrixPropertiesObject parent, StringBuilder stringBuilder, ArrayList o) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void xmlRepresentation(String filesPath, StringBuilder stringBuilder, Object o) {
        if (o == null) return;
        if (o instanceof StructureMatrix) {
            xmlRepresentation(filesPath, stringBuilder, (StructureMatrix) o);
        } else if (o instanceof Representable) {
            xmlRepresentation(filesPath, stringBuilder, ((Representable) o));
        } else {
            switch (o.getClass().getName()) {
                case "java.lang.Boolean":
                    xmlRepresentation(filesPath, stringBuilder, ((Boolean) o));
                    break;
                case "java.lang.Double":
                    xmlRepresentation(filesPath, stringBuilder, ((Double) o));
                    break;
                case "java.lang.Integer":
                    xmlRepresentation(filesPath, stringBuilder, ((Integer) o));
                    break;
                case "java.lang.String":
                    xmlRepresentation(filesPath, stringBuilder, ((String) o));
                    break;
                case "java.util.ArrayList":
                    //xmlRepresentation(filesPath, (XmlRepresentable)parent, stringBuilder, ((ArrayList) o));
                    break;
                case "java.io.File":
                    xmlRepresentation(filesPath, stringBuilder, ((File) o));
                    break;
            }

        }
    }

    public void xmlRepresentation(String filesPath, StringBuilder stringBuilder, Representable is) {
        if (stringBuilder.toString().length() == 0) {
            stringBuilder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        }
        stringBuilder.append("<Representable class=\"" + is.getClass().getName() + "\">\n");
        is.declareProperties();
        is.declarations().forEach((s, o) -> {
            if (o instanceof StructureMatrix) {
                StructureMatrix b = (StructureMatrix) o;
                xmlRepresentation(filesPath, s, stringBuilder, o);
            }

        });
        stringBuilder.append("</Representable>\n");
    }

    public void xmlRepresentation(String filesPath, MatrixPropertiesObject parent, StringBuilder stringBuilder) {
        xmlRepresentation(filesPath, stringBuilder, (Representable) parent);
    }

    public void xmlRepresentation(String filesPath, String name, StringBuilder stringBuilder, StructureMatrix is) {
        stringBuilder.append("<StructureMatrix name=\"" + name + "\" dim=\"" + is.getDim() + "\" class=\"" + is.getClass().getName() + "\" typeClass=\"" + is.getClassType().getName() + "\">");
        switch (is.getDim()) {
            case 0:
                stringBuilder.append("<Data>");
                stringBuilder.append("<Cell l=\"0\" c=\"0\">");
                xmlRepresentation(filesPath, stringBuilder, is.getElem());
                stringBuilder.append("</Cell>");
                stringBuilder.append("</Data>");
                break;
            case 1:
                stringBuilder.append("<Data>");
                int[] i1 = new int[]{0, 0};
                is.data1d.forEach(new Consumer() {
                    @Override
                    public void accept(Object o) {
                        stringBuilder.append("<Cell l=\"" + i1[0] + "\" c=\"" + i1[1] + "\">");
                        xmlRepresentation(filesPath, stringBuilder, o);
                        stringBuilder.append("</Cell>");
                        i1[1]++;

                    }

                });
                stringBuilder.append("</Data>");
                break;
            case 2:
                stringBuilder.append("<Data>");
                int[] i2 = new int[]{0, 0};
                is.data2d.forEach(new Consumer<List>() {
                    @Override
                    public void accept(List ts) {
                        i2[1] = 0;
                        ts.forEach(new Consumer() {
                            @Override
                            public void accept(Object o) {
                                stringBuilder.append("<Cell l=\"" + i2[0] + "\" c=\"" + i2[1] + "\">");
                                xmlRepresentation(filesPath, stringBuilder, o);
                                i2[1]++;
                                stringBuilder.append("</Cell>");
                            }
                        });

                        i2[0]++;
                    }
                });
                stringBuilder.append("</Data>");
                break;


        }
        stringBuilder.append("</StructureMatrix>");
    }

    /*
        Map<String, Double> data = new HashMap<>();
        public void addData(String key, double number) {
            data.put(key, number);
        }
        public double getData(String key)
        {
            return data.get(key);
        }
    */
    public MatrixPropertiesObject copy() throws CopyRepresentableError, IllegalAccessException, InstantiationException {
        Class<? extends Representable> aClass = this.getClass();
        try {
            Representable representable = aClass.newInstance();
            representable.declareProperties();
            declarations().forEach((s, structureMatrix) -> {
                try {
                    try {
                        representable.setProperty(s.split("/")[0], structureMatrix.copy());
                    } catch (CopyRepresentableError copyRepresentableError) {
                        copyRepresentableError.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    }
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            });
            return representable;
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return null;
    }

    public int getDisplayType() {
        return displayType;
    }

    public void setDisplayType(int displayType) {
        this.displayType = displayType;
    }

    @Override
    public double T(T t) {
        this.T.setElem(t);
        return t.getT();
    }

    @Override
    public Point3D calculerPointT(double t) {
        return null;
    }

    @Override
    public Point3D calculerCurveT(double u, double t) {
        return null;
    }

    @Override
    public Point3D calculerSurfaceT(double u, double v, double t) {
        return null;
    }

    public void setPosition(Point3D calcCposition) {
        getRotation().getElem().getCentreRot().setElem(calcCposition);
    }

    /***
     * Evaluates property of current object or of a subproperty
     * @param property propertyName:arrayIndex1:arrayIndex2,subpropertyName:i2:j2,subpro...
     * @return atomic property
     */
    public Path getPath(String property) {
        String[] split = property.split(",");

        Object o;

        Representable representable1 = this;
        Object value = null;
        StructureMatrix declaredProperty = null;
        int i=-1;
        int j=-1;
        for (int k = 0; k < split.length; k++) {
            String split0 = split[k].split("/")[0];
            if(value!=null) {
                if(value instanceof Representable) {
                    representable1 = ((Representable)value);
                }

            }
            String[] split1 = split0.split(":");
            if(split1.length>1) {
                i = Integer.parseInt(split1[1]);
            }
            if(split1.length>2) {
                j = Integer.parseInt(split1[2]);
            }

            declaredProperty = (StructureMatrix) representable1.getDeclaredProperty(split1[0]);

            if (declaredProperty == null)
                return null;
            else {
                if(declaredProperty instanceof StructureMatrix) {

                    StructureMatrix sm = (StructureMatrix) declaredProperty;
                    switch (sm.getDim()) {
                        case 0:
                            Object data0d = sm.getData0d();
                            value = sm.getElem();
                            break;
                        case 1:
                            List data1d = sm.getData1d();
                            value = sm.getElem(i);
                            break;
                        case 2:
                            List data2d = sm.getData2d();
                            value = sm.getElem(i, j);
                            break;
                    }
                }
            }
        }
        return new Path(declaredProperty, value, property, Representable.PATH_ELEM_STRUCTURE_MATRIX, i, j);


    }

    public Point3D getVectX() {
        return vectors.getElem(0);
    }

    public void setVectX(Point3D vectX) {
        this.vectors.setElem(vectX, 0);
    }

    public Point3D getVectY() {
        return vectors.getElem(1);
    }

    public void setVectY(Point3D vectY) {
        this.vectors.setElem(vectY, 1);
    }

    public Point3D getVectZ() {
        return vectors.getElem(2);
    }

    public void setVectZ(Point3D vectZ) {
        this.vectors.setElem(vectZ, 2);
    }
    public void setOrig(Point3D orig) {
        this.vectors.setElem(orig, 3);
    }

    public void drawOnCanvas(Canvas mCanvas, Bitmap bitmap, int transparent, PointF pointF, PointF scale) {
        //paint = new Paint();
        Scene scene1 = new Scene();

        scene1.add(this);

        StructureMatrix<Point3D> boundingRect = getBoundRect2d();
        double left = boundingRect.getElem(0).get(0);
        double top = boundingRect.getElem(0).get(1);
        double right = boundingRect.getElem(1).get(0);
        double bottom = boundingRect.getElem(1).get(1);
        double width = right - left;
        double height = bottom - top;
        if (left>=0 && top>=0 && width > 0 && height > 0 && width + left < bitmap.getWidth() && height + top < bitmap.getHeight()) {
            //System.out.println("Cordinates in bounds");
            try {

                Bitmap bitmap1 = Bitmap.createBitmap(bitmap, (int) left, (int) top, (int) width, (int) height);


                ZBufferImpl zBuffer = new ZBufferImpl((int) width, (int) height);

                Point3D middle = Point3D.n(left + width / 2., top + height / 2., 0);

                double v = -Math.max(width, height);
                Camera camera = new Camera(Point3D.Z.mult(v)
                        .plus(middle), middle, Point3D.Y.mult(-1));
                camera.declareProperties();

                double angleX = Math.asin((width/2)/Math.sqrt((width/2)*(width/2)+v*v));
                double angleY = Math.asin((height/2)/Math.sqrt((height/2)*(height/2)+v*v));

                camera.angleXY(angleX, angleY);

                zBuffer.idzpp();

                scene1.cameraActive(camera);
                zBuffer.scene(scene1);
                zBuffer.camera(camera);

                zBuffer.setTransparent(transparent);

                zBuffer.texture(new ColorTexture(transparent));


                zBuffer.couleurDeFond(new ColorTexture(transparent));

                //zBuffer.setDisplayType(ZBufferImpl.SURFACE_DISPLAY_TEXT_QUADS);

                zBuffer.draw(scene1);


                //System.err.println("drawOnCanvas" + boundingRect.toString());

                //inBounds = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

                zBuffer.drawOnImage(bitmap, zBuffer.image(),
                        mCanvas, boundingRect, pointF, scale);
            } catch (RuntimeException ex) {
                ex.printStackTrace();
            }
        }
    }

    public StructureMatrix<Point3D> getBoundRect2d() {
        StructureMatrix<Point3D> boundRect2d = new StructureMatrix<>(1, Point3D.class);
        boundRect2d.setElem(new Point3D( 10000d,  10000d, 0d), 0);
        boundRect2d.setElem(new Point3D(-10000d, -10000d, 0d), 1);

        return boundRect2d;
    }


    @Override
    public Serialisable decode(DataInputStream in) {
        return null;
    }

    @Override
    public int encode(DataOutputStream out) {
        return 0;
    }

    @Override
    public int type() {
        return 1;
    }
}


