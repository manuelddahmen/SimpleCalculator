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


public class Bille extends Point3D {
    public Color color;
    public String nom;
    public Point3D position;
    public Point3D vitesse = new Point3D(0d, 0d, 0d);
    public double attraction;
    public double repulsion;
    public double masse = 1;
    public double amortissement = 1;


    public Bille(Color color, int statut, String nom, Point3D position,
                 Point3D vitesse, double attraction, double repulsion, double masse,
                 double amortissement) {
        super();
        this.color = color;
        this.nom = nom;
        this.position = position;
        this.vitesse = vitesse;
        this.attraction = attraction;
        this.repulsion = repulsion;
        this.masse = masse;
        this.amortissement = amortissement;
    }

    public Bille(Bille b) {
        super();
        this.color = b.color;
        this.nom = b.nom;
        this.position = b.position;
        this.vitesse = b.vitesse;
        this.attraction = b.attraction;
        this.repulsion = b.repulsion;
        this.masse = b.masse;
        this.amortissement = b.amortissement;
    }

    public Bille() {
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Point3D getPosition() {
        return position;
    }

    public void setPosition(Point3D position) {
        this.position = position;
    }

    public Point3D getVitesse() {
        return vitesse;
    }

    public void setVitesse(Point3D vitesse) {
        this.vitesse = vitesse;
    }

    public double getAttraction() {
        return attraction;
    }

    public void setAttraction(double attraction) {
        this.attraction = attraction;
    }

    public double getRepulsion() {
        return repulsion;
    }

    public void setRepulsion(double repulsion) {
        this.repulsion = repulsion;
    }

    public double getMasse() {
        return masse;
    }

    public void setMasse(double masse) {
        this.masse = masse;
    }

    public double getAmortissement() {
        return amortissement;
    }

    public void setAmortissement(double amortissement) {
        this.amortissement = amortissement;
    }

}
