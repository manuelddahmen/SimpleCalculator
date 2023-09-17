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
import one.empty3.library.ITexture;
import one.empty3.library.TextureCol;
import one.empty3.library.TextureImg;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InterpreteTColor implements Interprete {

    public boolean success;
    private String repertoire;
    private int pos;

    public InterpreteConstants constant() {
        // TODO Auto-generated method stub
        return null;
    }

    public int getPosition() {
        return pos;
    }

    public Object interprete(String text, int pos) throws InterpreteException {

        success = false;

       ITexture tc = null;

        InterpretesBase ib;
        ib = new InterpretesBase();
        ArrayList<Integer> pattern;
        pattern = new ArrayList<Integer>();
        pattern.add(ib.BLANK);
        ib.compile(pattern);
        ib.read(text, pos);

        pos = ib.getPosition();

        try {
            InterpreteCouleur ic = new InterpreteCouleur();

            Color c = (Color) ic.interprete(text, pos);

            tc = new TextureCol(c);

            pos = ic.getPosition();

            success = true;
        } catch (InterpreteException ex) {
            try {
                InterpreteNomFichier inf = new InterpreteNomFichier();

                inf.setRepertoire(repertoire);
                File f = (File) inf.interprete(text, pos);

                ECBufferedImage bi = new ECBufferedImage(ImageIO.read(f));

                tc = new TextureImg(bi);

                pos = inf.getPosition();
                /*
                 InterpretePGM iPGM = new InterpretePGM();
				
                 ECBufferedImage ec = (ECBufferedImage) iPGM.interprete(text, pos);
				
                 tc = new TextureCol(ec);
				
                 this.pos = inf.getPosition();
                 */
                success = true;
            } catch (FileNotFoundException ex2) {
                Logger.getLogger(InterpreteTColor.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("File not found");

            } catch (IOException ex1) {
                Logger.getLogger(InterpreteTColor.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("IO error");
            } catch (InterpreteException ex3) {
                Logger.getLogger(InterpreteTColor.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("Interprete Error");
            } catch (Exception ex4) {
                Logger.getLogger(InterpreteTColor.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("Error");
            }
        }

        ib = new InterpretesBase();
        pattern = new ArrayList<Integer>();
        pattern.add(ib.BLANK);
        ib.compile(pattern);
        ib.read(text, pos);

        pos = ib.getPosition();

        this.pos = pos;

        return tc;

    }

    public void setConstant(InterpreteConstants c) {
        // TODO Auto-generated method stub

    }

    public void setRepertoire(String r) {
        this.repertoire = r;
    }

}
