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

package one.empty3.library;

import java.awt.*;

/*__
 * @author Manuel Dahmen
 */
public class ColorTexture extends ITexture {

    private StructureMatrix<Color> color = new StructureMatrix<Color>(0, Color.class);

    public ColorTexture() {
        color.setElem(Color.BLACK);
    }

    public ColorTexture(Color c) {
        this();
        if (c != null) {
            color.setElem(c);
        }
    }

    public ColorTexture(int colorAt) {
        this(Lumiere.getColor(colorAt));
    }

    public Color color() {
        return color.getElem();
    }

    public void color(Color c) {
        color.setElem(c);
    }

    @Override
    public void iterate() throws EOFVideoException {

    }

    public int getColorAt(double x, double y) {
        return color.getElem().getRGB();
    }

    public void timeNext() {
    }

    public void timeNext(long milli) {
    }

    @Override
    public StructureMatrix getDeclaredProperty(String name) {
        return color;
    }

    @Override
    public MatrixPropertiesObject copy() throws CopyRepresentableError, IllegalAccessException, InstantiationException {
        return null;
    }

    /*__
     * Quadrilatère numQuadX = 1, numQuadY = 1, coordArr, y 3----2 ^2y |\ | | 4 |
     * 0--\-1 1 -> 2x
     *
     * @param numQuadX nombre de maillage en coordArr
     * @param numQuadY nombre de maillage en y
     * @param x        valeur de coordArr
     * @param y        valeur de y
     * @return
     */
    public Color getMaillageTexturedColor(int numQuadX, int numQuadY, double x,
                                          double y) {
        return color.getElem();
    }

    public StructureMatrix<Color> getColor() {
        return color;
    }

    public void setColor(StructureMatrix<Color> color) {
        this.color = color;
    }
}
