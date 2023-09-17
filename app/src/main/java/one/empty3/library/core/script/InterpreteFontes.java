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

import one.empty3.library.BezierCubique;
import one.empty3.library.LineSegment;
import one.empty3.library.Point3D;
import one.empty3.library.Representable;

import java.util.ArrayList;
import java.util.Hashtable;

/*__
 * @author MANUEL DAHMEN
 *         <p>
 *         dev
 *         <p>
 *         15 oct. 2011
 */
public class InterpreteFontes implements Interprete {

    private String repertoire;
    private int pos;
    /*
     * (non-Javadoc)
     * 
     * @see be.ibiiztera.md.pmatrix.pushmatrix.scripts.Interprete#constant()
     */

    @Override
    public InterpreteConstants constant() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see be.ibiiztera.md.pmatrix.pushmatrix.scripts.Interprete#getPosition()
     */
    @Override
    public int getPosition() {
        // TODO Auto-generated method stub
        return pos;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * be.ibiiztera.md.pmatrix.pushmatrix.scripts.Interprete#interprete(java
     * .lang.String, int)
     */
    @Override
    public Object interprete(String text, int pos) throws InterpreteException {
        Hashtable<Character, ArrayList<Representable>> fonte = new Hashtable<Character, ArrayList<Representable>>();

        ArrayList<Representable> objets;

        Character c;
        LineSegment s;
        BezierCubique bc;
        Point3D p;
        boolean eof1 = false;
        while (eof1) {

            eof1 = true;

            objets = new ArrayList<Representable>();

            InterpreteSegment is = new InterpreteSegment();
            InterpreteBezier ib = new InterpreteBezier();
            InterpretePoint3DBAK ip = new InterpretePoint3DBAK();

            InterpretesBase isb;
            ArrayList<Integer> pattern;
            isb = new InterpretesBase();
            pattern = new ArrayList<Integer>();
            pattern.add(isb.BLANK);
            pattern.add(isb.CARACTERE);
            pattern.add(isb.BLANK);
            pattern.add(isb.LEFTPARENTHESIS);
            pattern.add(isb.BLANK);
            isb.compile(pattern);

            ArrayList<Object> o = isb.read(text, pos);
            pos = isb.getPosition();

            if (o.size() != 5) {
                break;
            }
            c = (Character) o.get(1);

            if (eof1) {
                try {
                    s = (LineSegment) is.interprete(text, pos);
                    pos = is.getPosition();
                    objets.add(s);
                    eof1 = false;
                    continue;
                } catch (InterpreteException e) {
                }
            }
            if (eof1) {
                try {
                    bc = (BezierCubique) ib.interprete(text, pos);
                    pos = ib.getPosition();
                    objets.add(bc);
                    eof1 = false;
                    continue;
                } catch (InterpreteException e) {

                }
            }
            if (eof1) {
                try {
                    p = (Point3D) ip.interprete(text, pos);
                    pos = ip.getPosition();
                    objets.add(p);
                    eof1 = false;
                    continue;
                } catch (InterpreteException e) {

                }
            }

            isb = new InterpretesBase();
            pattern = new ArrayList<Integer>();
            pattern.add(isb.BLANK);
            pattern.add(isb.RIGHTPARENTHESIS);
            pattern.add(isb.BLANK);
            isb.compile(pattern);
            c = (Character) isb.read(text, pos).get(1);
            pos = isb.getPosition();

            fonte.put(c, objets);
        }

        return fonte;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * be.ibiiztera.md.pmatrix.pushmatrix.scripts.Interprete#setConstant(be.
     * ibiiztera.md.pmatrix.pushmatrix.scripts.InterpreteConstants)
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
