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

import one.empty3.library.StructureMatrix;
import one.empty3.library.core.raytracer.tree.AlgebraicFormulaSyntaxException;
import one.empty3.library.core.raytracer.tree.AlgebricTree;
import one.empty3.library.core.raytracer.tree.TreeNodeEvalException;

/*__
 * Created by manue on 28-05-19.
 */
public class FctXY extends Fct1D_1D {
    private StructureMatrix<String> formulaX = new StructureMatrix<>(0, String.class);
    private AlgebricTree treeX;

    public FctXY()
    {

        formulaX.setElem("10.0");
        setFormulaX(formulaX.getElem());

    }

    public void setFormulaX(String formulaX)
    {
        this.formulaX.setElem(formulaX);

        try {
            treeX = new AlgebricTree(formulaX);
            treeX.getParametersValues().put("x", 0.0);
            treeX.construct();
        } catch (AlgebraicFormulaSyntaxException e) {
            e.printStackTrace();
        }
    }

    public String getFormulaX() {
        return formulaX.getElem();
    }

    public double result(double input)
    {
        treeX.getParametersValues().put("x", input);
        try {
            return treeX.eval();
        } catch (TreeNodeEvalException | AlgebraicFormulaSyntaxException e) {
            e.printStackTrace();
        }
        return Double.NaN;
    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("formulaX/fonction f(x)", formulaX);
    }

    @Override
    public String toString() {
        return "fctXY( \""+formulaX+"\" )\n";
    }
}
