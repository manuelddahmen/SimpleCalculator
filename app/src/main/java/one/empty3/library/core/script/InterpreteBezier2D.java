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
package one.empty3.library.core.script;

import one.empty3.library.*;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InterpreteBezier2D implements Interprete {

    private String repertoire;
    private int pos;

    @Override
    public InterpreteConstants constant() {
        return null;
    }

    @Override
    public int getPosition() {
        return pos;
    }

    @Override
    public Object interprete(String text, int pos) throws InterpreteException {

        Point3D[][] points = new Point3D[4][4];

        ArrayList<Integer> pattern = null;
        InterpretesBase ib = null;
        ib = new InterpretesBase();
        pattern = new ArrayList<Integer>();
        pattern.add(ib.BLANK);
        pattern.add(ib.LEFTPARENTHESIS);
        pattern.add(ib.BLANK);
        ib.compile(pattern);
        ib.read(text, pos);

        pos = ib.getPosition();
        ITexture c = null;
        try {
            InterpreteTColor pc = new InterpreteTColor();
            pc.setRepertoire(repertoire);
            c = (ITexture) pc.interprete(text, pos);
            pos = pc.getPosition();
        } catch (InterpreteException ex) {
            Logger.getLogger(InterpreteNomFichier.class.getName()).log(Level.SEVERE, null, ex);
        }
        ib = new InterpretesBase();
        pattern = new ArrayList<Integer>();
        pattern.add(ib.BLANK);
        pattern.add(ib.LEFTPARENTHESIS);
        pattern.add(ib.BLANK);
        ib.compile(pattern);
        ib.read(text, pos);
        pos = ib.getPosition();

        int i = 0, j = 0, k = 0;

        System.err.println("B2D POINT LIST 0");
        while (k < 16) {
            i = k % 4;
            j = k / 4;

            InterpretePoint3D ip = new InterpretePoint3D();
            points[i][j] = (Point3D) ip.interprete(text, pos);
            pos = ip.getPosition();

            System.err.println("B2D POINT LIST " + k);

            k++;
        }

        ib = new InterpretesBase();
        pattern = new ArrayList<Integer>();
        pattern.add(ib.BLANK);
        pattern.add(ib.RIGHTPARENTHESIS);
        pattern.add(ib.BLANK);
        ib.compile(pattern);
        ib.read(text, pos);

        System.err.println("B2D END");

        pos = ib.getPosition();

        this.pos = pos;
        BezierCubique2D bc2d = new BezierCubique2D(points);
        bc2d.texture(c);

        return bc2d;
    }

    @Override
    public void setConstant(InterpreteConstants c) {

    }

    @Override
    public void setRepertoire(String r) {
        this.repertoire = r;
    }
}
