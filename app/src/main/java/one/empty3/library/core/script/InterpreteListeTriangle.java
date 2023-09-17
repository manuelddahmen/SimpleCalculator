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

import one.empty3.library.TRI;
import one.empty3.library.TRIObject;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InterpreteListeTriangle implements Interprete {

    private String repertoire;

    private int pos = 0;

    private int numFacettes;

    @Override
    public InterpreteConstants constant() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getPosition() {
        return pos;
    }

    @Override
    public Object interprete(String text, int pos) throws InterpreteException {
        TRIObject fo = new TRIObject();
        InterpretesBase ib;
        ArrayList<Integer> pattern;
        ib = new InterpretesBase();
        pattern = new ArrayList<Integer>();
        pattern.add(ib.BLANK);
        pattern.add(ib.LEFTPARENTHESIS);
        pattern.add(ib.BLANK);
        ib.compile(pattern);
        ib.read(text, pos);
        pos = ib.getPosition();

        boolean ok = true;
        while (ok) {
            InterpreteTriangle ifacette = new InterpreteTriangle();
            try {
                fo.add((TRI) ifacette.interprete(text, pos));
                if (ifacette.getPosition() > pos) {
                    pos = ifacette.getPosition();
                    numFacettes++;
                }
            } catch (Exception ex) {
                ok = false;
            }
            try {
                InterpretesBase ib1 = new InterpretesBase();
                ArrayList<Integer> pattern1 = new ArrayList<Integer>();
                pattern1.add(ib.COMA);
                ib1.compile(pattern1);
                ib1.read(text, pos);
                pos = ib1.getPosition();
            } catch (InterpreteException ie) {
            }
        }
        Logger.getAnonymousLogger().log(Level.INFO,""+ numFacettes + "" + text.substring(pos));
        pattern = new ArrayList<Integer>();
        pattern.add(ib.BLANK);
        pattern.add(ib.RIGHTPARENTHESIS);
        pattern.add(ib.BLANK);
        ib.compile(pattern);
        ib.read(text, pos);
        this.pos = ib.getPosition();
        return fo;
    }

    @Override
    public void setConstant(InterpreteConstants c) {
        // TODO Auto-generated method stub
    }

    @Override
    public void setRepertoire(String r) {
        this.repertoire = r;
    }

}
