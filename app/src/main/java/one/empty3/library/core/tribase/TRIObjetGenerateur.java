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
package one.empty3.library.core.tribase;

import one.empty3.library.Point3D;
import one.empty3.library.TRI;

/*__
 * Implémentations requises: TRIGenerable, TourDeRevolution, Tubulaire, Spheres
 *
 * @author manuel
 */
public interface TRIObjetGenerateur {

    Point3D coordPoint3D(int x, int y);

    boolean getCirculaireX();

    void setCirculaireX(boolean cx);

    boolean getCirculaireY();

    void setCirculaireY(boolean cy);

    int getMaxX();

    void setMaxX(int maxX);

    int getMaxY();

    void setMaxY(int maxX);

    Point3D getPoint3D(TRI[] tris, int numX, int numY, double ratioX, double ratioY);


    void getTris(int numX, int numY, TRI[] tris);

}
