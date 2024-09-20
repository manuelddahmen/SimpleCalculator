package one.empty3.library1.tree;
/*
 *
 *  * Copyright (c) 2024. Manuel Daniel Dahmen
 *  *
 *  *
 *  *    Copyright 2024 Manuel Daniel Dahmen
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 *
 *
 */


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import one.empty3.library.StructureMatrix;


public class TreeTreeNodeVector extends TreeNode {
    private AlgebraicTree tree;
    private String functionName;

    public TreeTreeNodeVector(TreeNode t, Object[] objects, TreeNodeType type) {
        super(t, objects, type);
        tree = new AlgebraicTree((String) objects[0], (Map<String, Double>) objects[1]);
        try {
            tree.construct();
            if (objects[2] instanceof String && !((String) objects[2]).isEmpty()) {
                String call = (String) objects[2];
                if (call.length() >= 1) {
                    functionName = call;
                    //throw new NotImplementedException("Custom functions not implemented yet");
                }
            }
        } catch (AlgebraicFormulaSyntaxException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public StructureMatrix<Double> eval() throws TreeNodeEvalException, AlgebraicFormulaSyntaxException {
        StructureMatrix<Double> res = new StructureMatrix<>(1, Double.class);
        double r = 0.0;
        //System.err.println("TreeTreeNodeVector:eval()");
        StructureMatrix<Double> eval = tree.eval();
        if (eval.getDim() == 1) {
            Double[] array = eval.getData1d().toArray(new Double[]{0.0});
            r = Functions.search(functionName, array);
        } else if (eval.getDim() == 0) {
            r = eval.getElem();
        }
        res.setElem(r, 0);
        return res;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

}
