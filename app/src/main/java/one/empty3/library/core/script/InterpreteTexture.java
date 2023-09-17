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

import one.empty3.library.ECBufferedImage;
import one.empty3.library.ITexture;
import one.empty3.library.TextureCol;
import one.empty3.library.TextureImg;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class InterpreteTexture implements Interprete {

    private String rep;
    private int position;

    public InterpreteConstants constant() {
        return null;
    }

    public int getPosition() {
        return position;
    }

    public String getRep() {
        return rep;
    }

    public void setRep(String rep) {
        this.rep = rep;
    }

    public Object interprete(String text, int pos) throws InterpreteException {
        ITexture tc = null;

        boolean pass = false;
        try {
            InterpreteCouleur ic = new InterpreteCouleur();
            Color c = (Color) ic.interprete(text, pos);
            pos = ic.getPosition();
            pass = true;

            tc = new TextureCol(c);

        } catch (InterpreteException ex) {
        }
        if (!pass) {
            try {
                InterpreteNomFichier inf = new InterpreteNomFichier();
                inf.interprete(text, pos);
                pos = inf.getPosition();
                File f = (File) inf.interprete(text, pos);
                pos = inf.getPosition();
                pass = true;

                try {
                    tc = new TextureImg(new ECBufferedImage(ImageIO.read(f)));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } catch (InterpreteException ex) {
            }
        }

        this.position = pos;

        return tc;
    }

    public void setConstant(InterpreteConstants c) {

    }

    public void setRepertoire(String r) {
        this.setRep(r);

    }

}
