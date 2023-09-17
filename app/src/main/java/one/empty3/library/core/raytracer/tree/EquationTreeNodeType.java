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

package one.empty3.library.core.raytracer.tree;

/*__
 * Created by manuel on 25-12-16.
 */
public class EquationTreeNodeType extends TreeNodeType {
    public final String E1EQE2 = "=";
    public final String E1NTEQE2 = "<>";
    public final String E1EGTE2 = ">";
    public final String E1EGTQE2 = ">=";
    public final String E1ELTE2 = "<=";
    public final String E1LTE2 = "<";

    public EquationTreeNodeType(double sign1) {
        super(sign1);
    }

    @Override
    public Double eval() {
        return null;
    }
}
