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

public class Cube extends Representable implements TRIGenerable {
    private StructureMatrix<Double> cote = new StructureMatrix<>(0, Double.class);
    private StructureMatrix<Point3D> position0 = new StructureMatrix<>(0, Point3D.class);
    private TRIObject ts = new TRIObject();
    private static Double[][][] coordCube = new Double[][][]{
            {
                    {1.0, -1.0, -1.0},
                    {1.0, 1.0, -1.0},
                    {1.0, 1.0, 1.0}},
            {
                    {1.0, -1.0, -1.0},
                    {1.0, -1.0, 1.0},
                    {1.0, 1.0, 1.0}},
            {
                    {-1.0, -1.0, -1.0},
                    {-1.0, 1.0, -1.0},
                    {-1.0, 1.0, 1.0}},
            {{-1.0, -1.0, -1.0},
                    {-1.0, -1.0, 1.0},
                    {-1.0, 1.0, 1.0}}, {{-1.0, 1.0, -1.0},
            {1.0, 1.0, -1.0},
            {1.0, 1.0, 1.0}
    }, {{-1.0, 1.0, -1.0},
            {-1.0, 1.0, 1.0},
            {1.0, 1.0, 1.0}}, {{-1.0, -1.0, -1.0},
            {1.0, -1.0, -1.0},
            {1.0, -1.0, 1.0}}, {
            {-1.0, -1.0, -1.0},
            {-1.0, -1.0, 1.0},
            {1.0, -1.0, 1.0}
    }, {{-1.0, -1.0, -1.0},
            {-1.0, 1.0, -1.0},
            {1.0, 1.0, -1.0}
    }, {{-1.0, -1.0, -1.0},
            {1.0, -1.0, -1.0},
            {1.0, 1.0, -1.0}
    }, {{-1.0, -1.0, 1.0},
            {-1.0, 1.0, 1.0},
            {1.0, 1.0, 1.0}
    },
            {{-1.0, -1.0, 1.0},
                    {1.0, -1.0, 1.0},
                    {1.0, 1.0, 1.0}
            }
    };

    public Cube() {

        cote.setElem(1.0);
        position0.setElem(new Point3D(0.0, 0.0, 0.0));
    }

    public Cube(ITexture t) {
        this();
        texture(t);
    }

    public Cube(double cote, Point3D position) {

        this();
        this.cote.setElem(cote);
        this.position0.setElem(position);
    }

    public Cube(double cote, Point3D position, ITexture t) {
        this();
        this.position0.setElem(position);
        this.cote.setElem(cote);
        texture(t);
    }

    @Override
    public TRIObject generate() {
        ts.clear();

        for (int i = 0; i < coordCube.length; i++) {
            Point3D [] coord  = new Point3D[] {new Point3D(coordCube[i][0]), new Point3D(coordCube[i][1]), new Point3D(coordCube[i][2])};

            Point3D [] coord2 = new Point3D[3];

            for (int j = 0; j < 3; j++) {
                coord2[j] = new Point3D(0);
                for (int k = 0; k < 3; k++) {
                    coord2[j] = coord2[j].plus(vectors.getElem(j).mult(coord[j].get(k)));

                }
            }

            TRI t = new TRI(
                    coord2[0].mult(cote.getElem()/2).plus(position0.getElem()),
                    coord2[1].mult(cote.getElem()/2).plus(position0.getElem()),
                    coord2[2].mult(cote.getElem()/2).plus(position0.getElem()),
                    texture());

            ts.add(t);

        }
        return ts;
    }


    public double getMlc() {
        return cote.getElem();
    }

    public void setMlc(double mlc) {
        this.cote.setElem(mlc);
    }

    public StructureMatrix<Point3D> getPosition() {
        return position0;
    }

    public void setPosition(StructureMatrix<Point3D> position) {
        this.position0 = position;
    }

    public Representable place(MODObjet aThis) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static Double[][][] getData() {
        return coordCube;
    }

    public StructureMatrix<Double> getCote() {
        return cote;
    }

    public void setCote(StructureMatrix<Double> cote) {
        this.cote = cote;
    }

    public TRIObject getTs() {
        return ts;
    }

    public void setTs(TRIObject ts) {
        this.ts = ts;
    }

    public static Double[][][] getCoordCube() {
        return coordCube;
    }

    public static void setCoordCube(Double[][][] coordCube) {
        Cube.coordCube = coordCube;
    }


}
