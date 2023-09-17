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

package one.empty3.library.core.nurbs;

import one.empty3.library.Point3D;
import one.empty3.library.StructureMatrix;
import one.empty3.library.core.raytracer.tree.AlgebraicFormulaSyntaxException;
import one.empty3.library.core.raytracer.tree.AlgebricTree;
import one.empty3.library.core.raytracer.tree.TreeNodeEvalException;

import java.util.HashMap;

public class FunctionCurve extends ParametricCurve {
    private StructureMatrix<String> x = new StructureMatrix<>(0, String.class);
    private StructureMatrix<String> y = new StructureMatrix<>(0, String.class);
    private StructureMatrix<String> z = new StructureMatrix<>(0, String.class);
    private AlgebricTree treeX;
    private AlgebricTree treeY;
    private AlgebricTree treeZ;
    final HashMap<String, Double> hashMap = new HashMap<>(2);
    private boolean drawable;

    public FunctionCurve() throws AlgebraicFormulaSyntaxException {
        super();
        x.setElem("0.0");
        y.setElem("0.0");
        z.setElem("0.0");
        recomputeTrees();
    }

    FunctionCurve(String xEqFuv, String yEqFuv, String zEqFuv) throws AlgebraicFormulaSyntaxException {
        this();
        hashMap.put("u", 0d);
        hashMap.put("v", 0d);

        this.x.setElem(xEqFuv);
        this.y.setElem(yEqFuv);
        this.z.setElem(zEqFuv);




        treeX = new AlgebricTree(xEqFuv);
        treeX.getParametersValues().putAll(hashMap);
        treeX.construct();
        treeY = new AlgebricTree(yEqFuv);
        treeY.getParametersValues().putAll(hashMap);
        treeY.construct();
        treeZ = new AlgebricTree(zEqFuv);
        treeZ.getParametersValues().putAll(hashMap);
        treeZ.construct();
        setDrawable(true);
    }


    private void recomputeTrees() {
        try {
            treeX = new AlgebricTree(x.getElem());
            treeX.setParametersValues(hashMap);
            treeX.construct();
            treeY = new AlgebricTree(y.getElem());
            treeY.setParametersValues(hashMap);
            treeY.construct();
            treeZ = new AlgebricTree(z.getElem());
            treeZ.setParametersValues(hashMap);
            treeZ.construct();
            setDrawable(true);
            declareProperties();
        } catch (AlgebraicFormulaSyntaxException e) {
            setDrawable(false);
            e.printStackTrace();
        }
    }

    public String getX() {
        return x.getElem();
    }

    public void setX(String x) {
        this.x.setElem(x);
        recomputeTrees();
    }

    public String getY() {
        return y.getElem();
    }

    public void setY(String y) {
        this.y.setElem(y);
        recomputeTrees();
    }

    public String getZ() {
        return z.getElem();
    }

    public void setZ(String z) {
        this.z.setElem(z);
        recomputeTrees();
    }

    public Point3D calculerPoint3D(double u) {
        try {
            hashMap.put("u", u);
            double evalX = treeX.eval();
            double evalY = treeY.eval();
            double evalZ = treeZ.eval();
            return new Point3D(evalX, evalY, evalZ);
        } catch (TreeNodeEvalException | AlgebraicFormulaSyntaxException | NullPointerException exceptione) {
            exceptione.printStackTrace();
        }

        return null;
    }

    public void setDrawable(boolean drawable) {
        this.drawable = drawable;
    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("x/function [x = f(u)]", x);
        getDeclaredDataStructure().put("y/function (y = f(u)]", y);
        getDeclaredDataStructure().put("z/function (z = f(u)]", z);
    }

}