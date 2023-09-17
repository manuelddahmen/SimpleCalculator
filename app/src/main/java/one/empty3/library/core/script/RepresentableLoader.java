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

package one.empty3.library.core.script;

import one.empty3.library.Cube;
import one.empty3.library.Point3D;
import one.empty3.library.TRI;
import one.empty3.library.core.tribase.TRICylindre;
import one.empty3.library.core.tribase.TRISphere;

import java.util.HashMap;
import java.util.Map;

public class RepresentableLoader {

    public static Map<String, Class<?>> listObjectTypes() {
        Map<String, Class<?>> m = new HashMap<String, Class<?>>();

        m.put("p", Point3D.class);
        m.put("tri", TRI.class);
        m.put("sphere", TRISphere.class);
        m.put("cylindre", TRICylindre.class);
        m.put("cube", Cube.class);
        return m;

    }

    public Map<String, Object[]> classParametre(Class<Object> clazz) {

        return null;
    }

    public class ParametreObjet {

        public String nomCourt;
        public String nomComplet;
        public String caption;
        public String description;
        public Class<Object> clazz;
        public String defaultValue;
    }
}
