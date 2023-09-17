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
public class FactorTreeNodeType extends TreeNodeType {
    protected double signFactor = 1; // 1=*

    public FactorTreeNodeType(double signFactor) {
        super(1);
        this.signFactor = signFactor;
    }


    public Double eval() {
        return null;
    }

    public double getSignFactor() {
        return signFactor;
    }

    @Override
    public double getSign1() {
        return super.getSign1();
    }

    public String toString() {
        return super.toString() + "\nSign:" + sign1;
    }
}
