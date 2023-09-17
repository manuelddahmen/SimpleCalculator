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

import java.awt.*;
import java.util.ArrayList;

public class InterpretePoint3DCouleur implements Interprete {

    private String repertoire;
    private InterpreteConstants c;

    private int pos;

    public InterpretePoint3DCouleur() {
    }

    @Override
    public InterpreteConstants constant() {
        return c;
    }

    @Override
    public int getPosition() {
        return pos;
    }

    public Object interprete(String text, int pos) throws InterpreteException {
        InterpretesBase ib = new InterpretesBase();
        InterpretePoint3D pp;
        InterpreteCouleur pc;
        Point3D p = null;

        ArrayList<Integer> pattern = new ArrayList<Integer>();
        pattern.add(ib.BLANK);
        pattern.add(ib.LEFTPARENTHESIS);
        pattern.add(ib.BLANK);

        ib.compile(pattern);
        ArrayList<Object> os = ib.read(text, pos);

        pos = ib.getPosition();

        pp = new InterpretePoint3D();
        p = (Point3D) pp.interprete(text, pos);

        pos = pp.getPosition();

        pc = new InterpreteCouleur();
        Color cc = (Color) pc.interprete(text, pos);

        pos = pc.getPosition();

        pattern = new ArrayList<Integer>();
        pattern.add(ib.BLANK);
        pattern.add(ib.RIGHTPARENTHESIS);
        pattern.add(ib.BLANK);

        ib = new InterpretesBase();
        ib.compile(pattern);
        os = ib.read(text, pos);

        pos = ib.getPosition();

        p.texture(new TextureCol(cc));

        this.pos = pos;

        return p;
    }

    @Override
    public void setConstant(InterpreteConstants c) {
        this.c = c;
    }

    @Override
    public void setRepertoire(String r) {
        this.repertoire = r;
    }
}
