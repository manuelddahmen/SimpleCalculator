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

package one.empty3.library.core.Engine;

import one.empty3.library.*;

import java.lang.reflect.Method;

/*__
 * @author Manuel Dahmen _manuel.dahmen@gmx.com_
 */
public class RegisterObject {
    public class Entry {
        private Class<Representable> class3d;
        private boolean positionnable;
        private boolean rotatable;
        private boolean moveable;
        private boolean sizable;
        private Method positionnableSpecialMethod;
        private Method rotatableSpecialMethod;
        private Method moveableSpecialMethod;
        private Method sizableSpecialMethod;

        public Entry(Class<Representable> class3d, boolean positionnable, boolean rotatable, boolean moveable, boolean sizable, Method positionnableSpecialMethod, Method rotatableSpecialMethod, Method moveableSpecialMethod, Method sizableSpecialMethod) {
            this.class3d = class3d;
            this.positionnable = positionnable;
            this.rotatable = rotatable;
            this.moveable = moveable;
            this.sizable = sizable;
            this.positionnableSpecialMethod = positionnableSpecialMethod;
            this.rotatableSpecialMethod = rotatableSpecialMethod;
            this.moveableSpecialMethod = moveableSpecialMethod;
            this.sizableSpecialMethod = sizableSpecialMethod;
        }


        public Class<Representable> getClass3d() {
            return class3d;
        }

        public boolean isPositionnable() {
            return positionnable;
        }

        public boolean isRotatable() {
            return rotatable;
        }

        public boolean isMoveable() {
            return moveable;
        }

        public Method getPositionnableSpecialMethod() {
            return positionnableSpecialMethod;
        }

        public Method getRotatableSpecialMethod() {
            return rotatableSpecialMethod;
        }

        public Method getMoveableSpecialMethod() {
            return moveableSpecialMethod;
        }

        public Method getSizableSpecialMethod() {
            return sizableSpecialMethod;
        }


    }


}
