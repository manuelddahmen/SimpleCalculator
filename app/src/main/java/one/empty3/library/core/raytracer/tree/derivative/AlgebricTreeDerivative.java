/*
 * Copyright (c) 2022-2023. Manuel Daniel Dahmen
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

package one.empty3.library.core.raytracer.tree.derivative;

import one.empty3.library.core.raytracer.tree.*;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class AlgebricTreeDerivative {

    public TreeNode getDerivative(String variable, TreeNode currentTreeNode,
                                  TreeNode derivate) {

        Class<? extends TreeNodeType> type = currentTreeNode.getType().getClass();


        if (VariableTreeNodeType.class.equals(type)) {
            TreeNodeDouble treeNodeDouble = new TreeNodeDouble(derivate, new Object[]{}, new DoubleTreeNodeType());
            if (currentTreeNode.getExpressionString().equals(variable)) {
                treeNodeDouble.getType().setValues(new Object[]{1.0});
            } else {
                treeNodeDouble.getType().setValues(new Object[]{0.0});
            }
            return treeNodeDouble;
        } else if (TermTreeNodeType.class.equals(type)) {

            TermTreeNodeType termTreeNodeType = new TermTreeNodeType(+1.0);

            TreeNode treeNode = new TreeNode(derivate, new Object[]{currentTreeNode.getExpressionString()},
                    termTreeNodeType);

            currentTreeNode.getChildren().forEach(tn -> {
                derivate.getChildren().add(getDerivative(variable, tn, derivate));
            });
            return treeNode;
        } else if (FactorTreeNodeType.class.equals(type)) {

            TermTreeNodeType termTreeNodeType = new TermTreeNodeType(+1.0);

            for (int i = 0; i < currentTreeNode.getChildren().size(); i++) {

                TreeNode treeNode = new TreeNode(derivate, new Object[]{currentTreeNode.getExpressionString()},
                        termTreeNodeType);
                AtomicInteger j= new AtomicInteger();
                int finalI = i;
                currentTreeNode.getChildren().forEach(tn -> {
                    TreeNode treeNode1;
                    if(finalI ==j.get()) {
                        treeNode1 = getDerivative(variable, tn, treeNode);
                    } else {
                        treeNode1 = new TreeNode(tn, new Object[]{currentTreeNode.getExpressionString()},
                                new FactorTreeNodeType(tn.getType().getSign1()));
                    }
                    derivate.getChildren().add(treeNode1);
                    j.getAndIncrement();
                });
            }
            return derivate;

        } else if (ExponentTreeNodeType.class.equals(type)) {
            TreeNode e1 = currentTreeNode.getChildren().get(0);
            TreeNode e2 = currentTreeNode.getChildren().get(1);

            if(variable.equals(e1.getExpressionString())) {
                FactorTreeNodeType factorTreeNodeType = new FactorTreeNodeType(1.0);
                TreeNode treeNode = new TreeNode(derivate, new Object[] {derivate.getExpressionString()},
                        factorTreeNodeType);

                TreeNode treeNode1 = new TreeNode(treeNode, new Object[]{e2.getExpressionString()}, new TermTreeNodeType(1.0));
                treeNode1.getChildren().add(e2);
                TreeNodeDouble treeNodeDouble = new TreeNodeDouble(treeNode1, new Object[]{"-1.0"}
                        , new DoubleTreeNodeType());
                treeNode1.getChildren().add(treeNode1);
                treeNode1.getChildren().add(treeNodeDouble);
                treeNode.getChildren().add(treeNode1);
            }
        }

        throw new UnsupportedOperationException("Not yet implemented");
        //return algebricTree;

    }

}
