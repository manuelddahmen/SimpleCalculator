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

import java.awt.*;

public interface IPoint {
    Color getColor();

    void setColor(Color color);

    Point3D getPosition();

    void setPosition(Point3D position);

    Point3D getVitesse();

    void setVitesse(Point3D vitesse);

    double getAttraction();

    void setAttraction(double attraction);

    double getRepulsion();

    void setRepulsion(double repulsion);

    double getMasse();

    void setMasse(double masse);

    double getAmortissement();

    void setAmortissement(double amortissement);

}