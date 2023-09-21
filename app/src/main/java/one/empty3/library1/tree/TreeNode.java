/*
 * Copyright (c) 2023.
 *
 *
 *  Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

/*
 *  This file is part of Empty3.
 *
 *     Empty3 is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Empty3 is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Empty3.  If not, see <https://www.gnu.org/licenses/>. 2
 */

/*
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>
 */

package one.empty3.library1.tree;

import androidx.annotation.NonNull;

import java.util.ArrayList;


import one.empty3.library.StructureMatrix;

/*__
 * Created by Manuel Dahmen on 15-12-16.
 */
public class TreeNode {
    protected Object[] objects;
    protected TreeNodeType type = null;
    private TreeNodeValue value;
    protected ArrayList<TreeNode> children = new ArrayList<TreeNode>();
    protected TreeNode parent;
    protected String expressionString;

    public TreeNode(String expStr) {
        this.parent = null;
        if (expStr.trim().isEmpty())
            expressionString = "0.0";
        this.expressionString = expStr;
    }

    /***
     * Base constructor for all TreeNodes
     * @param src parent (!=null)
     * @param objects [0] = expressionString
     * @param clazz type associated to this TreeNode
     */
    public TreeNode(TreeNode src, Object[] objects, TreeNodeType clazz) {
        this.parent = src;
        this.objects = objects;
        clazz.instantiate(objects);
        this.type = clazz;
        expressionString = (String) objects[0];
    }

    public Object getValue() {
        return value;
    }

    public void setValue(TreeNodeValue value) {
        this.value = value;
    }


