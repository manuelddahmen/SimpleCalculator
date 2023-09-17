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
public class TreeNodeOperator extends TreeNodeValue
{

    public TreeNodeOperator(TreeNode parent, Object[] values, VariableTreeNodeType type) {
        super(parent, values);
    }

    @Override
    public Double eval() throws TreeNodeEvalException {
        final Object v1 = getChildren().get(0);
        final Object v2 = getChildren().get(1);

        if(v1 instanceof Double && v2 instanceof Double)
            return Math.exp(Math.log((Double) v1) * (Double) v2);
        else
            throw new TreeNodeEvalException("Not valid");
    }
}
