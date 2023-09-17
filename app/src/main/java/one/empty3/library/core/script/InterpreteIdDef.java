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

import java.util.ArrayList;

public class InterpreteIdDef implements Interprete {

    private int pos;
    private String repertoire;

    public InterpreteIdDef() {
    }

    public InterpreteConstants constant() {
        return null;
    }

    public int getPosition() {
        return pos;
    }

    public Object interprete(String text, int pos) throws InterpreteException {
        InterpretesBase ib;
        InterpreteIdentifier ii;
        ArrayList<Integer> p;

        ib = new InterpretesBase();
        p = new ArrayList<Integer>();
        p.add(ib.BLANK);
        p.add(ib.LEFTPARENTHESIS);
        p.add(ib.BLANK);
        ib.compile(p);

        ib.read(text, pos);
        pos = ib.getPosition();

        ii = new InterpreteIdentifier();

        String id = (String) ii.interprete(text, pos);
        pos = ii.getPosition();

        ib = new InterpretesBase();
        p = new ArrayList<Integer>();
        p.add(ib.BLANK);
        p.add(ib.RIGHTPARENTHESIS);
        p.add(ib.BLANK);
        ib.compile(p);

        ib.read(text, pos);
        pos = ib.getPosition();

        this.pos = pos;
        return id;
    }

    public void setConstant(InterpreteConstants c) {

    }

    public void setRepertoire(String r) {
        this.repertoire = r;

    }

}
