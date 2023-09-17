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
/*__
 *
 */
package one.empty3.library.core.script;

import one.empty3.library.Point3D;
import one.empty3.library.core.extra.SimpleSphere;

import java.awt.*;
import java.util.ArrayList;

/*__
 * @author MANUEL DAHMEN
 *         <p>
 *         dev
 *         <p>
 *         21 oct. 2011
 */
public class InterpreteSimpleSphere implements Interprete {

    private String repertoire;
    private int pos;
    /* (non-Javadoc)
     * @see be.ibiiztera.md.pmatrix.pushmatrix.scripts.Interprete#constant()
     */

    @Override
    public InterpreteConstants constant() {
        // TODO Auto-generated method stub
        return null;
    }
    /* (non-Javadoc)
     * @see be.ibiiztera.md.pmatrix.pushmatrix.scripts.Interprete#getPosition()
     */

    @Override
    public int getPosition() {
        // TODO Auto-generated method stub
        return pos;
    }

    /* (non-Javadoc)
     * @see be.ibiiztera.md.pmatrix.pushmatrix.scripts.Interprete#interprete(java.lang.String, int)
     */
    @Override
    public Object interprete(String text, int pos) throws InterpreteException {
        Point3D c = null;
        double r = 1;
        Color col = Color.black;

        InterpretesBase ib;
        InterpretePoint3DBAK ip;
        InterpreteCouleur pc;
        ArrayList<Integer> patt = null;

        ib = new InterpretesBase();
        patt = new ArrayList<Integer>();
        patt.add(ib.BLANK);
        patt.add(ib.LEFTPARENTHESIS);
        patt.add(ib.BLANK);
        ib.compile(patt);

        ib.read(text, pos);
        pos = ib.getPosition();

        ip = new InterpretePoint3DBAK();
        c = (Point3D) ip.interprete(text, pos);
        pos = ip.getPosition();

        ib = new InterpretesBase();
        patt = new ArrayList<Integer>();
        patt.add(ib.BLANK);
        patt.add(ib.DECIMAL);
        patt.add(ib.BLANK);
        ib.compile(patt);

        ib.read(text, pos);
        pos = ib.getPosition();
        r = (Double) ib.get().get(1);

        pc = new InterpreteCouleur();
        col = (Color) pc.interprete(text, pos);
        pos = pc.getPosition();

        ib = new InterpretesBase();
        patt = new ArrayList<Integer>();
        patt.add(ib.BLANK);
        patt.add(ib.RIGHTPARENTHESIS);
        patt.add(ib.BLANK);
        ib.compile(patt);

        ib.read(text, pos);
        pos = ib.getPosition();

        this.pos = pos;
        return new SimpleSphere(c, r, col);
    }

    /* (non-Javadoc)
     * @see be.ibiiztera.md.pmatrix.pushmatrix.scripts.Interprete#setConstant(be.ibiiztera.md.pmatrix.pushmatrix.scripts.InterpreteConstants)
     */
    @Override
    public void setConstant(InterpreteConstants c) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setRepertoire(String r) {
        this.repertoire = r;
    }

}
