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

/*__
 * *
 * rotation, translation, scaling
 * <p>
 * Translation et rotation d'objets. (OBJET) @ ((TRANSLATION: V3D) (ROTATION :
 * P3D, M3D)) Homothétie (OBJET) * (CENTRE: P3D, FACTEUR: DOUBLE) L'ordre a de
 * l'importance.
 *
 * @author Manuel
 * @ (T) @ (R) * (H)
 */
public class MODObjet {

    private MODRotation rotation;
    private MODTranslation translation;
    private MODHomothetie homothetie;

    public MODHomothetie homothetie() {
        return homothetie;
    }
    /*
     public Representable place(Representable r)
     {
     return r.place(this);
     }
     * 
     */

    public void modificateurs(MODRotation r, MODTranslation t, MODHomothetie h) {
        this.rotation = r;
        this.translation = t;
        this.homothetie = h;
    }

    public MODRotation rotation() {
        return rotation;
    }

    public MODTranslation translation() {
        return translation;
    }
}
