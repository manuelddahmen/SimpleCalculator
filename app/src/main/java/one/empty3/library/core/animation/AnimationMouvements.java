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

package one.empty3.library.core.animation;

import one.empty3.library.*;
import one.empty3.tests.Animation;

public class AnimationMouvements {

    private Animation animation;
    private Fonction fonction;
    private Representable representable;

    public AnimationMouvements(Representable representable, FonctionTemps fp) {
        this.representable = representable;
        fonction = fp;
    }

    protected void setFonctionModele(FonctionModele fm) {
        this.fonction = fm;
    }

    protected void setFonctionPosition(FonctionTemps fp) {
        this.fonction = fp;
    }

    public void updateObject(AnimationTime time) {

    }
}
