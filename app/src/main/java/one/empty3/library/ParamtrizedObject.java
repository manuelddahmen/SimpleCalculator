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

import java.util.HashMap;
import java.util.Set;

public abstract class ParamtrizedObject {

    private HashMap<String, Object> parametres = new HashMap<String, Object>();

    public Object getParametre(String name) {
        return parametres.get(name);
    }
    /*
     * 	public abstract List<String> parameterNames();
 
     public abstract Class<?> parameterClass(String name);
     public abstract List<Object> parameterValues(String name);
     public abstract List<Object> setParameterValue(Object value);
     public abstract List<Object> setParameterValues(List<Object> value);
     public abstract List<Object> getParameterValue(String name);
     public abstract List<Object> getParameterValues(String name);
     */

    public Set<String> getParametres() {
        return parametres.keySet();
    }

    public void setParametre(String name, Object value) {
        parametres.put(name, value);
    }
}
