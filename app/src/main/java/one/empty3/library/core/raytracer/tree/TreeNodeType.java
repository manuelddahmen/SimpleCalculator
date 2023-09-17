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
 * Created by Manuel Dahmen on 15-12-16.
 */
public abstract class TreeNodeType {
    protected Object[] values;
    protected double sign1 = 1.0;
    private TreeNodeType d;
    private TreeNodeType tnt;
    public TreeNodeType() {
    }

    public TreeNodeType(double sign1) {
        super();
        this.sign1 = sign1;
    }

    public double getSign1() {
        return sign1;
    }

    public abstract Double eval();

    public void setValues(Object[] values) {
        this.values = values;
    }

    protected void instantiate(Object[] objects) {

        this.tnt = this;


    }

    public String toString() {
        String s = "Type : " + this.getClass() + " \n";
        if (values != null) {
            int i = 0;
            for (Object o : values) {
                s += (o!=null?o.toString():"") + " (+) ";
            }
        }
        return s;
    }

}
