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
package one.empty3.library;

import java.awt.*;

/*__
 * @author MANUEL DAHMEN
 *         <p>
 *         dev
 *         <p>
 *         17 oct. 2011
 */
public abstract class PGeneratorZ extends Representable {

    public abstract void dessine(ZBuffer z);

    public void dessineStructure(ZBuffer zBufferImpl) {
        // TODO Auto-generated method stub

    }

    public abstract void generate(ZBuffer z);

    public int hauteurImage(ZBuffer z) {
        return z.resY();
    }

    public int largeurImage(ZBuffer z) {
        return z.resX();
    }

    public Point point(ZBuffer z, Point3D p) {
        return z.camera().coordonneesPoint2D(p, ((ZBufferImpl)z));
    }
}
