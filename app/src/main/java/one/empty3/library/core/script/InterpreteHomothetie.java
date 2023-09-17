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

package one.empty3.library.core.script;

import one.empty3.library.Barycentre;
import one.empty3.library.Matrix33;
import one.empty3.library.Point3D;
import one.empty3.library.Representable;

import java.util.ArrayList;

public class InterpreteHomothetie implements Interprete {

    private String rep;
    private Representable r;
    private int position;

    @Override
    public InterpreteConstants constant() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public Object interprete(String text, int pos) throws InterpreteException {
        Barycentre positionObject = new Barycentre();

        InterpretesBase ib;
        ArrayList<Integer> pattern;

        ib = new InterpretesBase();
        pattern = new ArrayList<Integer>();
        pattern.add(ib.AROBASE);

        ib.compile(pattern);

        ArrayList<Object> o = ib.read(text, pos);

        if (o.size() == 1) {
            InterpretePoint3D ip3 = new InterpretePoint3D();

            Point3D p = (Point3D) ip3.interprete(text, pos);

            positionObject.position = p;
        }

        ib = new InterpretesBase();
        pattern = new ArrayList<Integer>();
        pattern.add(ib.MULTIPLICATION);
        pattern.add(ib.DECIMAL);

        ib.compile(pattern);

        o = ib.read(text, pos);

        if (o.size() == 2) {
            double m = (Double) o.get(1);

            positionObject.agrandissement = m;
        }

        ib = new InterpretesBase();
        pattern = new ArrayList<Integer>();
        pattern.add(ib.PERCENT);

        ib.compile(pattern);

        o = ib.read(text, pos);

        if (o.size() == 1) {
            InterpreteMatrix33 im33 = new InterpreteMatrix33();

            Matrix33 m33 = (Matrix33) im33.interprete(text, pos);

            positionObject.rotation = m33;
        }
        this.position = pos;

        return positionObject;
    }

    @Override
    public void setConstant(InterpreteConstants c) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setRepertoire(String r) {
        this.rep = r;

    }

    public void setRepresentable(Representable r) {
        this.r = r;
    }

}
