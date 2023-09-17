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

import javaAnd.awt.Point;

public class ReyesLikeList {
    int size = 12;
    int idx = 0;
    private double[][] listsPolygons
            = new double[100000][12];

    public boolean add(Polygon polygon, Point point, Point3D texture,
                       Point3D normale) {
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 3; i++) {
                listsPolygons[idx][j * 3 + i] = polygon.getPoints().getElem(j).get(i);
            }
        }
        listsPolygons[idx][4 * 3 + 0] = point.getX();
        listsPolygons[idx][4 * 3 + 1] = point.getY();

        for (int i = 0; i < 3; i++) {
            listsPolygons[idx][4 * 3 + 2 + i] = texture.get(i);
        }
        for (int i = 0; i < 3; i++) {
            listsPolygons[idx][4 * 3 + 2 + 3 + i] = normale.get(i);
        }
        idx++;
        return true;
    }

    public void render(ECBufferedImage image) {
        for(int i=0; i<idx; i++) {
            image.setRGB((int) listsPolygons[idx][12],
                    (int) listsPolygons[idx][13],
                    Lumiere.getInt(new double[]{listsPolygons[idx][14],
                            listsPolygons[idx][15],
                            listsPolygons[idx][16]}));
        }
    }
    public static class MicroPolygon extends Polygon {

    }

}
