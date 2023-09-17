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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package one.empty3.library;

import java.util.*;

/*__
 * @author Atelier
 */
public class RepresentableConteneur extends Representable implements IMovable, IScalable {

    private StructureMatrix<Representable> re = new StructureMatrix(1, Representable.class);

    public RepresentableConteneur() {
    }


    public synchronized void add(Representable r) {
        re.add(1, r);
        r.setVectX(this.getVectX());
        r.setVectY(this.getVectX());
        r.setVectZ(this.getVectX());
    }

    public synchronized void clear() {
        re.getData1d().clear();
    }


    public Iterator<Representable> iterator() {
        return re.getData1d().iterator();
    }

    public synchronized void remove(Representable r2) {
        re.getData1d().remove(r2);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("conteneur (\n\n");

        Iterator<Representable> rs = iterator();

        while (rs.hasNext()) {
            Representable next = rs.next();

            sb.append(next.toString());
        }

        sb.append("\n\n)\n\n");

        return sb.toString();
    }

    @Override
    public void moveAdd(Point3D add) {
        Iterator<Representable> rs = iterator();

        while (rs.hasNext()) {
            Representable next = rs.next();

            if (next instanceof IMovable) {
                ((IMovable) next).moveAdd(add);
            }
        }

    }

    @Override
    public void moveTo(Point3D to) {

        Iterator<Representable> rs = iterator();

        while (rs.hasNext()) {
            Representable next = rs.next();
            if (next instanceof IMovable) {
                ((IMovable) next).moveTo(to);
            }

        }
    }

    @Override
    public void scale(Point3D center, double scale) {

        Iterator<Representable> rs = iterator();

        while (rs.hasNext()) {
            Representable next = rs.next();

            if (next instanceof IScalable) {
                ((IScalable) next).scale(center, scale);
            }
        }
    }

    @Override
    public void scale(double scale) {

        Iterator<Representable> rs = iterator();

        while (rs.hasNext()) {
            Representable next = rs.next();

            if (next instanceof IScalable) {
                ((IScalable) next).scale(scale);
            }
        }
    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("listRepresentable/representables list", re);
        getDeclaredDataStructure().put("re/representables list", re);
    }

    public List<Representable> getListRepresentable() {
        return re.getData1d();
    }

    public StructureMatrix<Representable> getRe() {
        return re;
    }

    public void setRe(StructureMatrix<Representable> re) {
        this.re = re;
    }
}