    public StructureMatrix<Double> eval() throws TreeNodeEvalException, AlgebraicFormulaSyntaxException {
        /*if(this instanceof TreeNode) {
            return Double.parseDouble(((TreeNode) this).expressionString);
        }*/
        TreeNodeType cType = (getChildren().size() == 0) ? type : getChildren().get(0).type;

        StructureMatrix<Double> evalRes = null;
        if (cType instanceof VectorTreeNodeType) {
            evalRes = new StructureMatrix<>(1, Double.class);
        } else {
            evalRes = new StructureMatrix<>(0, Double.class);
            evalRes.setElem(0.0);
        }
        if (cType instanceof IdentTreeNodeType) {
            System.out.println("cType Ident=" + getChildren().size());
            System.out.println("cType Ident=" + getChildren().get(0).eval());
            return getChildren().get(0).eval();
        } else if (cType instanceof EquationTreeNodeType) {
            switch (getChildren().get(0).eval().getDim()) {
                case 0:
                    return evalRes.setElem(getChildren().get(0).eval().getElem());
                case 1:
                    StructureMatrix<Double> eval = getChildren().get(0).eval();
                    for (int i = 0; i < eval.data1d.size(); i++)
                        evalRes.setElem(eval.getElem(i), i);
                    return evalRes;
                default:
                    break;
            }
            switch (getChildren().get(1).eval().getDim()) {
                case 0:
                    evalRes.setElem(getChildren().get(1).eval().getElem());
                    break;
                case 1:
                    int size = evalRes.data1d.size();
                    StructureMatrix<Double> eval = getChildren().get(1).eval();
                    for (int i = 0; i < eval.data1d.size(); i++)
                        evalRes.setElem(eval.getElem(i), i + size);
                    break;
                default:
                    break;
            }
            return evalRes;//TO REVIEW
        } else if (cType instanceof DoubleTreeNodeType) {
            return cType.eval();
        } else if (cType instanceof VariableTreeNodeType) {
            return getChildren().get(0).eval();//cType.eval();
        } else if (cType instanceof PowerTreeNodeType) {
            return evalRes.setElem(Math.pow((Double) getChildren().get(0).eval().getElem(), (Double) getChildren().get(1).eval().getElem()));
        } else if (cType instanceof FactorTreeNodeType) {
            if (getChildren().size() == 1) {
                evalRes = getChildren().get(0).eval();///SIGN
                if (evalRes.getDim() == 1) {
                    return evalRes;
                } else if (evalRes.getDim() == 0) {
                    double v = ((Double) getChildren().get(0).eval().getElem()) * getChildren().get(0).type.getSign1();
                    return evalRes.setElem(v);
                }
                return evalRes;
            } else if (getChildren().size() > 1) {
                double dot = 1.0;
                evalRes.setElem(1.0);
                for (int i = 0; i < getChildren().size(); i++) {
                    TreeNode treeNode = getChildren().get(i);
                    StructureMatrix<Double> treeNodeEval = treeNode.eval();
                    double op1;

                    if (treeNodeEval.getDim() == 1) {
                        for (int j = 0; j < treeNodeEval.data1d.size(); j++)
                            if (treeNode.type instanceof FactorTreeNodeType) {
                                op1 = ((FactorTreeNodeType) treeNode.type).getSignFactor();
                                if (op1 == 1) {
                                    dot = ((Double) treeNode.eval().getElem()) * evalRes.getElem(j);
                                    evalRes.setElem(dot, j);
                                } else {

                                    dot = 1. / ((Double) (Double) treeNode.eval().getElem()) * evalRes.getElem(j);///treeNode.type.getSign1()) *
                                    evalRes.setElem(dot, j);
                                }
                            }
                    }
                    if (treeNodeEval.getDim() == 0) {
                        if (treeNode.type instanceof FactorTreeNodeType) {
                            op1 = ((FactorTreeNodeType) treeNode.type).getSignFactor();
                            if (op1 == 1) {
                                dot = ((Double) treeNodeEval.getElem());
                                evalRes.setElem(dot * evalRes.getElem());
                            } else {
                                dot = 1. / ((Double) treeNodeEval.getElem());///treeNode.type.getSign1()) *
                                evalRes.setElem(dot * evalRes.getElem());
                            }
                        }
                    }
                }
            }
            return evalRes;
        } else if (cType instanceof TermTreeNodeType) {
            if (getChildren().size() == 1) {
                return evalRes.setElem(((Double) getChildren().get(0).eval().getElem()) * getChildren().get(0).type.getSign1());
            }
            double sum = 0.0;
            for (int i = 0; i < getChildren().size(); i++) {
                TreeNode treeNode1 = getChildren().get(i);
                double op1 = treeNode1.type.getSign1();
                StructureMatrix<Double> eval = treeNode1.eval();
                if (eval.getDim() == 1) {
                    sum = op1 * (Double) treeNode1.eval().getElem();
                    for (int j = 0; j < eval.data1d.size(); j++) {
                        evalRes.setElem(sum +
                                (evalRes.getElem(j) == null ? 0.0 : evalRes.getElem(j)), j);
                        //sum += op1 * (Double) treeNode1.eval().getElem();
                    }
                } else if (eval.getDim() == 0) {
                    sum = op1 * (Double) treeNode1.eval().getElem();
                    evalRes.setElem(sum + (evalRes.getElem() == null ? 0.0 : evalRes.getElem()));
                }
            }
            return evalRes;
        } else if (cType instanceof TreeTreeNodeType) {
            if (!getChildren().isEmpty()) {
                if (!getChildren().get(0).getChildren().isEmpty() &&
                        getChildren().get(0).getChildren().get(0).type instanceof VectorTreeNodeType) {
                    evalRes = new StructureMatrix<>(1, Double.class);
                    switch (evalRes.getDim()) {
                        case 0:
                            for (int i = 0; i < getChildren().get(0).getChildren().size(); i++) {
                                StructureMatrix<Double> eval = getChildren().get(0).getChildren().get(i).eval();
                                evalRes.setElem(eval.getElem(i), i);
                            }
                            break;
                        case 1:
                            int k=0;
                            for (int i = 0; i < getChildren().get(0).getChildren().size(); i++) {
                                StructureMatrix<Double> eval = getChildren().get(0).getChildren().get(i).eval();
                                if(eval.getDim()==1) {
                                    for (int j = 0; j < eval.data1d.size(); j++) {
                                        evalRes.setElem(eval.getElem(j), k++);
                                    }
                                } else if(eval.getDim()==0){
                                    evalRes.setElem(eval.getElem(), k++);
                                }
                            }
                        break;
                    }
                    return evalRes;
                } else {
                    StructureMatrix<Double> eval = getChildren().get(0).eval();
                    if (eval.getDim() == 1) {
                        for (int i = 0; i < eval.data1d.size(); i++) {
                            evalRes.setElem(eval.getElem(i), i);
                        }
                    } else if (eval.getDim() == 0) {
                        evalRes.setElem(eval.getElem());
                    }
                }
                return evalRes;
            }
            return null;
        } else if (cType instanceof SignTreeNodeType) {
            double s1 = ((SignTreeNodeType) cType).getSign();
            StructureMatrix<Double> eval;
            if (!getChildren().isEmpty()) {
                eval = getChildren().get(0).eval();
            } else {
                eval = evalRes.setElem(0.0);
            }
            if (eval.getDim() == 1) {
                for (int i = 0; i < eval.data1d.size(); i++) {
                    evalRes.setElem(eval.getElem(i) * s1, i);
                }
                return evalRes;
            } else if (eval.getDim() == 0) {
                evalRes = evalRes.setElem(s1 * eval.getElem());
                return evalRes;
            }
            return evalRes;
        } else if (cType instanceof VectorTreeNodeType) {
            evalRes = new StructureMatrix<>(1, Double.class);
            for (int i = 0; i < getChildren().get(0).getChildren().size(); i++) {
                StructureMatrix<Double> eval = getChildren().get(0).getChildren().get(i).eval();///!!!
                evalRes.setElem(eval.getElem(i), i);
            }
            return evalRes;
        }

        StructureMatrix<Double> eval = new StructureMatrix<>(0, Double.class);

        if (type != null) {
            eval = type.eval();
        } else if (!getChildren().isEmpty()) {
            eval = getChildren().get(0).eval();
        }

        return eval == null ? evalRes.setElem(0.0) : eval;

    }

    public ArrayList<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<TreeNode> children) {
        this.children = children;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public String getExpressionString() {
        return expressionString;
    }

    public void setExpressionString(String expressionString) {
        this.expressionString = expressionString;
    }


    @NonNull
    public String toString() {
        String s = "TreeNode " + this.getClass().getSimpleName() +
                "\nExpression string: " + expressionString +
                (type == null ? "Type null" :
                        "Type: " + type.getClass() + "\n " + type.toString()) +
                "\nChildren: " + getChildren().size() + "\n";
        int i = 0;
        for (TreeNode t :
                getChildren()) {
            s += "Child (" + i++ + ") : " + t.toString() + "\n";
        }
        return s + "\n";
    }
}
