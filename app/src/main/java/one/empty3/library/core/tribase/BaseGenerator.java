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
package one.empty3.library.core.tribase;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map.Entry;

@Deprecated
public abstract class BaseGenerator {

    protected BufferedImage buffer;
    protected Component comp;
    protected BufferedImage image;
    private int x;
    private int y;

    public BaseGenerator(int dx, int dy, Component c) {
        x = dx;
        y = dy;
        comp = c;
    }

    public void computeFrame() {
        // TODO Auto-generated method stub

    }

    public Image getBUFFER() {
        return buffer;
    }

    public Graphics getGraphicsDisque() {
        return image.getGraphics();
    }

    public Graphics getGraphicsEcran() {
        return comp.getGraphics();
    }

    public void initFrame() {
        // TODO Auto-generated method stub

    }

    public void paint() {
        comp.getGraphics().drawImage(buffer, 0, 0, x, y, null);
        image.getGraphics().drawImage(buffer, 0, 0, x, y, null);
    }

    public void renew() {
        image = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
    }

    public void save() {
    }

    public void setConfig(Config params) {
        for (Entry<String, Object> entry : params.entrySet()) {
            if (entry.getKey().startsWith("global") | entry.getKey().startsWith(this.getClass().getSimpleName())) {
                setField(entry.getKey().substring(
                        entry.getKey().indexOf(".") + 1,
                        entry.getKey().indexOf("=")),
                        entry.getKey().substring(
                                entry.getKey().indexOf("="))
                );
            }
        }

    }

    private void setField(String key, String stringvalue) {

    }

    public void setParams(Params params) {
        // TODO Auto-generated method stub

    }

    public void showFrame() {
        // TODO Auto-generated method stub

    }
}
