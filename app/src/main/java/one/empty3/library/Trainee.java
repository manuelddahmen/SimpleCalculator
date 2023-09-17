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

import one.empty3.library.core.tribase.ApproximationFonction1D;

import java.util.ArrayList;

/*__
 * Created by manuel on 29-07-16.
 * <p>
 * Opacités
 * Lumières diffuse
 * Lumière ambiante
 * Diffraction
 * Réffraction
 * Réflexion
 * Ombres
 */
public class Trainee {
    private ArrayList<Point3D> traines;
    private ApproximationFonction1D taillesF;
    private ArrayList<Double> taille;
    /*__
     * Distance Unité : px
     */
    private Double distMax;
    /*__
     * Distance Unité : px
     */
    private Double distMin;
    /*__
     * double 0..1
     */
    private ArrayList<Double> opacites;
    /*__
     * Fonction définie sur [0,taille] à valeur dans [0,1]
     */
    private ApproximationFonction1D opacitesF;

    public Trainee() {

    }

    public ApproximationFonction1D getOpacitesF() {
        return opacitesF;
    }

    public void setOpacitesF(ApproximationFonction1D opacitesF) {
        this.opacitesF = opacitesF;
    }

    public ApproximationFonction1D getTaillesF() {
        return taillesF;
    }

    public void setTaillesF(ApproximationFonction1D taillesF) {
        this.taillesF = taillesF;
    }

    public ArrayList<Double> getOpacites() {
        return opacites;
    }

    public void setOpacites(ArrayList<Double> opacites) {
        this.opacites = opacites;
    }

    public ArrayList<Double> getTaille() {
        return taille;
    }

    public void setTaille(ArrayList<Double> taille) {
        this.taille = taille;
    }

    public ArrayList<Point3D> getTraines() {
        return traines;
    }

    public void setTraines(ArrayList<Point3D> traines) {
        this.traines = traines;
    }

    public Double getDistMax() {
        return distMax;
    }

    public void setDistMax(Double distMax) {
        this.distMax = distMax;
    }

    public Double getDistMin() {
        return distMin;
    }

    public void setDistMin(Double distMin) {
        this.distMin = distMin;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
