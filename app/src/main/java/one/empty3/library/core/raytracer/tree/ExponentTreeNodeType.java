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
 * Created by manuel on 16-12-16.
 */
public class ExponentTreeNodeType extends TreeNodeType {
    private double sign1, sign2;

    public ExponentTreeNodeType(Double d1, Double d2) {
        this.sign1 = d1;
        this.sign2 = d2;
    }


    @Override
    public Double eval() {
        return Math.pow(sign1, sign2);
    }

    @Override
    protected void instantiate(Object[] objects) {
        this.sign1 = (Double) objects[0];
        this.sign2 = (Double) objects[1];
    }


}
