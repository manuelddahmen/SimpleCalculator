/*
 * Copyright (c) 2022-2023. Manuel Daniel Dahmen
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

public class TextureMorphing extends ITexture {
    private final ITexture texture2;
    private final ITexture texture1;
    private final LumiereIdent ident;
    private double t;

    public TextureMorphing(ITexture imageRead1, ITexture imageRead2) {
        super();
        this.texture1 = imageRead1;
        this.texture2 = imageRead2;
        ident = new LumiereIdent();
    }

    public void setT(double t) {
        this.t = t;
    }
    public int getColorAt(double x, double y) {
        double r = t;
        int rgb1 = texture1.getColorAt(x, y);
        int rgb2 = texture2.getColorAt(x, y);
        double [] dRgb1 = Lumiere.getDoubles(rgb1);
        double [] dRgb2 = Lumiere.getDoubles(rgb2);
        double[] d = new double[dRgb1.length];
        for(int i=0; i<dRgb1.length; i++) {
            d[i] = (1-t)*dRgb1[i]+(t)*dRgb2[i];
        }
        return Lumiere.getInt(d);
    }

    @Override
    public MatrixPropertiesObject copy() throws CopyRepresentableError, IllegalAccessException, InstantiationException {
        ITexture copy1 = (ITexture) texture1.copy();
        ITexture copy2 = (ITexture) texture2.copy();
        TextureMorphing morphing = new TextureMorphing(copy1, copy2);
        return morphing;
    }

    @Override
    public void timeNext(long milli) {
        super.timeNext(milli);
        this.texture1.timeNext(milli);
        this.texture2.timeNext(milli);
    }
}
