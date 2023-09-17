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

package one.empty3.library.core.physics;

import one.empty3.library.*;

import java.util.List;

/*__
 * Created by manuel on 07-06-18.
 */
public interface IntForce {
    void configurer(Bille[] courant);

    Point3D centreMasse();

    Point3D attractionRepulsion(Bille other, Bille p);

    Point3D frottement(Bille p);

    Point3D force(int ind);

    Point3D acc(int ind);

    Point3D vitesse(int ind);

    Point3D position(int ind);

    void calculer();

    double getDistMax();

    void setDistMax(double distMax);

    double getDistMin();

    void setDistMin(double distMin);

    List<Bille> getCourant();

    void setCourant(Bille[] courant);

    List<Bille> getNext();

    void setNext(List<Bille> next);

    double getDt();

    void setDt(double dt);

    double getG();

    void setG(double g);

    double getDistMinFusion();

    void setDistMinFusion(double distMinFusion);

    boolean isFusion();

    void setFusion(boolean fusion);
}
