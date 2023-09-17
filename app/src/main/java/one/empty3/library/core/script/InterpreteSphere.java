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

import one.empty3.library.ECBufferedImage;
import one.empty3.library.Point3D;
import one.empty3.library.TextureImg;
import one.empty3.library.core.tribase.TRISphere;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*__
 * @author Manuel DAHMEN
 */
public class InterpreteSphere implements Interprete {

    private String repertoire;

    private int position;


    public InterpreteConstants constant() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public int getPosition() {
        return position;
    }


    public Object interprete(String text, int pos) throws InterpreteException {
        InterpretesBase base = new InterpretesBase();
        InterpretePoint3D point3D = new InterpretePoint3D();
        InterpreteNomFichier nomFichier = new InterpreteNomFichier();
        ArrayList<Integer> pattern = new ArrayList<Integer>();

        pattern.add(base.BLANK);
        pattern.add(base.LEFTPARENTHESIS);
        pattern.add(base.BLANK);
        base.compile(pattern);
        base.read(text, pos);
        pos = base.getPosition();

        Point3D centre = null;
        centre = (Point3D) point3D.interprete(text, pos);
        pos = point3D.getPosition();

        pattern = new ArrayList<Integer>();
        pattern.add(base.BLANK);
        base.compile(pattern);
        base.read(text, pos);
        pos = base.getPosition();

        File file = null;
        file = (File) nomFichier.interprete(text, pos);
        pos = nomFichier.getPosition();

        pattern = new ArrayList<Integer>();
        pattern.add(base.BLANK);
        pattern.add(base.RIGHTPARENTHESIS);
        pattern.add(base.BLANK);
        base.compile(pattern);
        base.read(text, pos);
        pos = base.getPosition();

        this.position = pos;

        TRISphere sphere = new TRISphere(centre, pos);
        try {
            sphere.texture(
                    new TextureImg(new ECBufferedImage(ImageIO.read(file))));

            return sphere;
        } catch (IOException ex) {
            Logger.getLogger(InterpreteSphere.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


    public void setConstant(InterpreteConstants c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public void setRepertoire(String r) {
        this.repertoire = r;
    }

}
