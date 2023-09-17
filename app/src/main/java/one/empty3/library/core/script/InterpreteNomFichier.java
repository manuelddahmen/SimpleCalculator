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

/*__
 *
 */
package one.empty3.library.core.script;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*__
 * @author MANUEL DAHMEN
 *         <p>
 *         dev
 *         <p>
 *         27 oct. 2011
 */
public class InterpreteNomFichier implements Interprete {

    private int pos;
    private String repertoire;

    /*
     * (non-Javadoc) @see
     * be.ibiiztera.md.pmatrix.pushmatrix.scripts.Interprete#constant()
     */
    @Override
    public InterpreteConstants constant() {
        // TODO Auto-generated method stub
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
            int pos1 = text.indexOf("\"", pos);
            int pos2 = text.indexOf("\"", pos1 + 1);

            this.pos = pos2 + 1;

            return InterpreteIO.getFile(text.substring(pos1 + 1, pos2), repertoire);
            //new File(repertoire+File.separator+text.substring(pos1+1, pos2));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InterpreteNomFichier.class.getName()).log(Level.SEVERE, null, ex);
            throw new InterpreteException(ex);
        }
    }

    /*
     * (non-Javadoc) @see
     * be.ibiiztera.md.pmatrix.pushmatrix.scripts.Interprete#setConstant(be.ibiiztera.md.pmatrix.pushmatrix.scripts.InterpreteConstants)
     */
    @Override
    public void setConstant(InterpreteConstants c) {
        // TODO Auto-generated method stub
    }

    @Override
    public void setRepertoire(String r) {
        this.repertoire = r;
    }
    /*
     * (non-Javadoc) @see
     * be.ibiiztera.md.pmatrix.pushmatrix.scripts.Interprete#interprete(java.lang.String,
     * int)
     */

}
