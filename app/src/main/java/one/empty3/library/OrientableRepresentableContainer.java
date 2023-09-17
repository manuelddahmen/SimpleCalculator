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

import one.empty3.library.core.nurbs.ParametricSurface;

import java.util.ArrayList;
import java.util.List;

public class OrientableRepresentableContainer extends RepresentableConteneur {
    private final Representable ref;

    public OrientableRepresentableContainer(Representable ref) {
        this.ref = ref;
    }

    @Override
    public synchronized void add(Representable r) {
        super.add(r);
    }

    public Representable getRef() {
        return ref;
    }
    @Override
    public List<Representable> getListRepresentable() {
        List<Representable> listA = super.getListRepresentable();
        List<Representable> listB = new ArrayList<>();
        for (Representable A : listA) {
            if (A instanceof ParametricSurface) {
                Representable B;
                B = new OrientableParametricSurface((ParametricSurface) A);
                listB.add(B);
            } else {
                Representable B;
                B = (A);
                listB.add(B);
            }
        }
        return listB;

    }

}
