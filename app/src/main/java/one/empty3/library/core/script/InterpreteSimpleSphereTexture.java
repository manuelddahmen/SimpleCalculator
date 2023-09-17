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
import one.empty3.library.core.extra.SimpleSphereAvecTexture;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*__
 * @author MANUEL DAHMEN
 *         <p>
 *         dev
 *         <p>
 *         21 oct. 2011
 */
public class InterpreteSimpleSphereTexture implements Interprete {

    private String repertoire;

    private int pos;
    /*
     * (non-Javadoc) @see
     * be.ibiiztera.md.pmatrix.pushmatrix.scripts.Interprete#interprete(java.lang.String,
     * int)
     */
    /*
     * (non-Javadoc) @see
     * be.ibiiztera.md.pmatrix.pushmatrix.scripts.Interprete#constant()
     */

    @Override
    public InterpreteConstants constant() {
        return null;
    }

    /*
     * (non-Javadoc) @see
     * be.ibiiztera.md.pmatrix.pushmatrix.scripts.Interprete#getPosition()
     */
    @Override
    public int getPosition() {
        return pos;
    }

    @Override
    public Object interprete(String text, int pos) throws InterpreteException {
        try {
            Point3D c;
            double r;
            Color col;

            InterpretesBase ib;
            InterpretePoint3D ip;
            InterpreteCouleur pc;
            ArrayList<Integer> patt;

            ib = new InterpretesBase();
            patt = new ArrayList<Integer>();
            patt.add(ib.BLANK);
            patt.add(ib.LEFTPARENTHESIS);
            patt.add(ib.BLANK);
            ib.compile(patt);
            ib.read(text, pos);
            pos = ib.getPosition();

            ip = new InterpretePoint3D();
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

            InterpreteNomFichier inf = new InterpreteNomFichier();

            inf.setRepertoire(repertoire);

            File f = (File) inf.interprete(text, pos);
            if (f == null) {
                throw new InterpreteException("Fichier non trouvé");
            }
            pos = inf.getPosition();

            ib = new InterpretesBase();
            patt = new ArrayList<Integer>();
            patt.add(ib.BLANK);
            patt.add(ib.RIGHTPARENTHESIS);
            patt.add(ib.BLANK);
            ib.compile(patt);

            ib.read(text, pos);
            pos = ib.getPosition();

            this.pos = pos;
            SimpleSphereAvecTexture s = null;
            try {
                s = new SimpleSphereAvecTexture(c, r, Color.white, ImageIO.read(f));
                s.fichier(f.getName());
            } catch (IOException ex) {
                Logger.getLogger(InterpreteSimpleSphereTexture.class.getName()).log(Level.SEVERE, null, ex);
            }
            return s;
        } catch (InterpreteException ex) {
            Logger.getLogger(InterpreteSimpleSphereTexture.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /*
     * (non-Javadoc) @see
     * be.ibiiztera.md.pmatrix.pushmatrix.scripts.Interprete#setConstant(be.ibiiztera.md.pmatrix.pushmatrix.scripts.InterpreteConstants)
     */
    @Override
    public void setConstant(InterpreteConstants c) {
    }

    @Override
    public void setRepertoire(String r) {
        this.repertoire = r;
    }

}
