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

package one.empty3.library.core.extra;

import one.empty3.feature.PixM;
import one.empty3.feature.ProcessInMemory;
import one.empty3.library.*;

import java.util.ArrayList;
import java.util.List;

public class VoronoiImageTexture extends ProcessInMemory {


    private static final double N = 50;

    private Point3D near(Point3D point3D, List<Point3D> p) {
        double dist = 1000000;
        Point3D pRes = null;

        int index2 = 0;

        while (index2 <= p.size() - 1) {
            Point3D p3 = p.get(index2);

            if (Point3D.distance(point3D, p3) < dist && p3 != point3D && !p3.equals(point3D)) {
                dist = Point3D.distance(point3D, p3);
                pRes = p3;


            }
            index2++;
        }
        return pRes;
    }


    public PixM processInMemory(PixM in) {
        PixM out = super.processInMemory(in);
        try {
            List<Point3D> points = new ArrayList<>();
            PixM pixM = in;
            for (int i = 0; i < pixM.getColumns(); i++) {
                for (int j = 0; j < pixM.getLines(); j++) {
                    if (pixM.luminance(i, j) > 0.4 && Math.random()<(1.+N)/out.getColumns()/out.getLines()) {
                        points.add(new Point3D((double) i, (double) j, pixM.luminance(i, j)));
                    }
                }
            }


            for (int i = 0; i < pixM.getColumns(); i++) {
                for (int j = 0; j < pixM.getLines(); j++) {
                    Point3D near = near(new Point3D((double) i, (double) j, 0.0), points);
                    if (near != null) {
                        Point3D p = pixM.getP((int) (double) near.get(0), (int) (double) near.get(1));
                        out.setValues(i, j, p.getX(), p.getY(), p.getZ());
                    } else {
                        //Logger.getAnonymousLogger().log(Level.INFO, "Error near==null");
                    }
                }
            }




            return out;


        } catch (Exception ex) {
            ex.printStackTrace();
            return in;
        }
    }

}
