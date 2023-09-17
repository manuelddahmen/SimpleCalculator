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
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package one.empty3.library;

//import org.monte.media.avi.AVIReader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;


/*__
 * @author manu
 */
public class TextureImg extends ITexture {

    private StructureMatrix<ECBufferedImage> image = new StructureMatrix<>(0, ECBufferedImage.class);

    private String nom = "texture";

    private String file = "image.png";

    //private AVIReader reader;
    private int track = 0;
    private File avifile = null;
    private int transparent = Color.TRANSLUCENT;

    public TextureImg() {

    }
    public TextureImg(ECBufferedImage bi) {
        this.image.setElem(bi);
    }

    @Override
    public void iterate() throws EOFVideoException {

    }

    @Override
    public int getColorAt(double rx, double ry) {
        Point2D trans = getCoord(rx, ry);
        return couleur(trans.x, trans.y);
    }

    protected int couleur(double rx, double ry) {
        int x = (int) (rx * image.getElem().getWidth());
        int y = (int) (ry * image.getElem().getHeight());
        if (x < 0) {
            x = 0;
        }
        if (y < 0) {
            y = 0;
        }
        if (x >= image.getElem().getWidth()) {
            x = image.getElem().getWidth() - 1;
        }
        if (y >= image.getElem().getHeight()) {
            y = image.getElem().getHeight() - 1;
        }


        int c = image != null ? image
                .getElem().getRGB(x, y)
                :
                transparent;
        if(c==transparent)
            Logger.getAnonymousLogger().log(Level.INFO, "Transparent");
        return c;
    }


    public ECBufferedImage getImage() {
        return image.getElem();
    }

    public void setImage(ECBufferedImage image) {
        this.image.setElem(image);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }


    public void setTransparent(Color tr) {
        this.transparent = tr.getRGB();
    }

    public void timeNext() {
    }

    public void timeNext(long milli) {
    }

    @Override
    public StructureMatrix getDeclaredProperty(String name) {
        return image;
    }

    @Override
    public MatrixPropertiesObject copy() throws CopyRepresentableError, IllegalAccessException, InstantiationException {
        return null;
    }


    public String toString() {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image.getElem(), "jpg", bos);
            byte[] imageBytes = bos.toByteArray();
    
            Base64.Encoder encoder =Base64.getEncoder();
            imageString = encoder.encodeToString(imageBytes);

            bos.close();
        } catch (Exception ex) {}

        String t  = "textureImg( filename:"+getFile()+"\n\tdata : { "+imageString+" } \n)";
        return t;
    }

}
